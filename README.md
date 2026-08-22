# Task Manager — Java 
[🇧🇷 Versão em Português](README.pt-BR.md)

A console-based **Task Manager application built with Java Core**.

The project was created to practice object-oriented programming, collections, generics, exception handling, file persistence, layered architecture, and application-level business rules.

Users can create accounts, manage their own tasks, define priorities and categories, mark tasks as completed, edit existing tasks, and persist application data between executions using a text file.

---

## 📑 Table of Contents

* [About the Project](#-about-the-project)
* [Features](#-features)
* [Technologies and Concepts](#-technologies-and-concepts)
* [Architecture](#-architecture)
* [Project Structure](#-project-structure)
* [Domain Model](#-domain-model)
* [Application Flow](#-application-flow)
* [Persistence](#-persistence)
* [File Format](#-file-format)
* [Validations](#-validations)
* [Exception Handling](#-exception-handling)
* [How to Run](#-how-to-run)
* [Example Usage](#-example-usage)
* [Current Limitations](#-current-limitations)
* [Future Improvements](#-future-improvements)
* [Learning Goals](#-learning-goals)

---

# 📌 About the Project

**Task Manager** is a command-line application developed entirely with Java Core.

The system allows multiple users to be registered. Each user has their own collection of tasks.

A task contains:

* Title
* Description
* Due date
* Priority
* Status
* Category
* Task type

The application separates responsibilities into different layers instead of placing all logic inside the `Main` class.

The general architecture is:

```text
Console UI
    ↓
Services
    ↓
Repositories
    ↓
Domain Entities

File Persistence
    ↕
Repositories / Entities
```

The project currently does not use a database or external framework.

---

# ✨ Features

## User Management

The application allows users to:

* List registered users
* Create a new user
* Select an existing user
* Switch between users
* Prevent duplicate usernames

Each registered user maintains their own collection of tasks.

---

## Task Management

After selecting a user, the application allows them to:

* List tasks
* Create tasks
* Edit tasks
* Delete tasks
* Mark tasks as completed
* Return to the user menu
* Exit the application

---

## Task Information

Every task contains:

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

---

## Priorities

Tasks support three priority levels:

```java
LOW
MEDIUM
HIGH
```

These values are represented by the `Priority` enum.

---

## Task Status

Tasks support two states:

```java
PENDING
COMPLETED
```

Every newly created task starts with:

```java
TaskStatus.PENDING
```

---

## Task Types

The application currently supports two task types:

```text
Simple Task
Recurring Task
```

They are implemented through:

```java
SimpleTask
RecurringTask
```

Both classes inherit from the abstract `Task` class.

At the current stage of the project, recurring tasks are represented as a separate task type, but automatic recurrence logic has not yet been implemented.

---

## Categories

Every task belongs to a `Category`.

A category contains:

```text
Name
Description
```

Example:

```text
Name: Study
Description: Programming and university tasks
```

---

# 🛠 Technologies and Concepts

The project uses:

* Java
* Java Core
* Object-Oriented Programming
* Abstract classes
* Inheritance
* Polymorphism
* Encapsulation
* Interfaces
* Generics
* Collections API
* `ArrayList`
* `HashMap`
* Enums
* Custom exceptions
* File I/O
* `BufferedReader`
* `BufferedWriter`
* `FileReader`
* `FileWriter`
* `LocalDate`
* `DateTimeFormatter`

The application currently requires no external framework.

---

# 🏗 Architecture

The project follows a basic layered architecture.

```text
UI
 ↓
Service
 ↓
Repository
 ↓
Domain
```

---

## UI Layer

Package:

```text
ui
```

Main class:

```text
ConsoleUI
```

Responsibilities:

* Display menus
* Read keyboard input
* Display application data
* Display error messages
* Parse user input
* Call application services

The basic flow is:

```text
User Input
    ↓
ConsoleUI
    ↓
TaskService / UserService
```

---

## Service Layer

Package:

```text
service
```

Classes:

```text
TaskService
UserService
```

The service layer contains application logic and business rules.

### `UserService`

Responsible for:

* Creating users
* Finding users
* Preventing duplicate users
* Removing users
* Validating usernames

### `TaskService`

Responsible for:

* Adding tasks
* Removing tasks
* Completing tasks
* Checking whether users exist
* Checking whether tasks exist
* Validating task ownership during task removal

---

## Repository Layer

Package:

```text
repository
```

The generic repository interface is:

```java
public interface Repository<T> {

    void addItem(T item);

    T findById(String id);

    List<T> findAll();

    void delete(String id);
}
```

The current implementations are:

```text
InMemoryUserRepository
InMemoryTaskRepository
```

Both repositories use `HashMap` to store data in memory.

### User Repository

Conceptually:

```java
Map<String, User>
```

The username is used as the key.

### Task Repository

Conceptually:

```java
Map<String, Task>
```

The task ID converted to a `String` is used as the key.

---

## Domain Layer

The domain model is mainly located inside:

```text
model.entities
```

Main entities:

```text
User
Task
SimpleTask
RecurringTask
Category
```

Enums:

```text
Priority
TaskStatus
```

---

## Persistence Layer

Package:

```text
persistance
```

Main components:

```text
Exportable
FilePersistance
```

`Exportable` defines:

```java
String toFileFormat();
```

Entities that can be stored in the persistence file implement this interface.

---

# 📁 Project Structure

A simplified representation of the project structure:

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
│   │
│   ├── entities/
│   │   ├── Category.java
│   │   ├── Task.java
│   │   ├── SimpleTask.java
│   │   ├── RecurringTask.java
│   │   └── User.java
│   │
│   ├── entities/enums/
│   │   ├── Priority.java
│   │   └── TaskStatus.java
│   │
│   └── exceptions/
│       ├── DateException.java
│       ├── DomainException.java
│       ├── DuplicateUserException.java
│       ├── TaskNotFoundException.java
│       └── UserNotFoundException.java
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

---

# 🧩 Domain Model

## Task

`Task` is an abstract class that represents the base structure of every task in the application.

Main attributes:

```java
private final Integer id;
private String title;
private String description;
private LocalDate dueDate;
private Priority priority;
private TaskStatus status;
private Category category;
```

Task IDs are currently generated through a static counter:

```java
private static int count = 0;
```

When a new task is created:

```java
this.id = ++count;
```

Every new task starts with:

```java
TaskStatus.PENDING
```

---

## SimpleTask

`SimpleTask` extends `Task`.

Its persistence identifier is:

```text
SIMPLE
```

Its user-friendly type description is:

```text
Simples
```

---

## RecurringTask

`RecurringTask` also extends `Task`.

Its persistence identifier is:

```text
RECURRING
```

Its user-friendly type description is:

```text
Recorrente
```

The class currently represents a distinct task type but does not yet implement automatic recurring behavior.

---

## User

A `User` contains:

```java
private String name;
private List<Task> tasksList;
```

Every user owns their own list of tasks.

The entity provides operations such as:

```java
add(Task task)
remove(Task task)
```

---

## Category

A category contains:

```java
private String name;
private String description;
```

Each task is associated with a category.

---

# 🔄 Application Flow

The application starts inside the `Main` class.

The execution flow is approximately:

```text
Start Application
      ↓
Create Scanner
      ↓
Create Repositories
      ↓
Create FilePersistance
      ↓
Load Previous Data
      ↓
Create Services
      ↓
Create ConsoleUI
      ↓
Display Application Menu
      ↓
User Interacts With Application
      ↓
Exit UI
      ↓
Save Application Data
      ↓
Close Scanner
```

---

# 👤 User Menu

The initial menu provides the following options:

```text
---- BEM-VINDO AO TaskManager ----

[1] - Listar usuários existentes
[2] - Criar novo usuário
[3] - Selecionar usuário
[0] - Sair
```

After selecting a user, the application opens the task menu.

---

# ✅ Task Menu

The task menu contains:

```text
---- MENU DE TAREFAS ----

[1] - Listar tarefas
[2] - Adicionar tarefa
[3] - Editar tarefa
[4] - Excluir tarefa
[5] - Marcar como concluída
[6] - Voltar / Trocar usuário
[0] - Sair
```

---

# 💾 Persistence

Application data is stored in a text file.

Persistence is handled by:

```java
FilePersistance
```

When the application starts:

```java
filePersistance.load(...);
```

When the application exits:

```java
filePersistance.save(...);
```

This allows registered users and tasks to survive between program executions.

---

# 📄 File Format

The application uses a custom semicolon-separated text format.

## User

Users are stored as:

```text
USER;username
```

Example:

```text
USER;Arthur
```

---

## Task

Tasks are stored approximately as:

```text
TASK;id;title;description;dueDate;priority;status;category;type
```

Example:

```text
TASK;1;Study Java;Review Collections;30/08/2026;HIGH;PENDING;Study;SIMPLE
```

Another example:

```text
TASK;2;Workout;Gym session;31/08/2026;MEDIUM;COMPLETED;Health;RECURRING
```

Dates use the format:

```text
dd/MM/yyyy
```

---

# 🛡 Validations

The project contains several domain validations.

## User

A username cannot be:

```text
null
empty
```

Duplicate usernames are also rejected.

---

## Task Title

A task title cannot be:

```text
null
empty
```

---

## Task Description

A task description cannot be:

```text
null
empty
```

---

## Due Date

The due date cannot currently be:

```text
null
before the current date
```

---

## Priority

Priority cannot be:

```text
null
```

---

## Status

Task status cannot be:

```text
null
```

---

## Category

A task category cannot be:

```text
null
```

Category names and descriptions also contain validation rules.

---

# ⚠️ Exception Handling

The application uses custom runtime exceptions to represent invalid states and business rule violations.

Current exceptions include:

```text
DomainException
DateException
DuplicateUserException
TaskNotFoundException
UserNotFoundException
```

They are used for situations such as:

* Invalid username
* Duplicate user
* User not found
* Task not found
* Invalid task information
* Invalid due date
* Removing a task that does not belong to a user
* Completing an already completed task
* Invalid persisted task type
* Finding a task in the persistence file without an associated user

The `ConsoleUI` catches application exceptions and displays readable messages instead of allowing the application to terminate unexpectedly.

---

# ▶️ How to Run

## Requirements

You need:

* Java JDK installed
* A Java IDE such as IntelliJ IDEA, Eclipse, or VS Code with Java support

The application uses only standard Java APIs and currently has no external dependencies.

---

## 1. Clone the Repository

```bash
git clone <repository-url>
```

Enter the project directory:

```bash
cd taskManager
```

---

## 2. Open the Project

Open the project using your preferred Java IDE.

For IntelliJ IDEA:

```text
File
→ Open
→ Select the project directory
```

Configure a valid JDK if necessary.

---

## 3. Configure the Persistence Path

The current `Main` class uses an absolute Windows path.

Example:

```java
"C:\\Users\\Arthur Freitas\\IdeaProjects\\taskManager\\src\\data\\file.txt"
```

If the application is executed on another computer, this path must currently be changed.

A planned improvement is to replace it with a relative or configurable path, for example:

```java
"src/data/file.txt"
```

---

## 4. Run the Application

Execute:

```text
application.Main
```

The main console menu should appear.

---

# 💻 Example Usage

Example application session:

```text
---- BEM-VINDO AO TaskManager ----

Escolha uma das opções:

[1] - Listar usuários existentes
[2] - Criar novo usuário
[3] - Selecionar usuário
[0] - Sair
```

Creating a user:

```text
Entre com um nome de usuário: Arthur

Usuário Arthur adicionado com sucesso!
```

Selecting the user:

```text
Entre com o nome de um usuário: Arthur

Usuário Arthur selecionado!
```

Creating a task:

```text
Título: Study Java

Descrição: Study Collections and Generics

Data de vencimento DD/MM/YYYY: 30/08/2026

Prioridade LOW, MEDIUM, HIGH: HIGH

Categoria:
Nome: Study

Descrição: Programming studies

Tipo simples [1] ou recorrente [2]: 1
```

The task is created with:

```text
Status: PENDING
Type: Simple
```

---

# 🚧 Current Limitations

The project is still under development and contains some known architectural and persistence limitations.

## Task IDs During Loading

Task IDs are written to the persistence file, but the current loader creates new task instances through their normal constructors.

Because IDs are generated using a static counter, the original persisted task ID is not currently restored directly.

Planned improvement:

```text
Persist ID
    ↓
Load Exact ID
    ↓
Restore Counter Correctly
```

---

## Overdue Tasks

The current `Task` validation rejects due dates before `LocalDate.now()`.

This works when creating new tasks but may cause problems when loading an existing task that naturally became overdue after it was saved.

A future version should distinguish between:

```text
Creating a New Task
```

and:

```text
Reconstructing an Existing Task
```

---

## Category Description Persistence

The current task persistence format stores the:

```text
category name
```

but does not store the category description.

As a result, the description cannot currently be fully reconstructed after restarting the application.

---

## Recurring Tasks

`RecurringTask` currently represents a different task type but does not yet implement real recurrence scheduling.

A future implementation could introduce something such as:

```java
private int intervalDays;
```

or a more complete recurrence model.

---

## Repository ID Type

The current generic repository interface uses:

```java
String
```

for every entity ID.

A possible future refactor is:

```java
Repository<T, ID>
```

allowing repositories such as:

```java
Repository<Task, Integer>
Repository<User, String>
```

---

## Layer Boundaries

Some UI operations currently access repository objects indirectly through services.

A future refactor should enforce a cleaner dependency flow:

```text
UI
 ↓
Service
 ↓
Repository
```

without exposing repository objects directly to the UI.

---

## Automated Tests

The project currently does not contain a complete automated testing suite.

JUnit 5 is planned as one of the next improvements.

---

# 🚀 Future Improvements

Planned improvements include:

## 🚀 Future Improvements

- [ ] Add automated tests with JUnit 5
- [ ] Refactor repositories to `Repository<T, ID>`
- [ ] Fix task ID restoration during file loading
- [ ] Improve separation between UI, Service and Repository layers
- [ ] Improve recurring task behavior
- [ ] Add Maven
- [ ] Add JDBC and MySQL persistence
- [ ] Create a REST API with Spring Boot

---

# 📚 Learning Goals

This project was developed as part of the process of improving Java and software engineering fundamentals.

Concepts practiced include:

## Object-Oriented Programming

```text
Encapsulation
Inheritance
Abstraction
Polymorphism
```

## Java Language Features

```text
Enums
Interfaces
Generics
Exceptions
Collections
LocalDate
File I/O
```

## Software Design

```text
Entities
Repositories
Services
UI Separation
Business Rules
Persistence
Dependency Through Interfaces
```

## Next Learning Topics

```text
JUnit 5
Generics
Collections
Streams
SOLID
Maven
JDBC
MySQL
Spring Boot
```

---

# 🧠 Main Objective

The main objective of this project is not only to create a working Task Manager.

It also serves as practice for the transition from:

```text
Learning Java Syntax
```

to:

```text
Designing Complete Applications With Java
```

The project focuses on understanding how different parts of an application communicate and how responsibilities can be separated between domain objects, repositories, services, persistence, and the user interface.

The project will continue to evolve as new Java and software engineering concepts are learned.

---

⭐ **This project is currently being improved as part of an ongoing Java learning journey.**
