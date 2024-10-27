package org.example;

public class Main {

  public static void main(String[] args) {
    // Java 命名規則
    // 日本語、英語、アンダーバーとかいろいろな文字列が使える。
    // 先頭文字に数字は使えない。
    // 文字数制限はない
    // 大文字と小文字は区別される

    // Pascal
    // 先頭大文字、一般的な英語の書き方と一緒
    // Main、Greeting

    // Camel
    // 先頭は小文字、言葉の区切りから大文字
    // メソッド名や変数名に使われることが多い
    // sayHello、numberSecond

    // フィールド名、変数名
    // 名詞

    // メソッド名
    // 動詞

    // 真偽値、boolean
    // isXXX（数値化どうか）、hasXXX（持っているか、変化したか）

    // 定数
    // フィールドとの違いは固定値になること、絶対に変更できない
    // イミュータブルになる
    // すべて大文字、たまにスネークケース

    // 課題
    // 図書管理システム
    // Java 命名規則
    // 日本語、英語、アンダーバーとかいろいろな文字列が使える。
    // 先頭文字に数字は使えない。
    // 文字数制限はない
    // 大文字と小文字は区別される

    // Pascal
    // 先頭大文字、一般的な英語の書き方と一緒
    // Main、Greeting

    // Camel
    // 先頭は小文字、言葉の区切りから大文字
    // メソッド名や変数名に使われることが多い
    // sayHello、numberSecond

    // フィールド名、変数名
    // 名詞

    // メソッド名
    // 動詞

    // 真偽値、boolean
    // isXXX（数値化どうか）、hasXXX（持っているか、変化したか）

    // 定数
    // フィールドとの違いは固定値になること、絶対に変更できない
    // イミュータブルになる
    // すべて大文字、たまにスネークケース

    // 課題
    // 図書管理システムを作る。
    // 書籍（Book）を管理する情報（タイトル、著者、番号）を持つオブジェクト（クラス）を作って、そこに情報を格納してください。
    // 図書館（Library）みたいなものを作って、そこにBookをListで持つようなものを保持する。
    // mainメソッドからこのLibraryクラスに対して検索ができるようにする。Libraryクラスは書籍検索の機能を持つ。
    // タイトル検索、著者検索、番号検索メソッドをライブラリに持たせる。
    // それをmainメソッドから実行して、実行結果をコンソールに出力する。

    Library library = new Library();

    library.addBook("ハリー・ポッターと賢者の石", "J・K・ローリング", 111);
    library.addBook("Nのために", "湊かなえ", 112);
    library.addBook("チーム・バチスタの栄光", "海堂尊", 123);
    library.addBook("アリアドネの弾丸", "海堂尊", 412);

    System.out.println("Libraryで保持しているBookのListを表示");
    library.showBookList();
    System.out.println();

    System.out.println("タイトル検索：「ハリー」で検索）");
    library.showBookListByTitle("ハリー");
    System.out.println();

    System.out.println("タイトル検索：「・」で検索");
    library.showBookListByTitle("・");
    System.out.println();

    System.out.println("著者検索:「海堂」で検索");
    library.showBookListByAuthor("海堂");
    System.out.println();

    System.out.println("番号検索（完全一致）:「123」で検索");
    library.showBookListByNumber(123);
    System.out.println();

    System.out.println("番号検索（完全一致）:「12」で検索");
    library.showBookListByNumber(12);


  }
}