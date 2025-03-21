DELIMITER //

CREATE PROCEDURE create_coach_account(
    IN p_email VARCHAR(255),
    IN p_password VARCHAR(255),
    IN p_full_name VARCHAR(255),
    IN p_phone VARCHAR(20),
    IN p_expertise TEXT,
    IN p_max_classes INT
)
BEGIN
    DECLARE user_id INT;

    -- Tạo tài khoản user trước
    INSERT INTO users (email, password, full_name, phone, role)
    VALUES (p_email, p_password, p_full_name, p_phone, 'COACH');

    SET user_id = LAST_INSERT_ID();

    -- Tạo thông tin Coach
    INSERT INTO coaches (user_id, expertise, max_classes_per_week)
    VALUES (user_id, p_expertise, p_max_classes);
END //

DELIMITER 


-- Gọi procedure để tạo một Coach
CALL create_coach_account(
    'coach1@gmail.com', 
    'pass123', 
    'Phạm Văn C', 
    '0987654323', 
    'Bodybuilding, Strength Training', 
    5
);

-- Kiểm tra lại dữ liệu đã được thêm vào bảng users và coaches
SELECT * FROM users WHERE email = 'coach1@gmail.com';
SELECT * FROM coaches WHERE user_id = (SELECT id FROM users WHERE email = 'coach1@gmail.com');

