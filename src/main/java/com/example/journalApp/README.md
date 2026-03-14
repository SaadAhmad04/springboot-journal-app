# Spring Boot Journal App

A simple REST API Journal application built using Spring Boot and MongoDB Atlas.

---

## Features

- Create journal entries
- View all journal entries
- View journal entry by ID
- Update journal entries
- Delete journal entries
- User registration with secure password hashing (BCrypt)
- Role-based access control with Spring Security (USER, ADMIN roles)
- Authentication via HTTP Basic Auth
- MongoDB Atlas integration for cloud database management

---

## Tech Stack

- Java 8
- Spring Boot 3.x
- Spring Security
- Spring Data MongoDB
- MongoDB Atlas (cloud)
- Maven
- Lombok

---

## Project Structure

- **Controller** – REST endpoints for users, journals, and admin operations.
- **Service** – Business logic including user management, journal CRUD, and security handling.
- **Repository** – MongoDB data access layers.
- **Entity** – Data models for users and journals.
- **Config** – Security and transaction management configurations.

---

## Setup Instructions

### 1. Clone the repo

```bash
git clone https://github.com/yourusername/journal-app.git
cd journal-app