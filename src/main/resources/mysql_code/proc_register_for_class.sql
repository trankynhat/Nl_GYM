DELIMITER //

CREATE PROCEDURE register_for_class(
    IN p_customer_id INT,
    IN p_class_id INT
)
BEGIN
    DECLARE current_count INT;
    DECLARE max_slots INT;

    -- Lấy số lượng học viên hiện tại trong lớp
    SELECT COUNT(*) INTO current_count FROM bookings WHERE class_id = p_class_id;

    -- Lấy giới hạn tối đa của lớp
    SELECT max_participants INTO max_slots FROM classes WHERE id = p_class_id;

    -- Kiểm tra nếu lớp đã đầy
    IF current_count >= max_slots THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Lớp đã đầy! Không thể đăng ký thêm.';
    ELSE
        -- Tiến hành đăng ký
        INSERT INTO bookings (customer_id, class_id) VALUES (p_customer_id, p_class_id);
    END IF;
END //

DELIMITER ;
