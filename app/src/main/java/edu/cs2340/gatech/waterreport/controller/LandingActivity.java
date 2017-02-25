package edu.cs2340.gatech.waterreport.controller;

import android.app.ActivityOptions;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import edu.cs2340.gatech.waterreport.model.NavDrawerItem;

/**
 * A landing screen that user can logout or view the profile
 *
 * @author  Johnny Lee, Brian Piejak, Yudong Shao, Hui Li, Jimmy Dinh-Nguyen
 * @version 1.0
 * @since   02/21/2017
 */
public class LandingActivity extends AppCompatActivity {

    private NavDrawerItem[] mDrawerOptions = {
            new NavDrawerItem("Profile", R.drawable.ic_profile, R.layout.activity_profile),
            new NavDrawerItem("Reports", R.drawable.ic_home, R.layout.activity_landing),
            new NavDrawerItem("Log out", R.drawable.ic_leave, R.layout.activity_welcome)
    };
    private DrawerLayout mNavDrawerLayout;
    private ListView mDrawerList;

    private FirebaseUser mUser;

    /**
     * called when the landing activity is starting.
     * @param savedInstanceState the Bundle that maps form String key to various values. save the state of login Activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout_main);

        mUser = FirebaseAuth.getInstance().getCurrentUser();

        mNavDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.drawer_list);
        mDrawerList.setAdapter(new ArrayAdapter<>(this, R.layout.drawer_list_item, mDrawerOptions));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
    }

    /**
     * called when user click the logout button to logout
     * @param v represents the logout button
     */
    public void logoutButtonPressed(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        FirebaseAuth.getInstance().signOut();
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    /**
     * called when user click the logout button to changing the landing Activity to ProfileActivity
     * @param v represents the profile button
     */
    public void profileButtonPressed(View v) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    /**
     * Called when the activity has detected the user's press of the back key.
     */
    @Override
    public void onBackPressed() {
        FirebaseAuth.getInstance().signOut();
        super.onBackPressed();
    }

    /**
     * called on the press of the floating create report button
     * @param v
     */
    public void createReportButtonPressed(View v) {
        // probably will want to check the type of user and decide what to do based on that
        Intent intent = new Intent(this, ReportActivity.class);
        //animate the transition
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    /** Swaps fragments in the main content view */
    private void selectItem(int position) {
        // Create a new fragment and specify the planet to show based on position
        Fragment fragment = new StructureFragment();
        Bundle args = new Bundle();
        args.putInt(StructureFragment.ARG_PAGE, mDrawerOptions[position].getLayout());
        fragment.setArguments(args);

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        mNavDrawerLayout.closeDrawer(mDrawerList);
    }
}
