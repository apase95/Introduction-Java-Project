USE sales_mis;

-- ============================================================
-- [Customers] - 5 khách hàng
-- ============================================================
INSERT INTO customers(customer_code, full_name, phone, email, address, active) VALUES
('C001', 'Nguyễn Văn An',     '0901234501', 'an.nguyen@gmail.com',   '12 Lê Lợi, Q.1, TP.HCM',         TRUE),
('C002', 'Trần Thị Bích',     '0901234502', 'bich.tran@gmail.com',   '45 Trần Phú, Q.Hải Châu, Đà Nẵng', TRUE),
('C003', 'Lê Hoàng Cường',    '0901234503', 'cuong.le@yahoo.com',    '78 Kim Mã, Q.Ba Đình, Hà Nội',    TRUE),
('C004', 'Phạm Thị Dung',     '0901234504', 'dung.pham@outlook.com', '99 Ngô Quyền, TP. Huế',           TRUE),
('C005', 'Hoàng Minh Đức',    '0901234505', 'duc.hoang@gmail.com',   '33 Pasteur, Q.3, TP.HCM',         TRUE);

-- ============================================================
-- [Categories] - 5 danh mục đồ uống
-- ============================================================
INSERT INTO categories(category_code, category_name, active) VALUES
('CAT_CF',  'Cà Phê',       TRUE),
('CAT_TS',  'Trà Sữa',      TRUE),
('CAT_TTC', 'Trà Trái Cây', TRUE),
('CAT_ST',  'Sinh Tố',      TRUE),
('CAT_DX',  'Đá Xay',       TRUE);

-- ============================================================
-- [Products] - 10 sản phẩm (id 1-10)
-- ============================================================
INSERT INTO products(sku, product_name, category_id, unit_price, stock_qty, active, image_path) VALUES
('P001', 'Cà Phê Sữa Đá',         1, 29000, 100, TRUE, 'cf_sua.png'),          -- id 1
('P002', 'Bạc Xỉu',               1, 29000, 100, TRUE, 'bac-xiu.png'),         -- id 2
('P003', 'Trà Sữa Trân Châu',      2, 45000, 150, TRUE, 'tra-sua-tran-chau.png'),-- id 3
('P004', 'Trà Sữa Oolong',         2, 50000, 120, TRUE, 'tra-sua-Oolong.png'),   -- id 4
('P005', 'Trà Đào Cam Sả',         3, 45000,  80, TRUE, 'tra-dao-cam-xa.png'),   -- id 5
('P006', 'Trà Vải Hạt Chia',       3, 45000,  90, TRUE, 'tra-vai-hat-chia.png'), -- id 6
('P007', 'Sinh Tố Bơ',             4, 55000,  50, TRUE, 'sinh-to-bo.png'),       -- id 7
('P008', 'Sinh Tố Dâu',            4, 55000,  60, TRUE, 'sinh-to-dau.png'),      -- id 8
('P009', 'Matcha Đá Xay',          5, 60000,  40, TRUE, 'matcha-da-xay.png'),    -- id 9
('P010', 'Cà Phê Đá Xay Caramel',  5, 65000,  45, TRUE, 'socola-da-xay.png');    -- id 10

-- ============================================================
-- [Zones] - 3 khu vực
-- ============================================================
INSERT INTO zones(zone_code, zone_name, surcharge_percent, active) VALUES
('Z01', 'Tầng Trệt',              0.00, TRUE),
('Z02', 'Tầng 1 (Phòng Lạnh)',    5.00, TRUE),
('Z03', 'Sân Thượng',            10.00, TRUE);

-- ============================================================
-- [Dining Tables] - 6 bàn (id 1-6)
-- ============================================================
INSERT INTO dining_tables(zone_id, table_name, status, active) VALUES
(1, 'Bàn T1-01',   'OCCUPIED',  TRUE),  -- id 1
(1, 'Bàn T1-02',   'AVAILABLE', TRUE),  -- id 2
(2, 'Bàn Lạnh-01', 'AVAILABLE', TRUE),  -- id 3
(2, 'Bàn Lạnh-02', 'OCCUPIED',  TRUE),  -- id 4
(3, 'Bàn ST-01',   'AVAILABLE', TRUE),  -- id 5
(3, 'Bàn ST-02',   'AVAILABLE', TRUE);  -- id 6

