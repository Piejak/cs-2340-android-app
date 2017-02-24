package edu.cs2340.gatech.waterreport.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.cs2340.gatech.waterreport.model.AccountType;
import edu.cs2340.gatech.waterreport.model.UserInformation;

/**
 * Controller class for reports
 */

public class ReportActivity extends AppCompatActivity {

    //private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    // database
    private DatabaseReference mDatabase;


    /**
     * called when the activity is starting.
     * @param savedInstanceState  the Bundle that maps form String key to various values.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent intent = getIntent();
        //mAuth = FirebaseAuth.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        ValueEventListener userInformationListener = new ValueEventListener() {
            /**
             *getting UserInformation class from database
             * @param dataSnapshot the file Filebase Database location.
             */
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get UserInformation object and use the values to update the UI
                // TODO grab reports from server
                //userInformation = dataSnapshot.child("users").child(mUser.getUid()).getValue(UserInformation.class);
            }


            /**
             * runs after there exist a DatabaseError
             * @param databaseError Used to an error it received from interacting with the storage layer.
             */
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting user info failed, log a message
                Log.w("User Information",
                        "loadUserInformation:onCancelled", databaseError.toException());
            }
        };
        mDatabase.addValueEventListener(userInformationListener);
    }


    /**
     * called when user click the cancel Change Profile Button.
     * @param v represents the button for cancel changing the profile
     */
    public void cancelReportButtonPressed(View v) {
        Intent intent = new Intent(getApplicationContext(), LandingActivity.class);
        startActivity(intent);
    }

    /**
     * Called when the activity has detected the user's press of the back key.
     */
    @Override
    public void onBackPressed() {
        //FirebaseAuth.getInstance().signOut();
        //super.onBackPressed();
        // so back doesn't exit app
        Intent intent = new Intent(getApplicationContext(), LandingActivity.class);
        startActivity(intent);
    }
}
