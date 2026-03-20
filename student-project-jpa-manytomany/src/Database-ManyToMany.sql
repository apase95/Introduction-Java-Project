CREATE DATABASE student_course_mtm
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE student_course_mtm;
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE student_course;
TRUNCATE TABLE students;
TRUNCATE TABLE courses;
SET FOREIGN_KEY_CHECKS = 1;
INSERT INTO courses(course_name) VALUES
('Java Programming'),
('Database Systems'),
('Web Development'),
('Software Testing'),
('Data Structures and Algorithms'),
('Operating Systems'),
('Artificial Intelligence'),
('Computer Networks'),
('Cloud Computing'),
('Mobile App Development');

-- 4. Chèn 10 Sinh viên (Students)
-- Chú ý: Cột theo Java entity là full_name, email, gpa
INSERT INTO students(full_name, email, gpa) VALUES
('Nguyen Van An', 'an.nv@gmail.com', 8.5),
('Tran Thi Binh', 'binh.tt@gmail.com', 9.2),
('Le Van Cuong', 'cuong.lv@gmail.com', 7.0),
('Pham Minh Duc', 'duc.pm@gmail.com', 6.5),
('Hoang Thanh Ha', 'ha.ht@gmail.com', 8.0),
('Ngo Quang Hieu', 'hieu.nq@gmail.com', 9.8),
('Vu Thu Huong', 'huong.vt@gmail.com', 5.5),
('Dang Van Khanh', 'khanh.dv@gmail.com', 7.8),
('Bui Tuyet Lan', 'lan.bt@gmail.com', 8.8),
('Ly Hoang Nam', 'nam.lh@gmail.com', 4.0);

-- 5. Đăng ký khóa học cho sinh viên (Bảng trung gian student_course)
-- Theo schema: student_id và course_id
INSERT INTO student_course (student_id, course_id) VALUES
(1, 1), (1, 2),               -- An học Java và Database
(2, 1), (2, 3), (2, 5),       -- Bình học Java, Web, Data Structures
(3, 2), (3, 4),               -- Cường học Database, Testing
(4, 3),                       -- Đức học Web
(5, 1), (5, 7),               -- Hà học Java, AI
(6, 5), (6, 6), (6, 8),       -- Hiếu học Data Structures, OS, Networks
(7, 4),                       -- Hương học Testing
(8, 1), (8, 9),               -- Khánh học Java, Cloud
(9, 2), (9, 10),              -- Lan học Database, Mobile
(10, 1);