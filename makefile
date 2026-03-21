-include .gc.env

# Determine the operating system
ifeq ($(OS),Windows_NT)
    DETECTED_OS := Windows
    GRADLEW := gradlew.bat
else
    DETECTED_OS := $(shell uname -s)
    GRADLEW := ./gradlew
endif

# Command to set up and running the docker compose
up:
	docker compose -f docker-compose.dev.yml --env-file .env.dev up -d --build

# Command to run the project.
run:
	$(GRADLEW) bootRun

down:
	docker compose down

# Build the project
b:
	$(GRADLEW) clean build

# Substitua a regra create-migration por isto:
create-migration:
	@echo "Creating new migration file..."
	@read -p "Enter migration description: " desc; \
	timestamp=$$(date +%Y%m%d%H%M%S); \
	clean_desc=$$(echo "$$desc" | tr '[:upper:]' '[:lower:]' | tr ' ' '_' | tr -cd '[:alnum:]_'); \
	file="src/main/resources/db/migration/V$${timestamp}__$${clean_desc}.sql"; \
	mkdir -p src/main/resources/db/migration; \
	echo "-- Migration: $$clean_desc" > "$$file"; \
	echo "-- Created: $$(date '+%Y-%m-%d %H:%M:%S')" >> "$$file"; \
	echo "-- Description: $$desc" >> "$$file"; \
	echo "" >> "$$file"; \
	echo "-- === UP Migration ===" >> "$$file"; \
	echo "-- Your SQL statements go here" >> "$$file"; \
	echo "" >> "$$file"; \
	echo "-- === DOWN Migration (Rollback) ===" >> "$$file"; \
	echo "-- Rollback statements go here" >> "$$file"; \
	echo ""; \
	echo "✅ Migration file created: $$file"; \
	echo "Please edit the file to add your SQL statements."


# Run tests
test:
	$(GRADLEW) test

# Push the Docker image to Azure Container Registry
push:
	@echo "Pushing Docker image to Azure Container Registry..."
	@gcloud auth activate-service-account --key-file=gcloud-key.json 2>/dev/null || echo "Warning: gcloud-key.json not found or gcloud not installed"
	@gcloud config set project $(GC_PROJECT_ID) 2>/dev/null || echo "Warning: GC_PROJECT_ID not set"
	@gcloud auth configure-docker $(GC_REGISTRY_REGION)-docker.pkg.dev --quiet 2>/dev/null || echo "Warning: GC_REGISTRY_REGION not set"
	$(GRADLEW) jib

# Deploy to Gcloud
gc-deploy:
	@echo "Deploying to Google Cloud..."
	@python3 deploy.py 2>/dev/null || echo "Warning: deploy.py not found or python3 not installed"

deploy: push gc-deploy

# Backup the staging database
db-backup:
	@python backup.py 2>/dev/null || echo "Warning: backup.py not found or python not installed"

# Restore database - define FILE=path/to/backup.dump
restore:
ifndef FILE
	@echo "Error: FILE variable not set. Usage: make restore FILE=path/to/backup.dump"
	@exit 1
else
	@echo "Restoring database from $(FILE)..."
	@pg_restore -U postgres -p 5400 -c -d recredit $(FILE) 2>/dev/null || echo "Error: restore failed. Check if PostgreSQL is running on port 5400"
endif

drop-db:
	@echo "Dropping the database..."
	@psql -U postgres -p 5400 -c "DROP DATABASE IF EXISTS recredit;" 2>/dev/null || echo "Error: Could not connect to PostgreSQL on port 5400"

# Create the database
create-db:
	@echo "Creating the database..."
	@psql -U postgres -p 5400 -c "CREATE DATABASE recredit;" 2>/dev/null || echo "Error: Could not connect to PostgreSQL on port 5400"

lint:
	@echo "Running lint checks..."
	$(GRADLEW) spotlessApply

# Help command
help:
	@echo "Available commands:"
	@echo "  make up           - Start Docker containers"
	@echo "  make down         - Stop Docker containers"
	@echo "  make run          - Run Spring Boot application"
	@echo "  make b            - Clean and build project"
	@echo "  make create-migration - Create new database migration"
	@echo "  make test         - Run tests"
	@echo "  make restore FILE=backup.dump - Restore database from backup"
	@echo "  make drop-db      - Drop database"
	@echo "  make create-db    - Create database"
	@echo "  make lint         - Apply code formatting"

.PHONY: up down run b create-migration test push gc-deploy deploy db-backup restore drop-db create-db lint help