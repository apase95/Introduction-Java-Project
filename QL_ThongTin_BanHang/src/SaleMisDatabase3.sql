USE sales_mis;

-- [Customers]
INSERT INTO customers(customer_code, full_name, phone, email, address, active) VALUES
('C001', 'Nguyen Van A', '0901000001', 'a.customer@example.com', 'Ho Chi Minh City', TRUE),
('C002', 'Tran Thi B', '0901000002', 'b.customer@example.com', 'Da Nang', TRUE),
('C003', 'Le Van C', '0901000003', 'c.customer@example.com', 'Ha Noi', TRUE),
('C004', 'Pham Thi D', '0901000004', 'd.customer@example.com', 'Can Tho', FALSE),
('C005', 'Hoang Van E', '0901000005', 'e.customer@example.com', 'Hai Phong', TRUE),
('C006', 'Dinh Thi F', '0901000006', 'f.customer@example.com', 'Hue', TRUE);

-- [Products]
INSERT INTO products(sku, product_name, category, unit_price, stock_qty, active) VALUES
('P001', 'Laptop Dell Inspiron', 'Laptop', 15000000, 12, TRUE),
('P002', 'Wireless Mouse Logitech', 'Accessory', 350000, 50, TRUE),
('P003', 'Mechanical Keyboard', 'Accessory', 1200000, 25, TRUE),
('P004', '27-inch Monitor', 'Monitor', 4200000, 18, TRUE),
('P005', 'USB-C Hub', 'Accessory', 650000, 40, TRUE),
('P006', 'Office Chair', 'Furniture', 2800000, 10, TRUE),
('P007', 'Webcam 1080p', 'Accessory', 800000, 2, TRUE),
('P008', 'Ergonomic Mouse', 'Accessory', 450000, 0, TRUE),
('P009', 'Laptop Stand', 'Accessory', 250000, 5, TRUE),
('P010', 'MacBook Air M2', 'Laptop', 25000000, 8, TRUE);

-- [Orders]
INSERT INTO orders(order_no, order_date, customer_id, total_amount, status, note) VALUES
('SO001', '2026-01-15', 1, 15700000, 'COMPLETED', 'First order in Jan'),
('SO002', '2026-02-10', 2, 5400000, 'COMPLETED', 'Office setup Feb'),
('SO003', '2026-03-05', 1, 1850000, 'CONFIRMED', 'Accessories order Mar'),
('SO004', '2026-03-12', 3, 25000000, 'NEW', 'Buying Macbook'),
('SO005', '2026-03-15', 2, 800000, 'CANCELLED', 'Cancelled webcam'),
('SO006', '2026-04-02', 1, 4200000, 'NEW', 'Monitor only'),
('SO007', '2026-04-05', 3, 2800000, 'CONFIRMED', 'Chair for home office');

-- [Order Details]
INSERT INTO order_details(order_id, product_id, quantity, unit_price, line_total) VALUES
-- order 1
(1, 1, 1, 15000000, 15000000),
(1, 2, 2, 350000, 700000),

-- order 2
(2, 4, 1, 4200000, 4200000),
(2, 3, 1, 1200000, 1200000),

-- order 3
(3, 3, 1, 1200000, 1200000),
(3, 5, 1, 650000, 650000),

-- order 4
(4, 10, 1, 25000000, 25000000),

-- order 5
(5, 7, 1, 800000, 800000),

-- order 6
(6, 4, 1, 4200000, 4200000),

-- order 7
(7, 6, 1, 2800000, 2800000);
