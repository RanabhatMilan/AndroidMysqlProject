package com.example.db.newproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Profile_activity extends AppCompatActivity {
        private TextView username,userEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_activity);


        username = findViewById(R.id.usernm);
        userEmail = findViewById(R.id.userps);

        username.setText(SharedPreferReference.getInstance(this).getUsername());
        userEmail.setText(SharedPreferReference.getInstance(this).getUserEmail());

    }

    public void logoutUser(View view) {
        SharedPreferReference.getInstance(this).logout();
        finish();
        startActivity(new Intent(this, LoginHandler.class));
    }
}
