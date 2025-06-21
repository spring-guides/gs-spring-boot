# Use the official Python base image
FROM python:3.13-slim-bookworm

# Set the working directory in the container
WORKDIR /app

# Copy requirements.txt and install dependencies
# This step is cached by Docker if requirements.txt doesn't change
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# Copy the rest of your application code
COPY . .

# Expose the port your application listens on
# Cloud Run injects the PORT environment variable. Your application
# should listen on 0.0.0.0:$PORT
ENV PORT 8080
EXPOSE 8080

# Command to run your application
# This directly executes your main.py
CMD ["python", "main.py"]

# For Gunicorn + Uvicorn:
CMD ["gunicorn", "-w", "1", "-k", "uvicorn.workers.UvicornWorker", "main:app", "--bind", "0.0.0.0:8080"]
# Make sure your main.py uses os.environ.get('PORT') correctly for the port:
# `port = int(os.environ.get("PORT", "8080"))` and then use `port` in bind.
# So the CMD might be:
# CMD ["gunicorn", "-w", "1", "-k", "uvicorn.workers.UvicornWorker", "main:app", "--bind", "0.0.0.0:" + os.environ.get("PORT", "8080")]

# OR, if just using uvicorn directly (less common for production):
CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "$PORT", "--workers", "1"]