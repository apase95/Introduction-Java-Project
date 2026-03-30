USE sales_mis;
DROP TRIGGER IF EXISTS trg_after_order_detail_insert;
DELIMITER //
CREATE TRIGGER trg_after_order_detail_insert
AFTER INSERT ON order_details
FOR EACH ROW
BEGIN
    IF NEW.recipe_id IS NOT NULL THEN
        UPDATE ingredients i
        JOIN recipe_ingredients ri ON i.id = ri.ingredient_id
        SET i.stock_qty = i.stock_qty - (ri.quantity * NEW.quantity)
        WHERE ri.recipe_id = NEW.recipe_id;
    END IF;
END //
DELIMITER ;
