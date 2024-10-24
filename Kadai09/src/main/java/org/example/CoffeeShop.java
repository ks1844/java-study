package org.example;

public final class CoffeeShop extends Shop implements Selling {

  public CoffeeShop() {
  }

  @Override
  public String sell(String item) {
    return "Sell " + item;
  }

  public String giveCoupon(int price){
    return "Hand out "+price+" yen";
  }

  public String employStaff(String name) {
//    return "Employ Staff " + name + " with monthly salary of " + salary + " yen";
    return "Coffee Shop Employed " + name;
  }

}
