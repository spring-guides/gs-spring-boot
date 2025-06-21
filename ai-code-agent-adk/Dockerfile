# Use the official Python base image
FROM python:3.13-slim-bookworm

# Set the working directory in the container
WORKDIR /app

# Copy requirements.txt and install dependencies
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# Copy the rest of your application code
COPY . .

# Expose the port your application listens on
ENV PORT 8080
EXPOSE 8080

# --- Set Gunicorn/Uvicorn environment variables ---
# These are still relevant as ADK might pass them through
ENV GUNICORN_TIMEOUT=600 # Increased to 10 minutes
ENV GUNICORN_WORKERS=1
ENV GUNICORN_THREADS=8

# --- This is the CRITICAL change ---
# Use Uvicorn directly, or Gunicorn with UvicornWorker,
# and tell it where to find the 'app' object.
# The `root_agent` from `agent.py` is likely where the ADK app is initialized.
# We need to expose the *actual* ASGI application object.

# Let's assume `agent.py` or `main.py` has a global variable named `app`
# that represents the ASGI application created by ADK.
# Based on common patterns, it might be something like this:
#
# In agent.py or main.py:
# from google.adk.run import App # Or similar import
# app = App(root_agent) # Or adk.create_app(root_agent) or just root_agent.app if it's an attribute
#
# If the ADK framework provides an explicit ASGI app object, that's what we need.

# Let's try the common FastAPI/Starlette pattern, which ADK might follow:
# CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "$PORT", "--workers", "1"]

# However, given your `main.py` structure, where `run_server(root_agent)` is called,
# it means `root_agent` itself (or some object it contains) *is* the application.
# If `run_server` itself starts the server, then trying to run Uvicorn/Gunicorn separately
# is a conflict.

# The fact that Gunicorn is even getting invoked *with* this error when you had `CMD ["python", "main.py"]`
# indicates that the Cloud Run Python buildpack is trying to be "smart" and run Gunicorn for you,
# even if your `main.py` isn't designed for it.

**Let's try to override the buildpack's behavior by giving a clear Gunicorn command, and assuming `adk.run_server` provides a callable at `main:app` or similar:**

**Revised Dockerfile (Most Promising based on error message):**

```dockerfile
# Use the official Python base image
FROM python:3.13-slim-bookworm

# Set the working directory in the container
WORKDIR /app

# Copy requirements.txt and install dependencies
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# Copy the rest of your application code
COPY . .

# Expose the port your application listens on
ENV PORT 8080
EXPOSE 8080

# --- Set Gunicorn/Uvicorn environment variables ---
ENV GUNICORN_TIMEOUT=600 # Increased
ENV GUNICORN_WORKERS=1   # Single worker for memory control
ENV GUNICORN_THREADS=8   # Multiple threads per worker

# The critical part: explicitly tell Gunicorn where your application is.
# Your `main.py` has `from agent import root_agent`.
# If `root_agent` *is* the ASGI/WSGI application object itself, then we can use that.
# OR, if `google.adk.run.run_server` creates an `app` object you can import.
# Let's assume `root_agent` is the callable (this is common in some frameworks).
CMD ["gunicorn", "--bind", "0.0.0.0:<span class="math-inline">\{PORT\}", "\-\-workers", "</span>{GUNICORN_WORKERS}", "--threads", "<span class="math-inline">\{GUNICORN\_THREADS\}", "\-\-timeout", "</span>{GUNICORN_TIMEOUT}", "agent:root_agent"]