-- ============================================================
-- [Ingredients] - 11 nguyên liệu
-- ============================================================
INSERT INTO ingredients(ingredient_code, ingredient_name, unit, stock_qty, active) VALUES
('ING_CF',  'Hạt Cà Phê Robusta', 'g',   5000.00, TRUE),  -- id 1
('ING_SM',  'Sữa Đặc',            'ml', 10000.00, TRUE),  -- id 2
('ING_ST',  'Sữa Tươi',           'ml', 15000.00, TRUE),  -- id 3
('ING_TD',  'Trà Đen',            'g',   2000.00, TRUE),  -- id 4
('ING_DT',  'Đường Trắng',        'g',   3000.00, TRUE),  -- id 5
('ING_TC',  'Trân Châu Đen',      'g',   4000.00, TRUE),  -- id 6
('ING_MC',  'Bột Matcha',         'g',   1500.00, TRUE),  -- id 7
('ING_SC',  'Sữa Chua',           'ml',  5000.00, TRUE),  -- id 8
('ING_CS',  'Lá Cam Sả',          'g',   1000.00, TRUE),  -- id 9
('ING_VA',  'Vải Ngâm',           'g',   2000.00, TRUE),  -- id 10
('ING_BO',  'Bơ Tươi',            'g',   3000.00, TRUE);  -- id 11

-- ============================================================
-- [Recipes] - 9 công thức (id 1-9)
-- ============================================================
INSERT INTO recipes(product_id, variation_name, active) VALUES
(1,  'Size M – Chuẩn',                    TRUE),  -- id 1
(1,  'Size L – Đậm Vị',                   TRUE),  -- id 2
(2,  'Bạc Xỉu – Nóng',                    TRUE),  -- id 3
(2,  'Bạc Xỉu – Đá',                      TRUE),  -- id 4
(3,  'Size M – Trân Châu Đen',             TRUE),  -- id 5
(3,  'Size L – Trân Châu Đen (Thêm Đường)',TRUE),  -- id 6
(5,  'Size M – Cam Sả Mát Lạnh',          TRUE),  -- id 7
(7,  'Sinh Tố Bơ – Size L',               TRUE),  -- id 8
(9,  'Matcha Đá Xay – Đặc Biệt',          TRUE);  -- id 9

-- ============================================================
-- [Recipe Ingredients]
-- ============================================================
-- id 1 (CFSD Size M): 20g Cà Phê + 30ml Sữa Đặc + 10g Đường
INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity) VALUES
(1, 1, 20.00), (1, 2, 30.00), (1, 5, 10.00);

-- id 2 (CFSD Size L): 30g Cà Phê + 40ml Sữa Đặc + 15g Đường
INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity) VALUES
(2, 1, 30.00), (2, 2, 40.00), (2, 5, 15.00);

-- id 3 (Bạc Xỉu Nóng): 15g Cà Phê + 50ml Sữa Tươi + 20ml Sữa Đặc
INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity) VALUES
(3, 1, 15.00), (3, 3, 50.00), (3, 2, 20.00);

-- id 4 (Bạc Xỉu Đá): 15g Cà Phê + 50ml Sữa Tươi + 20ml Sữa Đặc
INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity) VALUES
(4, 1, 15.00), (4, 3, 50.00), (4, 2, 20.00);

-- id 5 (TS Trân Châu Size M): 10g Trà Đen + 50ml Sữa Tươi + 50g Trân Châu
INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity) VALUES
(5, 4, 10.00), (5, 3, 50.00), (5, 6, 50.00);

-- id 6 (TS Trân Châu Size L): 15g Trà Đen + 80ml Sữa Tươi + 70g Trân Châu + 10g Đường
INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity) VALUES
(6, 4, 15.00), (6, 3, 80.00), (6, 6, 70.00), (6, 5, 10.00);

-- id 7 (Trà Đào Cam Sả Size M): 15g Trà Đen + 10g Lá Cam Sả + 20g Đường
INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity) VALUES
(7, 4, 15.00), (7, 9, 10.00), (7, 5, 20.00);

-- id 8 (Sinh Tố Bơ Size L): 100g Bơ + 50ml Sữa Tươi + 30ml Sữa Đặc
INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity) VALUES
(8, 11, 100.00), (8, 3, 50.00), (8, 2, 30.00);

-- id 9 (Matcha Đá Xay Đặc Biệt): 15g Matcha + 100ml Sữa Tươi + 20g Đường
INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity) VALUES
(9, 7, 15.00), (9, 3, 100.00), (9, 5, 20.00);

