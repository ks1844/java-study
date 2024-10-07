package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class Main {

  public static void main(String[] args) throws IOException {
    // 入出力、入力処理、出力処理
    // ファイル操作を指すことが多いです。
    // エラー：どうしようもないもの
    // 例外：制御できるもの

//    try {
//      Path path = Path.of("./src/main/java/org/example/JavaCourse.txt");
//      Files.writeString(path, "眠すぎるzzz\n",StandardOpenOption.APPEND);
//      System.out.println(Files.readString(path));
//    } catch(IOException e){
//      // 例外の内容をコンソール出力で知らせる
//      e.printStackTrace();
//      System.out.println("例外が発生した！");
//    }

    // catchした中でさらにファイルに書き込もうとして例外になった場合、どうなるのか確認してください。
    // （回答）catch後にファイル書き込みをしようとしてもIOExceptionで補足できないとのエラーが発生する。
    // メソッドシグネチャーでIOExceptionを補足するように変更すると、catch後のファイル書き込みに対してもIOExceptionで補足されるようになる。
    try {
      Path path = Path.of("./src/main/java/org/example/JavaCourse.txt");
      Files.writeString(path, "Javaコースたのしい！\n",StandardOpenOption.APPEND);
      System.out.println(Files.readString(path));
    } catch(IOException e){
      // 例外の内容をコンソール出力で知らせる
      e.printStackTrace();
      System.out.println("例外発生後に再度ファイル書き込みを行う");

      Path path = Path.of("./src/main/java/org/example/JavaCourse.txt");
      Files.writeString(path, "眠すぎるzzz\n",StandardOpenOption.APPEND);
      System.out.println(Files.readString(path));
    }
  }
}