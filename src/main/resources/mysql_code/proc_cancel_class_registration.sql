DELIMITER //

CREATE PROCEDURE cancel_class_registration(
    IN p_customer_id INT,
    IN p_class_id INT
)
BEGIN
    -- Kiểm tra xem học viên có đăng ký lớp này không
    IF (SELECT COUNT(*) FROM bookings WHERE customer_id = p_customer_id AND class_id = p_class_id) = 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Bạn chưa đăng ký lớp này!';
    ELSE
        -- Hủy đăng ký lớp
        DELETE FROM bookings WHERE customer_id = p_customer_id AND class_id = p_class_id;
    END IF;
END //

DELIMITER ;
