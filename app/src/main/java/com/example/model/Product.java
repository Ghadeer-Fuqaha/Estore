package com.example.model;

public class Product {
    private String Name, Price, Description, ImgPath;

    public Product(String name, String price, String description, String imgPath) {
        Name = name;
        Price = price;
        Description = description;
        ImgPath = imgPath;

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImgPath() {
        return ImgPath;
    }

    public void setImgPath(String imgPath) {
        ImgPath = imgPath;
    }
}
