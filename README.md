# Task Manager — Java

[🇧🇷 Versão em Português](README.pt-BR.md)

A console-based **Task Manager application developed with Java Core**.

The project was created to practice the development of a complete Java application using object-oriented programming, layered architecture, collections, exception handling, business rules, and file persistence.

---

## 📌 About the Application

Task Manager allows multiple users to manage their own tasks through a console interface.

Users can:

* Create and select users
* Create tasks
* List tasks
* Edit tasks
* Delete tasks
* Mark tasks as completed
* Organize tasks by priority and category
* Create simple or recurring task types
* Save and restore application data between executions

Each task contains information such as:

```text
ID
Title
Description
Due Date
Priority
Status
Category
Type
```

The application currently runs entirely with **Java Core**, without frameworks or a database.

---

## 📁 What's Inside

The project is divided into different packages according to their responsibilities:

```text
src/
│
├── application/
│   └── Main.java
│
├── data/
│   └── file.txt
│
├── model/
│   ├── entities/
│   ├── entities/enums/
│   └── exceptions/
│
├── persistance/
│   ├── Exportable.java
│   └── FilePersistance.java
│
├── repository/
│   ├── Repository.java
│   ├── InMemoryTaskRepository.java
│   └── InMemoryUserRepository.java
│
├── service/
│   ├── TaskService.java
│   └── UserService.java
│
└── ui/
    └── ConsoleUI.java
```

Main components:

* **User** — represents an application user and their tasks
* **Task** — abstract base class for tasks
* **SimpleTask** — represents a regular task
* **RecurringTask** — represents a recurring task type
* **Category** — organizes tasks
* **Priority** — defines task priority
* **TaskStatus** — defines whether a task is pending or completed
* **Repositories** — store users and tasks in memory
* **Services** — contain application and business rules
* **ConsoleUI** — handles interaction with the user
* **FilePersistance** — saves and restores application data

---

## 🏗 Architecture

The project follows a basic **layered architecture**:

```text
Console UI
    ↓
Services
    ↓
Repositories
    ↓
Domain Entities
```

Persistence interacts with the domain and repositories to save and restore data:

```text
File Persistence
      ↕
Repositories / Entities
```

### UI Layer

Responsible for:

* Displaying menus
* Reading user input
* Showing results and errors
* Calling services

### Service Layer

Responsible for application rules, including:

* User creation and removal
* Duplicate user validation
* Task creation and removal
* Task completion
* User and task validation
* Task ownership rules

### Repository Layer

Responsible for storing entities in memory.

The project uses a generic repository interface:

```java
public interface Repository<T> {

    void addItem(T item);

    T findById(String id);

    List<T> findAll();

    void delete(String id);
}
```

Current implementations use `HashMap`:

```text
InMemoryUserRepository
InMemoryTaskRepository
```

### Domain Layer

Contains the main entities, enums, and domain validations.

Examples:

```text
User
Task
SimpleTask
RecurringTask
Category
Priority
TaskStatus
```

---

## 🎯 Project Context

This project was developed as a practical Java Core project after studying fundamental Java concepts.

Its main purpose is to move beyond isolated exercises and practice how different parts of a real application communicate.

Concepts applied in the project include:

* Object-oriented programming
* Encapsulation
* Inheritance
* Abstraction
* Polymorphism
* Interfaces
* Generics
* Collections
* `ArrayList`
* `HashMap`
* Enums
* Custom exceptions
* `LocalDate`
* File I/O
* Business rules
* Repository pattern
* Service layer
* Separation of responsibilities

The project intentionally avoids frameworks so the application structure and Java fundamentals can be understood before moving to technologies such as JDBC and Spring Boot.

---

## ▶️ How to Run

### 1. Clone the repository

```bash
git clone <repository-url>
```

Enter the project directory:

```bash
cd taskManager
```

### 2. Open the project

Open the project with a Java IDE such as:

* IntelliJ IDEA
* Eclipse
* VS Code with Java support

### 3. Configure the JDK

Make sure the project is using a valid Java JDK.

### 4. Configure the persistence file path

The project currently uses a file to store application data.

If `Main.java` contains an absolute path such as:

```java
"C:\\Users\\...\\taskManager\\src\\data\\file.txt"
```

change it according to your environment.

### 5. Run the application

Run:

```text
application.Main
```

The console menu will be displayed and the application will be ready to use.

---

## ⚙️ Requirements

To run the project you need:

* Java JDK installed
* A Java-compatible IDE or command-line environment
* Git, if you want to clone the repository

No external libraries, frameworks, or database servers are currently required.

The project uses only the Java Standard Library.

---

## 🧠 Interesting Notes

This project does not store tasks only inside each `User`.

Tasks are also managed through a dedicated repository, which helps practice separation between domain objects, data access, and application logic.

The project also implements different task types using inheritance:

```text
           Task
          /    \
 SimpleTask   RecurringTask
```

`RecurringTask` already exists as a separate domain type, although automatic recurrence behavior has not yet been implemented.

Application data is persisted using a custom text-file format instead of a database. This was intentional to practice Java file manipulation before introducing JDBC or ORM technologies.

---

## 🔧 Small Future Fixes

Some improvements are planned for future versions:

* [ ] Replace the absolute persistence path with a relative or configurable path
* [ ] Restore persisted task IDs correctly when loading data
* [ ] Improve handling of overdue tasks during file loading
* [ ] Persist the complete category information
* [ ] Improve recurring task behavior
* [ ] Strengthen separation between UI, Service, and Repository layers
* [ ] Refactor the repository interface to support different ID types
* [ ] Add automated tests with JUnit 5

Possible future versions may also explore Maven, JDBC, MySQL, and Spring Boot.

---

⭐ Built as a practical project to strengthen **Java Core and software architecture fundamentals**.
