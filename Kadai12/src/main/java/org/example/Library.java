package org.example;

import java.util.ArrayList;
import java.util.List;

public class Library {

  private List<Book> bookList = new ArrayList<>();


  public List<Book> getBookList() {
    return bookList;
  }

  public void setBookList(List<Book> bookList) {
    this.bookList = bookList;
  }

  public void addBook(String title, String author, int number) {
    // 書籍リストに書籍を追加する
    Book book = new Book(title, author, number);
    bookList.add(book);
  }

  public void showBookList() {
    // 書籍リストを全件コンソール出力する
    showBookList(this.bookList);
  }

  public void showBookList(List<Book> bookListSelected) {
    // 取得した書籍リストをコンソール出力する
    if (bookListSelected.isEmpty()) {
      System.out.println("書籍は見つかりませんでした。");
    } else {
      bookListSelected.stream()
          .forEach(book -> System.out.printf("%d,%s,%s\n", book.getNumber(), book.getTitle(),
              book.getAuthor()));
    }
  }

  public List<Book> selectBookByTitle(String title) {
    // タイトルを検索し、検索に引っかかった書籍をリストにして返す
    return bookList.stream()
        .filter(book -> book.getTitle().matches(".*%s.*".formatted(title)))
        .toList();
  }

  public void showBookListByTitle(String title) {
    // タイトルを検索し、検索に引っかかった書籍をコンソール出力する
    List<Book> bookListSelected = selectBookByTitle(title);
    showBookList(bookListSelected);
  }

  public List<Book> selectBookByAuthor(String author) {
    // 著者を検索し、検索に引っかかった書籍をリストにして返す
    return bookList.stream()
        .filter(book -> book.getAuthor().matches(".*%s.*".formatted(author)))
        .toList();
  }

  public void showBookListByAuthor(String author) {
    // 著者を検索し、検索に引っかかった書籍をコンソール出力する
    List<Book> bookListSelected = selectBookByAuthor(author);
    showBookList(bookListSelected);
  }

  public List<Book> selectBookByNumber(int number) {
    // 番号を検索し、検索に引っかかった書籍をリストにして返す
    return bookList.stream()
        .filter(book -> book.getNumber() == number)
        .toList();
  }

  public void showBookListByNumber(int number) {
    // 番号を検索し、検索に引っかかった書籍をコンソール出力する
    List<Book> bookListSelected = selectBookByNumber(number);
    showBookList(bookListSelected);
  }

}
