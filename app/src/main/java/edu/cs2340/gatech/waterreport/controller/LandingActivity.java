package edu.cs2340.gatech.waterreport.controller;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.MapFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A landing screen that user can logout or view the profile
 *
 * @author  Johnny Lee, Brian Piejak, Yudong Shao, Hui Li, Jimmy Dinh-Nguyen
 * @version 1.0
 * @since   02/21/2017
 */
public class LandingActivity extends GenericActivity {
    private ActionBarDrawerToggle mDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout_main);

        // set up a custom toolbar so we can put icons in it
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // this sets up the navdrawer as something that can be opened and closed
        DrawerLayout navDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                navDrawerLayout,         /* DrawerLayout object */
                toolbar,
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        );

        // set up the toggle button to correspond to our navdrawer
        navDrawerLayout.addDrawerListener(mDrawerToggle);

        // set the icon in the action bar to be the three lines
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        // tells the navdrawer that it is closed right now
        mDrawerToggle.syncState();

        // when we first login, show the report list
        if (savedInstanceState == null) {
            Fragment fragment = null;
            Class fragmentClass = ReportListFragment.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        }

        // set up the drawer with the click listener that we have a method for
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                selectDrawerItem(menuItem);
                return true;
            }
        });

        // set the first item (reports) as being selected
        navigationView.getMenu().getItem(0).setChecked(true);

        // change the default header text to the user's email
        View headerView = navigationView.getHeaderView(0);
        TextView headerText = (TextView) headerView.findViewById(R.id.header_text);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            headerText.setText(user.getEmail());
        }
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
        } else if (id == R.id.nav_map) {
            // go to map of reports
            title = "Map";
            fragmentClass = MapFragment.class;
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
