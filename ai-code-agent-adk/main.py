import os
import json
from google.adk.run import run_server
from agent import root_agent # Import your defined agent

# This will run the ADK server, exposing your agent via HTTP.
# ADK's run_server function handles the FastAPI app creation,
# endpoint exposure (e.g., /run), and session management.

if __name__ == "__main__":
    # The default port for Cloud Run is 8080.
    # ADK's run_server will pick this up from the environment.
    # You can also explicitly set it: port = int(os.environ.get("PORT", 8080))
    
    # You can configure the session service if you want persistent sessions
    # beyond in-memory, e.g., using Firestore or Cloud SQL.
    # For now, it will use an in-memory session service by default.
    
    # The run_server function starts a web server (FastAPI) that exposes
    # the agent. This is what Cloud Run expects.
    run_server(root_agent)