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
    private TextView mLandingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mLandingText = (TextView) findViewById(R.id.landing_text);
        Intent intent = getIntent();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mLandingText.setText(mUser.getEmail() + " was successfully logged in.");
    }

    public void changeProfileButtonPressed(View v) {
        Intent intent = new Intent(this, LandingActivity.class);



        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        FirebaseAuth.getInstance().signOut();
        super.onBackPressed();
    }

}
