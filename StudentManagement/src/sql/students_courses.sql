-- 受講生講座情報TBLを作成
CREATE TABLE IF NOT EXISTS students_courses (
    id VARCHAR(36) PRIMARY KEY,
    student_id VARCHAR(36) NOT NULL,
    course_name VARCHAR(36) NOT NULL,
    course_start_at TIMESTAMP,
    course_end_at TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES students(id)
) DEFAULT CHARSET=utf8;


-- 受講生講座情報を挿入
INSERT INTO students_courses VALUES (
    '80630e2d-7790-4118-85fe-05bc04f126da',
    '753b1b86-fb63-43ce-b7e0-615ca9748a1e',
    'Javaコース',
    '2024-01-15 09:00:00',
    '2024-04-15 17:00:00'
);

INSERT INTO students_courses VALUES (
    '5268cdff-ef40-4797-8a08-a6ae73daf2ca',
    '340abfde-a4cd-430a-b991-370ffdb78379',
    'AWSコース',
    '2024-02-01 10:00:00',
    '2024-05-01 18:00:00'
);

INSERT INTO students_courses VALUES (
    '8ae08a91-9722-4484-b0e5-33a32a633cb9',
    '03124128-1dfc-4303-b5cd-ea67eb32b7bb',
    'WordPressコース',
    '2024-03-10 11:00:00',
    '2024-06-10 16:00:00'
);

INSERT INTO students_courses VALUES (
    '2a6242f9-b3b3-46bb-900f-576dc5f1ffb1',
    '64e7a198-4b6b-46a4-b3f4-1532f1a30290',
    'Javaコース',
    '2024-04-01 09:00:00',
    '2030-01-01 00:00:00'
);

INSERT INTO students_courses VALUES (
    '8c1e8248-8d5a-47fa-87ed-7083020a020b',
    'a82de084-3b97-4ad3-a74c-4334b251248d',
    'AWSコース',
    '2024-05-05 10:00:00',
    '2030-01-01 00:00:00'
);

INSERT INTO students_courses VALUES (
    'acdbf5a1-268d-4692-b69e-622df14b586d',
    '753b1b86-fb63-43ce-b7e0-615ca9748a1e',
    'WordPressコース',
    '2024-06-01 09:00:00',
    '2024-09-01 17:00:00'
);

INSERT INTO students_courses VALUES (
    'b23b74ff-c18e-4f56-bcc5-57b84c697226',
    '340abfde-a4cd-430a-b991-370ffdb78379',
    'Javaコース',
    '2024-07-10 09:00:00',
    '2024-10-10 17:00:00'
);

INSERT INTO students_courses VALUES (
    'f3b2c8d2-f1ac-4c85-84f9-27640a279bf0',
    '03124128-1dfc-4303-b5cd-ea67eb32b7bb',
    'AWSコース',
    '2024-08-01 10:00:00',
    '2030-01-01 00:00:00'
);
