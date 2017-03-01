package edu.cs2340.gatech.waterreport.controller;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Generic activity for all activities that have an active user to be a superclass of
 */

public abstract class GenericActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }
    }

    protected void switchActivity(Class cls) {
        // probably will want to check the type of user and decide what to do based on that
        Intent intent = new Intent(this, cls);
        //animate the transition
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    //if needed make another switchActivity to pass values to new Activity

    protected void switchActivityPermanently(Class cls) {
        Intent intent = new Intent(this, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
