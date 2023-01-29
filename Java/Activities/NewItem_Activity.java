package com.example.e_commerceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_commerceapp.db.AppDatabase;
import com.example.e_commerceapp.db.UserDAO;

public class  NewItem_Activity extends AppCompatActivity {

    private TextView newItemName;
    private TextView newItemPrice;
    private TextView newItemRate;
    private TextView newItemCategory;
    private Button addItemBtn;
    private UserDAO projectDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item_);

        wireUpConnections();
        getDatabase();

        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItemToDB();
            }
        });

    }

    //START UP METHODS----------------------------------------------------
    public static Intent newIntent(Context packageContext){
        Intent intent = new Intent(packageContext, NewItem_Activity.class);
        return intent;
    }

    //---------------------------------
    private void wireUpConnections(){
        newItemName = findViewById(R.id.newItemName);
        newItemPrice = findViewById(R.id.newItemPrice);
        newItemRate = findViewById(R.id.newItemRate);
        newItemCategory = findViewById(R.id.newItemCategory);
        addItemBtn = findViewById(R.id.submitItem);
    }

    //---------------------------------
    private void getDatabase(){
        //builds database on create---
        projectDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getUserDAO();
    }

    //ADD NEW ITEM TO DB--------------------------------------------------
    private void addItemToDB(){
        String name = newItemName.getText().toString();
        String rating = newItemRate.getText().toString();
        String category = newItemCategory.getText().toString();
        double price = 0.0;

        try {
            price = Double.parseDouble(newItemPrice.getText().toString());   //turns text into double
        }
        catch (NumberFormatException e) {
            Log.d("CONVERSION", "getValue: couldn't covert double. ");
        }

        Items newItem = new Items(name, price, rating, category);
        projectDAO.insert(newItem);

        Toast toast = Toast.makeText(NewItem_Activity.this, "Item published", Toast.LENGTH_SHORT);
        toast.show();
    }

}