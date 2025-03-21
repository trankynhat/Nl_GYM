DROP DATABASE IF EXISTS nl_gym;
CREATE DATABASE nl_gym;
USE nl_gym;

-- Bảng users (KHÔNG thay đổi)
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    avatar VARCHAR(255),
    role ENUM('CUSTOMER', 'COACH', 'ADMIN') NOT NULL,  -- Thêm 'ADMIN' để phân quyền
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Bảng customers (KHÔNG thay đổi)
CREATE TABLE customers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL UNIQUE,
    birth_date DATE,
    address TEXT,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Bảng coaches
CREATE TABLE coaches (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL UNIQUE,
    expertise TEXT,
    max_classes_per_week INT DEFAULT 5,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Bảng class_templates (ADMIN tạo lớp học mẫu)
CREATE TABLE class_templates (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    description TEXT NOT NULL,
    default_duration INT NOT NULL,
    max_participants INT NOT NULL,
    created_by INT NOT NULL, -- Người tạo (ADMIN)
    FOREIGN KEY (created_by) REFERENCES users(id) ON DELETE CASCADE
);

-- Bảng classes (Coach chỉ có thể đăng ký từ class_templates)
CREATE TABLE classes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    template_id INT NOT NULL,
    coach_id INT NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    max_participants INT NOT NULL,
    FOREIGN KEY (template_id) REFERENCES class_templates(id) ON DELETE RESTRICT,
    FOREIGN KEY (coach_id) REFERENCES coaches(id) ON DELETE CASCADE
);

-- Bảng bookings (Khách hàng đăng ký lớp học)
CREATE TABLE bookings (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    class_id INT NOT NULL,
    booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE,
    FOREIGN KEY (class_id) REFERENCES classes(id) ON DELETE CASCADE
);
