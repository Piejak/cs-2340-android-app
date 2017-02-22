package edu.cs2340.gatech.waterreport.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import edu.cs2340.gatech.waterreport.model.AccountType;

public class LandingActivity extends AppCompatActivity {

    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        Intent intent = getIntent();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    public void logoutButtonPressed(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        FirebaseAuth.getInstance().signOut();
        startActivity(intent);
    }

    public void profileButtonPressed(View v) {
        Intent intent = new Intent(this, ProfileActivity.class);
        //this may be used for Firebase user profile
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        FirebaseAuth.getInstance().signOut();
        super.onBackPressed();
    }
}
