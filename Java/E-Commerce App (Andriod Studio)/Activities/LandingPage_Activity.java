package com.example.e_commerceapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.e_commerceapp.db.AppDatabase;
import com.example.e_commerceapp.db.UserDAO;

import java.util.List;

public class LandingPage_Activity extends AppCompatActivity {

    //DECLARE VARIABLES---------------------------------------------------
    private static final String USER_ID_KEY = "com.example.e_commerceapp.userIdKey";    //TODO: what is this for
    private static final String PREFERENCES_KEY = "com.example.e_commerceapp.PREFERENCES_KEY";
    private Button shop;
    private Button orderHistory;
    private Button reportIssue;
    private Button logout;
    private Button admin;

    private UserDAO shoppingDAO;
    private int userId = -1;        //-1 to denote no user
    private SharedPreferences preferences = null;
    private User user;

    private String issueDescription;


    //ONCREATE METHOD-----------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page_);

        wireUpConnections();
        getDatabase();
        checkForUser();     //determines whether someone is logged in or not, when starting app
        addUserToPreference(userId);
        loginUser(userId);

        //BUTTON LISTENERS-------------
        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ItemsActivity.newIntent(LandingPage_Activity.this);
                startActivity(intent);

            }
        });

        orderHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = OrderHistory_Activity.newIntent(LandingPage_Activity.this);
                startActivity(intent);
            }
        });

        reportIssue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertMaker();
            }
        });

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AdminActivity.newIntent(LandingPage_Activity.this);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
    }


    //START UP METHODS----------------------------------------------------
    //needs to be accessed outside the class and without instantiating anything
    public static Intent newIntent(Context packageContext, int userId){
        Intent intent = new Intent(packageContext, LandingPage_Activity.class);
        intent.putExtra(USER_ID_KEY, userId);
        return intent;
    }

    private void wireUpConnections(){
        //connect variables to elements on display---
        shop = findViewById(R.id.shop);
        orderHistory = findViewById(R.id.orderHistory);
        reportIssue = findViewById(R.id.reportIssue);
        logout = findViewById(R.id.logout);
        admin = findViewById(R.id.admin);
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
    private void checkForUser(){
        //do we have a user in the intent? - is a user being passed by the intent?
        userId = getIntent().getIntExtra(USER_ID_KEY, -1);  //check if userId in intent
        if(userId != -1){      //if userId exists in intent, stay in Landing Page Activity
            return;
        }

        //do we have a user in the preferences? - was someone already logged in?
        if(preferences == null){
            getPrefs();
        }

        userId = preferences.getInt(USER_ID_KEY, -1);   //if no userId found, set to -1
        if(userId != -1){
            return;     //if userId exists in shared preferences, stay in Landing Page Activity
        }

        //do we have any users at all?
        List<User> users = shoppingDAO.getAllUsers();
        if(users.size() <= 0){      //if there's no users generate these
            User defaultAdmin = new User("admin2", "admin2", 1);
            User defaultUser = new User("testuser1", "testuser1", 0);
            shoppingDAO.insert(defaultAdmin, defaultUser);
        }

        //starts main activity
        Intent intent = LoginActivity.newIntent(LandingPage_Activity.this);
        startActivity(intent);

    }

    //LOGIN A USER--------------------------------------------------------
    private void addUserToPreference(int userId){
        if (preferences == null) {
            getPrefs();
        }
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(USER_ID_KEY, userId);
    }

    //---------------------------------
    private void getPrefs(){
        //get the userId stored in the intent related to USER_ID_KEY, MODE_PRIVATE keeps it in app
        preferences = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
    }

    //---------------------------------
    private void loginUser(int userId){
        user = shoppingDAO.getUserById(userId);
        logout.setText(user.getUsername());

        if(user.getAdmin() == 0){
            admin.setVisibility(View.INVISIBLE);
        }
    }

    //REMOVING USER METHODS-----------------------------------------------
    private void logoutUser(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        alertBuilder.setMessage(R.string.total_logout);

        alertBuilder.setPositiveButton(getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast toast = Toast.makeText(LandingPage_Activity.this, "You logged out", Toast.LENGTH_SHORT);
                        toast.show();
                        clearUserFromIntent();
                        clearUserFromPref();
                        userId = -1;    //resets userId
                        checkForUser(); //returns to login screen bc userId is now -1
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

    //---------------------------------
    private void clearUserFromIntent(){
        getIntent().putExtra(USER_ID_KEY, -1);      //set the user to -1, meaning no user
    }

    //---------------------------------
    private void clearUserFromPref(){
        addUserToPreference(-1);    //sets the userId to -1, meaning no user
    }

    //OTHER METHODS-------------------------------------------------------
    private void alertMaker(){
        //alert builder will build an alert

        //build an alert in 'this' activity
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setMessage("Please report the issue below");

        EditText issue = new EditText(LandingPage_Activity.this);
        issue.setInputType(InputType.TYPE_CLASS_TEXT);
        issue.setHint("Enter issue...");
        alertBuilder.setView(issue);

        //set up functionality of 2 buttons------------
        alertBuilder.setPositiveButton(getString(R.string.submit),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        issueDescription = issue.getText().toString();

                        Issues issue = new Issues(issueDescription);
                        shoppingDAO.insert(issue);
                        Toast toast = Toast.makeText(LandingPage_Activity.this, "added", Toast.LENGTH_SHORT);
//                        Toast toast = Toast.makeText(LandingPage_Activity.this, "You reported an issue: " + issueDescription, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
        alertBuilder.setNegativeButton(getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //nothing will happen, dialog will simply close
                    }
                });
        alertBuilder.create().show();
    }
}