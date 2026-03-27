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
('ING_TC', 'Trân Châu Đen', 'g', 4000),
('ING_MC', 'Bột Matcha', 'g', 1500),
('ING_BX', 'Sữa Chua', 'ml', 5000),
('ING_SI', 'Lá Cam Sả', 'g', 1000),
('ING_VA', 'Vải Ngâm', 'g', 2000),
('ING_BO', 'Bơ Tươi', 'g', 3000);

-- [Recipes]
-- Recipe for Cà Phê Sữa Đá (id 1)
INSERT INTO recipes(product_id, variation_name) VALUES
(1, 'Size M - Chuẩn'), -- id 1
(1, 'Size L - Đậm Vị'), -- id 2
(2, 'Bạc Xỉu - Nóng'), -- id 3
(2, 'Bạc Xỉu - Đá'), -- id 4
(3, 'Size M - Trân Châu Đen'), -- id 5
(3, 'Size L - Trân Châu Đen (Thêm Đường)'), -- id 6
(5, 'Size M - Cam Sả Mát Lạnh'), -- id 7
(7, 'Sinh Tố Bơ - Size L'), -- id 8
(9, 'Matcha Đá Xay - Đặc Biệt'); -- id 9

-- [Recipe Ingredients]
-- ID 1 (CFSD Size M) = 20g CF + 30ml Sữa Đặc + 10g Đường
INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity) VALUES
(1, 1, 20), (1, 2, 30), (1, 5, 10);
-- ID 2 (CFSD Size L) = 30g CF + 40ml Sữa Đặc + 15g Đường
INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity) VALUES
(2, 1, 30), (2, 2, 40), (2, 5, 15);
-- ID 3 (Bạc Xỉu Nóng) = 15g CF + 50ml Sữa Tươi + 20ml Sữa Đặc
INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity) VALUES
(3, 1, 15), (3, 3, 50), (3, 2, 20);
-- ID 5 (Trà Sữa TC Size M) = 10g Trà + 50ml Sữa Tươi + 50g Trân Châu
INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity) VALUES
(5, 4, 10), (5, 3, 50), (5, 6, 50);
-- ID 7 (Trà Đào Cam Sả) = 15g Trà Đen + 10g Cam Sả + 20g Đường
INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity) VALUES
(7, 4, 15), (7, 9, 10), (7, 5, 20);
-- ID 8 (Sinh Tố Bơ) = 100g Bơ + 50ml Sữa Tươi + 30ml Sữa Đặc
INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity) VALUES
(8, 11, 100), (8, 3, 50), (8, 2, 30);
-- ID 9 (Matcha Đá Xay) = 15g Matcha + 100ml Sữa Tươi + 20g Đường
INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity) VALUES
(9, 7, 15), (9, 3, 100), (9, 5, 20);

-- [Orders]
INSERT INTO orders(order_no, order_date, customer_id, table_id, total_amount, status, note) VALUES
('SO001', '2026-03-01', 1, 1, 103000, 'COMPLETED', 'Đơn hàng buổi sáng'), -- Bàn T1-01
('SO002', '2026-03-05', 2, 4, 145000, 'COMPLETED', 'Khách VIP đổi bàn'), -- Bàn Lạnh-02
('SO003', '2026-03-12', 3, NULL, 55000, 'COMPLETED', 'Takeaway nhanh'), -- Takeaway
('SO004', '2026-03-24', 1, 3, 120000, 'NEW', 'Đang pha chế'), -- Bàn Lạnh-01
('SO005', '2026-03-25', 2, 5, 210000, 'CONFIRMED', 'Hẹn nhóm bạn'); -- Bàn ST-01

-- [Order Details]
INSERT INTO order_details(order_id, product_id, recipe_id, quantity, unit_price, line_total) VALUES
-- Order 1: 2 CFSD Size M, 1 TS Trân Châu Size M
(1, 1, 1, 2, 29000, 58000),
(1, 3, 5, 1, 45000, 45000),

-- Order 2: 2 Bạc Xỉu Nóng, 1 Trà Vải Hạt Chia (Không size)
(2, 2, 3, 2, 50000, 100000),
(2, 6, NULL, 1, 45000, 45000),

-- Order 3: 1 Sinh Tố Bơ Size L
(3, 7, 8, 1, 55000, 55000),

-- Order 4: 2 CFSD Size L, 1 Matcha Đá Xay
(4, 1, 2, 2, 30000, 60000),
(4, 9, 9, 1, 60000, 60000),

-- Order 5: 3 TS Trân Châu Size M, 1 Trà Đào Cam Sả, 1 Sinh Tố Bơ
(5, 3, 5, 3, 45000, 135000),
(5, 5, 7, 1, 45000, 45000),
(5, 7, 8, 1, 30000, 30000);
