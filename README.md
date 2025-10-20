
---

## 🧱 Microservices Overview

| Service              | Description                                                                 |
|----------------------|-----------------------------------------------------------------------------|
| **Card Service**      | Manages virtual card creation, balance, and card operations                |
| **Transfer Service**  | Handles top-ups, peer-to-peer transfers, refunds, and transaction history  |
| **Notification Service** | Sends real-time alerts via email and SMS using external providers     |
| **Security Service**  | Manages user authentication, JWT issuance, and role-based access control   |
| **API Gateway**       | Centralized entry point for routing and securing client requests           |
| **Eureka Server**     | Service registry for dynamic discovery and load balancing                  |

---

## 🔐 Security & Authentication

- JWT-based authentication
- Role-based access control (User/Admin)
- Secure login flows with encrypted credentials
- Token validation at API Gateway and downstream services

---

## 🚀 Core Features

### 💳 Virtual Card Management
- Unique virtual card per user
- Secure card data storage
- Real-time balance and status visibility

### 💰 Top-Up & Fund Management
- Add funds from linked accounts
- Secure and validated top-up workflows

### 🔁 Money Transfer
- Peer-to-peer transfers between users
- Real-time balance updates and transaction logging

### 💸 Refunds
- Initiate and track eligible refunds
- Notification on refund status

### 📜 Transaction History
- Detailed logs of all financial activity
- Filter by date, type, and amount
- Exportable summaries (future enhancement)

### 📬 Notifications
- Real-time alerts for:
  - Successful transactions
  - Refunds
  - Low balance warnings
  - Security alerts
- Email via SMTP
- SMS via Twilio (or configurable provider)

---

## 🛠️ Tech Stack

| Category         | Technology                          |
|------------------|--------------------------------------|
| Language         | Java 17                              |
| Framework        | Spring Boot 3.x                      |
| Architecture     | Microservices                        |
| Service Discovery| Spring Cloud Netflix Eureka          |
| Config Management| Spring Cloud Config (optional)       |
| API Gateway      | Spring Cloud Gateway                 |
| Database         | PostgreSQL, Redis                    |
| Messaging        | RabbitMQ / Kafka (optional)          |
| Containerization | Docker                               |
| Testing          | JUnit, Mockito                       |

---

## 🐳 Deployment

### Prerequisites
- Docker & Docker Compose installed
- Java 17 and Maven/Gradle (for local builds)
