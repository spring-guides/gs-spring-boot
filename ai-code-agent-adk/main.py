# main.py
import os
from google.adk.run import run_server
from agent import root_agent

if __name__ == "__main__":
    # ADK's run_server is designed to pick up the PORT env var
    # or default to 8080.
    run_server(root_agent)