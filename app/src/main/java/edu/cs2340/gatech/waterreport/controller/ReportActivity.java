package edu.cs2340.gatech.waterreport.controller;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.cs2340.gatech.waterreport.model.Location;
import edu.cs2340.gatech.waterreport.model.WaterCondition;
import edu.cs2340.gatech.waterreport.model.WaterSourceReport;
import edu.cs2340.gatech.waterreport.model.WaterType;


/**
 * Controller class for reports
 */

public class ReportActivity extends GenericActivity {

    //private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    // database
    private DatabaseReference mDatabase;

    private Spinner waterTypeSpinner;
    private Spinner waterConditionSpinner;
    private Location location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        waterTypeSpinner = (Spinner) findViewById(R.id.type_spinner);
        waterConditionSpinner = (Spinner) findViewById(R.id.condition_spinner);

        waterTypeSpinner.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, WaterType.values()));

        waterConditionSpinner.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, WaterCondition.values()));

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
     * called when user click the cancel report Button.
     * @param v represents the button for cancel changing the profile
     */
    public void cancelReportButtonPressed(View v) {
        switchActivity(LandingActivity.class);
    }

    /**
     * called when user click the submit report Button.
     * @param v represents the button for cancel changing the profile
     */
    public void submitReportButtonPressed(View v) {
        // TODO make this submit to firebase @Johnny
        // maybe make the key the number of the report
        // going to need to count the number of reports currently in the database
        // and add location, there is a very basic location object in the model
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        WaterType waterType = (WaterType) waterTypeSpinner.getSelectedItem();
        WaterCondition waterCondition = (WaterCondition) waterConditionSpinner.getSelectedItem();
        int reportNumber = 0; // this needs to be retreived from firebase
        WaterSourceReport report = new WaterSourceReport(user, waterType, waterCondition, reportNumber);
    }
}
