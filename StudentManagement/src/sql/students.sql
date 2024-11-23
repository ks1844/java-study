-- 受講生情報TBLを作成
CREATE TABLE IF NOT EXISTS students (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    kana_name VARCHAR(50) NOT NULL,
    nickname VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    area VARCHAR(50),
    age INT,
    sex VARCHAR(10)
) DEFAULT CHARSET=utf8;

-- 受講生情報を挿入
INSERT INTO students VALUES (
    '753b1b86-fb63-43ce-b7e0-615ca9748a1e',
    '佐々木 真琴',
    'ささき まこと',
    'まこ',
    'sasaki.makoto012@yahoo.co.jp',
    '東京都',
    22,
    'Male'
);

INSERT INTO students VALUES (
    '340abfde-a4cd-430a-b991-370ffdb78379',
    '中川 葵',
    'なかがわ あおい',
    'あお',
    'aoi.nakagawa@outlook.com',
    '神奈川県',
    20,
    'Female'
);

INSERT INTO students VALUES (
    '03124128-1dfc-4303-b5cd-ea67eb32b7bb',
    '井上 悠人',
    'いのうえ ゆうと',
    'ゆう',
    'yuto.inoue@gmail.com',
    '大阪府',
    37,
    'Male'
);

INSERT INTO students VALUES (
    '64e7a198-4b6b-46a4-b3f4-1532f1a30290',
    '吉田 楓',
    'よしだ かえで',
    'かえ',
    'kaede_yoshida@icloud.com',
    '愛知県',
    42,
    'Female'
);

INSERT INTO students VALUES (
    'a82de084-3b97-4ad3-a74c-4334b251248d',
    '石井 陽向',
    'いしい ひなた',
    'ひな',
    'hinata.ishii@protonmail.com',
    '福岡県',
    22,
    'Other'
);

-- 備考欄と削除フラグを追加
ALTER TABLE Students ADD remark VARCHAR(100) AFTER sex;
ALTER TABLE Students ADD isDeleted BOOLEAN AFTER remark;