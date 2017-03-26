package edu.cs2340.gatech.waterreport.controller;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.cs2340.gatech.waterreport.model.Location;
import edu.cs2340.gatech.waterreport.model.User;
import edu.cs2340.gatech.waterreport.model.WaterCondition;
import edu.cs2340.gatech.waterreport.model.WaterOverallCondition;
import edu.cs2340.gatech.waterreport.model.WaterPurityReport;
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
    private LatLng location;
    private int reportNumber;
    private Spinner waterPurityConditionSpinner;
    private int virusPPM;
    private int contaminantPPM;


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

        mDatabase = FirebaseDatabase.getInstance().getReference();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                reportNumber = dataSnapshot.child("reportNumber").getValue(Integer.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Report", databaseError.getMessage());
            }

        };
        mDatabase.addValueEventListener(valueEventListener);

    }

    public void currentLocationButtonPressed(View v) {
        // pause and save state then switch state to map and grab a latitude/longitude from map
        // TODO
        switchActivity(LandingActivity.class);
    }

    public void pickLocationButtonPressed(View v) {
        // pause and save state then switch state to map and grab a latitude/longitude from map
        //TODO
        switchActivity(LandingActivity.class);
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
        //submitting the report pushes it to firebase now
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        WaterType waterType = (WaterType) waterTypeSpinner.getSelectedItem();
        WaterCondition waterCondition = (WaterCondition) waterConditionSpinner.getSelectedItem();
        EditText latitudeEntry = (EditText) findViewById(R.id.latitude_entry);
        EditText longitudeEntry = (EditText) findViewById(R.id.longitude_entry);
        double latitude = Double.parseDouble(latitudeEntry.getText().toString());
        double longitude = Double.parseDouble(longitudeEntry.getText().toString());
        if (latitudeEntry.getText().toString().equals("")) {
            // show a snackbar saying that the latitude is required
        } else if (longitudeEntry.getText().toString().equals("")) {
            // show a snackbar saying that the longitude is required
        } else {
            Location location = new Location(latitude, longitude);
            reportNumber++;
            User localUser = new User(user.getEmail(), user.getUid(), null);
            WaterSourceReport report = new WaterSourceReport(localUser, waterType, waterCondition, reportNumber, location);
            mDatabase.child("sourceReports").push().setValue(report);
            mDatabase.child("reportNumber").setValue(reportNumber);
            switchActivity(LandingActivity.class);
        }
    }

    /**
     * Switching to purity report instead of source and creating views
     * @param v switch to purity report button
     */
    public void switchToPurityReportButtonPressed(View v) {
        setContentView(R.layout.activity_purity_report);
        waterConditionSpinner = (Spinner) findViewById(R.id.purity_condition_spinner);

        waterConditionSpinner.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, WaterOverallCondition.values()));
    }

    /**
     * Switching back to source report layout
     * @param v switch to source report button
     */
    public void switchToSourceReportButtonPressed(View v) {
        setContentView(R.layout.activity_report);
        waterConditionSpinner = (Spinner) findViewById(R.id.condition_spinner);

        waterConditionSpinner.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, WaterCondition.values()));
    }

    /**
     * Submits the purity report to the server
     * @param v the submission button
     */
    public void submitPurityReportButtonPressed(View v) {
        //TODO confirm everything works
        //submitting the report pushes it to firebase now
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        WaterOverallCondition waterOverallCondition = (WaterOverallCondition) waterPurityConditionSpinner.getSelectedItem();
        EditText latitudeEntry = (EditText) findViewById(R.id.purity_latitude_entry);
        EditText longitudeEntry = (EditText) findViewById(R.id.purity_longitude_entry);
        double latitude = Double.parseDouble(latitudeEntry.getText().toString());
        double longitude = Double.parseDouble(longitudeEntry.getText().toString());
        if (latitudeEntry.getText().toString().equals("")) {
            // show a snackbar saying that the latitude is required
        } else if (longitudeEntry.getText().toString().equals("")) {
            // show a snackbar saying that the longitude is required
        } else {
            Location location = new Location(latitude, longitude);
            reportNumber++;
            User localUser = new User(user.getEmail(), user.getUid(), null);
            WaterPurityReport report = new WaterPurityReport(localUser, waterOverallCondition, reportNumber, location, virusPPM, contaminantPPM);
            mDatabase.child("purityReports").push().setValue(report);
            mDatabase.child("reportNumber").setValue(reportNumber);
            switchActivity(LandingActivity.class);
        }
    }
}
