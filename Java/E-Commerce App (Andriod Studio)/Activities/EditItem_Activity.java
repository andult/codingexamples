package com.example.e_commerceapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_commerceapp.db.AppDatabase;
import com.example.e_commerceapp.db.UserDAO;

public class EditItem_Activity extends AppCompatActivity {

    private TextView editName;
    private TextView editPrice;
    private TextView editRating;
    private TextView editCategory;
    private TextView findItem;
    private Button findBtn;
    private Button editBtn;

    private UserDAO projectDAO;
    private Items item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item_);

        wireUpConnections();
        getDatabase();

        findBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefillFields();
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateItem();
            }
        });
    }

    //START UP METHODS----------------------------------------------------
    public static Intent newIntent(Context packageContext){
        Intent intent = new Intent(packageContext, EditItem_Activity.class);
        return intent;
    }

    //---------------------------------
    private void wireUpConnections(){
        editName = findViewById(R.id.editName);
        editPrice = findViewById(R.id.editPrice);
        editRating = findViewById(R.id.editRating);
        editCategory = findViewById(R.id.editCategory);
        findItem = findViewById(R.id.findItemText);
        findBtn = findViewById(R.id.findItemBtn);
        editBtn = findViewById(R.id.editBtn);
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
    private void prefillFields(){
        String searchTerm = findItem.getText().toString();
        item = projectDAO.getItemByName(searchTerm);

        if(item == null) {
            Toast toast = Toast.makeText(EditItem_Activity.this,  "Item not found", Toast.LENGTH_SHORT);
            toast.show();
        }
        else {
            editName.setText(item.getName());
            editPrice.setText(String.valueOf(item.getPrice()));
            editRating.setText(item.getRating());
            editCategory.setText(item.getCategory());
        }
    }

    //UPDATE ITEM---------------------------------------------------------
    private void updateItem(){
        //get text from layout-----
        String name = editName.getText().toString();
        String rating = editRating.getText().toString();
        String category = editCategory.getText().toString();
        double price = 0.0;

        //parse string into double---
        try {
            price = Double.parseDouble(editPrice.getText().toString());   //turns text into double
        }
        catch (NumberFormatException e) {
            Log.d("CONVERSION", "getValue: couldn't covert double. ");
        }

        //update item details
        if(item == null) {
            Toast toast = Toast.makeText(EditItem_Activity.this,  "Item not found", Toast.LENGTH_SHORT);
            toast.show();
        }
        else {
            item.setName(name);
            item.setPrice(price);
            item.setRating(rating);
            item.setCategory(category);
        }

        //check if they really want to update-----
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        alertBuilder.setMessage(R.string.edit);

        alertBuilder.setPositiveButton(getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        projectDAO.update(item);

                        Toast toast = Toast.makeText(EditItem_Activity.this, "Item updated" +  item, Toast.LENGTH_SHORT);
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