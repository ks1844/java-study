CREATE TABLE student (
    id VARCHAR(36) NOT NULL,
    name VARCHAR(50) NOT NULL,
    kana_name VARCHAR(50) NOT NULL,
    nickname VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    area VARCHAR(20) NOT NULL,
    age INT NOT NULL,
    sex VARCHAR(10) NOT NULL,
    remark VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (id)
);

CREATE TABLE student_course (
    id VARCHAR(36) NOT NULL,
    student_id VARCHAR(36) NOT NULL,
    course_master_id VARCHAR(36) NOT NULL,
    course_start_at DATETIME,
    course_end_at DATETIME,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS course_application_status (
    id VARCHAR(36) PRIMARY KEY,
    student_course_id VARCHAR(36) NOT NULL,
    status VARCHAR(5)
);

CREATE TABLE course_master (
    id VARCHAR(36) NOT NULL,
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);
