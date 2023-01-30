package com.example.e_commerceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_commerceapp.db.AppDatabase;
import com.example.e_commerceapp.db.UserDAO;

public class NewReview_Activity extends AppCompatActivity {

    private EditText reviewTitle;
    private EditText reviewDescription;
    private Button submit;

    private UserDAO projectDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_review_);

        wireUpConnections();
        getDatabase();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addReviewToDB();
            }
        });
    }
    //START UP METHODS----------------------------------------------------
    public static Intent newIntent(Context packageContext){
        Intent intent = new Intent(packageContext,
                NewReview_Activity.class);
        return intent;
    }

    //---------------------------------
    private void wireUpConnections(){
        reviewTitle = findViewById(R.id.item_name);
        reviewDescription = findViewById(R.id.review);
        submit = findViewById(R.id.submitReview);
    }

    //---------------------------------
    private void getDatabase(){
        //builds database on create---
        projectDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getUserDAO();
    }

    //---------------------------------
    private void addReviewToDB(){
        String title = reviewTitle.getText().toString();
        String review = reviewDescription.getText().toString();

        Reviews newReview = new Reviews(title, review);
        projectDAO.insert(newReview);

        Toast toast = Toast.makeText(NewReview_Activity.this, "Review Submitted", Toast.LENGTH_SHORT);
        toast.show();
    }
}