-- ============================================================
-- [Orders] - 10 đơn hàng
-- ============================================================
INSERT INTO orders(order_no, order_date, customer_id, table_id, total_amount, status, note) VALUES
('SO001', '2026-03-01', 1, 1, 103000.00, 'COMPLETED', 'Đơn buổi sáng đầu tháng'),    -- id 1
('SO002', '2026-03-05', 2, 4, 145000.00, 'COMPLETED', 'Khách VIP ngồi phòng lạnh'), -- id 2
('SO003', '2026-03-08', 3, NULL, 55000.00, 'COMPLETED', 'Takeaway – mang về'),       -- id 3
('SO004', '2026-03-12', 4, 3, 120000.00, 'COMPLETED', 'Thanh toán thẻ'),            -- id 4
('SO005', '2026-03-15', 5, 5, 210000.00, 'COMPLETED', 'Buổi họp nhóm sân thượng'),  -- id 5
('SO006', '2026-03-18', 1, 2, 90000.00,  'COMPLETED', 'Khách quen – giảm giá 10%'), -- id 6
('SO007', '2026-03-20', 2, NULL, 65000.00, 'COMPLETED', 'Takeaway – đặt online'),   -- id 7
('SO008', '2026-03-22', 3, 6, 175000.00, 'COMPLETED', 'Sinh nhật khách hàng'),      -- id 8
('SO009', '2026-03-25', 4, 1, 135000.00, 'CONFIRMED', 'Đang pha chế – ưu tiên'),    -- id 9
('SO010', '2026-03-29', 5, 5, 200000.00, 'NEW',       'Đơn mới – chờ xác nhận');    -- id 10

-- ============================================================
-- [Order Details]
-- ============================================================
-- Đơn SO001: 2x CFSD Size M + 1x Trà Sữa TC Size M
INSERT INTO order_details(order_id, product_id, recipe_id, quantity, unit_price, line_total) VALUES
(1, 1, 1, 2, 29000.00,  58000.00),
(1, 3, 5, 1, 45000.00,  45000.00);

-- Đơn SO002: 2x Bạc Xỉu Nóng + 1x Trà Vải Hạt Chia (không size riêng)
INSERT INTO order_details(order_id, product_id, recipe_id, quantity, unit_price, line_total) VALUES
(2, 2, 3, 2, 50000.00, 100000.00),
(2, 6, NULL, 1, 45000.00, 45000.00);

-- Đơn SO003: 1x Sinh Tố Bơ Size L – Takeaway
INSERT INTO order_details(order_id, product_id, recipe_id, quantity, unit_price, line_total) VALUES
(3, 7, 8, 1, 55000.00, 55000.00);

-- Đơn SO004: 1x CFSD Size L + 1x Matcha Đá Xay
INSERT INTO order_details(order_id, product_id, recipe_id, quantity, unit_price, line_total) VALUES
(4, 1, 2, 1, 30000.00, 30000.00),
(4, 9, 9, 1, 60000.00, 60000.00),
(4, 4, NULL, 1, 30000.00, 30000.00);

-- Đơn SO005: 3x Trà Sữa TC Size M + 1x Trà Đào Cam Sả + 1x Sinh Tố Bơ
INSERT INTO order_details(order_id, product_id, recipe_id, quantity, unit_price, line_total) VALUES
(5, 3, 5,    3, 45000.00, 135000.00),
(5, 5, 7,    1, 45000.00,  45000.00),
(5, 7, 8,    1, 30000.00,  30000.00);

-- Đơn SO006: 2x Bạc Xỉu Đá + 1x Trà Đào Cam Sả
INSERT INTO order_details(order_id, product_id, recipe_id, quantity, unit_price, line_total) VALUES
(6, 2, 4, 2, 30000.00, 60000.00),
(6, 5, 7, 1, 30000.00, 30000.00);

-- Đơn SO007: 1x Cà Phê Đá Xay Caramel – Takeaway
INSERT INTO order_details(order_id, product_id, recipe_id, quantity, unit_price, line_total) VALUES
(7, 10, NULL, 1, 65000.00, 65000.00);

-- Đơn SO008: 2x Trà Sữa Oolong + 1x Sinh Tố Dâu + 1x Matcha Đá Xay
INSERT INTO order_details(order_id, product_id, recipe_id, quantity, unit_price, line_total) VALUES
(8, 4,  NULL, 2, 50000.00, 100000.00),
(8, 8,  NULL, 1, 55000.00,  55000.00),
(8, 9,  9,    1, 60000.00,  60000.00), -- Matcha + 10k phụ phí tầng thượng → ghi trong note
(8, 6,  NULL, 1, 45000.00,  45000.00); -- Giảm 1 order detail vì trùng tổng. sửa SO008 tổng ở trên đã đủ

-- Đơn SO009: 2x CFSD Size L + 1x Trà Vải Hạt Chia
INSERT INTO order_details(order_id, product_id, recipe_id, quantity, unit_price, line_total) VALUES
(9, 1, 2, 2, 30000.00,  60000.00),
(9, 6, NULL, 1, 45000.00, 45000.00),
(9, 3, 5,    1, 30000.00, 30000.00);

-- Đơn SO010: 2x TS TC Size L + 1x CFSD Size M + 1x Sinh Tố Dâu
INSERT INTO order_details(order_id, product_id, recipe_id, quantity, unit_price, line_total) VALUES
(10, 3, 6, 2, 50000.00, 100000.00),
(10, 1, 1, 1, 29000.00,  29000.00),
(10, 8, NULL, 1, 55000.00, 55000.00),
(10, 5, 7,   1, 16000.00, 16000.00);
