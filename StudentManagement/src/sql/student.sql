-- 受講生情報TBLを作成
CREATE TABLE student (
    id varchar(36) NOT NULL,
    name varchar(50) NOT NULL,
    kana_name varchar(50) NOT NULL,
    nickname varchar(50) NOT NULL,
    email varchar(100) NOT NULL,
    area varchar(20) NOT NULL,
    age int NOT NULL,
    sex varchar(10) NOT NULL,
    remark varchar(255),
    is_deleted tinyint(1) NOT NULL DEFAULT 0,
    PRIMARY KEY (id)
);

-- 受講生情報を挿入
INSERT INTO student (id, name, kana_name, nickname, email, area, age, sex, remark, is_deleted) VALUES
('aaaaaaaa-0000-0000-0000-000000000001', '山田 太郎', 'ヤマダ タロウ', 'タロウタロウタ', 'taro@example.com', '東京都', 28, '男', NULL, 0),
('aaaaaaaa-0000-0000-0000-000000000002', '佐藤 花子', 'サトウ ハナコ', 'ハナ', 'hanako@example.com', '大阪府', 32, '女', NULL, 0),
('aaaaaaaa-0000-0000-0000-000000000003', '鈴木 一郎', 'スズキ イチロウ', 'イチロウ', 'ichiro@example.com', '京都府', 25, '男', NULL, 0),
('aaaaaaaa-0000-0000-0000-000000000004', '田中 美穂', 'タナカ ミホ', 'ミホ', 'miho@example.com', '東京都', 29, '女', NULL, 0),
('aaaaaaaa-0000-0000-0000-000000000005', '高橋 俊介', 'タカハシ シュンスケ', 'シュンスケ', 'shunsuke@example.com', '愛知県', 26, '男', NULL, 0),
('aaaaaaaa-0000-0000-0000-000000000006', '二宮 二郎', 'ニノミヤ ジロウ', 'ジロウ', 'jiro@example.com', '神奈川県', 24, '男', NULL, 0),
('aaaaaaaa-0000-0000-0000-000000000007', '小林 美香', 'コバヤシ ミカ', 'ミカ', 'mika@example.com', '埼玉県', 27, '女', 'プログラミング経験あり', 0),
('aaaaaaaa-0000-0000-0000-000000000008', '無藤 健', 'ムトウ ケン', 'ケン', 'ken@example.com', '千葉県', 30, '男', NULL, 1);

-- 受講生情報TBLを確認
DESC student;
SELECT * FROM student;
