package org.example;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TelephoneNumberChecker {

  Scanner scanner = new Scanner(System.in);

  public void run() {
    // 電話番号を入力で受ける
    System.out.print("携帯電話番号を入力してください: ");
    String telephoneNumber = scanner.next();

    // 電話番号が有効か判定する
    Pattern pattern = Pattern.compile("^0\\d{2}-\\d{4}-\\d{4}$");
    Matcher matcher = pattern.matcher(telephoneNumber);
    if (matcher.find()) {
      System.out.printf("%s は有効な携帯電話番号です。", telephoneNumber);
    } else {
      System.out.printf("%s は無効な携帯電話番号です。", telephoneNumber);

    }

  }

}
