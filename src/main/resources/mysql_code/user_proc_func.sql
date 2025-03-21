-- Insert Coaches using Procedure
CALL create_coach_account('coach1@gmail.com', 'pass123', 'Phạm Văn C', '0987654323', 'Bodybuilding, Strength Training', 5);
CALL create_coach_account('coach2@gmail.com', 'pass123', 'Lê Thị D', '0987654324', 'Yoga, Cardio', 4);

-- Insert Customers using Procedure
CALL create_customer_account('customer1@gmail.com', 'pass123', 'Nguyễn Văn A', '0987654321', '1990-05-15', '123 Đường A, Quận 1, TP.HCM');
CALL create_customer_account('customer2@gmail.com', 'pass123', 'Trần Thị B', '0987654322', '1995-08-20', '456 Đường B, Quận 2, TP.HCM');

-- Insert Class Templates using Procedure
CALL create_class_template('Yoga cơ bản', 'Lớp Yoga giúp tăng cường sức khỏe', 10);
CALL create_class_template('Gym cho người mới', 'Lớp gym dành cho người mới tập', 15);
CALL create_class_template('Cardio nâng cao', 'Bài tập Cardio tăng cường sức bền', 12);

-- Coaches Creating Classes using Procedure (Only if Coach has not exceeded weekly limit)
CALL create_class(1, 1, '2025-03-05 08:00:00', '2025-03-05 09:00:00', 10);
CALL create_class(1, 2, '2025-03-06 10:00:00', '2025-03-06 11:30:00', 15);
CALL create_class(2, 3, '2025-03-07 07:00:00', '2025-03-07 07:45:00', 12);

-- Customers Registering for Classes using Procedure (Only if Class is Available)
CALL register_for_class((SELECT id FROM customers WHERE user_id = (SELECT id FROM users WHERE email = 'customer1@gmail.com')), 1);
CALL register_for_class((SELECT id FROM customers WHERE user_id = (SELECT id FROM users WHERE email = 'customer2@gmail.com')), 2);

-- Cancel Class Registration using Procedure
CALL cancel_class_registration((SELECT id FROM customers WHERE user_id = (SELECT id FROM users WHERE email = 'customer1@gmail.com')), 1);

-- Update User Avatars using Procedure
CALL update_user_avatar((SELECT id FROM users WHERE email = 'customer1@gmail.com'), 'new_avatar1.png');
CALL update_user_avatar((SELECT id FROM users WHERE email = 'coach1@gmail.com'), 'new_avatar3.png');

-- Ensure Consistency by Checking Existing Data Before Execution
SELECT * FROM users;
SELECT * FROM customers;
SELECT * FROM coaches;
SELECT * FROM class_templates;
SELECT * FROM classes;
SELECT * FROM bookings;
