USE sales_mis;

INSERT INTO customers(customer_code, full_name, phone, email, address, active) VALUES
('C001', 'Nguyen Van A', '0901000001', 'a.customer@example.com', 'Ho Chi Minh City', TRUE),
('C002', 'Tran Thi B', '0901000002', 'b.customer@example.com', 'Da Nang', TRUE),
('C003', 'Le Van C', '0901000003', 'c.customer@example.com', 'Ha Noi', TRUE),
('C004', 'Pham Thi D', '0901000004', 'd.customer@example.com', 'Can Tho', FALSE);

INSERT INTO products(sku, product_name, category, unit_price, stock_qty, active) VALUES
('P001', 'Laptop Dell Inspiron', 'Laptop', 15000000, 12, TRUE),
('P002', 'Wireless Mouse Logitech', 'Accessory', 350000, 50, TRUE),
('P003', 'Mechanical Keyboard', 'Accessory', 1200000, 25, TRUE),
('P004', '27-inch Monitor', 'Monitor', 4200000, 18, TRUE),
('P005', 'USB-C Hub', 'Accessory', 650000, 40, TRUE),
('P006', 'Office Chair', 'Furniture', 2800000, 10, TRUE);

INSERT INTO orders(order_no, order_date, customer_id, total_amount, status, note) VALUES
('SO001', '2026-03-01', 1, 15700000, 'CONFIRMED', 'First order'),
('SO002', '2026-03-05', 2, 5400000, 'COMPLETED', 'Office setup'),
('SO003', '2026-03-08', 1, 1850000, 'NEW', 'Accessories order');

INSERT INTO order_details(order_id, product_id, quantity, unit_price, line_total) VALUES
(1, 1, 1, 15000000, 15000000),
(1, 2, 2, 350000, 700000),
(2, 4, 1, 4200000, 4200000),
(2, 5, 1, 650000, 650000),
(2, 2, 1, 350000, 350000),
(2, 3, 1, 1200000, 1200000),
(3, 2, 1, 350000, 350000),
(3, 3, 1, 1200000, 1200000),
(3, 5, 1, 650000, 650000);
