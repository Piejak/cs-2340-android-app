package edu.cs2340.gatech.waterreport;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LandingActivity extends AppCompatActivity {

    private FirebaseUser mUser;
    private TextView mLandingText;
    private Button mLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        mLandingText = (TextView) findViewById(R.id.landing_text);
        Intent intent = getIntent();
        String uid = intent.getStringExtra("FIREBASE_UID");
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mLandingText.setText(mUser.getEmail() + " was successfully logged in.");
        mLogout = (Button) findViewById(R.id.logout_button);
    }

    public void logoutButtonPressed(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        FirebaseAuth.getInstance().signOut();
        startActivity(intent);
    }
}
