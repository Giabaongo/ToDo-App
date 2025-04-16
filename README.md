# ToDo Application

A full-stack Todo application built with Spring Boot 3 and React, featuring user authentication, task management, and advanced search capabilities.

Video demo:


https://github.com/user-attachments/assets/3665ba44-4951-482c-be9e-715f3639e19c


## Features

### Authentication

- User registration and login
- JWT-based authentication
- Password encryption
- Session management

### Task Management

- Create, read, update, and delete todos
- Mark todos as complete/incomplete
- Advanced search functionality
- Task filtering and sorting

### Security

- Secure password storage
- Protected API endpoints
- Input validation
- CORS configuration

## Technology Stack

### Backend

- Java 17
- Spring Boot 3
- Spring Security
- Spring Data JPA
- MySQL
- JWT for authentication

### Frontend

- React
- Material-UI
- Axios for API calls
- React Router for navigation
- Context API for state management

## Setup Instructions

### Prerequisites

- Java 17 or higher
- Node.js 16 or higher
- MySQL 8.0 or higher
- Maven
- npm or yarn

### Backend Setup

1. Clone the repository:

   ```bash
   git clone https://github.com/Giabaongo/ToDo-App.git
   cd ToDo-App
   ```

2. Configure MySQL:

   - Create a new database named `todo_db`
   - Update the database configuration in `backend/src/main/resources/application.yaml`

3. Run the Spring Boot application:
   ```bash
   cd backend
   mvn spring-boot:run
   ```

### Frontend Setup

1. Install dependencies:

   ```bash
   cd frontend
   npm install
   ```

2. Start the development server:
   ```bash
   npm start
   ```

## API Endpoints

### Authentication

- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Login and get JWT token

### Todos

- `GET /api/todos` - Get all todos for the authenticated user
- `POST /api/todos` - Create a new todo
- `GET /api/todos/{id}` - Get a specific todo
- `PUT /api/todos/{id}` - Update a todo
- `DELETE /api/todos/{id}` - Delete a todo
- `GET /api/todos/search` - Search todos with filters

## Database Schema

### User Table

```sql
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

### Todo Table

```sql
CREATE TABLE todos (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    completed BOOLEAN DEFAULT FALSE,
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
```

## Future Enhancements

- Role-based access control
- Email notifications for task deadlines
- Task categories and tags
- Due dates and reminders
- User profile management
- Task sharing and collaboration
- Mobile application
- Dark mode support

## Contact

Ngô Phan Gia Bảo - [giabaongo1@gmail.comcom](mailto:giabaongo1@gmail.com)
Project Link: [https://github.com/giabaongo/todo-app](https://github.com/giabaongo/todo-app)
