package com.example.e_commerceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.example.e_commerceapp.db.AppDatabase;
import com.example.e_commerceapp.db.UserDAO;

import java.util.List;

public class OrderHistory_Activity extends AppCompatActivity {

    private TextView orderHistory_display;
    private UserDAO shoppingDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history_);

        wireUpConnections();
        getDatabase();
        //refreshDisplay();
    }

    //START UP METHODS----------------------------------------------------
    public static Intent newIntent(Context packageContext){
        Intent intent = new Intent(packageContext, OrderHistory_Activity.class);
        return intent;
    }

    //---------------------------------
    private void wireUpConnections(){
        orderHistory_display = findViewById(R.id.orderHistory_display);
        orderHistory_display.setMovementMethod(new ScrollingMovementMethod());
    }

    //---------------------------------
    private void getDatabase(){
        //builds database on create---
        shoppingDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getUserDAO();
    }

    //---------------------------------
    private void refreshDisplay(){
        //TODO: implement method
    }
}