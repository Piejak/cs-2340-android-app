package edu.cs2340.gatech.waterreport.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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

import edu.cs2340.gatech.waterreport.model.UserInformation;

/**
 * Profile activity to create/modify a user profile
 */

public class ProfileActivity extends AppCompatActivity {

    //private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mDatabase;

    // for the profile view
    private EditText mEmailView;
    private EditText mPasswordView;
    private EditText mNameView;
    private EditText mAddressView;
    private EditText mAgeView;
    private EditText mAffiliationView;
    private Spinner mAccountTypeSpinner;

    // user profile stored in firebase
    private UserInformation userInformation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent intent = getIntent();
        //mAuth = FirebaseAuth.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //grabbing the display texts
        mEmailView = (EditText) findViewById(R.id.profile_email);
        mPasswordView = (EditText) findViewById(R.id.profile_password);
        mNameView = (EditText) findViewById(R.id.profile_name);
        mAddressView = (EditText) findViewById(R.id.profile_address);
        mAgeView = (EditText) findViewById(R.id.profile_age);
        mAffiliationView = (EditText) findViewById(R.id.profile_affiliation);
        mAccountTypeSpinner = (Spinner) findViewById(R.id.profile_account_type);

        // setting profile texts
        mEmailView.setText(mUser.getEmail(), TextView.BufferType.EDITABLE);
        ValueEventListener userInformationListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get UserInformation object and use the values to update the UI
                userInformation = dataSnapshot.getValue(UserInformation.class);
                //FIXME java.lang.NullPointerException: Attempt to invoke virtual method 'java.lang.String edu.cs2340.gatech.waterreport.model.UserInformation.getRealName()' on a null object reference
                mNameView.setText(userInformation.getRealName(), TextView.BufferType.EDITABLE);
                mAgeView.setText(userInformation.getAge(), TextView.BufferType.EDITABLE);
                mAddressView.setText(userInformation.getAddress(), TextView.BufferType.EDITABLE);
                mAffiliationView.setText(userInformation.getAffiliation(), TextView.BufferType.EDITABLE);
                mAccountTypeSpinner.setPrompt(userInformation.getAccountType().getName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting user info failed, log a message
                Log.w("User Information",
                        "loadUserInformation:onCancelled", databaseError.toException());
            }
        };
        mDatabase.addListenerForSingleValueEvent(userInformationListener);
        // TODO fill all EditText hints with current user info which needs to be grabbed from firebase
        // TODO profile_account_type needs to be set to default with profile_account_type.setPrompt(String accountType) pulled from firebase
    }

    public void changeProfileButtonPressed(View v) {
        Intent intent = new Intent(this, LandingActivity.class);

        //TODO add to Firebase

        startActivity(intent);
    }

    public void cancelProfileButtonPressed(View v) {
        // maybe something to add for extra functionality
        Intent intent = new Intent(getApplicationContext(), LandingActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        FirebaseAuth.getInstance().signOut();
        super.onBackPressed();
    }

}
