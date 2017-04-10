package edu.cs2340.gatech.waterreport.controller;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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

    /**
     * A method that will encapsulate the process of starting a new activity
     * @param cls the class representing the new activity to start
     */
    void switchActivity(Class cls) {
        // probably will want to check the type of user and decide what to do based on that
        Intent intent = new Intent(this, cls);
        //animate the transition
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    /**
     * Starts a new activity and clears the back button stack
     * @param cls the class of the activity to start
     */
    void switchActivityPermanently(Class cls) {
        Intent intent = new Intent(this, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        this.finish();
    }
}
