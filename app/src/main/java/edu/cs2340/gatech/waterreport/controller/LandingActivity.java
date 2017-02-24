package edu.cs2340.gatech.waterreport.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import edu.cs2340.gatech.waterreport.model.AccountType;
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
        Intent intent = getIntent();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    /**
     * called when user click the logout button to logout
     * @param v represents the logout button
     */
    public void logoutButtonPressed(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        FirebaseAuth.getInstance().signOut();
        startActivity(intent);
    }

    /**
     * called when user click the logout button to changing the landing Activity to ProfileActivity
     * @param v represents the profile button
     */
    public void profileButtonPressed(View v) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    /**
     * Called when the activity has detected the user's press of the back key.
     */
    @Override
    public void onBackPressed() {
        FirebaseAuth.getInstance().signOut();
        super.onBackPressed();
    }
}
