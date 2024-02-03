# PAGE FLOW

Welcome to the PageFlow Library Management System repository! This system provides an efficient way to manage books, user accounts, and borrowing within a library.

## Journal

Implementation  and resolutions in the Journal section for a comprehensive overview of the project [Journal](journal.md).

## Features

- **User Authentication and Authorization:** Secure access to the system with user roles.
- **Book Cataloging and Search:** Efficiently catalog books and search through available titles.
- **Checkout and Return Book:** Manage book borrowing and return processes.

## Technologies

- Java
- Spring Boot
- Hibernate
- PostgreSQL 
- Spring Validation
- Spring Web
- Spring Security
- Lombok
- JWT Token
- JavaScript

## Prerequisites

Ensure you have the following software installed on your machine:

- **Java Development Kit (JDK):** The PageFlow Library Management System is built using Java. Install [JDK](https://www.oracle.com/java/technologies/downloads/).
  
  > Verify your Java installation by running `java -version` in your terminal or command prompt.

- **Apache Maven:** Maven is used to manage the project's build and dependencies. Install [Maven](https://maven.apache.org/download.cgi) following the installation instructions for your operating system.
  
  > Confirm your Maven installation by running `mvn -v` in your terminal or command prompt.

- **PostgreSQL:** The system uses PostgreSQL as its database management system. Install [PostgreSQL](https://www.postgresql.org/download/) on your machine.
  
  > Ensure PostgreSQL is running and accessible. You might need to configure the database details in `application.properties` before starting the application.


## Getting Started (Backend)

1. Clone this repository.
   ```bash
   git clone https://github.com/BykaWF/PageFlow.git
2. Navigate to the project directory:
   ```bash
   cd PageFlow

3. Run the following Maven command to build the project:
   ```bash
   mvn clean install

4. Once built successfully, run the application using Maven:
   ```bash
   mvn spring-boot:run
5. Access the application via `http://localhost:8080` in your browser.

### Configuration

For database configurations and other settings, please refer to the `application.properties` file in the `src/main/resources` directory and make necessary adjustments according to your environment.

## Frequently Asked Questions (FAQ)

For commonly asked questions and troubleshooting tips, refer to our [FAQ section](https://github.com/BykaWF/PageFlow/wiki/F.A.Q).

### API Test Video Demonstration
This video demonstrates the process of testing Student Controller APIs using Postman [Student Controller](https://youtu.be/_eyT2dUtHCA)
