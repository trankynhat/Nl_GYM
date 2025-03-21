DELIMITER //

CREATE FUNCTION is_class_available(p_class_id INT) 
RETURNS BOOLEAN
DETERMINISTIC
BEGIN
    DECLARE current_count INT;
    DECLARE max_slots INT;

    -- Lấy số lượng học viên đã đăng ký
    SELECT COUNT(*) INTO current_count FROM bookings WHERE class_id = p_class_id;

    -- Lấy số lượng tối đa của lớp
    SELECT max_participants INTO max_slots FROM classes WHERE id = p_class_id;

    -- Trả về TRUE nếu còn chỗ, FALSE nếu đã đầy
    RETURN current_count < max_slots;
END //

DELIMITER ;
