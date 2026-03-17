CREATE DATABASE QuanLyKho;
USE QuanLyKho;

CREATE TABLE Categories (
    category_id INT PRIMARY KEY AUTO_INCREMENT,
    category_name VARCHAR(100) NOT NULL
);

CREATE TABLE Products (
    product_id INT PRIMARY KEY AUTO_INCREMENT,
    product_name VARCHAR(255) NOT NULL,
    price DOUBLE,
    quantity INT,
    category_id INT,
    FOREIGN KEY (category_id) REFERENCES Categories(category_id)
);

CREATE TABLE Customers (
    customer_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_name VARCHAR(255) NOT NULL,
    address VARCHAR(255),
    phone VARCHAR(20)
);

-- Thêm dữ liệu mẫu
INSERT INTO Categories (category_name) VALUES ('Điện thoại'), ('Laptop'), ('Phụ kiện');
INSERT INTO Products (product_name, price, quantity, category_id) VALUES ('iPhone 15', 25000000, 10, 1);
INSERT INTO Customers (customer_name, address, phone)
VALUES
    ('Nguyễn Văn A', 'Q. Thủ Đức, TP. HCM', '0901234567'),
    ('Trần Thị B', 'Q. 1, TP. HCM', '0987654321');