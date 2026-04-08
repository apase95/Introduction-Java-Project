# 🚀 Student - Project - JPA - ManyToMany

This project demonstrates a complete Many-to-Many relationship using the same technology stack: Java Swing, MVC architecture, JPA/Hibernate, JPQL, and lambda expressions. Specifically, it models a system where a `Student` can enroll in multiple `Course`s, and a `Course` can have multiple `Student`.

## 🛠 System Requirements
- [Java JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Maven](https://maven.apache.org/)
- [MySQL Server](https://dev.mysql.com/downloads/mysql/)
- [Java Swing]()

## 📂 Project Structure
|Path|Description|
|:-:|:-:|
|`src/main/java/.../model/`|Contains Entities (`Student`, `Course`), DAOs (`StudentDao`, `CourseDao`), and utility classes (`JpaUtil`)                   |
|`src/main/java/.../view/`|Contains GUI components like `StudentCourseView` and `StudentTableModel`                                    |
|`src/main/java/.../controller/`|Contains `StudentCourseController.java` to handle the MVC flow, integrating lambda expressions and `SwingWorker` for background database queries|
|`persistence.xml`|The persistence configuration file|

## 🚀 Setup and Launch Instructions

### 1. Database Setup
First, create the required MySQL database and set the character encoding:
```sql
CREATE DATABASE student_course_mtm 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

INSERT INTO courses (course_name) VALUES
('Java Programming'),
('Database Systems'),
('Web Development'),
('Software Testing');
```
### 2. Configure Database Connection
Update the `persistence.xml` file with your MySQL credentials, targeting the `student-course-pu` persistence unit. Modify the following properties to match your local setup:
- URL: `jdbc:mysql://localhost:3306/student_mvc_jpa?serverTimezone=UTC`
- User: `root`
- Password: `123456`

### 3. Launch the Application
Run the application by executing the `MainApp.java` file. The Swing interface will be safely launched on the Event Dispatch Thread (EDT) using `SwingUtilities.invokeLater`

<img width="1236" height="742" alt="image" src="https://github.com/user-attachments/assets/7deeeede-ec01-4147-8b60-6417fad237f7" />

## ⚙️ JPA Configurations & Many-to-Many Mapping
You can customize how Hibernate interacts with the database by modifying properties in the `persistence.xml`.
```xml
<property name="hibernate.hbm2ddl.auto" value="update"/>
<property name="hibernate.show_sql" value="true"/>
<property name="hibernate.format_sql" value="true"/>
```
- `hibernate.hbm2ddl.auto="update"`: Automatically updates the database schema when the application runs.
- `hibernate.show_sql="true"`: Prints the executed SQL queries to the console, which is helpful for debugging.

**Mapping Details:** In this bidirectional relationship, the `Student` entity is the owning side where `@JoinTable` is configured to map the `student_course` intermediate table. The `Course` entity acts as the inverse side using `mappedBy = "courses"`
