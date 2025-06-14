# Microservices Activity Recommendation Platform

This is a **Spring Boot-based microservices application** designed to manage users and activities, and provide AI-powered recommendations and guidelines. The architecture leverages modern cloud-native tools such as **Spring Cloud**, **Keycloak**, **PostgreSQL**, **MongoDB**, and **Google Gemini AI** to create a robust, scalable, and secure system.

---

## 🧩 Microservices Overview

| Service           | Description |
|------------------|-------------|
| **user-service** | Manages user data and synchronizes with Keycloak. Uses PostgreSQL for persistence. |
| **activity-service** | Handles user activities. Uses MongoDB for storing activity data. |
| **ai-service** | Provides activity recommendations using Gemini AI. Stores recommendations in MongoDB. |
| **discovery-service** | Eureka Server to register and discover other services. |
| **api-gateway** | Acts as the single-entry point to route external requests to services. |
| **config-server** | Centralized configuration for all services using Spring Cloud Config. |

---

## 🛠️ Tech Stack

- **Java 17** & **Spring Boot**
- **Spring Cloud (Eureka, Config, Gateway)**
- **PostgreSQL** – User data storage
- **MongoDB** – Activity & AI recommendation storage
- **Keycloak** – Authentication & Authorization (OAuth2/OpenID Connect)
- **WebClient** – For inter-service communication
- **Gemini AI** – For generating personalized activity recommendations

---

## 🔐 Authentication

- **Keycloak** is used for securing all microservices.
- Users in `user-service` are **synchronized** with Keycloak to ensure consistency across systems.
- The API Gateway validates tokens and forwards authenticated requests.

---

## 🔁 Inter-Service Communication

All microservices use **Spring WebClient** to communicate asynchronously. Service discovery is handled via **Eureka**.

---

## 🗄️ Data Storage

| Service         | Database     |
|----------------|--------------|
| user-service   | PostgreSQL   |
| activity-service | MongoDB    |
| ai-service     | MongoDB      |

---

## 🔧 Configuration

- All microservices load their configuration from the **config-server**.
- Configurations are stored in a Git-backed repository.

---

## 🚀 Running the Application

### Prerequisites

- Java 17+
- Docker & Docker Compose
- Maven
- Git

### Setup

1. **Clone the Repository**
   ```bash
   git clone https://github.com/your-org/your-repo-name.git
   cd your-repo-name
