/*The appdatabase abstract class builds/holds the database
    uses the singleton pattern - makes sure there is only ever one
    instance of the class at a time.
*/

package com.example.e_commerceapp.db;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.e_commerceapp.Issues;
import com.example.e_commerceapp.Items;
//import com.example.e_commerceapp.Orders;
import com.example.e_commerceapp.Reviews;
import com.example.e_commerceapp.User;

@Database(entities = {User.class, Items.class, Reviews.class, Issues.class}, version = 3)
        //an entity is building the table: create table users...
public abstract class AppDatabase extends RoomDatabase {
    public static final String DB_NAME = "USER_DATABASE";
    public static final String USER_TABLE = "USER_TABLE";
    public static final String ITEMS_TABLE = "ITEMS_TABLE";
    public static final String REVIEWS_TABLE = "REVIEWS_TABLE";
    public static final String ISSUES_TABLE = "ISSUES_TABLE";
//    public static final String ORDERS_TABLE = "ORDERS_TABLE";

    public abstract UserDAO getUserDAO();
    //defines abstract method that return DAOs (data access abjects)
}
