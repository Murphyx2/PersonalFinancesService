# Personal Finances Service

**Personal Finances Service** is a RESTful API built with Spring Boot to help users manage their personal finances. This service enables you to create, retrieve, update, and delete financial transactions, categorize them, and generate financial reports. It uses a layered architecture (controller, service, repository) and integrates with a database for persistent storage.

---

## Features

- Create and manage financial transactions (income and expenses)
- Categorize transactions for better organization
- Retrieve transactions by ID or fetch all transactions
- Generate financial reports summarizing income, expenses, and balance
- Delete transactions as needed

---

## Installation

### Prerequisites

- Java 17 or higher
- Maven (for dependency management and building)
- Database (e.g., PostgreSQL, MySQL, or H2 for testing)
- Git (to clone the repository)
- Docker (optional, for containerized deployment)

### Setup

1. **Clone the Repository**
    ```bash
    git clone https://github.com/Murphyx2/PersonalFinancesService.git
    cd PersonalFinancesService
    ```

2. **Configure the Application and Database**

   From `.envTemplate` create a new enviromental variable file `.env` and refactor the parameters.
    ```
        # APPLICATION
        SERVER_PORT=1111
        DEV_SERVER=1111

        # DB
        DB_HOST=personal-finance-db
        POSTGRES_DB=personal_finance_db
        POSTGRES_USER=postgresUsername
        POSTGRES_PASSWORD=postgresPassword
        DB_PORT=1111
        DB_PORT_EXTERNAL=1111

        # SECURITY
        SECRET_KEY="Secrets are meant to be hidden"
     ```
    
   or edit `src/main/resources/application.yml` to set up your instance.  
    Example configuration for PostgreSQL:
    ```properties

    spring:
        application:
            name: personal-finance
      datasource:
        url: jdbc:postgresql://${DB_HOST:localhost}:${DB_PORT:5432}/${POSTGRES_DB:personal_finance_db}
        username: ${POSTGRES_USER:postgres}
        password: ${POSTGRES_PASSWORD}
        driver-class-name: org.postgresql.Driver
    
    ```

4. **Build the Project**
    ```bash
    mvn clean install
    ```

5. **Run the Application**
    ```bash
    mvn spring-boot:run
    ```

The API will be available at [http://localhost:8080](http://localhost:8080) (or the port specified in `application.yml` or `.env`).

---

## Usage

You can interact with the API using tools like Postman, cURL, or any HTTP client.  
All endpoints accept and return JSON.

### Example: Creating a Transaction

```bash
curl -X POST http://localhost:8080/api/personal-finance/transactions \
-H "Content-Type: application/json" \
-d '{
    "budgetId":"ca3dfd97-a2f8-4eb4-aaac-4cc4c51aa254",
    "categoryId":"4b039b61-d0b8-4368-a1d8-1d9399d98283",
    "description":"Nier:Automata",
    "amount": 10.5,
    "currencyCode": "CAD",
    "transactionDate": "2025-07-02 00:00:00"
}'
```

**Response:**
```json
{
    "transaction": {
        "id": "092e6d93-6721-4d03-b64e-cddc2d4c4004",
        "userId": "14b52c06-7da1-4d1e-bd73-9551eedbec88",
        "budgetId": "ca3dfd97-a2f8-4eb4-aaac-4cc4c51aa254",
        "category": {
            "id": "4b039b61-d0b8-4368-a1d8-1d9399d98283",
            "userId": "14b52c06-7da1-4d1e-bd73-9551eedbec88",
            "name": "VIDEOGAMES",
            "transactionType": "EXPENSE",
            "createdAt": "2025-07-02 17:00:38"
        },
        "currencyCode": "CAD",
        "description": "Nier:Automata",
        "amount": 10.5,
        "transactionDate": "2025-07-02 00:00:00",
        "createdAt": "2025-07-15 22:19:43",
        "updatedAt": null
    }
}
```

---

## API Endpoints

Base URL: `/api/personal-finance/`

| Method | Endpoint              | Description                | Request Body Example |
|--------|----------------------|----------------------------|---------------------|
| POST   | `/transactions`      | Create a new transaction   | `{"budgetId":"ca3dfd97-a2f8-4eb4-aaac-4cc4c51aa254", "categoryId":"4b039b61-d0b8-4368-a1d8-1d9399d98283", "description":"Nier:Automata", "amount": 10.5, "currencyCode": "CAD", "transactionDate": "2025-07-02 00:00:00"}` |
| GET    | `/transactions?sortBy=NAME&sortDirection=DESC&budgetId=1254`      | Retrieve all transactions  | None                |
| GET    | `/transactions/{id}` | Retrieve a transaction by ID | None                |
| PUT    | `/transactions/{id}` | Update a transaction       | `{"id":"092e6d93-6721-4d03-b64e-cddc2d4c4004","categoryId":"4b039b61-d0b8-4368-a1d8-1d9399d98283","description":"Crosscode + DLC","amount": 5.5,"currencyCode": "CAD","transactionDate": "2025-06-24 00:00:00"}` |
| DELETE | `/transactions/{id}` | Delete a transaction       | None                |

### Example Requests

- **Get All Transactions**
    ```bash
    curl http://localhost:8080/api/transactions
    ```

- **Delete a Transaction**
    ```bash
    curl -X DELETE http://localhost:8080/api/transactions/1
    ```

---

## Project Structure

```
src/main/java/com/example/personalfinances/
├── config         # Service custom configurations(e.g., Cache, OpenApi)
├── controllers    # Handle HTTP requests (e.g., TransactionController)
├── converters     # Converters for the entities and DTOs
├── exceptions     # Custom exceptions
├── facade         # Implementation of the facade repositories
├── filter         # Filters and sorters for lists
├── repository     # JPA repositories interfaces
├── services       # Business logic (e.g., TransactionService)
└── utils          # General purpose classes
src/main/resources/
└── application.yml         # Database and server configuration
pom.xml                     # Maven dependencies and build configuration
```

---

## Contributing

Contributions are welcome!  
Feel free to fork, submit pull requests, and suggest improvements.

### Steps

1. Fork the repository on GitHub
2. Clone your fork
    ```bash
    git clone https://github.com/your-username/PersonalFinancesService.git
    ```
3. Create a branch for your feature
    ```bash
    git checkout -b feature/your-feature-name
    ```
4. Make changes and add tests if applicable
5. Run tests
    ```bash
    mvn test
    ```
6. Commit your changes
    ```bash
    git commit -m "Add your feature description"
    ```
7. Push to your fork
    ```bash
    git push origin feature/your-feature-name
    ```
8. Open a pull request with a clear description

Please follow the project’s coding standards and include tests for new features.

---

## License

This project is licensed under the [MIT License](LICENSE).

---

If you have any questions or suggestions, feel free to open an issue.
