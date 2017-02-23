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

        //setting up spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item,
                AccountType.values()) {
                // this is so it hides the DEFAULT enum
                @Override
                public int getCount() {
                    return(AccountType.values().length - 1); // Truncate the list
                }
        };
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mAccountTypeSpinner.setAdapter(spinnerAdapter);
        mAccountTypeSpinner.setSelection(AccountType.DEFAULT.ordinal());

        //getting UserInformation class from database
        ValueEventListener userInformationListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get UserInformation object and use the values to update the UI
                userInformation = dataSnapshot.getValue(UserInformation.class);
                System.out.println(userInformation);
                if (userInformation != null) {
                    // FIXME i think android doesn't care what's inputted
                    // what's below is permanent to the text views
                    mNameView.setText(userInformation.getRealName(), TextView.BufferType.EDITABLE);
                    mAgeView.setText((userInformation.getAge() == null) ? userInformation.getAge().toString(): null, TextView.BufferType.EDITABLE);
                    mAddressView.setText(userInformation.getAddress(), TextView.BufferType.EDITABLE);
                    mAffiliationView.setText(userInformation.getAffiliation(), TextView.BufferType.EDITABLE);
                    mAccountTypeSpinner.setSelection(userInformation.getAccountType().ordinal());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting user info failed, log a message
                Log.w("User Information",
                        "loadUserInformation:onCancelled", databaseError.toException());
            }
        };
        mDatabase.addListenerForSingleValueEvent(userInformationListener);

    }

    public void changeProfileButtonPressed(View v) {
        Intent intent = new Intent(this, LandingActivity.class);

        if (userInformation == null) {
            userInformation = new UserInformation();
        }
        userInformation.updateAllFields(
                mNameView.getText().toString(),
                Integer.parseInt(mAgeView.getText().toString()),
                mAddressView.getText().toString(),
                mAffiliationView.getText().toString(),
                mAccountTypeSpinner.getSelectedItem().toString()
        );
        mDatabase.child("users").child(mUser.getUid()).setValue(userInformation);
        mUser.updateEmail(mEmailView.getText().toString());
        mUser.updatePassword(mPasswordView.getText().toString());
        startActivity(intent);
    }

    public void cancelProfileButtonPressed(View v) {
        Intent intent = new Intent(getApplicationContext(), LandingActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        //FirebaseAuth.getInstance().signOut();
        //super.onBackPressed();
        // so back doesn't exit app
        Intent intent = new Intent(getApplicationContext(), LandingActivity.class);
        startActivity(intent);
    }

}
