package org.example;

public class Main {

  public static void main(String[] args) {
    System.out.println("今日作ったものを一通り実践");
    Greeting greeting = new Greeting();
    String message = greeting.sayHello(("John"));

    Printer printer = new Printer();
    printer.printMessage(message);

    // Speaking speaking = new Speaking()
    printer.printMessage(greeting.say("Alen"));

    Speaking speaking = new Greeting();
    printer.printMessage(speaking.say("Bob"));
    System.out.println();

    // 課題
    // 今日実践したものを一通り実装してみてください。
    // GreetingとSpeakingは使わずにinterfaceと実装を組み合わせて作ってみてください。
    // interfaceの実装を2つ以上作ってください。
    // 継承を使ってみてください。extends 上記で作った2つの実装クラスのどちらかを継承してクラスを作って実際にメソッドを呼び出して見てください。
    // メソッド呼び出しは親子のどちらとも呼び出してみてください。

    System.out.println("インターフェースの実装2つ：Selling.java、Buying.java");
    System.out.println("継承を使ってみてください：");
    System.out.println("親クラス：Shop.java");
    System.out.println("子クラス：CoffeeShop.java");
    System.out.println();

    System.out.println("実際にメソッドを使って呼び出し");
    System.out.println("インターフェースからオーバーライドしたメソッドを使う：");
    Selling komedaCoffee = new CoffeeShop();
    printer.printMessage(komedaCoffee.sell("Original"));

    CoffeeShop starbucksCoffee = new CoffeeShop();
    System.out.println("子クラスでオーバーライドしたメソッドを使う：");
    printer.printMessage(starbucksCoffee.employStaff("tanaka"));
    System.out.println();

    System.out.println("親クラスで停止されたメソッドを使う：");
    printer.printMessage(starbucksCoffee.employManager("suzuki"));
    System.out.println();

    System.out.println("子クラスで定義したメソッドを使う：");
    printer.printMessage(starbucksCoffee.giveCoupon(500));
    System.out.println();

  }
}