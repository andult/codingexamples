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

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    //DECLARE VARIABLES---------------------------------------------------
    private EditText usernameField;
    private EditText passwordField;
    private Button login;
    private TextView signUp;

    private UserDAO userDAO;    //var to access db objects
    private List<User> users;   //too store results from db query
    private String username;
    private String password;
    private User user;

    //MAIN/RUN PROGRAM----------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //runs the main display---

        wireUpConnections();
        getDatabase();

        //goes to login activity to make a new account---
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //starts login activity
                Intent intent = CreateAccount_Activity.newIntent(LoginActivity.this);
                startActivity(intent);
            }
        });
    }

    //START UP METHODS----------------------------------------------------
    private void wireUpConnections(){
        //connect variables to elements on display---
        usernameField = findViewById(R.id.username);
        passwordField = findViewById(R.id.password);
        login = findViewById(R.id.login);
        signUp = findViewById(R.id.signUp);

        //checks login---
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValuesFromDisplay();
                if(checkForUserInDatabase()){
                    if(!validatePassword()){
                        Toast.makeText(LoginActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                    }
                    else{   //if pwd is correct, send to landing page activity
                        Intent intent = LandingPage_Activity.newIntent(getApplicationContext(), user.getUserId());
                        startActivity(intent);
                    }
                }
            }
        });
    }

    //---------------------------------
    private void getDatabase(){
        //builds database on create---
        userDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getUserDAO();
    }

    //---------------------------------
    public static Intent newIntent(Context packageContext) {
        //creates an intent to render login activity---
        Intent intent = new Intent(packageContext, LoginActivity.class);     //calls main activity?

        return intent;
    }

    //LOGIN VALIDATION METHODS-------------------------------------------------------
    private void getValuesFromDisplay(){
        username = usernameField.getText().toString();
        password = passwordField.getText().toString();
    }

    //---------------------------------
    private boolean checkForUserInDatabase(){
        //add default users
        if(userDAO.getUserByUsername("admin2") == null && userDAO.getUserByUsername("testuser1") == null){
            User defaultAdmin = new User("admin2", "admin2", 1);
            User defaultUser = new User("testuser1", "testuser1", 0);
            userDAO.insert(defaultAdmin, defaultUser);
        }
        
        user = userDAO.getUserByUsername(username);
        if(user == null){
            Toast.makeText(this, "No user " + username + " found", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    //---------------------------------
    private boolean validatePassword(){
        return user.getPassword().equals(password);     //checks if passwords match
    }
}