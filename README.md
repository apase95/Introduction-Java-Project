# 📚 Introduction-Java-Project

Welcome to my **Introduction to Java Projects** repository! This repository serves as a personal portfolio and learning archive containing various Java desktop applications. Throughout these projects, I have focused on applying OOP principles, clean code architectures, and modern database integration techniques.

## 🛠 Technology Stack
|Path|Description|
|:-:|:-:|
|**Language**|Java (JDK 17+)|
|**GUI Framework**|Java Swing
|**Build Tool**|Maven (for dependency management)|
|**Database**|MySQL Server|
|**ORM & Data Access**|JDBC, Jakarta Persistence (JPA), Hibernate|
|**Core Concepts**|3-Layer Architecture, MVC Pattern, JPQL, Lambda Expressions, Stream API|

---

## 📂 Projects Overview

This repository is divided into four main mini-projects, each demonstrating different architectural patterns and database relationship mappings.

### 1. Quản Lý Kho (QL_Kho) - 3-Layer Architecture
This project demonstrates the **Three-Layer Architecture** (GUI, BLL, DAL) to manage Products and Categories.
- **Goal:** To strictly separate responsibilities and avoid "Spaghetti code" where UI, business logic, and SQL are mixed together.
- **Architecture:** * **GUI (Presentation Layer):** Handles user interaction without containing complex logic or SQL.
  - **BLL (Business Logic Layer):** Acts as the "brain" validating data (e.g., checking for negative numbers or empty strings) before passing it to the database.
  - **DAL (Data Access Layer):** Exclusively handles JDBC connections and executing `SELECT`, `INSERT`, `UPDATE`, `DELETE` statements.
  - **DTO (Data Transfer Object):** Pure Java classes used to pass data neatly between the three layers.

### 2. Hibernate & JPA - Basic CRUD (Hibernate_JPA)
A foundational project to learn the **MVC (Model-View-Controller)** pattern combined with **JPA/Hibernate**.
- **Goal:** Implement clean CRUD operations using `EntityManager` and `EntityManagerFactory` instead of raw JDBC.
- **Key Features:**
  - Separates the View (Swing UI) from the DAO/Model, keeping SQL/JPQL out of the Swing forms.
  - Uses JPQL for database filtering and Java Stream API/Lambdas for in-memory data processing and event handling.
  - Employs `SwingUtilities.invokeLater()` to safely update the GUI on the Event Dispatch Thread.

### 3. Student Management - JPA One-To-Many
Expands on the MVC and JPA concepts by introducing relational database mapping.
- **Goal:** Manage a `Department` and `Student` system to demonstrate a `1:N` relationship.
- **Key Features:**
  - Uses `@OneToMany` and `@ManyToOne` annotations to map the relationship.
  - Demonstrates how to fetch and display related entities using `LEFT JOIN FETCH` in JPQL.
  - Implements `SwingWorker` for background database queries to prevent the UI from freezing.

### 4. Course Enrollment - JPA Many-To-Many
The most advanced project in this repository, handling complex `N:M` relationships.
- **Goal:** Manage a `Student` and `Course` registration system.
- **Key Features:**
  - Uses `@ManyToMany` and `@JoinTable` on the owning side to map the intermediate table.
  - Demonstrates how to enroll and unenroll students from courses using JPA entity lifecycle management.
  - Integrates advanced JPQL queries to filter students by their enrolled courses.

### 5. Sales MIS (Coffee Shop POS & Inventory) - The Capstone Project
This is the ultimate capstone project that integrates all previous knowledge into a complete, real-world application for the F&B industry.
- **Goal:** Build a comprehensive Point of Sale (POS) and Bill of Materials (BOM) inventory system.
- **Key Features:**
  - **Layered Architecture + MVC:** Fully decoupled layers using Constructor-based Dependency Injection.
  - **Auto Inventory Management:** Deducts raw ingredients (e.g., coffee beans, milk) based on multi-level recipes (Size M, Size L) when a product is sold.
  - **Performance Optimization:** Solves the JPA N+1 Query problem using `JOIN FETCH` and implements **JPQL DTO Projections** to generate 15+ complex reports without overloading RAM.
  - **Transaction Management:** Strict `try-catch-rollback` mechanisms in the Service layer to ensure ACID properties during checkout.
  - **PDF Export:** Integrates the `iText` library to generate professional invoices.

---

## 🚀 How to Run the Projects

### 1. **Clone the repository:**
```bash
git clone https://github.com/apase95/Introduction-Java-Project.git
cd Introduction-Java-Project/any-project
```

### 2. **Database Setup:**
- Ensure MySQL is running on `localhost:3306`.
- Execute the provided `.sql` scripts or copy the SQL creation commands found in the respective project folders to create the databases (e.g., `QuanLyKho`, `jpa_crud_demo`, etc.).

### 3. **Configure Connections:**
- For the **QL_Kho** project: Update your credentials in the `DBConnection.java` class.
- For **JPA** projects: Update the `<properties>` in the `src/main/resources/META-INF/persistence.xml` files.

### 4. **Build and Run:**
- Open the project in IntelliJ IDEA, Eclipse, or NetBeans.
- Reload Maven to download the required dependencies (like `mysql-connector-j` and `hibernate-core`).
- Run the `MainApp.java` or `MainForm.java` file of the specific project you want to test.

---

## 📬 Contact
- Email: hodtduy.work@gmail.com
- Linked In: [hodangthaiduy](https://www.linkedin.com/in/duy-ho-dang-thai-a33159383/)  

---
### Thank you for checking this repository!
Happy coding! 💻
