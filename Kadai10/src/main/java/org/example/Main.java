package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Main {

  public static void main(String[] args) {
    System.out.println("講義内のコード");
    Map<String, String> addressMap = new HashMap<>();
    addressMap.put("井上", "inoue@gmail.com");
    addressMap.put("佐藤", "sato@gmail.com");
    addressMap.put("田中", "tamalae@yahoo.co.jp");
    addressMap.put("高橋", "takahashi.net");

    List<String> addressList = new ArrayList<>();
//    for (Entry<String, String> address : addressMap.entrySet()) {
//      if (address.getValue().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
//        addressList.add(address.getValue());
//      }
//    }

    addressMap.values().stream()
        .filter(s -> s.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"))
        .forEach(addressList::add);
    System.out.println(addressList);
    System.out.println();

    // 課題は正規表現をたくさん使う。
    // Listにランダムな文字列（色々自分で入れてもいいし、ChatGPTとかにお願いしてもいい）を30個ほど入れてください。
    // その文字のリストに対して「数字だけを抜き出す正規表現」と「文字だけを抜き出す正規表現」を試してみてください。
    // 実際にあっているか確認してください。

    // 更にそれが出来たら文字を抜き出すときに英数字の大文字だけを抜き出してください。
    // 更に更にそれができたら、漢字とひらがな、カタカナなどの日本語にマッチする正規表現を抜き出してください。
    // 更に更に更にそれができたら、英数字以外の文字を抜き出すという正規表現にチャレンジしてみてください。
    System.out.println("課題");
    System.out.println("Listにランダムな文字列を30個ほど入れる");
    List<String> mixedElementsList = List.of(
        "12345", "67890",
        "abcdef", "XYZxyz",
        "qwerty", "asdfgh",
        "HELLO", "WORLD",
        "example1@example.com", "user123@domain.org",
        "こんにちは", "さようなら",
        "漢字のみ", "日本語",
        "カタカナ", "サンプル",
        "Abc123!", "Test456?", "User@789", "Demo_2023",
        "abcこんにちは", "HELLOサンプル", "テスト123", "メールxyz@example.com",
        "追加要素1", "追加要素2", "Mix123あいさつ", "漢字テスト123",
        "新千歳空港（CTS）", "香港国際空港（HKG）"
    );
    System.out.println("mixedElementsList:");
    System.out.println(mixedElementsList);
    System.out.println("mixedElementsList size:" + mixedElementsList.size());
    System.out.println();

    System.out.println("数字だけ抜き出す正規表現");
    List<Integer> numberList = mixedElementsList.stream()
        .filter(s -> s.matches("^\\d*$"))
        .map(Integer::parseInt)
        .toList();
    System.out.println("numberList:" + numberList);
    System.out.println();

    System.out.println("文字（アルファベット）だけ抜き出す正規表現");
    List<String> alphabetList = mixedElementsList.stream()
        .filter(s -> s.matches("[a-zA-Z]*"))
        .toList();
    System.out.println("alphabetList:" + alphabetList);
    System.out.println();

    System.out.println("英数字の大文字だけ抜き出す正規表現");
    List<String> alphabetUpperCaseList = mixedElementsList.stream()
        .filter(s -> s.matches("^[A-Z]*$"))
        .toList();
    System.out.println(alphabetUpperCaseList);
    System.out.println();

    System.out.println("漢字、ひらがな、カタカナだけ抜き出す正規表現");
    List<String> japaneseList = mixedElementsList.stream()
        .filter(s -> s.matches("^[\\p{IsHan}\\p{IsHiragana}\\p{IsKatakana}]*$"))
        .toList();
    System.out.println("kanjiList:" + japaneseList);
    System.out.println();

    System.out.println("英数字以外を抜き出す正規表現");
    List<String> nonAlphanumericList = mixedElementsList.stream()
        .filter(s -> s.matches("^[^0-9a-zA-Z]*$"))
        .toList();
    System.out.println("nonAlphanumericList:" + nonAlphanumericList);
    System.out.println();


  }
}