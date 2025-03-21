DELIMITER //

CREATE FUNCTION get_coach_weekly_classes(p_coach_id INT) 
RETURNS INT
DETERMINISTIC
BEGIN
    DECLARE class_count INT;

    -- Đếm số lớp mà Coach đã tạo trong tuần hiện tại
    SELECT COUNT(*) INTO class_count 
    FROM classes 
    WHERE coach_id = p_coach_id 
    AND YEARWEEK(start_time, 1) = YEARWEEK(NOW(), 1);

    RETURN class_count;
END //

DELIMITER ;
