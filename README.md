# Personal Finances Service

The Personal Finances Service is a RESTful API built with Spring Boot to help users manage their personal finances. It allows you to create, retrieve, update, and delete financial transactions, categorize them, and generate financial reports. The application uses a layered architecture (controller, service, repository) and integrates with a database for persistent storage.
### Features

    - Create and manage financial transactions (income and expenses).
    - Categorize transactions for better organization.
    - Retrieve transactions by ID or fetch all transactions.
    - Generate financial reports summarizing income, expenses, and balance.
    - Delete transactions as needed.

## Installation
### Prerequisites

    - Java 17 or higher
    - Maven (for dependency management and building)
    - Database (e.g., PostgreSQL, MySQL, or H2 for testing)
    - Git (to clone the repository)
    - Docker (optional, for containerized deployment)

### Setup
1. Clone the Repository:    

        git clone https://github.com/Murphyx2/PersonalFinancesService.git
2. Navigate to the Project Directory:

       cd PersonalFinancesService

3. Configure the Database:

- Edit src/main/resources/application.properties to set up your database. Example for PostgreSQL:

    #### properties

            spring.datasource.url=jdbc:postgresql://localhost:5432/finances
            spring.datasource.username=your_username
            spring.datasource.password=your_password
            spring.jpa.hibernate.ddl-auto=update            

4. Build the Project:

        mvn clean install
    
5. Run the Application:
    
        mvn spring-boot:run

The API will be available at http://localhost:8080 (or the port specified in application.properties).

### Usage

Interact with the API using tools like Postman, cURL, or any HTTP client. The API uses JSON for request and response bodies.
Example: Creating a Transaction

#### Create a new transaction by sending a POST request:

    curl -X POST http://localhost:8080/api/transactions \
    -H "Content-Type: application/json" \
    -d '{
      "amount": 100.00,
      "description": "Grocery Shopping",
      "category": "Expenses",
      "date": "2025-04-30"
    }'

#### Response:    
    {
      "id": 1,
      "amount": 100.00,
      "description": "Grocery Shopping",
      "category": "Expenses",
      "date": "2025-04-30"
    }
## API Endpoints

The API is accessible at http://localhost:8080/api. Below are the main endpoints.
## Method	Endpoint	Description	Request Body Example
##### POST	/transactions	Create a new transaction	{"amount": 50.00, "description": "Coffee", "category": "Expenses", "date": "2025-04-30"}
##### GET	/transactions	Retrieve all transactions	None
##### GET	/transactions/{id}	Retrieve a transaction by ID	None
##### PUT	/transactions/{id}	Update a transaction	{"amount": 75.00, "description": "Updated Coffee", "category": "Expenses", "date": "2025-04-30"}
##### DELETE	/transactions/{id}	Delete a transaction	None

## Example Requests
- Get All Transactions:

      curl http://localhost:8080/api/transactions

- Delete a Transaction:

      curl -X DELETE http://localhost:8080/api/transactions/1

# Project Structure

The project follows a standard Spring Boot architecture:

    - src/main/java/com/example/personalfinances/:
        - Controllers: Handle HTTP requests (e.g., TransactionController).
        - Services: Contain business logic (e.g., TransactionService).
        - Repositories: Manage database operations (e.g., TransactionRepository).
        - Models: Define entities (e.g., Transaction).
    - src/main/resources/application.properties: Database and server configuration.
    - pom.xml: Maven dependencies and build configuration.

Contributing

Do whatever you want with this project. 

1. Fork the Repository on GitHub.
2. Clone Your Fork:    

        git clone https://github.com/your-username/PersonalFinancesService.git
3. Create a Branch:

        git checkout -b feature/your-feature-name
4. Make Changes and write tests if applicable.
5. Run Tests:

        mvn test

6. Commit Changes:

        git commit -m "Add your feature description"
7. Push to Your Fork:

        git push origin feature/your-feature-name
8. Open a Pull Request on the original repository with a clear description.

Please follow the project’s coding standards and include tests for new features.
License

This project is licensed under the MIT License. See the  file for details.
