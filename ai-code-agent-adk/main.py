# main.py
import os
from google.adk.cli.fast_api import get_fast_api_app
from agent import root_agent
import uvicorn # You might need this if you run locally for testing

# Create the FastAPI app using ADK's helper
# This 'app' object is what Gunicorn/Uvicorn needs to serve
app = get_fast_api_app(
    agent_dir=".", # Points to the current directory where agent.py resides
    # You might also need to configure session_db_url or other ADK options here if you're using them
    # For example: session_db_url=os.environ.get("SESSION_DB_URI")
)

# This part is for local development testing, Cloud Run won't use it directly
if __name__ == "__main__":
    port = int(os.environ.get("PORT", 8080))
    uvicorn.run(app, host="0.0.0.0", port=port, reload=False) # reload=True for dev