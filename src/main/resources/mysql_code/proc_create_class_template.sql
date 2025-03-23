DELIMITER //

CREATE PROCEDURE create_class_template(
    IN p_name VARCHAR(255),
    IN p_description TEXT,
    IN p_duration INT,
    IN p_max_participants INT
)
BEGIN
    -- Kiểm tra xem template có tồn tại chưa
    IF (SELECT COUNT(*) FROM class_templates WHERE name = p_name) > 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Tên mẫu lớp học đã tồn tại!';
    ELSE
        -- Thêm mẫu lớp học mới
        INSERT INTO class_templates (name, description, default_duration, max_participants)
        VALUES (p_name, p_description, p_duration, p_max_participants);
    END IF;
END //

DELIMITER ;
