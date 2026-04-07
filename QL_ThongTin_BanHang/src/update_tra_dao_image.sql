-- Cập nhật image_path cho Trà Đào Cam Sả
USE sales_mis;

UPDATE products 
SET image_path = 'tra-dao-cam-xa.png' 
WHERE product_name = 'Trà Đào Cam Sả' OR sku = 'P005';
