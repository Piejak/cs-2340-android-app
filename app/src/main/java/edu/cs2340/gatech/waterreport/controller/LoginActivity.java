package edu.cs2340.gatech.waterreport.controller;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A login screen that offers login via email/password.
 *
 * @author  Johnny Lee, Brian Piejak, Yudong Shao, Hui Li, Jimmy Dinh-Nguyen
 * @version 1.0
 * @since   02/21/2017
 */
public class LoginActivity extends AppCompatActivity {

    // UI references.
    private AutoCompleteTextView mLoginEmailView;
    private AutoCompleteTextView mRegisterEmailView;
    private EditText mLoginPasswordView;
    private EditText mRegisterPasswordView;
    private EditText mResetEmailView;

    // Firebase instance variables
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // initialize firebase auth with the current instance
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //user is non-null so there is someone signed in
                    //eventually do something with this
                    Log.d("LoginActivity", "mAuthListener:onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    //user is signed out
                    Log.d("LoginActivity", "mAuthListener:onAuthStateChanged:signed_out");
                }
            }
        };
    }

    /**
     *Start the Activity
     */
    @Override
    public void onStart() {
        super.onStart();

        //attach listener to the firebase auth
        mAuth.addAuthStateListener(mAuthListener);
    }


    /**
     * Stop the Activity
     */
    @Override
    public void onStop() {
        super.onStop();

        //remove listener from firebase auth
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    /**
     * called when user click the login button
     * @param v represents the Login button
     */
    public void onLoginButtonClicked(View v) {
        setContentView(R.layout.activity_login);
        mLoginEmailView = (AutoCompleteTextView) findViewById(R.id.login_email);
        mLoginPasswordView = (EditText) findViewById(R.id.login_password);
    }

    /**
     * called when user click the register button
     * @param v represents the Register button
     */
    public void onRegisterButtonClicked(View v) {
        setContentView(R.layout.activity_register);
        mRegisterEmailView = (AutoCompleteTextView) findViewById(R.id.register_email);
        mRegisterPasswordView = (EditText) findViewById(R.id.register_password);
    }

    /**
     * runs after user click the create button
     * @param v represents the Register button
     */
    public void createAccount(View v) {
        showProgress();
        mAuth.createUserWithEmailAndPassword(mRegisterEmailView.getText().toString(), mRegisterPasswordView.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        hideProgress();
                        Log.d("LoginActivity", "mAuth:createUser:onComplete::" + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            //the creation failed
                            Toast.makeText(LoginActivity.this, "Account creation failed", Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    }
                });
    }

    /**
     * runs after user click the SignIn button
     * @param v represents the SignIn button
     */
    public void signIn(View v) {
        showProgress();
        mAuth.signInWithEmailAndPassword(mLoginEmailView.getText().toString(), mLoginPasswordView.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("LoginActivity", "mAuth:signIn:onComplete::" + task.isSuccessful());

                        hideProgress();

                        if (!task.isSuccessful()) {
                            //the creation failed
                            Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent = new Intent(getApplicationContext(), LandingActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    }
                });
    }

    /**
     * Displays a rotating progress indicator when the user is logging in or registering
     */
    public void showProgress() {
        // previously invisible view
        View myView = findViewById(R.id.login_progress);

        // get the center for the clipping circle
        int cx = myView.getWidth() / 2;
        int cy = myView.getHeight() / 2;

        // get the final radius for the clipping circle
        float finalRadius = (float) Math.hypot(cx, cy);

        // create the animator for this view (the start radius is zero)
        Animator anim =
                ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius);

        // make the view visible and start the animation
        myView.setVisibility(View.VISIBLE);
        anim.start();
    }

    /**
     * Hides the progress indicator when the login or signup is complete
     */
    public void hideProgress() {
        // previously visible view
        final View myView = findViewById(R.id.login_progress);

        // get the center for the clipping circle
        int cx = myView.getWidth() / 2;
        int cy = myView.getHeight() / 2;

        // get the initial radius for the clipping circle
        float initialRadius = (float) Math.hypot(cx, cy);

        // create the animation (the final radius is zero)
        Animator anim =
                ViewAnimationUtils.createCircularReveal(myView, cx, cy, initialRadius, 0);

        // make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                myView.setVisibility(View.INVISIBLE);
            }
        });

        // start the animation
        anim.start();

    }

    /**
     * called when user click the Forgot Password button
     * @param v represents the Forgot Password button
     */
    public void onForgotPasswordButtonPressed(View v) {
        setContentView(R.layout.activity_password_reset);
        mResetEmailView = (AutoCompleteTextView) findViewById(R.id.reset_email);
    }

    /**
     * called when user click the Password Reset button
     * @param v represents the Password Reset button
     */
    public void onResetPasswordButtonPressed(View v) {
        mAuth.sendPasswordResetEmail(mResetEmailView.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("LoginActivity", "Email Sent.");
                        } else {
                            Toast.makeText(LoginActivity.this, "Invalid email", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        recreate();
    }
}

