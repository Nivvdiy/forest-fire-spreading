# Backend Help Guide

This document provides instructions to install and launch the backend of the Forest Fire Spreading project.

## Prerequisites

Ensure you have the following installed on your system:
- Java Development Kit (JDK) 17 or higher
- Apache Maven 3.8 or higher
- A compatible IDE (e.g., IntelliJ IDEA, Eclipse) or a terminal for running commands

## Installation

1. Clone the repository:
	```bash
	git clone https://github.com/your-username/forest-fire-spreading.git
	cd forest-fire-spreading/back
	```

2. Build the project using Maven:
	```bash
	mvn clean install
	```

## Launch

1. Run the Spring Boot application:
	```bash
	mvn spring-boot:run
	```

2. The backend will start on the default port (e.g., `http://localhost:8080`).
