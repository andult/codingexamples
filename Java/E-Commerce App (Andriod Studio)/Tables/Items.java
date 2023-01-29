package com.example.e_commerceapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.e_commerceapp.db.AppDatabase;

import java.util.Objects;

@Entity(tableName = AppDatabase.ITEMS_TABLE) //this is just naming the table 'ITEM_TABLE'
public class Items {

    //DECLARE VARIABLES---------------------------------------------------
    @PrimaryKey(autoGenerate = true)
    private int itemId;
    private String name;
    private double price;
//    private int quantity;
    private String rating;
    private String category;

    //CONSTRUCTOR---------------------------------------------------------
    public Items(String name, double price, String rating, String category) {
        this.name = name;
        this.price = price;
//        this.quantity = quantity;
        this.rating = rating;
        this.category = category;
    }

    //GETTERS & SETTERS---------------------------------------------------
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

//    public int getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(int quantity) {
//        this.quantity = quantity;
//    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    //TOSTRING, EQUALS, AND HASHCODE--------------------------------------
    @Override
    public String toString() {
        String output;
        output = name + "\n";
        output += "Price: $" + price + "\n";
//        output += "In Stock: " + quantity + "\n";
        output += "Category: " + category + "\n";
        output += "Rating: " + rating + "\n";
        return output;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Items items = (Items) o;
        return itemId == items.itemId &&
                Double.compare(items.price, price) == 0 &&
                Objects.equals(name, items.name) &&
                Objects.equals(rating, items.rating) &&
                Objects.equals(category, items.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, name, price, rating, category);
    }
}
