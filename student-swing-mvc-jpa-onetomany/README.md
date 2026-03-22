# 🚀 STUDENT - SWING - MVC - JPA - ONE to MANY

This is a desktop Java project demonstrating a One-to-Many relationship using **JPA/Hibernate** and **Java Swing**. This project is designed for learning purposes and demonstrates how to implement the **MVC architecture**, execute **JPQL** queries, and use **Lambda expressions** for event handling and list processing.

## 🛠 System Requirements
* [Java JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
* [Maven](https://maven.apache.org/)
* [MySQL Server](https://dev.mysql.com/downloads/mysql/)
## 📂 Project Structure
|Path|Description|
|:-:|:-:|
|`pom.xml`|Configuration for Maven dependencies, including Hibernate Core (v7.3.0.Final) and MySQL Connector J (v9.6.0).|
|`src/main/java/.../model/`|Contains Entities (`Department`, `Student`), DAOs (`StudentDao`, `DepartmentDao`), DTOs, and utility classes (`JpaUtil`).|
|`src/main/java/.../view/`|Contains Swing GUI classes like `StudentView` and `StudentTableModel`.|
|`src/main/java/.../controller/`|Manages events and data flow via `StudentController.java` to keep GUI logic separate from DB logic.|
|`src/main/resources/META-INF/persistence.xml`|The standard configuration file for Jakarta Persistence.|

## 🚀 Setup and Launch Instructions

### 1. Database Setup
First, create the required MySQL database:
```sql
CREATE DATABASE student_mvc_jpa 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

INSERT INTO departments (name) VALUES
('Information Systems'),
('Business Administration'),
('Data Science');
```
### 2. Configure Database Connection
Update the `persistence.xml` file with your MySQL credentials. Modify the following properties to match your local setup:
- URL: jdbc:mysql://localhost:3306/student_mvc_jpa?serverTimezone=UTC
- User: root 
- Password: 123456

### 3. Launch the Application
Run the application by executing the `MainApp.java` file. The Swing interface will be safely launched on the Event Dispatch Thread (EDT) using `SwingUtilities.invokeLater`

## ⚙️ JPA Configurations (`persistence.xml`)
You can customize how Hibernate interacts with the database by modifying properties in the `persistence.xml`.
```xml
<property name="hibernate.hbm2ddl.auto" value="update"/>
<property name="hibernate.show_sql" value="true"/>
<property name="hibernate.format_sql" value="true"/>
```
- `hibernate.hbm2ddl.auto="update"`: Automatically updates the database schema when the application runs.
- `hibernate.show_sql="true"`: Prints the executed SQL queries to the console, which is helpful for debugging.
