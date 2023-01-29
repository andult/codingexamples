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

public class CreateAccount_Activity extends AppCompatActivity {

    //DECLARE VARIABLES---------------------------------------------------
    private EditText username_create_field;
    private EditText password_create_field;
    private Button createAccount;
    private TextView signIn;

    private UserDAO userDAO;
    private String username_create;
    private String password_create;
    private User newUser;

    //ONCREATE METHOD-----------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        wireUpConnections();
        getDatabase();

        //goes back to sign in to login---
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //starts main activity
                Intent intent = LoginActivity.newIntent(CreateAccount_Activity.this);
                startActivity(intent);
            }
        });
    }

    //START UP METHODS----------------------------------------------------
    public static Intent newIntent(Context packageContext){
        Intent intent = new Intent(packageContext, CreateAccount_Activity.class);

        return intent;
    }

    //---------------------------------
    private void wireUpConnections(){
        //connect variables to elements on display---
        username_create_field = findViewById(R.id.username_create);
        password_create_field = findViewById(R.id.password_create);
        createAccount = findViewById(R.id.createAccount);
        signIn = findViewById(R.id.signIn);

        //create account button---
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValuesFromDisplay();
                if(checkForUserInDatabase()){
                    Toast.makeText(CreateAccount_Activity.this, "Account already exists", Toast.LENGTH_SHORT).show();

                    //starts main activity
                    Intent intent = LoginActivity.newIntent(CreateAccount_Activity.this);
                    startActivity(intent);
                }
                else{
                    addUserToDatbase();
                }
            }
        });
    }

    //---------------------------------
    private void getDatabase(){
        userDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getUserDAO();
    }

    //CREATE ACCOUNT METHODS----------------------------------------------
    private void getValuesFromDisplay(){
        username_create = username_create_field.getText().toString();
        password_create = password_create_field.getText().toString();
    }

    //---------------------------------
    private boolean checkForUserInDatabase(){
        //should return false because the user doesn't exist yet
        newUser = userDAO.getUserByUsername(username_create);
        if (newUser == null){
            return false;
        }
        return true;
    }

    //---------------------------------
    private void addUserToDatbase(){
        newUser = new User(username_create, password_create, 0); //0 bc not an admin
        userDAO.insert(newUser);        //adds new user to database

        Toast toast = Toast.makeText(CreateAccount_Activity.this, "Account Created", Toast.LENGTH_SHORT);
        toast.show();

        //starts the landing page activity
        Intent intent = LandingPage_Activity.newIntent(getApplicationContext(), newUser.getUserId());
        startActivity(intent);
    }
}