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
('P001', 'Cà Phê Sữa Đá', 'Cà phê', 29000, 100, TRUE),
('P002', 'Bạc Xỉu', 'Cà phê', 29000, 100, TRUE),
('P003', 'Trà Sữa Trân Châu', 'Trà sữa', 45000, 150, TRUE),
('P004', 'Trà Sữa Oolong', 'Trà sữa', 50000, 120, TRUE),
('P005', 'Trà Đào Cam Sả', 'Trà trái cây', 45000, 80, TRUE),
('P006', 'Trà Vải Hạt Chia', 'Trà trái cây', 45000, 90, TRUE),
('P007', 'Sinh Tố Bơ', 'Sinh tố', 55000, 50, TRUE),
('P008', 'Sinh Tố Dâu', 'Sinh tố', 55000, 60, TRUE),
('P009', 'Matcha Đá Xay', 'Đá xay', 60000, 40, TRUE),
('P010', 'Cà Phê Đá Xay Caramel', 'Đá xay', 65000, 45, TRUE);

-- [Orders]
INSERT INTO orders(order_no, order_date, customer_id, total_amount, status, note) VALUES
('SO001', '2026-01-15', 1, 103000, 'COMPLETED', 'First order in Jan'),
('SO002', '2026-02-10', 2, 145000, 'COMPLETED', 'Office drinks Feb'),
('SO003', '2026-03-05', 1, 145000, 'CONFIRMED', 'Afternoon drinks Mar'),
('SO004', '2026-03-12', 3, 180000, 'NEW', 'Buying Matcha'),
('SO005', '2026-03-15', 2, 29000, 'CANCELLED', 'Cancelled bac xiu'),
('SO006', '2026-04-02', 1, 110000, 'NEW', 'Smoothies only'),
('SO007', '2026-04-05', 3, 65000, 'CONFIRMED', 'Drink for home office');

-- [Order Details]
INSERT INTO order_details(order_id, product_id, quantity, unit_price, line_total) VALUES
(1, 1, 2, 29000, 58000),
(1, 3, 1, 45000, 45000),
(2, 4, 2, 50000, 100000),
(2, 6, 1, 45000, 45000),
(3, 5, 2, 45000, 90000),
(3, 7, 1, 55000, 55000),
(4, 9, 3, 60000, 180000),
(5, 2, 1, 29000, 29000),
(6, 8, 2, 55000, 110000),
(7, 10, 1, 65000, 65000);
