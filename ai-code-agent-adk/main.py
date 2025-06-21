# main.py
import os
from google.adk.run import run_server
from agent import root_agent

if __name__ == "__main__":
    run_server(root_agent)