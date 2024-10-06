package org.example;

import java.util.List;
import java.util.stream.Collectors;

public class Main {

  public static void main(String[] args) {
    // 今日紹介したものは一通り実装してください。

    List<Integer> numberList = List.of(1,2,3,4,5,6,7,8,9,10);
    List<String> studentList = List.of("inoue","enami","tanaka","inoue");

    // 5以下の数値を出力
    numberList.stream()
        .filter(number -> number <= 5)
        .forEach(System.out::println);

    // 修了処理をアロー演算子で書く
    numberList.stream()
        .filter(number -> 5 >= number)
        .forEach(number -> System.out.println(number));

    // 3つのみ表示する
    numberList.stream()
        .limit(3)
        .forEach(System.out::println);

    // 大文字に変換する
    studentList.stream()
        .map(student -> student.toUpperCase())
        .forEach(System.out::println);

    // 大文字に変換する（メソッド参照を使う）
    studentList.stream()
        .map(String::toUpperCase)
        .forEach(System.out::println);

    // 重複を消して、並び変える
    studentList.stream()
        .sorted()
        .distinct()
        .forEach(System.out::println);

    // 重複を消し大文字にし並び変え、カンマ区切りで表示する
    System.out.println(studentList.stream()
        .distinct()
        .map(String::toUpperCase)
        .sorted()
        .collect(Collectors.joining(",")));

    // 先頭の文字がeのものがあればtrueと出力
    System.out.println(studentList.stream()
        .anyMatch(student -> student.startsWith("e")));

    // 文字列のリストを作ってください。要素数は10個。
    // その文字列に対して、文字数が2以上のものを抽出して、文字列に変換してください。区切り文字はカンマ(,)で区切ってください。
    // その文字列を出力してください。
    List<String> stringList = List.of(
        "a", "apple", "banana", "cat", "dog", "elephant", "frog", "giraffe", "hat", "ice cream"
    );

    stringList.stream()
        .filter(fruit -> fruit.length() >= 2)
        .forEach(System.out::println);

    // 数値のリストを作成してください。要素数は10個。
    // その数値の中の奇数のものだけ抽出して、平均値を出してください。
    // その平均値を出力してください。
    List<Integer> randomeNumberList = List.of(3, 14, 7, 22, 5, 18, 9, 2, 13, 11);

    System.out.println(randomeNumberList.stream()
        .filter(number -> number % 2 == 1)
        .mapToInt(Integer::intValue).sum());

  }
}
