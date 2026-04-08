# ☕ Sales MIS - Coffee Shop POS & Inventory Management

This project is a comprehensive **Point of Sale (POS)** and **Inventory Management System** designed for F&B businesses (Coffee shops, Milk tea stores, Restaurants). It demonstrates an advanced enterprise-level technology stack using **Java Swing**, **Layered Architecture + MVC**, **JPA/Hibernate (ORM)**, and **JPQL DTO Projections**.

Specifically, it solves the complex F&B problem of **BOM (Bill of Materials)**: dynamically deducting raw ingredients (e.g., coffee beans, milk) based on multi-level recipes (Size M, Size L) whenever a final product is sold.

## 🛠 System Requirements
- [Java JDK 17+](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Maven](https://maven.apache.org/)
- [MySQL Server 8.x](https://dev.mysql.com/downloads/mysql/)
- **iText** (PDF Generation Library - Handled by Maven `pom.xml`)

## 📂 Project Structure
The project strictly follows a **Layered Architecture** to ensure Single Responsibility and clean code:

| Path | Description |
|:---|:---|
| `.../config/` | Contains `JpaUtil` for `EntityManagerFactory` singleton management. |
| `.../model/entity/` | JPA Entities (`Product`, `Recipe`, `SalesOrder`, `Ingredient`, etc.) with advanced mappings (`@OneToMany`, Cascade, Orphan Removal). |
| `.../model/dto/` | Data Transfer Objects used for JPQL Projections (Optimizing RAM for Reports). |
| `.../dao/` | Data Access Object layer executing complex JPQL queries (e.g., `JOIN FETCH` to fix N+1 issues). |
| `.../service/` | Business Logic layer handling strict rules (Stock validation, Auto-deduction, Transaction rollback). |
| `.../controller/` | Bridges the View and Service layers using **Constructor-based Dependency Injection**. |
| `.../view/` | Java Swing GUI components split into modular `JPanel`s (POS, Reports, Recipes). |
| `.../util/` | Utility classes like `PdfExportUtil` for generating professional PDF invoices. |
| `persistence.xml`| The core JPA configuration file (`salesPU`). |

## 🚀 Setup and Launch Instructions

### 1. Database Setup
The database schema and sample data are split into 3 SQL files. Execute them in your MySQL Server in the following order:
1. Run `SaleMisDatabase1.sql` (Creates the database `sales_mis`).
2. Run `SaleMisDatabase2.sql` (Creates tables, constraints, and the `trg_after_order_detail_insert` trigger).
3. Run `SaleMisDatabase3.sql` (Inserts mock data: 5 customers, 10 products, 11 ingredients, 9 recipes, 10 orders).

### 2. Configure Database Connection
Update the `src/main/resources/META-INF/persistence.xml` file with your MySQL credentials, targeting the `salesPU` persistence unit. Modify the properties to match your local setup:
```xml
<property name="jakarta.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/sales_mis?useSSL=false&amp;serverTimezone=Asia/Ho_Chi_Minh"/>
<property name="jakarta.persistence.jdbc.user" value="root"/>
<property name="jakarta.persistence.jdbc.password" value="YOUR_PASSWORD"/>
```

### 3. Launch the Application
Run the application by executing the AppLauncher.java file. The application uses SwingUtilities.invokeLater to safely launch the GUI on the Event Dispatch Thread (EDT).

### 🔑 Default Login Credentials:
- **Admin Role:** Username: admin | Password: 123456 (Full access including Reports & Ingredients)
- **Staff Role:** Username: staff | Password: 123456 (Access limited to POS and Orders)

<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/6846ce88-b532-461f-93a4-98402367f6cc" />
<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/ef43e88b-563d-407b-a8bd-d92fc33418d8" />
<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/c4ec834b-9e43-4ef5-ae8d-3c1968db555e" />


### ⚙️ Advanced JPA & Architecture Highlights
Unlike basic CRUD applications, this project implements enterprise-level database interaction techniques:
- Solving N+1 Query Problem: Instead of relying on default Lazy Fetching which executes hundreds of hidden queries, the DAOs use JOIN FETCH in JPQL to load Orders, Customers, and OrderDetails in a single, highly optimized query.
- DTO Projections for Reports: The ReportDAOImpl generates 15+ complex reports (Revenue by Month, Top Selling Products). Instead of loading heavy Entities into RAM, it uses JPQL to map query results directly to lightweight Java Records/DTOs SELECT new com.example...dto.MonthlyRevenueDTO(...).
- Cascade & Orphan Removal: Parent-child relationships (SalesOrder -> OrderDetail, Recipe -> RecipeIngredient) are strictly managed by JPA. Deleting a parent automatically cleans up child records, preventing database garbage.
- Intentional Denormalization: The order_details table explicitly saves unit_price and line_total. This is a deliberate architectural decision to preserve historical accounting accuracy even if the base product price changes in the future.
