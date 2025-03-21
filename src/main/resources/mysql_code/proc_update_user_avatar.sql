DELIMITER //

CREATE PROCEDURE update_user_avatar(
    IN p_user_id INT,
    IN p_avatar_url VARCHAR(500)
)
BEGIN
    -- Kiểm tra xem user có tồn tại không
    IF (SELECT COUNT(*) FROM users WHERE id = p_user_id) = 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'User không tồn tại!';
    ELSE
        -- Cập nhật avatar
        UPDATE users SET avatar_url = p_avatar_url WHERE id = p_user_id;
    END IF;
END //

DELIMITER ;
