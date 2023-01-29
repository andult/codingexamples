package com.example.e_commerceapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_commerceapp.db.AppDatabase;
import com.example.e_commerceapp.db.UserDAO;

import java.util.List;

public class ViewReviews_Activity extends AppCompatActivity {

    private TextView searchID;
    private TextView mainDisplay;
    private Button deleteReviewBtn;

    private UserDAO projectDAO;
    private List<Reviews> reviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reviews_);

        wireUpConnections();
        getDatabase();
        displayAllReviews();

        deleteReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteReviewFromDB();
                displayAllReviews();
            }
        });

    }

    //START UP METHODS----------------------------------------------------
    public static Intent newIntent(Context packageContext){
        Intent intent = new Intent(packageContext, ViewReviews_Activity.class);
        return intent;
    }

    //---------------------------------
    private void wireUpConnections(){
        searchID = findViewById(R.id.searchIDText);
        mainDisplay = findViewById(R.id.mainDisplay_reviews);
        mainDisplay.setMovementMethod(new ScrollingMovementMethod());
        deleteReviewBtn = findViewById(R.id.deleteReviewsBtn);
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
    private void displayAllReviews(){
        reviews = projectDAO.getAllReviews();

        if (reviews.size() <= 0){
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (Reviews review : reviews) {
            sb.append(review);
            sb.append("\n");
            sb.append("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
            sb.append("\n");
        }
        mainDisplay.setText(sb.toString());
    }

    //REVIEW RELATED METHODS----------------------------------------------
    private void deleteReviewFromDB(){
        int searchItem = -1;
        Reviews single_review;

        //try to convert input to integer---
        try {
            searchItem = Integer.parseInt(searchID.getText().toString());
        }
        catch (NumberFormatException e) {
            Log.d("CONVERSION", "getValue: couldn't covert int. ");
        }

        //delete review---
        if (searchItem != -1){
            single_review = projectDAO.getReviewById(searchItem);

            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

            alertBuilder.setMessage("Are you sure you want to DELETE review #" + searchItem + "?");

            alertBuilder.setPositiveButton(getString(R.string.yes),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            projectDAO.delete(single_review);
                            Toast toast = Toast.makeText(ViewReviews_Activity.this,  "Review was removed", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });
            alertBuilder.setNegativeButton(getString(R.string.no),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //nothing will happen, dialog will simply close
                        }
                    });

            alertBuilder.create().show();

        }
    }
}