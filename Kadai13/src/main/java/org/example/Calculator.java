package org.example;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Calculator {

  Scanner scanner = new Scanner(System.in);

  public void run() {
    // 入力を受ける
    int firstNumber = inputIntNumber("1番目の数字を入力してください:");
    String operator = inputOperator("演算子を入力してください (+, -, *, /): ");
    int secondNumber = inputIntNumber("2番目の数字を入力してください:");

    // 計算する
    int answer = calculate(firstNumber, operator, secondNumber);

    // 答えを出力する
    System.out.printf("計算結果: %s %s %s = %s\n",
        firstNumber,
        operator,
        secondNumber,
        answer);
  }

  public int calculate(int firstNumber, String operator, int secondNumber) {
    if (operator.equals("+")) {
      return firstNumber + secondNumber;
    } else if (operator.equals("-")) {
      return firstNumber - secondNumber;
    } else if (operator.equals("*")) {
      return firstNumber * secondNumber;
    } else if (operator.equals("/")) {
      return firstNumber / secondNumber;
    } else {
      return 0;
    }
  }

  public int inputIntNumber(String message) {
    // 数字以外が入力されたとき再入力を受け、int型を返す
    while (true) {
      System.out.print(message);
      try {
        int number = scanner.nextInt();
        return number;
      } catch (InputMismatchException e) {
        // 不正な入力を飛ばして次の入力を受ける
        scanner.next();
      }
    }
  }

  public String inputOperator(String message) {
    // 「+-*/」以外が入力されたとき再入力を受け、文字列型の演算子を返す
    while (true) {
      System.out.print(message);
      String operator = scanner.next();
      if ("+-*/".contains(operator)) {
        return operator;
      }
    }
  }
}
