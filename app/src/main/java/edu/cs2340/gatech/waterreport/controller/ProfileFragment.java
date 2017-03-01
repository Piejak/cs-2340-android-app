package edu.cs2340.gatech.waterreport.controller;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class ProfileFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

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

    private Button mCancelButton;
    private Button mProfileButton;

    // user profile stored in firebase
    private UserInformation userInformation;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_profile, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //grabbing the display texts
        mEmailView = (EditText) getActivity().findViewById(R.id.profile_email);
        mPasswordView = (EditText) getActivity().findViewById(R.id.profile_password);
        mNameView = (EditText) getActivity().findViewById(R.id.profile_name);
        mAddressView = (EditText) getActivity().findViewById(R.id.profile_address);
        mAgeView = (EditText) getActivity().findViewById(R.id.profile_age);
        mAffiliationView = (EditText) getActivity().findViewById(R.id.profile_affiliation);
        mAccountTypeSpinner = (Spinner) getActivity().findViewById(R.id.profile_account_type);

        mCancelButton = (Button) getActivity().findViewById(R.id.cancelProfileButton);
        mCancelButton.setOnClickListener(this);

        mProfileButton = (Button) getActivity().findViewById(R.id.changeProfileButton);
        mProfileButton.setOnClickListener(this);

        // setting profile texts
        mEmailView.setText(mUser.getEmail(), TextView.BufferType.EDITABLE);

        //setting up spinner
        spinnerAdapter = new ArrayAdapter<AccountType>(getContext(),
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cancelProfileButton) {
            NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
            navigationView.getMenu().getItem(0).setChecked(true);
            try {
                getFragmentManager().beginTransaction().replace(R.id.content_frame, ReportListFragment.class.newInstance()).commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (v.getId() == R.id.changeProfileButton) {
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

            NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
            navigationView.getMenu().getItem(0).setChecked(true);
            try {
                getFragmentManager().beginTransaction().replace(R.id.content_frame, ReportListFragment.class.newInstance()).commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
