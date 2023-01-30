package com.example.e_commerceapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_commerceapp.db.AppDatabase;
import com.example.e_commerceapp.db.UserDAO;

import java.util.List;

public class AdminItems_Activity extends AppCompatActivity {

    private TextView searchText;
    private TextView mainDisplay;
    private Button searchBtn;
    private Button deleteItemBtn;
    private Button addItemBtn;
    private Button editItemBtn;

    private UserDAO shoppingDAO;
    private List<Items> items;
    private Items single_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_items_);

        wireUpConnections();
        getDatabase();
        populateDatabase();
        displayAllItems();

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchByName();
            }
        });

        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToDatabase();
            }
        });

        deleteItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFromDatabase();
                displayAllItems();
            }
        });

        editItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = EditItem_Activity.newIntent(AdminItems_Activity.this);
                startActivity(intent);
            }
        });
    }

    //START UP METHODS----------------------------------------------------
    public static Intent newIntent(Context packageContext){
        Intent intent = new Intent(packageContext, AdminItems_Activity.class);
        return intent;
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
    private void wireUpConnections(){
        searchText = findViewById(R.id.searchIDText);
        searchBtn = findViewById(R.id.searchBtn);
        mainDisplay = findViewById(R.id.mainDisplay_admin);
        mainDisplay.setMovementMethod(new ScrollingMovementMethod());
        deleteItemBtn = findViewById(R.id.deleteItem);
        addItemBtn = findViewById(R.id.createItem);
        editItemBtn = findViewById(R.id.editItemBtn);
    }

    //----------------------------------
    private void populateDatabase(){
        items = shoppingDAO.getAllItems();
        if (items.size() <= 0){
            Items lore = new Items("A Touch of Darkness", 13.49, "★★★★★", "books");
            Items addieLarue = new Items("The Invisible Life of Addie Larue", 16.19, "★★★★", "books");
            Items embosser = new Items("Embosser", 34.99, "★★★★", "books");
            Items floatingBooks = new Items("Floating Book Shelves", 32.99,"★★★★", "books");
            Items leash = new Items("Skating Leash", 9.99, "★★★", "skates");
            Items ledWheels = new Items("LED Wheels", 19.99,"★★★★★", "skates");
            Items pads = new Items("Safety Guards", 25.99, "★★★", "skates");
            Items mousePad = new Items("Kitty Paw Mousepad", 7.99, "★★★★", "cats");
            Items ledCat = new Items("LED Cat Night Light", 19.95, "★★★★★", "cats");
            Items pillowCat = new Items("Marshmellow Cat Pillow", 27.91, "★★★★★★", "cats");

            shoppingDAO.insert(lore, addieLarue, embosser, floatingBooks,
                    ledWheels, leash, pads, mousePad, ledCat, pillowCat);
        }
    }

    //DISPLAY METHODS-----------------------------------------------------
    private void displayAllItems(){
        items = shoppingDAO.getAllItems();

        StringBuilder sb = new StringBuilder();
        for (Items item : items) {
            sb.append(item);
            sb.append("\n");
            sb.append("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
            sb.append("\n");
        }
        mainDisplay.setText(sb.toString());
    }

    //---------------------------------
    private void searchByName(){
        String searchTerm = searchText.getText().toString();

        if(searchTerm == "") {
            items = shoppingDAO.getAllItems();
        }
        else {
            single_item = shoppingDAO.getItemByName(searchTerm);

            if (single_item == null) {
                Toast toast = Toast.makeText(AdminItems_Activity.this, "Item not found", Toast.LENGTH_SHORT);
                toast.show();
                displayAllItems();
                return;
            }
            else {
                mainDisplay.setText(single_item.toString());
            }
        }
    }

    //ITEM RELATED METHODS------------------------------------------------
    private void addToDatabase(){
        Intent intent = NewItem_Activity.newIntent(AdminItems_Activity.this);
        startActivity(intent);
    }

    //---------------------------------
    private void deleteFromDatabase(){
        String itemRequest = searchText.getText().toString();
        Items remove_item = shoppingDAO.getItemByName(itemRequest);

        if(remove_item == null){
            Toast toast = Toast.makeText(AdminItems_Activity.this, "Item not found", Toast.LENGTH_SHORT);
            toast.show();
        }
        else {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

            alertBuilder.setMessage("Are you sure you want to DELETE " + itemRequest + "?");

            alertBuilder.setPositiveButton(getString(R.string.yes),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            shoppingDAO.delete(remove_item);
                            Toast toast = Toast.makeText(AdminItems_Activity.this, itemRequest + " was removed", Toast.LENGTH_SHORT);
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