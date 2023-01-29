package com.example.e_commerceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;

import com.example.e_commerceapp.db.AppDatabase;
import com.example.e_commerceapp.db.UserDAO;

public class AdminActivity extends AppCompatActivity {

    private Button viewItems;
    private Button addItems;
    private Button reviews;
    private Button accounts;
    private UserDAO shoppingDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        wireUpConnections();
        getDatabase();

        viewItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AdminItems_Activity.newIntent(AdminActivity.this);
                startActivity(intent);
            }
        });

        addItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = NewItem_Activity.newIntent(AdminActivity.this);
                startActivity(intent);
            }
        });

        reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ViewReviews_Activity.newIntent(AdminActivity.this);
                startActivity(intent);
            }
        });

        accounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ViewAccounts_Activity.newIntent(AdminActivity.this);
                startActivity(intent);
            }
        });
    }

    //START UP METHODS----------------------------------------------------
    public static Intent newIntent(Context packageContext){
        Intent intent = new Intent(packageContext, AdminActivity.class);
        return intent;
    }

    //---------------------------------
    private void wireUpConnections(){
        viewItems = findViewById(R.id.items);
        addItems = findViewById(R.id.addItems);
        reviews = findViewById(R.id.reviews);
        accounts = findViewById(R.id.accounts);
    }

    //---------------------------------
    private void getDatabase(){
        //builds database on create---
        shoppingDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getUserDAO();
    }
}