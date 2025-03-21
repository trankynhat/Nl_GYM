DELIMITER //

CREATE TRIGGER prevent_overlapping_bookings
BEFORE INSERT ON bookings
FOR EACH ROW
BEGIN
    DECLARE conflict_count INT;

    -- Kiểm tra xem có lớp nào khác mà học viên này đã đăng ký trùng thời gian hay không
    SELECT COUNT(*) INTO conflict_count
    FROM bookings AS b
    JOIN classes AS c1 ON b.class_id = c1.id
    JOIN classes AS c2 ON NEW.class_id = c2.id
    WHERE b.customer_id = NEW.customer_id
    AND (
        (c2.start_time BETWEEN c1.start_time AND c1.end_time) 
        OR 
        (c2.end_time BETWEEN c1.start_time AND c1.end_time)
    );

    IF conflict_count > 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Bạn đã đăng ký một lớp khác trong cùng thời gian!';
    END IF;
END //

DELIMITER ;
