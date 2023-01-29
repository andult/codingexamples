//package com.example.e_commerceapp;
//
//import androidx.room.Entity;
//import androidx.room.PrimaryKey;
//
//import com.example.e_commerceapp.db.AppDatabase;
//
//import java.util.List;
//import java.util.Objects;
//
//@Entity(tableName = AppDatabase.ORDERS_TABLE)
//public class Orders {
//
//    //DECLARE VARIABLES---------------------------------------------------
//    @PrimaryKey(autoGenerate = true)
//    private int orderId;
//
//    private String username;
//    private String itemName;
//
//    //CONSTRUCTOR---------------------------------------------------------
//    public Orders(String username, String itemName) {
//        this.username = username;
//        this.itemName = itemName;
//    }
//
//    //GETTERS & SETTERS---------------------------------------------------
//    public int getOrderId() {
//        return orderId;
//    }
//
//    public void setOrderId(int orderId) {
//        this.orderId = orderId;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getItemName() {
//        return itemName;
//    }
//
//    public void setItemName(String itemName) {
//        this.itemName = itemName;
//    }
//
//    //OTHER METHODS-------------------------------------------------------
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Orders orders = (Orders) o;
//        return orderId == orders.orderId &&
//                Objects.equals(username, orders.username) &&
//                Objects.equals(itemName, orders.itemName);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(orderId, username, itemName);
//    }
//
//    @Override
//    public String toString() {
//        return "Orders{" +
//                "orderId=" + orderId +
//                ", username='" + username + '\'' +
//                ", itemName='" + itemName + '\'' +
//                '}';
//    }
//}
