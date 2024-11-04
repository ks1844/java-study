package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class StudentManagement {

  Scanner scanner = new Scanner(System.in);
  private List<Student> studentList = new ArrayList<>();
  private Map<Integer, String> menuMap = new TreeMap<>();

  public StudentManagement() {
    createMenuMap();
  }

  public void createMenuMap() {
    // メニューマップの作成
    menuMap.put(1, "学生の追加");
    menuMap.put(2, "学生の削除");
    menuMap.put(3, "点数を更新");
    menuMap.put(4, "平均点を計算");
    menuMap.put(5, "全学生の情報を表示");
    menuMap.put(6, "終了");
  }

  public void inputStudentAdded() {
    // 「学生を追加」の入力を受ける
    System.out.print("学生の名前を入力してください: ");
    String studentName = scanner.next();
    System.out.printf("%sの点数を入力してください:", studentName);
    int score = scanner.nextInt();
    System.out.println();

    // 学生リストに追加
    studentList.add(new Student(studentName, score));
  }

  public void inputStudentRemoved() {
    // 「学生を削除」の入力を受ける
    System.out.print("削除する学生の名前を入力してください: ");
    String studentName = scanner.next();
    System.out.println();

    // 学生を削除
    studentList.removeIf(student -> studentName.equals(student.getName()));
  }

  public void inputScoreUpdated() {
    // 「点数を更新」の入力を受ける
    System.out.print("点数を更新する学生の名前を入力してください: ");
    String studentName = scanner.next();
    System.out.printf("%sの更新後の点数を入力してください:", studentName);
    int score = scanner.nextInt();
    System.out.println();

    // 点数を更新
    studentList.stream()
        .filter(student -> studentName.equals(student.getName()))
        .forEach(student -> student.setScore(score));
  }

  public void showAverage() {
    // 平均点を計算
    int sum = studentList.stream()
        .mapToInt(Student::getScore)
        .sum();
    double average = studentList.isEmpty() ? 0 : (double) sum / studentList.size();

    System.out.printf("平均点：　%.1f点\n\n", average);
  }

  public void showStudents() {
    // 全学生の情報を表示
    System.out.println("学生一覧");
    studentList.forEach(student ->
        System.out.printf("%s: %d点\n", student.getName(), student.getScore()));
    System.out.println();
  }

  public void run() {

    while (true) {
      // メニューを表示
      menuMap.forEach((key, value) -> System.out.printf("%d,%s\n", key, value));

      // メニューを選択する
      System.out.print("選択してください： ");
      int menuNumber = scanner.nextInt();
      System.out.println();

      if (menuNumber == 1) {
        // 学生を追加
        inputStudentAdded();
      } else if (menuNumber == 2) {
        // 学生を削除
        inputStudentRemoved();
      } else if (menuNumber == 3) {
        // 点数を更新
        inputScoreUpdated();
      } else if (menuNumber == 4) {
        // 平均点を計算
        showAverage();
      } else if (menuNumber == 5) {
        // 全学生の情報を表示
        showStudents();
      } else if (menuNumber == 6) {
        // 終了
        System.out.println("プログラムを終了します。");
        break;
      } else {
        System.out.println(menuMap.keySet().stream()
            .map(String::valueOf)
            .collect(Collectors.joining(","))
            + "を入力して下さい。");
        System.out.println();
      }
    }
  }
}
