DELIMITER //

CREATE PROCEDURE create_customer_account(
    IN p_email VARCHAR(255),
    IN p_password VARCHAR(255),
    IN p_full_name VARCHAR(255),
    IN p_phone VARCHAR(20),
    IN p_avatar VARCHAR(255),
    IN p_birth_date DATE,
    IN p_address TEXT
)
BEGIN
    DECLARE user_id INT;

    -- Tạo tài khoản user trước
    INSERT INTO users (email, password, full_name, phone, avatar, role)
    VALUES (p_email, p_password, p_full_name, p_phone, p_avatar, 'CUSTOMER');

    SET user_id = LAST_INSERT_ID();

    -- Tạo thông tin Customer
    INSERT INTO customers (user_id, birth_date, address)
    VALUES (user_id, p_birth_date, p_address);
END //

DELIMITER ;

-- Gọi procedure để tạo một Customer
CALL create_customer_account(
    'customer1@gmail.com', 
    'pass123', 
    'Nguyễn Văn A', 
    '0987654321', 
    'avatar1.png', 
    '1990-05-15', 
    '123 Đường A, Quận 1, TP.HCM'
);

-- Kiểm tra lại dữ liệu đã được thêm vào bảng users và customers
SELECT * FROM users WHERE email = 'customer1@gmail.com';
SELECT * FROM customers WHERE user_id = (SELECT id FROM users WHERE email = 'customer1@gmail.com');
