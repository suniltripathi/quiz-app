# QuizMaster - Full Stack Quiz Application

A mobile-responsive quiz application built with **Java Spring Boot** (backend), **Angular** (frontend), and **MySQL** (database).

## Features

- **Browse Quizzes** – View all available quizzes with descriptions and metadata
- **Take Quizzes** – Answer questions with a timer, progress tracking, and question navigation
- **Create Quizzes** – Build custom quizzes with single choice, multiple choice, and true/false questions
- **View Results** – See detailed results with score breakdown, grade, and per-question feedback
- **Mobile Responsive** – Fully responsive design that works on phones, tablets, and desktops
- **Seed Data** – Ships with 3 sample quizzes (Java, Web Dev, General Knowledge)

## Tech Stack

| Layer    | Technology           |
|----------|----------------------|
| Backend  | Java 17, Spring Boot 3.2, Spring Data JPA |
| Frontend | Angular 21, TypeScript, SCSS |
| Database | MySQL 8              |
| API      | RESTful JSON         |

## Prerequisites

- Java 17+
- Maven 3.6+
- Node.js 18+
- npm 9+
- MySQL 8+

## Getting Started

### 1. Database Setup

```bash
# Start MySQL and create the database
sudo service mysql start
sudo mysql -e "CREATE DATABASE IF NOT EXISTS quiz_app;"
sudo mysql -e "CREATE USER IF NOT EXISTS 'quizuser'@'localhost' IDENTIFIED BY 'quizpass123';"
sudo mysql -e "GRANT ALL PRIVILEGES ON quiz_app.* TO 'quizuser'@'localhost';"
sudo mysql -e "FLUSH PRIVILEGES;"
```

### 2. Backend

```bash
cd backend
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`.

### 3. Frontend

```bash
cd frontend
npm install
ng serve
```

The app will be available at `http://localhost:4200`.

## API Endpoints

| Method | Endpoint               | Description             |
|--------|------------------------|-------------------------|
| GET    | `/api/quizzes`         | List active quizzes     |
| GET    | `/api/quizzes/all`     | List all quizzes        |
| GET    | `/api/quizzes/{id}`    | Get quiz (without answers) |
| GET    | `/api/quizzes/{id}/admin` | Get quiz (with answers) |
| POST   | `/api/quizzes`         | Create a new quiz       |
| DELETE | `/api/quizzes/{id}`    | Delete a quiz           |
| POST   | `/api/quizzes/{id}/submit` | Submit quiz answers  |

## Project Structure

```
quiz-app/
├── backend/                  # Spring Boot application
│   ├── src/main/java/com/quizapp/
│   │   ├── config/           # CORS & data seeder
│   │   ├── controller/       # REST controllers
│   │   ├── dto/              # Data transfer objects
│   │   ├── model/            # JPA entities
│   │   ├── repository/       # Spring Data repositories
│   │   └── service/          # Business logic
│   └── src/main/resources/
│       └── application.properties
├── frontend/                 # Angular application
│   └── src/app/
│       ├── components/       # UI components
│       │   ├── navbar/
│       │   ├── quiz-list/
│       │   ├── quiz-detail/
│       │   ├── take-quiz/
│       │   ├── quiz-result/
│       │   └── create-quiz/
│       ├── models/           # TypeScript interfaces
│       └── services/         # HTTP services
└── README.md
```
