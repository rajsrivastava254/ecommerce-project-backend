# ecommerce-project-backend


---

## 🚀 Tech Stack

- Java 17+
- Spring Boot
- Spring Data JPA
- H2 Database (In-Memory)
- RESTful Web Services
- Maven

---

## 🔧 How to Run

### Prerequisites
- Java 17+
- Maven

### Steps

# Clone the repository
git clone https://github.com/your-username/ecommerce-project-backend.git

# Navigate into the project directory
cd ecommerce-project-backend

# Run the application
./mvnw spring-boot:run

🧪 API Endpoints (Sample)
Get all products
bash
Copy
Edit
GET /api/products
Add a product
bash
Copy
Edit
POST /api/products
Content-Type: application/json

{
  "name": "Sample Product",
  "price": 99.99,
  "description": "A test product"
}
🗃️ Database
Uses H2 in-memory database.

Initial test data is populated from data.sql.

To access the H2 console:

bash
Copy
Edit
http://localhost:8080/h2-console
Configure using:

JDBC URL: jdbc:h2:mem:testdb

Username: sa

Password: (blank)

⚠️ Error Handling
Global exception handling is done through GlobalExceptionHandler.java, ensuring that client receives meaningful messages and HTTP status codes.

📄 License
This project is licensed under the MIT License.

🙋‍♂️ Author
Raj Srivastava

Connect with me on LinkedIn(https://in.linkedin.com/in/raj-srivastava-b51053207)
Feel free to raise issues or contribute!

yaml
Copy
Edit

---
