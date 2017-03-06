package edu.cs2340.gatech.waterreport.controller;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
    private int reportNumber;


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
        // TODO for some reason incrementing the report number doesn't work, need to fix that
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        WaterType waterType = (WaterType) waterTypeSpinner.getSelectedItem();
        WaterCondition waterCondition = (WaterCondition) waterConditionSpinner.getSelectedItem();
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
            WaterSourceReport report = new WaterSourceReport(user, waterType, waterCondition, reportNumber, location);
            mDatabase.child("sourceReports").push().setValue(report);
            mDatabase.child("reportNumber").setValue(reportNumber);
            switchActivity(LandingActivity.class);
        }
    }
}
