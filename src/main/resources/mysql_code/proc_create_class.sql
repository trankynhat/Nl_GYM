drop 

DELIMITER //
CREATE PROCEDURE CreateClassWithSchedule(
    IN p_template_id INT,
    IN p_coach_user_id INT,  -- ID của Coach trong bảng users
    IN p_start_date DATE,
    IN p_schedule TEXT, 
    OUT p_class_id INT,
    OUT p_status_message VARCHAR(255)
)
proc_block: BEGIN  -- Gán nhãn proc_block cho toàn bộ procedure
    -- KHAI BÁO BIẾN PHẢI ĐƯỢC ĐẶT Ở ĐẦU
    DECLARE v_coach_id INT;
    DECLARE v_max_classes_per_week INT;
    DECLARE v_classes_count INT;
    DECLARE i INT DEFAULT 0;
    DECLARE v_day_of_week VARCHAR(20);
    DECLARE v_start_time TIME;

    -- Kiểm tra Coach có tồn tại không
    SELECT id, max_classes_per_week INTO v_coach_id, v_max_classes_per_week
    FROM coaches WHERE user_id = p_coach_user_id;
    
    IF v_coach_id IS NULL THEN
        SET p_status_message = 'Coach không tồn tại';
        SET p_class_id = NULL;
        LEAVE proc_block;  -- Thoát khỏi procedure nếu không có Coach
    END IF;

    -- Kiểm tra Template có tồn tại không
    IF NOT EXISTS (SELECT 1 FROM class_templates WHERE id = p_template_id) THEN
        SET p_status_message = 'Class template không tồn tại';
        SET p_class_id = NULL;
        LEAVE proc_block;
    END IF;

    -- Đếm số lớp học Coach đã tạo trong tuần
    SELECT COUNT(*) INTO v_classes_count
    FROM classes
    WHERE coach_id = v_coach_id AND YEARWEEK(start_date) = YEARWEEK(p_start_date);

    -- Kiểm tra Coach có vượt quá giới hạn lớp mỗi tuần không
    IF v_classes_count >= v_max_classes_per_week THEN
        SET p_status_message = 'Coach đã đạt giới hạn lớp học trong tuần';
        SET p_class_id = NULL;
        LEAVE proc_block;
    END IF;

    -- Thêm lớp học mới vào bảng classes
    INSERT INTO classes (template_id, coach_id, start_date)
    VALUES (p_template_id, v_coach_id, p_start_date);

    -- Lấy ID của lớp học mới tạo
    SET p_class_id = LAST_INSERT_ID();

    -- Thêm lịch học từ JSON vào class_schedules
    SET i = 0;
    WHILE i < JSON_LENGTH(p_schedule) DO
        SET v_day_of_week = JSON_UNQUOTE(JSON_EXTRACT(p_schedule, CONCAT('$[', i, '].day')));
        SET v_start_time = JSON_UNQUOTE(JSON_EXTRACT(p_schedule, CONCAT('$[', i, '].time')));

        -- Kiểm tra trùng lịch học
        IF EXISTS (
            SELECT 1 FROM class_schedules cs
            JOIN classes c ON cs.class_id = c.id
            WHERE c.coach_id = v_coach_id
            AND cs.day_of_week = v_day_of_week
            AND cs.start_time = v_start_time
        ) THEN
            SET p_status_message = CONCAT('Coach đã có lớp vào ', v_day_of_week, ' lúc ', v_start_time);
            SET p_class_id = NULL;
            LEAVE proc_block;
        END IF;

        -- Thêm vào class_schedules
        INSERT INTO class_schedules (class_id, day_of_week, start_time)
        VALUES (p_class_id, v_day_of_week, v_start_time);

        SET i = i + 1;
    END WHILE;

    SET p_status_message = 'Lớp học và lịch tập đã được tạo thành công';

END proc_block; //
DELIMITER ;
