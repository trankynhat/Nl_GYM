DELIMITER //

CREATE TRIGGER enforce_class_template
BEFORE INSERT ON classes
FOR EACH ROW
BEGIN
    DECLARE template_exists INT;

    -- Kiểm tra xem template_id có tồn tại trong bảng class_templates hay không
    SELECT COUNT(*) INTO template_exists 
    FROM class_templates 
    WHERE id = NEW.template_id;

    IF template_exists = 0 THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Coach chỉ có thể tạo lớp từ danh sách mẫu có sẵn!';
    END IF;
END //

DELIMITER ;
