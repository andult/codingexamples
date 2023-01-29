package com.example.e_commerceapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.e_commerceapp.db.AppDatabase;

@Entity(tableName = AppDatabase.ISSUES_TABLE)
public class Issues {

    //DECLARE VARIABLES---------------------------------------------------
    @PrimaryKey(autoGenerate = true)
    private int issueId;
    private String review;

    //CONSTRUCTOR---------------------------------------------------------
    public Issues(String review) {
        this.review = review;
    }

    //GETTERS & SETTERS---------------------------------------------------
    public int getIssueId() {
        return issueId;
    }

    public void setIssueId(int issueId) {
        this.issueId = issueId;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
