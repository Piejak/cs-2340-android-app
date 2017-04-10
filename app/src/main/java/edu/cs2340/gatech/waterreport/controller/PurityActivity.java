package edu.cs2340.gatech.waterreport.controller;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.cs2340.gatech.waterreport.model.Location;
import edu.cs2340.gatech.waterreport.model.User;
import edu.cs2340.gatech.waterreport.model.WaterOverallCondition;
import edu.cs2340.gatech.waterreport.model.WaterPurityReport;


/**
 * Controller class for reports
 */
public class PurityActivity extends GenericActivity {

    // database
    private DatabaseReference mDatabase;

    // --Commented out by Inspection (4/9/17 7:48 PM):private Spinner waterTypeSpinner;
    private Spinner waterOverallConditionSpinner;
    // --Commented out by Inspection (4/9/17 7:48 PM):private LatLng location;
    private int reportNumber;
    private double latitude;
    private double longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purity_report);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            latitude = extras.getDouble("REPORT_LAT");
            longitude = extras.getDouble("REPORT_LONG");
        }


        //FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        waterOverallConditionSpinner = (Spinner) findViewById(R.id.purity_condition_spinner);

        waterOverallConditionSpinner.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, WaterOverallCondition.values()));

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

    /*
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
    */


    /**
     * called when user click the cancel report Button.
     * @param v represents the button for cancel changing the profile
     */
    public void cancelReportButtonPressed(View v) {
        switchActivity(LandingActivity.class);
    }


    /**
     * Submits the purity report to the server
     * @param v the submission button
     */
    public void submitPurityReportButtonPressed(View v) {
        //submitting the report pushes it to firebase now
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        WaterOverallCondition waterOverallCondition = (WaterOverallCondition) waterOverallConditionSpinner.getSelectedItem();

        Location location = new Location(latitude, longitude);
        reportNumber++;
        User localUser = new User(user.getEmail(), user.getUid(), null);
        double virusPPM = Double.parseDouble(((EditText) findViewById(R.id.virus_PPM)).getText().toString());
        double contaminantPPM = Double.parseDouble(((EditText) findViewById(R.id.contaminant_PPM)).getText().toString());

        WaterPurityReport report = new WaterPurityReport(localUser, waterOverallCondition, reportNumber, location, virusPPM, contaminantPPM);
        mDatabase.child("purityReports").push().setValue(report);
        mDatabase.child("reportNumber").setValue(reportNumber);
        switchActivity(LandingActivity.class);
    }
}
