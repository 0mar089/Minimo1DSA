package models;

public class Product {

    String id;
    String name;
    double price;
    int sales;

    public Product(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int sales() {
        return this.sales;
    }

    public double getPrice() {
        return price;
    }

    public void UpSales() {
        this.sales++;
    }

    public String getName(){
        return name;
    }

    public String getId(){
        return id;
    }
}
