import os
import json
import requests
import base64
from google.cloud import secretmanager
from google.cloud import build_v1
from google.adk.tools import FunctionTool # Import FunctionTool

# --- Configuration ---
PROJECT_ID = os.environ.get('GOOGLE_CLOUD_PROJECT', 'weighty-diorama-463514-a1')
GITHUB_REPO_OWNER = 'RamziBrini'
GITHUB_REPO_NAME = 'gs-spring-boot'
GITHUB_PAT_SECRET_NAME = f'projects/{PROJECT_ID}/secrets/github-pat-for-ai-agent/versions/latest'
CLOUD_RUN_SERVICE_ACCOUNT = f'spring-app-runtime@{PROJECT_ID}.iam.gserviceaccount.com'

# --- Initialize Clients ---
secret_manager_client = secretmanager.SecretManagerServiceClient()
cloud_build_client = build_v1.CloudBuildClient()

# --- Helper Functions (Tools for the Agent) ---

def get_github_pat() -> str:
    """Retrieves GitHub PAT from Secret Manager."""
    try:
        response = secret_manager_client.access_secret_version(request={"name": GITHUB_PAT_SECRET_NAME})
        return response.payload.data.decode('UTF-8')
    except Exception as e:
        print(f"Error accessing GitHub PAT: {e}")
        raise

def read_github_file(file_path: str) -> str:
    """
    Reads the content of a file from the GitHub repository.
    Args:
        file_path: The path to the file within the repository (e.g., 'src/main/java/com/example/MyApplication.java').
    Returns:
        The content of the file as a string.
    """
    pat = get_github_pat()
    headers = {'Authorization': f'token {pat}'}
    url = f'https://api.github.com/repos/{GITHUB_REPO_OWNER}/{GITHUB_REPO_NAME}/contents/{file_path}'
    response = requests.get(url, headers=headers)
    response.raise_for_status()
    content_b64 = response.json().get('content')
    if content_b64:
        return base64.b64decode(content_b64).decode('utf-8')
    return ""

def commit_and_push_to_github(file_path: str, new_content: str, commit_message: str, branch: str = 'main') -> dict:
    """
    Commits changes to a file and pushes to GitHub.
    Args:
        file_path: The path to the file within the repository.
        new_content: The new content for the file.
        commit_message: The commit message.
        branch: The branch to commit to (default 'main').
    Returns:
        A dictionary with commit details including 'html_url' and 'sha'.
    """
    pat = get_github_pat()
    headers = {
        'Authorization': f'token {pat}',
        'Accept': 'application/vnd.github.v3+json'
    }
    
    # 1. Get current file SHA (required for updating)
    get_url = f'https://api.github.com/repos/{GITHUB_REPO_OWNER}/{GITHUB_REPO_NAME}/contents/{file_path}?ref={branch}'
    try:
        get_response = requests.get(get_url, headers=headers)
        get_response.raise_for_status()
        current_sha = get_response.json().get('sha')
    except requests.exceptions.RequestException as e:
        if e.response and e.response.status_code == 404:
            current_sha = None # File does not exist, so it's a new file
        else:
            raise f"Error getting file SHA: {e}"

    # 2. Update file content
    encoded_content = base64.b64encode(new_content.encode('utf-8')).decode('utf-8')

    update_url = f'https://api.github.com/repos/{GITHUB_REPO_OWNER}/{GITHUB_REPO_NAME}/contents/{file_path}'
    payload = {
        'message': commit_message,
        'content': encoded_content,
        'branch': branch
    }
    if current_sha:
        payload['sha'] = current_sha

    try:
        update_response = requests.put(update_url, headers=headers, json=payload)
        update_response.raise_for_status()
        return update_response.json()
    except requests.exceptions.RequestException as e:
        raise f"Error committing to GitHub: {e.response.text if e.response else e}"

