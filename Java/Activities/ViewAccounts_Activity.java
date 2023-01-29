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

public class ViewAccounts_Activity extends AppCompatActivity {

    private TextView searchUsername;
    private TextView mainDisplay;
    private Button deleteAccountBtn;

    private UserDAO projectDAO;
    private List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_accounts_);

        wireUpConnections();
        getDatabase();
        displayAllAccounts();

        deleteAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAccountFromDB();
                displayAllAccounts();
            }
        });
    }

    //START UP METHODS----------------------------------------------------
    public static Intent newIntent(Context packageContext){
        Intent intent = new Intent(packageContext, ViewAccounts_Activity.class);
        return intent;
    }

    //---------------------------------
    private void wireUpConnections(){
        searchUsername = findViewById(R.id.searchUserName);
        mainDisplay = findViewById(R.id.mainDisplay_accounts);
        mainDisplay.setMovementMethod(new ScrollingMovementMethod());
        deleteAccountBtn = findViewById(R.id.deleteAccountsBtn);
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
    private void displayAllAccounts(){
        users = projectDAO.getAllUsers();

        if (users.size() <= 0){
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (User user : users) {
            sb.append(user);
            sb.append("\n");
            sb.append("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
            sb.append("\n");
        }
        mainDisplay.setText(sb.toString());
    }

    //ACCOUNT RELATED METHODS----------------------------------------------
    private void deleteAccountFromDB(){
        String username = searchUsername.getText().toString();
        User deleteUser = projectDAO.getUserByUsername(username);

        if (deleteUser == null) {
            Toast toast = Toast.makeText(ViewAccounts_Activity.this,  "Account not found", Toast.LENGTH_SHORT);
            toast.show();
        }
        else {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

            alertBuilder.setMessage("Are you sure you want to DELETE user: " + username + "?");

            alertBuilder.setPositiveButton(getString(R.string.yes),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            projectDAO.delete(deleteUser);
                            Toast toast = Toast.makeText(ViewAccounts_Activity.this,  "Account was deleted", Toast.LENGTH_SHORT);
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