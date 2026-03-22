# 🚀 STUDENT - SWING - MVC - JPA - ONE - TO - MANY

[cite_start]This is a desktop Java project demonstrating a One-to-Many relationship using **JPA/Hibernate** and **Java Swing**[cite: 2, 3]. [cite_start]This project is designed for learning purposes and demonstrates how to implement the **MVC architecture**, execute **JPQL** queries, and use **Lambda expressions** for event handling and list processing[cite: 9, 11, 12].

## 🛠 System Requirements
* [cite_start][Java JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) [cite: 80, 81]
* [cite_start][Maven](https://maven.apache.org/) [cite: 19]
* [cite_start][MySQL Server](https://dev.mysql.com/downloads/mysql/) [cite: 20]

## 📂 Project Structure
* [cite_start]`pom.xml`: Configuration for Maven dependencies, including Hibernate Core (v7.3.0.Final) and MySQL Connector J (v9.6.0)[cite: 40, 69, 83, 84, 88, 89, 93, 94].
* [cite_start]`src/main/java/.../model/`: Contains Entities (`Department`, `Student`), DAOs (`StudentDao`, `DepartmentDao`), DTOs, and utility classes (`JpaUtil`)[cite: 51, 52, 53, 54, 55, 58, 59, 60, 61, 62].
* [cite_start]`src/main/java/.../view/`: Contains Swing GUI classes like `StudentView` and `StudentTableModel`[cite: 63, 64, 65].
* [cite_start]`src/main/java/.../controller/`: Manages events and data flow via `StudentController.java` to keep GUI logic separate from DB logic[cite: 46, 47, 888].
* [cite_start]`src/main/resources/META-INF/persistence.xml`: The standard configuration file for Jakarta Persistence[cite: 66, 67, 68, 107].

## 🚀 Setup and Launch Instructions

### 1. Database Setup
[cite_start]First, create the required MySQL database[cite: 99]:
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
Update the persistence.xml file with your MySQL credentials. Modify the following properties to match your local setup:
- URL: jdbc:mysql://localhost:3306/student_mvc_jpa?serverTimezone=UTC
- User: root 
- Password: 123456

### 3. Launch the Application
Run the application by executing the `MainApp.java` file. The Swing interface will be safely launched on the Event Dispatch Thread (EDT) using `SwingUtilities.invokeLater`

## ⚙️ JPA Configurations (persistence.xml)
You can customize how Hibernate interacts with the database by modifying properties in the `persistence.xml`.
```xml
<property name="hibernate.hbm2ddl.auto" value="update"/>
<property name="hibernate.show_sql" value="true"/>
<property name="hibernate.format_sql" value="true"/>
```
- `hibernate.hbm2ddl.auto="update"`: Automatically updates the database schema when the application runs.
- `hibernate.show_sql="true"`: Prints the executed SQL queries to the console, which is helpful for debugging.