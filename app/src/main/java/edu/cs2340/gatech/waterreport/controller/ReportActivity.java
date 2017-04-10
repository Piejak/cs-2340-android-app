package edu.cs2340.gatech.waterreport.controller;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import edu.cs2340.gatech.waterreport.model.WaterSourceReport;
import edu.cs2340.gatech.waterreport.model.WaterType;


/**
 * Controller class for reports
 */
public class ReportActivity extends GenericActivity {

    // database
    private DatabaseReference mDatabase;

    private Spinner waterTypeSpinner;
    private Spinner waterConditionSpinner;
    // --Commented out by Inspection (4/9/17 7:48 PM):private LatLng location;
    private int reportNumber;
    private EditText latitudeEntry;
    private EditText longitudeEntry;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        // grabbing location from previous source report if possible
        latitudeEntry = (EditText) findViewById(R.id.latitude_entry);
        longitudeEntry = (EditText) findViewById(R.id.longitude_entry);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            latitudeEntry.setText(String.valueOf(extras.getDouble("REPORT_LAT")), TextView.BufferType.EDITABLE);
            longitudeEntry.setText(String.valueOf(extras.getDouble("REPORT_LONG")), TextView.BufferType.EDITABLE);
        }

        //FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
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

// --Commented out by Inspection START (4/9/17 7:49 PM):
//    public void currentLocationButtonPressed(View v) {
//        // pause and save state then switch state to map and grab a latitude/longitude from map
//        // TODO
//        switchActivity(LandingActivity.class);
//    }
// --Commented out by Inspection STOP (4/9/17 7:49 PM)

// --Commented out by Inspection START (4/9/17 7:49 PM):
//    public void pickLocationButtonPressed(View v) {
//        // pause and save state then switch state to map and grab a latitude/longitude from map
//        //TODO
//        switchActivity(LandingActivity.class);
//    }
// --Commented out by Inspection STOP (4/9/17 7:49 PM)


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
        if (user != null) {
            WaterType waterType = (WaterType) waterTypeSpinner.getSelectedItem();
            WaterCondition waterCondition = (WaterCondition) waterConditionSpinner.getSelectedItem();
            double latitude = Double.parseDouble(latitudeEntry.getText().toString());
            double longitude = Double.parseDouble(longitudeEntry.getText().toString());
            if (latitudeEntry.getText().toString().equals("")) {
                // show a snackbar saying that the latitude is required
                Toast.makeText(this, "Enter a latitude", Toast.LENGTH_LONG).show();
            } else if (longitudeEntry.getText().toString().equals("")) {
                // show a snackbar saying that the longitude is required
                Toast.makeText(this, "Enter a longitude", Toast.LENGTH_LONG).show();
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
    }
}
