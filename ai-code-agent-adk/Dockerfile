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

# --- Set Gunicorn environment variables for ADK's internal server ---
ENV GUNICORN_TIMEOUT=600
ENV GUNICORN_WORKERS=1
ENV GUNICORN_THREADS=8

# Command to run your application.
# This directly executes your main.py, and adk.run_server will start the web server.
CMD ["python", "main.py"]