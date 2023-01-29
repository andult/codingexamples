package com.example.e_commerceapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.e_commerceapp.db.AppDatabase;

@Entity(tableName = AppDatabase.REVIEWS_TABLE)
public class Reviews {

    //DECLARE VARIABLES---------------------------------------------------
    @PrimaryKey(autoGenerate = true)
    private int reviewId;

    private String itemName;
    private String review;

    //CONSTRUCTOR---------------------------------------------------------
    public Reviews(String itemName, String review) {
        this.itemName = itemName;
        this.review = review;
    }
    //GETTERS & SETTERS---------------------------------------------------
    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    @Override
    public String toString() {
        String output = "Review ID: " + reviewId + "\n";
        output += itemName + "\n";
        output += review;
        return output;
    }
}
