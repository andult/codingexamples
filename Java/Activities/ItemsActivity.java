package com.example.e_commerceapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_commerceapp.db.AppDatabase;
import com.example.e_commerceapp.db.UserDAO;

import java.util.ArrayList;
import java.util.List;

public class ItemsActivity extends AppCompatActivity {

    private TextView mainDisplay;
    private TextView category;
    private TextView item_name;
    private Button searchBtn;
    private Button addToCart;
    private Button removeFromCart;
    private Button writeReview;
    private Button placeOrder;

    private UserDAO shoppingDAO;
    private List<Items> items;
    private List<Items> cart = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        wireUpConnections();
        getDatabase();
        populateDatabase();
        displayAllItems();

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchByCategory();
            }
        });

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItemToCart();
            }
        });

        removeFromCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItemFromCart();
            }
        });

        writeReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = NewReview_Activity.newIntent(ItemsActivity.this);
                startActivity(intent);
            }
        });

        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewCart();
            }
        });
    }

    //START UP METHODS----------------------------------------------------
    public static Intent newIntent(Context packageContext){
        Intent intent = new Intent(packageContext, ItemsActivity.class);
        return intent;
    }

    //---------------------------------
    private void wireUpConnections(){
        mainDisplay = findViewById(R.id.mainDisplay);
        mainDisplay.setMovementMethod(new ScrollingMovementMethod());
        category = findViewById(R.id.editTextTextPersonName);
        item_name = findViewById(R.id.editTextItem);
        searchBtn = findViewById(R.id.search);
        addToCart = findViewById(R.id.addToCart);
        removeFromCart = findViewById(R.id.removeFromCart);
        writeReview = findViewById(R.id.add_review);
        placeOrder = findViewById(R.id.place_order);
    }

    //---------------------------------
    private void getDatabase(){
        //builds database on create---
        shoppingDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getUserDAO();
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
            sb.append("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
            sb.append("\n");
        }
        mainDisplay.setText(sb.toString());
    }

    //---------------------------------
    private void searchByCategory(){
        String searchTerm = category.getText().toString();

        if(searchTerm == "reset") {
            items = shoppingDAO.getAllItems();  //TODO:not working?
        }
        else {
            items = shoppingDAO.getItemsByCategory(searchTerm);
        }

        StringBuilder sb = new StringBuilder();
        for (Items item : items) {
            sb.append(item);
            sb.append("\n");
            sb.append("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
            sb.append("\n");
        }
        mainDisplay.setText(sb.toString());
    }

    //CART RELATED METHODS------------------------------------------------
    private void addItemToCart(){
        String itemRequest = item_name.getText().toString();
        Items add_item = shoppingDAO.getItemByName(itemRequest);

        //add to list
        if(add_item == null){
            Toast toast = Toast.makeText(ItemsActivity.this, "Item not found", Toast.LENGTH_SHORT);
            toast.show();
        }
        else {
            cart.add(add_item);
            Toast toast = Toast.makeText(ItemsActivity.this, itemRequest + " was added", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    //---------------------------------
    private void removeItemFromCart(){
        String itemRequest = item_name.getText().toString();
        Items remove_item = shoppingDAO.getItemByName(itemRequest);

        if(remove_item == null){
            Toast toast = Toast.makeText(ItemsActivity.this, "Item not found", Toast.LENGTH_SHORT);
            toast.show();

        }
        else {
             if (cart.contains(remove_item)) {
                cart.remove(remove_item);
                Toast toast = Toast.makeText(ItemsActivity.this, itemRequest + " was removed", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                Toast toast = Toast.makeText(ItemsActivity.this, "Item not in cart " + cart.toString(), Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

    //---------------------------------
    private void viewCart(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setMessage("My Cart");

        TextView cartDisplay = new TextView(ItemsActivity.this);
        cartDisplay.setText(cart.toString());
        cartDisplay.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        cartDisplay.setHeight(500);
        cartDisplay.setMovementMethod(new ScrollingMovementMethod());
        alertBuilder.setView(cartDisplay);

        //set up functionality of 2 buttons------------
        alertBuilder.setPositiveButton(getString(R.string.place_order_text),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(cart.size() > 0) {
                            Toast toast = Toast.makeText(ItemsActivity.this, "Order placed :)", Toast.LENGTH_SHORT);
                            toast.show();
                            cart.clear();
                        }
                        else {
                            Toast toast = Toast.makeText(ItemsActivity.this, "Cart is empty", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                });
        alertBuilder.setNegativeButton(getString(R.string.clear_cart),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cart.clear();
                        Toast toast = Toast.makeText(ItemsActivity.this, "Cart cleared", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
        alertBuilder.create().show();
    }
}