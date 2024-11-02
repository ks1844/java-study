package org.example;

import java.util.Scanner;

public class Calculator {

  public void run() {
    // String型で入力を受ける
    Scanner scanner = new Scanner(System.in);
    System.out.print("1番目の数字を入力してください: ");
    int firstNumber = scanner.nextInt();

    System.out.print("演算子を入力してください (+, -, *, /): ");
    String operator = scanner.next();

    System.out.print("2番目の数字を入力してください:");
    int secondNumber = scanner.nextInt();

    // 計算する
    int answer = calculate(firstNumber, operator, secondNumber);

    // 答えを出力する
    System.out.printf("計算結果: %s %s %s = %s\n",
        firstNumber,
        operator,
        secondNumber,
        answer);
  }

  public int calculate(int firstNumber, String operater, int secondNumber) {
    if (operater.equals("+")) {
      return firstNumber + secondNumber;
    } else if (operater.equals("-")) {
      return firstNumber - secondNumber;
    } else if (operater.equals("*")) {
      return firstNumber * secondNumber;
    } else {
      return firstNumber / secondNumber;
    }
  }
}
