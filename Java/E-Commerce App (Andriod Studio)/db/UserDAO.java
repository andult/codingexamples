/*The DAO allows us to access the data in the db.
Its where the queries are designed/declared, not made/built bc its an interface.*/

package com.example.e_commerceapp.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.FtsOptions;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.e_commerceapp.Issues;
import com.example.e_commerceapp.Items;
//import com.example.e_commerceapp.Orders;
import com.example.e_commerceapp.Reviews;
import com.example.e_commerceapp.User;

import java.util.List;

@Dao
public interface UserDAO {
    //USER QUERIES--------------------------------------------------------
    //ellipses mean that the insert statement can take one or more arguments
    @Insert
    void insert(User... users);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE)
    List<User> getAllUsers();  //returns a list of all the users

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE UserId = :userId")
    User getUserById(int userId);   //returns a specific user

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE username = :userName")
    User getUserByUsername(String userName);   //returns a specific user

    //ITEMS QUERIES-------------------------------------------------------
    @Insert
    void insert(Items... items);

    @Update
    void update(Items... items);

    @Delete
    void delete(Items item);

    @Query("SELECT * FROM " + AppDatabase.ITEMS_TABLE)
    List<Items> getAllItems();

    @Query("SELECT * FROM " + AppDatabase.ITEMS_TABLE + " WHERE itemId = :ItemId")
    Items getItemById(int ItemId);

    @Query("SELECT * FROM " + AppDatabase.ITEMS_TABLE + " WHERE name = :itemName")
    Items getItemByName(String itemName);

    @Query("SELECT * FROM " + AppDatabase.ITEMS_TABLE + " WHERE category = :Category")
    List<Items> getItemsByCategory(String Category);

    //REVIEWS QUERIES-------------------------------------------------------
    @Insert
    void insert(Reviews... reviews);

    @Delete
    void delete(Reviews review);

    @Query("SELECT * FROM " + AppDatabase.REVIEWS_TABLE)
    List<Reviews> getAllReviews();

    @Query("SELECT * FROM " + AppDatabase.REVIEWS_TABLE + " WHERE reviewId = :ReviewID")
    Reviews getReviewById(int ReviewID);

    //ITEMS QUERIES-------------------------------------------------------
    @Insert
    void insert(Issues... issues);

    @Delete
    void delete(Issues... issue);

    @Query("SELECT * FROM " + AppDatabase.ISSUES_TABLE)
    List<Issues> getAllIssues();

    //ORDERS QUERIES------------------------------------------------------
//    @Insert
//    void insert(Orders... orders);
//
//    @Delete
//    void delete(Orders order);
//
//    @Query("SELECT * FROM "+ AppDatabase.ORDERS_TABLE)
//    List<Orders> getAllOrders();
//
//    @Query("SELECT * FROM "+ AppDatabase.ORDERS_TABLE + " WHERE username + :user")
//    List<Orders> getAllOrdersByUser(String user);
}