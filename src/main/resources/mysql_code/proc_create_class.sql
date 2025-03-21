DELIMITER //

CREATE PROCEDURE create_class(
    IN p_coach_id INT,
    IN p_template_id INT,
    IN p_start_time DATETIME,
    IN p_end_time DATETIME,
    IN p_max_participants INT
)
BEGIN
    DECLARE weekly_limit INT;
    DECLARE current_count INT;
    DECLARE template_exists INT;
    DECLARE coach_exists INT;

    -- Kiểm tra xem Coach có tồn tại không
    SELECT COUNT(*) INTO coach_exists FROM coaches WHERE user_id = p_coach_id;
    IF coach_exists = 0 THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Coach không hợp lệ!';
    END IF;

    -- Kiểm tra template có tồn tại không
    SELECT COUNT(*) INTO template_exists FROM class_templates WHERE id = p_template_id;
    IF template_exists = 0 THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Template lớp học không hợp lệ!';
    END IF;

    -- Kiểm tra thời gian hợp lệ
    IF p_end_time <= p_start_time THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Thời gian kết thúc phải lớn hơn thời gian bắt đầu!';
    END IF;

    -- Lấy số lớp tối đa mà Coach có thể tạo mỗi tuần
    SELECT max_classes_per_week INTO weekly_limit FROM coaches WHERE user_id = p_coach_id;

    -- Đếm số lớp Coach đã tạo trong tuần hiện tại
    SELECT COUNT(*) INTO current_count
    FROM classes
    WHERE coach_id = p_coach_id
    AND YEARWEEK(start_time, 1) = YEARWEEK(NOW(), 1);

    -- Nếu số lượng lớp đã đạt giới hạn, ngăn chặn việc tạo thêm lớp
    IF current_count >= weekly_limit THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Coach đã đạt giới hạn số lớp có thể tạo trong tuần!';
    ELSE
        -- Tạo lớp học mới
        INSERT INTO classes (template_id, coach_id, start_time, end_time, max_participants)
        VALUES (p_template_id, p_coach_id, p_start_time, p_end_time, p_max_participants);
    END IF;
END //

DELIMITER ;
-- Giả sử có Coach với user_id = 1 và Template với id = 1
CALL create_class(
    1,               -- p_coach_id (user_id của Coach)
    1,               -- p_template_id (ID của class template)
    '2025-03-10 08:00:00', -- p_start_time
    '2025-03-10 09:00:00', -- p_end_time
    10               -- p_max_participants
);
