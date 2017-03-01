package edu.cs2340.gatech.waterreport.controller;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A landing screen that user can logout or view the profile
 *
 * @author  Johnny Lee, Brian Piejak, Yudong Shao, Hui Li, Jimmy Dinh-Nguyen
 * @version 1.0
 * @since   02/21/2017
 */
public class LandingActivity extends AppCompatActivity {

    private FirebaseUser mUser;

    /**
     * called when the landing activity is starting.
     * @param savedInstanceState the Bundle that maps form String key to various values. save the state of login Activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    /**
     * called when user click the logout button to logout
     * @param v represents the logout button
     */
    public void logoutButtonPressed(View v) {
        //Back button and logout button do the same thing anyways -Johnny
        onBackPressed();
    }

    /**
     * called when user click the logout button to changing the landing Activity to ProfileActivity
     * @param v represents the profile button
     */
    public void profileButtonPressed(View v) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    /**
     * Called when the activity has detected the user's press of the back key.
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, LoginActivity.class);
        FirebaseAuth.getInstance().signOut();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    /**
     * called on the press of the floating create report button
     * @param v
     */
    public void createReportButtonPressed(View v) {
        // probably will want to check the type of user and decide what to do based on that
        Intent intent = new Intent(this, ReportActivity.class);
        //animate the transition
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

}
