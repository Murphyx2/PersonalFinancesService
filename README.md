# Personal Finances Service

A RESTful API built with **Spring Boot** to help users manage their personal finances.  
This microservice allows you to create, retrieve, update, and delete financial transactions, categorize them, and generate basic financial reports.

---

## Features

- Create and manage financial transactions (income & expenses)
- Categorize transactions for better organization
- Retrieve transactions with filtering and sorting
- Update and delete existing transactions
- Generate financial reports (income, expenses, and balance summaries)
- RESTful API with JSON responses
- Docker and Docker Compose support for easy deployment

---

## Tech Stack

- **Java 17+**
- **Spring Boot** (with Spring Data JPA)
- **Maven**
- **PostgreSQL** (primary database)
- **Docker** & Docker Compose

---

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven (or use the included Maven Wrapper)
- PostgreSQL database
- Docker (optional but recommended)

### Setup

1. **Clone the repository**

   ```bash
   git clone https://github.com/Murphyx2/PersonalFinancesService.git
   cd PersonalFinancesService
   ```

2. **Configure environment variables**
   Copy the template and create a .env file:   
    ```
    cp .envTemplate .env
    ```
    
   Edit the .env file with your database and application settings.
   
3. **Build the project**
    ```
    ./mvnw clean install
    ```

4. **Run the Application**
    ```
    ./mvnw spring-boot:run
    ```
The API will be available at http://localhost:8080 (or the port defined in your configuration).

---

## API Documentation
Base URL: /api/personal-finance

### API Endpoints

**Base URL:** `http://localhost:8080/api/personal-finance`

| Method | Endpoint              | Description                              | Request Body | Query Parameters                  | Response                  |
|--------|-----------------------|------------------------------------------|--------------|-----------------------------------|---------------------------|
| POST   | `/transactions`       | Create a new transaction                 | Yes          | -                                 | Transaction object        |
| GET    | `/transactions`       | Get all transactions (with filters)      | No           | `sortBy`, `sortDirection`, `budgetId` | List of transactions |
| GET    | `/transactions/{id}`  | Get a single transaction by ID           | No           | -                                 | Transaction object        |
| PUT    | `/transactions/{id}`  | Update an existing transaction           | Yes          | -                                 | Updated transaction       |
| DELETE | `/transactions/{id}`  | Delete a transaction                     | No           | -                                 | No content (204)          |



## Detailed Endpoint Reference

1. Create Transaction.

    POST `/transactions`

    Creates a new financial transaction.

    Request Body:
```
{
  "budgetId": "ca3dfd97-a2f8-4eb4-aaac-4cc4c51aa254",
  "categoryId": "4b039b61-d0b8-4368-a1d8-1d9399d98283",
  "description": "Nier:Automata",
  "amount": 10.5,
  "currencyCode": "CAD",
  "transactionDate": "2025-07-02T00:00:00"
}
```
Success Response (201 Created):

```
{
  "transaction": {
    "id": "092e6d93-6721-4d03-b64e-cddc2d4c4004",
    "userId": "14b52c06-7da1-4d1e-bd73-9551eedbec88",
    "budgetId": "ca3dfd97-a2f8-4eb4-aaac-4cc4c51aa254",
    "category": { ... },
    "description": "Nier:Automata",
    "amount": 10.5,
    "currencyCode": "CAD",
    "transactionDate": "2025-07-02T00:00:00",
    "createdAt": "2025-07-15T22:19:43"
  }
}
```

2. Get All Transactions
   
    GET `/transactions`
   
    Supports filtering and sorting.
   
    Query Parameters:

| Parameter     | Type        | Description                              | Example                     | 
|---------------|-------------|------------------------------------------|-----------------------------|
| budgetId      | UUID        | Filter by budget                         |  ca3dfd97-...               |
| sortBy        | String      | Field to sort by                         |  transactionDate, amount    |
| sortDirection | String      | Sort direction                           |  ASC or DESC                |

3. Get Transaction by ID
   
    GET `/transactions/{id}`

    Returns a single transaction.

    *Example:*
   
    `GET /api/personal-finance/transactions/092e6d93-6721-4d03-b64e-cddc2d4c4004`

4. Update Transaction
   
    PUT `/transactions/{id}`

    Updates an existing transaction.

    Request Body: Same structure as create (partial updates supported depending on implementation).

5. Delete Transaction

    DELETE `/transactions/{id}`

    Deletes a transaction.

    Response: 204 No Content


---
## Docker Support

Run the entire stack using Docker Compose:

```
docker-compose up --build
```

---

## Project Structure

```
src/main/java/com/example/personalfinances/
├── config/
├── controllers/       # TransactionController
├── converters/
├── exceptions/
├── facade/
├── filter/
├── repository/
├── services/
└── utils/
```

---

## License

This project is licensed under the MIT License — see the LICENSE file for details. 
Copyright (c) 2026 Braulio Cespedes



---

If you have any questions or suggestions, feel free to open an issue.
