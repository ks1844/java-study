-- 申込状況TBLを作成
CREATE TABLE IF NOT EXISTS course_application_status (
    id VARCHAR(36) PRIMARY KEY,
    student_course_id VARCHAR(36) NOT NULL,
    status VARCHAR(5)
);

-- 申込状況を挿入
INSERT INTO course_application_status VALUES
('cccccc-0000-0000-0000-000000000201', '80630e2d-7790-4118-85fe-05bc04f126da', '受講終了'),
('cccccc-0000-0000-0000-000000000202', '5268cdff-ef40-4797-8a08-a6ae73daf2ca', '受講終了'),
('cccccc-0000-0000-0000-000000000203', '8ae08a91-9722-4484-b0e5-33a32a633cb9', '受講中'),
('cccccc-0000-0000-0000-000000000204', '2a6242f9-b3b3-46bb-900f-576dc5f1ffb1', '受講中'),
('cccccc-0000-0000-0000-000000000205', '8c1e8248-8d5a-47fa-87ed-7083020a020b', '仮申込'),
('cccccc-0000-0000-0000-000000000206', 'acdbf5a1-268d-4692-b69e-622df14b586d', '本申込'),
('cccccc-0000-0000-0000-000000000207', 'b23b74ff-c18e-4f56-bcc5-57b84c697226', '本申込'),
('cccccc-0000-0000-0000-000000000208', 'f3b2c8d2-f1ac-4c85-84f9-27640a279bf0', '仮申込');

--　申込状況TBLを確認
SELECT * FROM course_application_status;
DESC course_application_status;