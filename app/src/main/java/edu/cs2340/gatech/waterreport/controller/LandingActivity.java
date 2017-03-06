package edu.cs2340.gatech.waterreport.controller;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import edu.cs2340.gatech.waterreport.model.WaterPurityReport;
import edu.cs2340.gatech.waterreport.model.WaterSourceReport;

/**
 * A landing screen that user can logout or view the profile
 *
 * @author  Johnny Lee, Brian Piejak, Yudong Shao, Hui Li, Jimmy Dinh-Nguyen
 * @version 1.0
 * @since   02/21/2017
 */
public class LandingActivity extends GenericActivity {
    private ActionBarDrawerToggle mDrawerToggle;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<WaterSourceReport> reportList;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        reportList = new ArrayList<>();

        // start putting things into the recycler view
        mRecyclerView = (RecyclerView) findViewById(R.id.card_views);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        System.out.println(mLayoutManager);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // populating the list and setting adapter for the activity
        ValueEventListener reportsListener = new ValueEventListener() {
            /**
             *getting reports class from database
             * @param dataSnapshot the file Filebase Database location.
             */
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get reports object and use the values to update the UI
                for (DataSnapshot postSnapshot : dataSnapshot.child("sourceReports").getChildren()) {
                    WaterSourceReport newReport = postSnapshot.getValue(WaterSourceReport.class);
                    reportList.add(newReport);
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
                        "loadreports:onCancelled", databaseError.toException());
            }
        };
        mDatabase.addValueEventListener(reportsListener);
        mAdapter = new ReportAdapter(reportList);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            switchActivityPermanently(LoginActivity.class);
        }
    }

    /**
     * called on the press of the floating create report button
     * @param v the create report button
     */
    public void createReportButtonPressed(View v) {
        switchActivity(ReportActivity.class);
    }


    /**
     * A method that handles the presses of the items in the navdrawer
     * @param item the item that was selected
     * @return true if the selection was successful
     */
    public boolean selectDrawerItem(MenuItem item) {

        int id = item.getItemId();
        Fragment fragment = null;
        Class fragmentClass = null;
        CharSequence title = "Water Logged";
        if (getSupportActionBar() != null) {
            title = getSupportActionBar().getTitle();
        }

        // decide which button was pressed
        if (id == R.id.nav_logout) {
            switchActivityPermanently(LoginActivity.class);
            return true;
        } else if (id == R.id.nav_profile) {
            // do stuff for profile
            title = "Profile";
            fragmentClass = ProfileFragment.class;
        } else if (id == R.id.nav_reports) {
            // show main page
            title = "Water Reports";
            fragmentClass = ReportListFragment.class;
        }

        //replace the frame layout with the content we want to show
        if (fragmentClass != null) {
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        }

        // close the navdrawer
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
        return true;
    }
}
