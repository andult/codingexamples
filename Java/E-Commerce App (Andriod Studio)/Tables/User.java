package com.example.e_commerceapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.e_commerceapp.db.AppDatabase;

import java.util.Objects;

@Entity(tableName = AppDatabase.USER_TABLE) //this is just naming the table 'USER_TABLE'
public class User {
    //DECLARE VARIABLES/COLUMNS IN TABLE--------------
    @PrimaryKey(autoGenerate = true)
    private int UserId;

    private String username;
    private String password;
    private int admin;

    //CONSTRUCTOR-------------------------------------
    public User(String username, String password, int admin) {
        this.username = username;
        this.password = password;
        this.admin = admin;
    }

    //GETTERS & SETTERS-------------------------------
    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    //EQUALS, HASHCODE, & TOSTRING----------------------------------------
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return UserId == user.UserId &&
                admin == user.admin &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password);
    }

    //------------------
    @Override
    public int hashCode() {
        return Objects.hash(UserId, username, password, admin);
    }

    //------------------
    @Override
    public String toString() {
        String output = "username: " + username + "\n";
        output += "password: " + password;
        return output;
    }
}
