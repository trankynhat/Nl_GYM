DELIMITER //

CREATE TRIGGER prevent_exceeding_weekly_classes
BEFORE INSERT ON classes
FOR EACH ROW
BEGIN
    DECLARE weekly_limit INT;
    DECLARE current_count INT;

    -- Lấy số lớp tối đa mà Coach có thể tạo mỗi tuần
    SELECT max_classes_per_week INTO weekly_limit 
    FROM coaches 
    WHERE id = NEW.coach_id;

    -- Đếm số lớp Coach đã tạo trong tuần hiện tại
    SELECT COUNT(*) INTO current_count
    FROM classes
    WHERE coach_id = NEW.coach_id
    AND YEARWEEK(start_time, 1) = YEARWEEK(NOW(), 1);

    -- Nếu số lượng lớp đã đạt giới hạn, ngăn chặn việc tạo thêm lớp
    IF current_count >= weekly_limit THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Coach đã đạt giới hạn số lớp có thể tạo trong tuần!';
    END IF;
END //

DELIMITER ;
