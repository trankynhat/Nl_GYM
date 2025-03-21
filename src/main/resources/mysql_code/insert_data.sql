-- Insert Users
INSERT INTO users (email, password, full_name, phone, role, avatar) 
VALUES  
('customer1@gmail.com', 'pass123', 'Nguyễn Văn A', '0987654321', 'CUSTOMER', 'avatar1.png'), 
('customer2@gmail.com', 'pass123', 'Trần Thị B', '0987654322', 'CUSTOMER', 'avatar2.png'), 
('coach1@gmail.com', 'pass123', 'Phạm Văn C', '0987654323', 'COACH', 'avatar3.png'), 
('coach2@gmail.com', 'pass123', 'Lê Thị D', '0987654324', 'COACH', 'avatar4.png');

-- Insert Customers
INSERT INTO customers (user_id, birth_date, address)
VALUES 
(1, '1990-05-15', '123 Đường A, Quận 1, TP.HCM'),
(2, '1995-08-20', '456 Đường B, Quận 2, TP.HCM');

INSERT INTO coaches (user_id, expertise, max_classes_per_week)
VALUES 
(3, 'Bodybuilding, Strength Training', 5),
(4, 'Yoga, Cardio', 4);


-- Insert Class Templates
INSERT INTO class_templates (name, description, default_duration, max_participants)
VALUES 
('Yoga cơ bản', 'Lớp Yoga giúp tăng cường sức khỏe', 60, 10),
('Gym cho người mới', 'Lớp gym dành cho người mới tập', 90, 15),
('Cardio nâng cao', 'Bài tập Cardio tăng cường sức bền', 45, 12);

-- Coaches Creating Classes
INSERT INTO classes (template_id, coach_id, start_time, end_time, max_participants)
VALUES 
(1, (SELECT id FROM coaches WHERE user_id = (SELECT id FROM users WHERE email = 'coach1@gmail.com')), '2025-03-05 08:00:00', '2025-03-05 09:00:00', 10),
(2, (SELECT id FROM coaches WHERE user_id = (SELECT id FROM users WHERE email = 'coach1@gmail.com')), '2025-03-06 10:00:00', '2025-03-06 11:30:00', 15),
(3, (SELECT id FROM coaches WHERE user_id = (SELECT id FROM users WHERE email = 'coach2@gmail.com')), '2025-03-07 07:00:00', '2025-03-07 07:45:00', 12);

-- Customers Registering for Classes
INSERT INTO bookings (customer_id, class_id)
VALUES 
((SELECT id FROM customers WHERE user_id = (SELECT id FROM users WHERE email = 'customer1@gmail.com' LIMIT 1) LIMIT 1), (SELECT id FROM classes WHERE start_time = '2025-03-05 08:00:00' LIMIT 1)),
((SELECT id FROM customers WHERE user_id = (SELECT id FROM users WHERE email = 'customer2@gmail.com' LIMIT 1) LIMIT 1), (SELECT id FROM classes WHERE start_time = '2025-03-06 10:00:00' LIMIT 1));

-- Cancel Class Registration
DELETE FROM bookings WHERE customer_id = (SELECT id FROM customers WHERE user_id = (SELECT id FROM users WHERE email = 'customer1@gmail.com' LIMIT 1) LIMIT 1) AND class_id = (SELECT id FROM classes WHERE start_time = '2025-03-05 08:00:00' LIMIT 1);

-- Update User Avatars
UPDATE users SET avatar = 'new_avatar1.png' WHERE email = 'customer1@gmail.com';
UPDATE users SET avatar = 'new_avatar3.png' WHERE email = 'coach1@gmail.com';

