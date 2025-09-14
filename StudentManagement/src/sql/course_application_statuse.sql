-- 申込状況TBLを作成
CREATE TABLE IF NOT EXISTS course_application_status (
    id VARCHAR(36) PRIMARY KEY,
    student_course_id VARCHAR(36) NOT NULL,
    status VARCHAR(5)
);

-- 申込状況を挿入
INSERT INTO course_application_status (id, student_course_id, status) VALUES
('pppppppp-0000-0000-0000-000000000001', 'ssssssss-0000-0000-0000-000000000001', '受講中'),
('pppppppp-0000-0000-0000-000000000002', 'ssssssss-0000-0000-0000-000000000002', '本申込'),
('pppppppp-0000-0000-0000-000000000003', 'ssssssss-0000-0000-0000-000000000003', '仮申込'),
('pppppppp-0000-0000-0000-000000000004', 'ssssssss-0000-0000-0000-000000000004', '受講中'),
('pppppppp-0000-0000-0000-000000000005', 'ssssssss-0000-0000-0000-000000000005', '本申込'),
('pppppppp-0000-0000-0000-000000000006', 'ssssssss-0000-0000-0000-000000000006', '仮申込'),
('pppppppp-0000-0000-0000-000000000007', 'ssssssss-0000-0000-0000-000000000007', '受講中'),
('pppppppp-0000-0000-0000-000000000008', 'ssssssss-0000-0000-0000-000000000008', '本申込'),
('pppppppp-0000-0000-0000-000000000009', 'ssssssss-0000-0000-0000-000000000009', '受講終了');

-- 申込状況TBLを確認
SELECT * FROM course_application_status;
DESC course_application_status;
