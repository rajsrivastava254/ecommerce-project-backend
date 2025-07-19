Ecommerce-project-backend
A robust Spring Boot backend for e-commerce, featuring RESTful APIs, H2 in-memory database, centralized error handling, and live API documentation.

🚀 Tech Stack
Java 21

Spring Boot

Spring Data JPA

H2 Database (In-Memory)

RESTful Web Services

Maven

Swagger / OpenAPI (springdoc-openapi)

📦 Features
Modular, extensible codebase

RESTful endpoints for key e-commerce operations

Pre-populated H2 in-memory test database

Interactive Swagger UI documentation

Global exception handling for reliable API feedback

🔧 Getting Started
Prerequisites
Java 21

Maven

Steps to Run
bash
# Clone the repository
git clone https://github.com/your-username/ecommerce-project-backend.git

# Navigate into the project directory
cd ecommerce-project-backend

# Run the application
./mvnw spring-boot:run
📚 API Documentation (Swagger/OpenAPI)
Interactive API documentation is available via Swagger UI.
After starting the application, visit:

text
http://localhost:8080/swagger-ui.html
or

text
http://localhost:8080/swagger-ui/index.html
View and test all endpoints directly in your browser.

OpenAPI docs are auto-generated and always current.

Swagger/OpenAPI Dependency Reference
Already integrated—use this if updating:

xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.5.0</version>
</dependency>
🧪 Example API Endpoints
List all products

text
GET /api/products
Add a product

text
POST /api/products
Content-Type: application/json

{
  "name": "Sample Product",
  "price": 99.99,
  "description": "A test product"
}
(Additional endpoints and details available in Swagger UI)

🗃️ Database (H2)
Uses H2 in-memory database for quick setup and testing.

Initial data loaded from src/main/resources/data.sql.

H2 Console
URL: http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:testdb

Username: sa

Password: (leave blank)

⚠️ Error & Exception Handling
Global exception handling via GlobalExceptionHandler.java returns consistent API error messages and proper HTTP status codes.

🤝 Contributing
Open to issues and pull requests.

Feedback and feature suggestions welcome!

👋 Author
Raj Srivastava
LinkedIn (https://in.linkedin.com/in/raj-srivastava-b51053207)

Feel free to connect, contribute, or ask questions!
