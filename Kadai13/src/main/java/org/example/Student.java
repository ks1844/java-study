package org.example;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class Student {

  private String name;
  private int score;


  public Student() {

  }

  public Student(String name, int score) {
    this.name = name;
    this.score = score;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


}
