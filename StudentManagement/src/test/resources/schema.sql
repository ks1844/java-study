-- 受講生情報TBLを作成
CREATE TABLE IF NOT EXISTS students (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    kana_name VARCHAR(50) NOT NULL,
    nickname VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    area VARCHAR(50),
    age INT,
    sex VARCHAR(10),
    remark VARCHAR(100),
    is_deleted boolean
);

-- 受講生講座情報TBLを作成
CREATE TABLE IF NOT EXISTS students_courses (
    id VARCHAR(36) PRIMARY KEY,
    student_id VARCHAR(36) NOT NULL,
    course_name VARCHAR(36) NOT NULL,
    course_start_at TIMESTAMP,
    course_end_at TIMESTAMP
);