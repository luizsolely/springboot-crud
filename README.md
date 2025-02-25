# 📊 User Management API

Simple CRUD in Spring Boot with MySQL, allowing users to be created, read, updated, and deleted.

---

## 🚀 Technologies

- Java 17
- Spring Boot 3
- MySQL
- Thymeleaf
- Springdoc OpenAPI (Swagger)

---

## 📖 Endpoints

### 🔍 List Users
```
GET /users
```

### ➕ Create User
```
POST /users/create
```
Request body example (JSON):
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@email.com",
  "phone": "123456789",
  "address": "Example Street, 123",
  "status": "ACTIVE"
}
```

### ✏️ Update User
```
PUT /users/edit/{id}
```

### ❌ Delete User
```
DELETE /users/{id}
```

---

## 📑 Swagger UI

Access the interactive documentation:
```
http://localhost:8080/swagger-ui/index.html
```

---

## 🛠️ Running Locally

### ✅ Prerequisites

- Java 17+
- MySQL
- Maven

### 📌 Steps

1. Clone the repository:
```bash
git clone https://github.com/luizsolely/springboot-crud.git
cd springboot-crud
```

2. Configure MySQL in `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/user_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

3. Run the application:
```bash
mvn spring-boot:run
```

Access at: `http://localhost:8080/users`

