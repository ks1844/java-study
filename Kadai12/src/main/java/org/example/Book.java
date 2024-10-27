package org.example;

public class Book {

  private String title;
  private String author;
  private int number;


  public Book(String title, String author, int number) {
    this.title = title;
    this.author = author;
    this.number = number;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public int getNumber() {
    return number;
  }

  public void setNumber(int number) {
    this.number = number;
  }
}
