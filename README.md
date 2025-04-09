# PersonalFinancesService

Service to handle personal finances.

## Table of Contents

- [Introduction](#introduction)
- [Database Structure](#database-structure)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Installation](#installation)
- [Usage](#usage)
- [Configuration](#configuration)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

## Introduction

PersonalFinancesService is a Java-based service designed to help users manage their personal finances effectively. This service provides functionalities to track expenses, manage budgets, and generate financial reports.

## Database Structure
The main means of persistency is a Postgresql database, below you'll find the structure implemented in the project.
![Entity-relationship diagram drawio](https://github.com/user-attachments/assets/76e5475d-6d3a-41a9-bb45-9dd0c7c4e633)

## Features

- Expense tracking
- Budget management
- Financial report generation
- User authentication and authorization
- RESTful API

## Technologies Used

- Java (99.5%)
- Dockerfile (0.5%)

## Installation

### Prerequisites

- Java 17 or higher
- Sprint Boot 3.4.4
- Postgresql
- Docker (optional, but recommended)

### Steps

1. Clone the repository:
    ```
    git clone https://github.com/Murphyx2/PersonalFinancesService.git
    ```
2. Navigate to the project directory:
    ```
    cd PersonalFinancesService
    ```
3. Build the project using Maven:
    ```
    mvn clean install
    ```

## Usage

### Running the Application

To run the application, use the following command:
```
java -jar target/PersonalFinancesService-0.0.1-SNAPSHOT.jar
```
### Using Docker

To run the application using Docker, follow these steps:

1. Build the Docker image:
    ```
    docker build -t personal-finances-service .
    ```
2. Run the Docker container:
    ```
    docker run -p 8080:8080 personal-finances-service
    ```

## Configuration

The application can be configured using the `application.properties` file located in the `src/main/resources` directory. Adjust the configurations as needed for your environment.

## Contributing

Contributions are welcome! Follow these steps to contribute:

1. Fork the repository
2. Create a new branch (`git checkout -b feature/YourFeature`)
3. Commit your changes (`git commit -m 'Add some feature'`)
4. Push to the branch (`git push origin feature/YourFeature`)
5. Create a new Pull Request

## License

This project is licensed under the MIT License.

## Contact

For any inquiries or feedback, feel free to reach out via [GitHub Issues](https://github.com/Murphyx2/PersonalFinancesService/issues).
