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
# Cloud Run injects the PORT environment variable. Your application
# should listen on 0.0.0.0:$PORT
ENV PORT 8080
EXPOSE 8080

# --- Set Gunicorn environment variables ---
ENV GUNICORN_TIMEOUT=600 # Increased to 10 minutes
ENV GUNICORN_WORKERS=1   # Single worker to manage memory during LLM startup
ENV GUNICORN_THREADS=8   # Multiple threads for concurrency within that single worker

# Command to run your application using Gunicorn with Uvicorn worker
# This tells Gunicorn to look in 'main.py' for an object named 'app'
# and use the Uvicorn worker to serve it (since ADK seems to generate FastAPI/ASGI apps).
CMD ["gunicorn", "-w", "${GUNICORN_WORKERS}", "-k", "uvicorn.workers.UvicornWorker", "main:app", "--bind", "0.0.0.0:${PORT}", "--timeout", "${GUNICORN_TIMEOUT}"]