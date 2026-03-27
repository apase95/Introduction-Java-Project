USE sales_mis;

-- [Customers]
INSERT INTO customers(customer_code, full_name, phone, email, address, active) VALUES
('C001', 'Nguyen Van A', '0901000001', 'a.customer@example.com', 'Ho Chi Minh City', TRUE),
('C002', 'Tran Thi B', '0901000002', 'b.customer@example.com', 'Da Nang', TRUE),
('C003', 'Le Van C', '0901000003', 'c.customer@example.com', 'Ha Noi', TRUE);

-- [Categories]
INSERT INTO categories(category_code, category_name) VALUES
('CAT_CF', 'Cà Phê'),
('CAT_TS', 'Trà Sữa'),
('CAT_TTC', 'Trà Trái Cây'),
('CAT_ST', 'Sinh Tố'),
('CAT_DX', 'Đá Xay');

-- [Products]
INSERT INTO products(sku, product_name, category_id, unit_price, stock_qty, active) VALUES
('P001', 'Cà Phê Sữa Đá', 1, 29000, 100, TRUE), -- ID: 1
('P002', 'Bạc Xỉu', 1, 29000, 100, TRUE), -- ID: 2
('P003', 'Trà Sữa Trân Châu', 2, 45000, 150, TRUE), -- ID: 3
('P004', 'Trà Sữa Oolong', 2, 50000, 120, TRUE), -- ID: 4
('P005', 'Trà Đào Cam Sả', 3, 45000, 80, TRUE), -- ID: 5
('P006', 'Trà Vải Hạt Chia', 3, 45000, 90, TRUE), -- ID: 6
('P007', 'Sinh Tố Bơ', 4, 55000, 50, TRUE), -- ID: 7
('P008', 'Sinh Tố Dâu', 4, 55000, 60, TRUE), -- ID: 8
('P009', 'Matcha Đá Xay', 5, 60000, 40, TRUE), -- ID: 9
('P010', 'Cà Phê Đá Xay Caramel', 5, 65000, 45, TRUE); -- ID: 10

-- [Zones]
INSERT INTO zones(zone_code, zone_name, surcharge_percent) VALUES
('Z01', 'Tầng Trệt', 0),
('Z02', 'Tầng 1 (Phòng Lạnh)', 5.0),
('Z03', 'Sân Thượng', 10.0);

-- [Dining Tables]
INSERT INTO dining_tables(zone_id, table_name, status) VALUES
(1, 'Bàn T1-01', 'OCCUPIED'),
(1, 'Bàn T1-02', 'AVAILABLE'),
(2, 'Bàn Lạnh-01', 'AVAILABLE'),
(2, 'Bàn Lạnh-02', 'OCCUPIED'),
(3, 'Bàn ST-01', 'AVAILABLE');

-- [Ingredients]
INSERT INTO ingredients(ingredient_code, ingredient_name, unit, stock_qty) VALUES
('ING_CF', 'Hạt Cà Phê Robusta', 'g', 5000),
('ING_SM', 'Sữa Đặc', 'ml', 10000),
('ING_ST', 'Sữa Tươi', 'ml', 15000),
('ING_TD', 'Trà Đen', 'g', 2000),
('ING_DT', 'Đường Trắng', 'g', 3000),
('ING_TC', 'Trân Châu Đen', 'g', 4000);

-- [Recipes]
-- Recipe 1 for Cà Phê Sữa Đá (id 1): Size M
INSERT INTO recipes(product_id, variation_name) VALUES
(1, 'Size M - Chuẩn'), -- id 1
(1, 'Size L - Đậm Vị'), -- id 2
(3, 'Size M - Trân Châu Đen'); -- id 3

-- [Recipe Ingredients]
-- ID 1 (CFSD Size M) = 20g CF + 30ml Sữa Đặc + 10g Đường
INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity) VALUES
(1, 1, 20),
(1, 2, 30),
(1, 5, 10);
-- ID 2 (CFSD Size L) = 30g CF + 40ml Sữa Đặc + 15g Đường
INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity) VALUES
(2, 1, 30),
(2, 2, 40),
(2, 5, 15);
-- ID 3 (Trà Sữa TC) = 10g Trà + 50ml Sữa Tươi + 50g Trân Châu
INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity) VALUES
(3, 4, 10),
(3, 3, 50),
(3, 6, 50);

-- [Orders]
INSERT INTO orders(order_no, order_date, customer_id, table_id, total_amount, status, note) VALUES
('SO001', '2026-01-15', 1, 1, 103000, 'COMPLETED', 'First order in Jan'), -- Bàn T1-01
('SO002', '2026-02-10', 2, 4, 145000, 'COMPLETED', 'Office drinks Feb'); -- Bàn Lạnh-02

-- [Order Details]
INSERT INTO order_details(order_id, product_id, quantity, unit_price, line_total) VALUES
-- order 1
(1, 1, 2, 29000, 58000),
(1, 3, 1, 45000, 45000),
-- order 2
(2, 4, 2, 50000, 100000),
(2, 6, 1, 45000, 45000);
