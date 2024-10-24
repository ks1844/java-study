package org.example;

public class Greeting implements Speaking {

  public String sayHello(String name) {
    return "Hello, " + name + "!";
  }

  public String sayHelloJapanse(String name) {
    return "こんにちは, " + name + "!";
  }

  @Override
  public String say(String name) {
    return "Implement Speaking Interface, "+ name +"!";
  }
}
