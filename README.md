# Ktor Notes API

This repository contains the backend for the [CMP Notes Client](https://github.com/laetuz/CMP-Notes-Client), 
a multiplatform app built with Compose. 
It's a modern and secure RESTful API for managing notes, 
developed with Ktor, Kotlin, and Exposed for type-safe database access.

A modern, robust, and secure RESTful API for managing notes,
built with Kotlin and the Ktor framework. This project serves 
as a powerful backend foundation, demonstrating best practices in API development, 
authentication, and database interaction.

Visit the client side repo: [CMP Notes Client](https://github.com/laetuz/CMP-Notes-Client)

---

## ✨ Features

* **User Authentication**: Secure user registration and login using **Basic Authentication** and **BCrypt**.
* **CRUD Operations for Notes**: Full Create, Read, Update, and Delete functionality for user-specific notes.
* **Dependency Injection**: Clean, decoupled architecture powered by **Koin**.
* **Modern Database Access**: Type-safe ORM using **JetBrains Exposed**.
* **Asynchronous by Design**: Built on Kotlin Coroutines for high-performance, non-blocking I/O.

---

## 🛠️ Technology Stack

* **Framework**: [Ktor](httpshttps://ktor.io/)
* **Language**: [Kotlin](https://kotlinlang.org/)
* **Authentication**: Ktor Basic Auth with BCrypt hashing
* **Database**: [PostgreSQL](https://www.postgresql.org/)
* **Database Access**: [JetBrains Exposed](https://github.com/JetBrains/Exposed)
* **Dependency Injection**: [Koin](https://insert-koin.io/)
* **Serialization**: [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization)

---

## 🚀 Getting Started

Follow these instructions to get a copy of the project up and running on your local machine for development and testing.

### Prerequisites

* **JDK 17** or higher.
* **PostgreSQL** database server installed and running. or
* **Docker Desktop** dto run the docker script for the postgresql setup.
* An IDE like IntelliJ IDEA is recommended.

### Setup & Installation

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/laetuz/ktor-notes-api.git](https://github.com/laetuz/ktor-notes-api.git)
    cd project
    ```

2.  **Set up the PostgreSQL Database:**
    * Open docker-compose.yml and run services. 
    This will automatically setup the container for postgres.
    * Connect to your PostgreSQL instance.
    * Create a new database for the project (e.g., `notes_db`).
    * The necessary tables will be created automatically by Exposed when the application starts for the first time.

3.  **Configure Environment Variables:**
    This project uses environment variables to manage sensitive data like database credentials. You can set these in your IDE's run configuration or export them in your terminal.

    | Variable            | Description                                        | Example Value                                  |
        | ------------------- | -------------------------------------------------- | ---------------------------------------------- |
    | `DATABASE_URL`      | The JDBC URL for your PostgreSQL database.         | `jdbc:postgresql://localhost:5432/notes_db`    |
    | `DATABASE_USER`     | The username for your database.                    | `postgres`                                     |
    | `DATABASE_PASSWORD` | The password for your database user.               | `your_secure_password`                         |

4.  **Run the Application:**
    Use the Gradle wrapper to build and run the server.
    ```bash
    ./gradlew run
    ```
    The server will start, typically on `http://localhost:8081`.

---

## 📖 API Endpoints

* I have provided the API documentation in a notebook format  [here](https://github.com/laetuz/ktor-notes-api/blob/main/src/main/resources/ApiDocs.ipynb)
* or you could go to **/notes/src/main/resources/ApiDocs.ipynb** in this project scope. 

---

## 📂 Project Structure

A brief overview of the key directories in the project. ~later lah ya i will update.

---

Visit our website: [Neotica](https://neotica.id)