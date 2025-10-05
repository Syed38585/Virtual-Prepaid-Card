# Virtual-Prepaid-Card

Code
project-root/
├── service-eurekaServer/
|
├── service-security/
│ 
├── service-card/
│   
├── service-notification/
|
├── service-apiGateway/
|
└── docker-compose.yml



💳 Virtual Prepaid Card
A microservices-based financial application that enables users to manage virtual prepaid cards with features like top-up, money transfer, transaction history, refunds, and real-time notifications via email and SMS.

🚀 Features
💳 Virtual Card Management
Each user is issued a unique virtual prepaid card.

Card details are securely stored and managed.

Users can view card balance and status.

💰 Top-Up & Fund Management
Users can add money to their virtual card from linked accounts.

🔁 Money Transfer
Peer-to-peer transfers between users.

Real-time balance updates and transaction logging.

Secure and validated transfer workflows.

💸 Refunds
Users can initiate refunds for eligible transactions.

Refund status tracking and notifications.

📜 Transaction History
Detailed logs of all spending, transfers, top-ups, and refunds.

Filter by date, type, or amount.

Exportable summaries (optional future enhancement).

📲 Notifications
Real-time alerts via email and SMS for:

Successful transactions

Refunds

Low balance warnings

Security alerts

🔐 Security & Authentication
JWT-based authentication for secure access.

Role-based authorization (e.g., user vs admin).

Password encryption and secure login flows.

🧱 Microservices Architecture
Service Name	Description
Card Service	Manages virtual card creation, balance, and card-related operations.
Transfer Service	Handles money transfers, top-ups, refunds, and transaction history.
Messaging Service	Sends notifications via email and SMS using external providers.
Security Service	Manages user authentication, authorization, and token-based access control.
API Gateway: Centralized entry point for routing requests to respective services.

🛠️ Tech Stack
Language: Java

Framework: Spring Boot

Database: PostgreSQL, Redis

Containerization: Docker

Testing: JUnit, Mockito

Architecture: Microservices

Other Tools: Eureka (Service Discovery), Spring Cloud Config, API Gateway


📬 Notifications
Email: Integrated with SMTP provider.

SMS: Integrated with Twilio (or other provider).

Configurable via environment variables or Spring Cloud Config.

🔐 Security
JWT-based authentication.

Role-based access control.

Secure endpoints for sensitive operations.



🧪 Developer-Friendly Features
Dockerized Services: Easy deployment and scalability.

Unit Testing: JUnit and Mockito for robust test coverage.

PostgreSQL Integration: Reliable and scalable relational data storage.

Spring Boot: Rapid development with clean architecture.
