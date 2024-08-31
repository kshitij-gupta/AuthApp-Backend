# Makefile for Spring Boot Application with Docker support, dynamically reading project name and version from pom.xml

# Extract project name, and version from pom.xml
PROJECT_NAME := $(shell grep -oPm1 "(?<=<name>)[^<]+" pom.xml)
PROJECT_VERSION := $(shell grep -oPm1 "(?<=<version>)[^<]+" pom.xml)
JAR_NAME := $(PROJECT_NAME)-$(PROJECT_VERSION).jar
JAR_FILE := target/$(JAR_NAME)
DOCKER_IMAGE := $(PROJECT_NAME):$(PROJECT_VERSION)
DOCKER_CONTAINER := $(PROJECT_NAME)_container

# Commands
MVN := ./mvnw
JAVA := java
DOCKER := sudo docker
DOCKER_COMPOSE := sudo docker compose

# Default target

##@ Local

.PHONY: all
all: clean build run ## Clean, build, and run the application locally.

.PHONY: install
install: ## Install Maven dependencies for the project.
	$(MVN) install

.PHONY: clean
clean: ## Clean the project by removing compiled files.
	$(MVN) clean

.PHONY: build
build: ## Build the Spring Boot application into a JAR file.
	$(MVN) package

.PHONY: test
test: ## Run unit tests for the project.
	$(MVN) test

.PHONY: run
run: $(JAR_FILE) ## Run the Spring Boot application locally.
	$(JAVA) -jar $(JAR_FILE)

##@ Docker

.PHONY: docker-build
docker-build: ## Build the Docker image for the Spring Boot application, tagged with the project version.
	$(DOCKER) build -t $(DOCKER_IMAGE) .

.PHONY: docker-run
docker-run: docker-stop ## Run the Docker container for the Spring Boot application.
	$(DOCKER_COMPOSE) up

.PHONY: docker-stop
docker-stop: ## Stop the Docker container for the Spring Boot application.
	$(DOCKER_COMPOSE) down

##@ Help

.PHONY: help
help: ## Display this help.
	@awk 'BEGIN {FS = ":.*##"; printf "\nUsage:\n  make \033[36m<target>\033[0m\n"} /^[a-zA-Z_0-9-]+:.*?##/ { printf "  \033[36m%-15s\033[0m %s\n", $$1, $$2 } /^##@/ { printf "\n\033[1m%s\033[0m\n", substr($$0, 5) } ' $(MAKEFILE_LIST)
