# Siberian Spruce 🧥🌲

**Siberian Spruce** is a modern e-commerce application for premium clothing, built with microservices architecture using Spring Boot, React, and Docker.

---

## ⚙️ Tech Stack

- **Backend**: Spring Boot (Java)
- **Frontend**: React
- **Databases**: PostgreSQL, Redis, Elasticsearch
- **Communication**: REST, RabbitMQ, gRPC
- **Infrastructure**: Docker, Docker Compose

---

## 🧱 Microservices

- `account-service` – registration, login, JWT authentication
- `catalog-service` – products & search
- `inventory-service` – stock levels
- `cart-service` – user shopping cart
- `order-service` – order placement and history
- `payment-service` – payment integration
- `notification-service` – email notifications
- `admin-panel-backend` – admin dashboard backend
- Infrastructure: `api-gateway`, `config-server`, `service-discovery`

---


## ▶️ Run the project

```bash
cd backend
docker-compose up -d
