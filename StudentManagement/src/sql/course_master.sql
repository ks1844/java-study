-- コースマスタTBLを作成
CREATE TABLE course_master (
    id varchar(36) NOT NULL,
    name varchar(50) NOT NULL,
    PRIMARY KEY (id)
);

-- コースマスタを作成
INSERT INTO course_master (id, name) VALUES
('cccccccc-0000-0000-0000-000000000001', 'Javaコース'),
('cccccccc-0000-0000-0000-000000000002', 'AWSコース'),
('cccccccc-0000-0000-0000-000000000003', 'WordPressコース');

-- コースマスタTBLを確認
DESC course_master;
SELECT * FROM course_master;