def trigger_cloud_build_and_deploy(commit_sha: str) -> str:
    """
    Triggers a new Cloud Build based on a specific commit SHA and deploys to Cloud Run.
    Args:
        commit_sha: The SHA of the commit that triggered the build.
    Returns:
        The Cloud Build ID.
    """
    build_config = {
        'source': {
            'repoSource': {
                'projectId': PROJECT_ID,
                'repoName': GITHUB_REPO_NAME,
                'branchName': 'main', 
                'commitSha': commit_sha 
            }
        },
        'steps': [
            {
                'name': 'gcr.io/cloud-builders/docker',
                'args': ['build', '-t', f'europe-west1-docker.pkg.dev/{PROJECT_ID}/ramzi-spring-boot-repo/gs-spring-boot:{commit_sha}', '.'],
                'id': 'Build',
            },
            {
                'name': 'gcr.io/cloud-builders/docker',
                'args': ['push', f'europe-west1-docker.pkg.dev/{PROJECT_ID}/ramzi-spring-boot-repo/gs-spring-boot:{commit_sha}'],
                'id': 'Push',
            },
            {
                'name': 'gcr.io/cloud-builders/gcloud',
                'args': [
                    'run', 'deploy', 'gs-spring-boot-service',
                    '--image', f'europe-west1-docker.pkg.dev/{PROJECT_ID}/ramzi-spring-boot-repo/gs-spring-boot:{commit_sha}',
                    '--allow-unauthenticated', # Adjust based on your Cloud Run service auth
                    '--platform', 'managed',
                    '--region', 'europe-west1',
                    '--service-account', CLOUD_RUN_SERVICE_ACCOUNT
                ],
                'id': 'Deploy',
            }
        ],
        'images': [f'europe-west1-docker.pkg.dev/{PROJECT_ID}/ramzi-spring-boot-repo/gs-spring-boot:{commit_sha}'],
        'options': {
            'defaultLogsBucketBehavior': 'REGIONAL_USER_OWNED_BUCKET'
        }
    }
    
    try:
        request = build_v1.CreateBuildRequest(project_id=PROJECT_ID, build=build_config)
        operation = cloud_build_client.create_build(request=request)
        return operation.metadata.build.id
    except Exception as e:
        print(f"Error triggering Cloud Build: {e}")
        raise


# --- Define ADK Tools ---
# Important: Provide clear descriptions for the LLM to understand when to use them.
read_github_file_tool = FunctionTool(
    name="read_github_file",
    description="Reads the content of a file from the specified GitHub repository path. Useful for analyzing existing code.",
    func=read_github_file
)

commit_and_push_to_github_tool = FunctionTool(
    name="commit_and_push_to_github",
    description="Commits changes to a file in the GitHub repository and pushes them. Use this to modify or create code files. Returns commit details including the commit SHA.",
    func=commit_and_push_to_github
)

trigger_cloud_build_and_deploy_tool = FunctionTool(
    name="trigger_cloud_build_and_deploy",
    description="Triggers a new Cloud Build and deploys the application to Cloud Run based on a specific commit SHA. This is typically called after code has been committed.",
    func=trigger_cloud_build_and_deploy
)

# --- Define the Main AI Agent ---
from google.adk.agents import Agent
from google.adk.models.vertexai import Gemini
from vertexai.preview.generative_models import HarmCategory, HarmBlockThreshold

# Configure Gemini model with safety settings
gemini_model = Gemini(
    model_name="gemini-1.5-flash-001", # Or "gemini-1.5-pro-001" for more advanced reasoning
    # Configure safety settings if needed
    safety_settings={
        HarmCategory.HARM_CATEGORY_HATE_SPEECH: HarmBlockThreshold.BLOCK_NONE,
        HarmCategory.HARM_CATEGORY_DANGEROUS_CONTENT: HarmBlockThreshold.BLOCK_NONE,
        HarmCategory.HARM_CATEGORY_HARASSMENT: HarmBlockThreshold.BLOCK_NONE,
        HarmCategory.HARM_CATEGORY_SEXUALLY_EXPLICIT: HarmBlockThreshold.BLOCK_NONE,
    }
)

root_agent = Agent(
    name="CodeUnderstandingAndExtensionAgent",
    model=gemini_model,
    instruction="""
    You are a highly skilled AI agent specializing in Java Spring Boot applications hosted on Google Cloud.
    Your capabilities include:
    1.  **Understanding and Explaining Code:** When asked about code, you can read files from the 'gs-spring-boot' GitHub repository and explain their functionality, design, and purpose.
    2.  **Checking for Features:** You can analyze the code to determine if a specific feature or endpoint is implemented.
    3.  **Extending Code:** When instructed to add a new feature or endpoint, you will generate the necessary Java code, commit it to the 'gs-spring-boot' GitHub repository, and then trigger a new Cloud Build to deploy the updated application.
    4.  **Error Handling:** If you encounter an error during any step (e.g., cannot read file, commit fails, build fails), report it clearly to the user.

    **Important Guidelines:**
    * Always try to use the provided tools to achieve the user's request.
    * When explaining code, be concise but comprehensive.
    * When extending code, ensure the generated code is valid Java, follows Spring Boot conventions, and is integrated correctly into the specified file.
    * Always inform the user about the outcome of your actions, especially after code modifications and deployments. Provide the commit URL and Cloud Build ID if successful.
    * Do not make up information if you cannot find it using your tools. State that you don't know or cannot perform the request.
    * The main Spring Boot application file is typically `src/main/java/com/example/gsspringboot/GsSpringBootApplication.java`. Use this path when reading or modifying the main application.
    """,
    tools=[
        read_github_file_tool,
        commit_and_push_to_github_tool,
        trigger_cloud_build_and_deploy_tool,
    ],
)