package edu.cs2340.gatech.waterreport.controller;

import android.app.ActivityOptions;
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
 * The ProfileActivity class creates a user interface that can use to
 * display the user profile and edit the profile information for a specific user
 *
 * @author  Johnny Lee, Brian Piejak, Yudong Shao, Hui Li, Jimmy Dinh-Nguyen
 * @version 1.0
 * @since   02/21/2017
 */

public class ProfileActivity extends GenericActivity {

    private FirebaseUser mUser;
    // database
    private DatabaseReference mDatabase;

    // for the profile view
    private EditText mEmailView;
    private EditText mPasswordView;
    private EditText mNameView;
    private EditText mAddressView;
    private EditText mAgeView;
    private EditText mAffiliationView;
    private Spinner mAccountTypeSpinner;
    private ArrayAdapter<AccountType> spinnerAdapter;

    // user profile stored in firebase
    private UserInformation userInformation;

    /**
     * called when the activity is starting.
     * @param savedInstanceState  the Bundle that maps form String key to various values.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
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
        spinnerAdapter = new ArrayAdapter<AccountType>(this,
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

        ValueEventListener userInformationListener = new ValueEventListener() {
            /**
             *getting UserInformation class from database
             * @param dataSnapshot the file Filebase Database location.
             */
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get UserInformation object and use the values to update the UI
                userInformation = dataSnapshot.child("users").child(mUser.getUid()).getValue(UserInformation.class);

                // pulling values for user from server object
                if (userInformation != null) {
                    mNameView.setText(userInformation.getRealName(), TextView.BufferType.EDITABLE);
                    mAgeView.setText((userInformation.getAge() != null) ? userInformation.getAge().toString(): null, TextView.BufferType.EDITABLE);
                    mAddressView.setText(userInformation.getAddress(), TextView.BufferType.EDITABLE);
                    mAffiliationView.setText(userInformation.getAffiliation(), TextView.BufferType.EDITABLE);
                    mAccountTypeSpinner.setSelection(userInformation.getAccountType().ordinal());
                }
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
     * called when user click the Change Profile Button
     * @param v represents the button for changing Profile
     */
    public void changeProfileButtonPressed(View v) {
        if (userInformation == null) {
            userInformation = new UserInformation();
        }
        // setting age in userInformation object
        if (mAgeView.getText().length() != 0) {
           userInformation.setAge(Integer.parseInt(mAgeView.getText().toString()));
        }

        // update the rest of the fields in userInformation
        userInformation.updateAllFields(
                mNameView.getText().toString(),
                mAddressView.getText().toString(),
                mAffiliationView.getText().toString(),
                (AccountType) mAccountTypeSpinner.getSelectedItem()
        );

        // sending userInformation to server
        mDatabase.child("users").child(mUser.getUid()).setValue(userInformation);
        // updating email and password
        mUser.updateEmail(mEmailView.getText().toString());
        if (mPasswordView.getText().length() != 0) {
            //System.out.println("password is " + mPasswordView.getText());
            mUser.updatePassword(mPasswordView.getText().toString());
        }
        switchActivity(LandingActivity.class);
    }

    /**
     * called when user click the cancel Change Profile Button.
     * @param v represents the button for cancel changing the profile
     */
    public void cancelProfileButtonPressed(View v) {
        //Back button and cancel button do the same thing anyways -Johnny
        switchActivity(LandingActivity.class);
        /*
        Intent intent = new Intent(getApplicationContext(), LandingActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        */
    }
}
