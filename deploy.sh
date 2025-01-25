#!/bin/bash

# Set variables
PROJECT_DIR="/Users/user/Projects/sus_jw_server"           # Update with your Spring Boot project path
DOCKER_DIR="/Users/user/Projects/sus_jw_server/docker"     # Update with your Docker folder path
JAR_NAME="app.jar"                                         # Desired name for the JAR file in the Docker folder
REMOTE_USER="root"                                         # Remote server username
REMOTE_HOST="168.119.191.26"                               # Remote server address (e.g., IP or hostname)
REMOTE_DIR="/app"                                          # Remote server directory for Docker folder
SSH_KEY="/Users/user/.ssh/sus-jw"                          # SSH key to server

# Step 1: Navigate to the project directory
echo "Navigating to project directory: $PROJECT_DIR"
cd "$PROJECT_DIR" || { echo "Project directory not found! Exiting."; exit 1; }

# Step 2: Build the application using Gradle
echo "Building the Spring Boot application using Gradle..."
./gradlew clean build -x test || { echo "Gradle build failed! Exiting."; exit 1; }

# Step 3: Find the JAR file in the build/libs directory
echo "Finding the generated JAR file..."
BUILD_JAR=$(find build/libs -type f -name "sus_jw-*.jar" ! -name "*plain*.jar" | head -n 1)

if [ -z "$BUILD_JAR" ]; then
  echo "JAR file not found! Ensure the build/libs directory contains the built JAR. Exiting."
  exit 1
fi

echo "JAR file found: $BUILD_JAR"

# Step 4: Copy the JAR file to the Docker folder
echo "Copying the JAR file to the Docker folder: $DOCKER_DIR"
cp "$BUILD_JAR" "$DOCKER_DIR/$JAR_NAME" || { echo "Failed to copy the JAR file! Exiting."; exit 1; }

# Step 5: Copy the Docker folder to the remote server
ssh -i "$SSH_KEY" "$REMOTE_USER@$REMOTE_HOST" "rm -rf $REMOTE_DIR" || { echo "Failed to remove remote directory! Exiting."; exit 1; }
echo "Copying the Docker folder to the remote server: $REMOTE_USER@$REMOTE_HOST:$REMOTE_DIR"
scp -i "$SSH_KEY" -r "$DOCKER_DIR" "$REMOTE_USER@$REMOTE_HOST:$REMOTE_DIR" || { echo "Failed to copy the Docker folder to the remote server! Exiting."; exit 1; }

# Step 5.1: Stop and remove existing containers and the sus_jw_server image
echo "Stopping and removing existing Docker containers and the sus_jw_server image on the remote server..."
ssh -i "$SSH_KEY" "$REMOTE_USER@$REMOTE_HOST" "cd $REMOTE_DIR && docker-compose down --rmi local" || { echo "Failed to stop and remove existing containers! Exiting."; exit 1; }

# Step 6: Execute docker-compose up --build -d on the remote server
echo "Building and starting the Docker containers on the remote server..."
ssh -i "$SSH_KEY" "$REMOTE_USER@$REMOTE_HOST" "cd $REMOTE_DIR && docker-compose --env-file /app.env up --build -d" || { echo "Failed to execute docker-compose on the remote server! Exiting."; exit 1; }

# Step 7: Confirm success
echo "Successfully built and deployed the Docker folder and started the application on $REMOTE_USER@$REMOTE_HOST:$REMOTE_DIR"