# Riddler 🧩

<!-- Tech Stack Badges -->
<p align="left">
  <img src="https://shields.io" alt="Java 25" />
  <img src="https://shields.io" alt="Spring Boot 4" />
  <img src="https://shields.io" alt="Vaadin" />
  <img src="https://shields.io" alt="Hilla React" />
  <img src="https://shields.io" alt="PostgreSQL" />
</p>

<!-- CI/CD Status Telemetry -->
[![Java CI with Maven](https://github.com)](https://github.com)
![GitHub repo size](https://shields.io)
![GitHub last commit](https://shields.io)

---

## 🛠️ Environment Setup

### 🐳 Docker

Spin up the required database and middleware infrastructure services:
```bash
docker compose -f infrastructure/docker-compose.yml up -d
```

### ☕ Java

**Target Version:** OpenJDK 25

#### Via SDKMAN!
```bash
sdk env install
```

#### Manual Installation

Download and export the runtime explicitly to your system path:

* **Eclipse Temurin:** [Adoptium Releases](https://adoptium.net/en-GB/temurin/releases)

---

## 📦 Build & Dependency Management

### Via SDKMAN!
```bash
sdk env install
```

### Manual Installation

* **Apache Maven:** [Maven Binaries Archive](https://archive.apache.org/dist/maven/maven-3/)

### Compile and Package Application

Execute a clean build cycle to verify type compilation and process frontend bundles:
```bash
mvn clean install
```

---

## 🚀 Tech Stack

* **Backend Matrix:** Spring Boot 4 Application ecosystem
* **Persistence Layer:** JPA (Hibernate)
* **Web Layer:** Spring MVC
* **Database Driver:** PostgreSQL Engine

### 🎨 User Interface (UI)

* Full-stack Reactive composition using **Vaadin + Hilla (React integration)**.

### 🧪 Test Automation

* Isolated infrastructure integration mapping managed via **Testcontainers**.

---

## 🔌 Connection Network Mapping

| Technology                    | Default Container Port |
|:------------------------------|:----------------------:|
| 🗄️ **PostgreSQL**            |         `5433`         |
| ⚙️ **Backend Core Engine**    |        `31373`         |
| 🖥️ **UI Client Application** |         `8080`         |

---

## 🔐 Authentication Credentials

| Target Security Role          | Default Username | Default Password |
|:------------------------------|:----------------:|:----------------:|
| 🛡️ **Administrator Account** |     `admin`      |     `admin`      |
| 👤 **Standard User Account**  |      `user`      |      `user`      |
