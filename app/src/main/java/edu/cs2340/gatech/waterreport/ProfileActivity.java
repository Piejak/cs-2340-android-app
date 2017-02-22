package edu.cs2340.gatech.waterreport;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Profile activity to create/modify a user profile
 */

public class ProfileActivity extends AppCompatActivity {

    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent intent = getIntent();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
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
