package edu.cs2340.gatech.waterreport.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import edu.cs2340.gatech.waterreport.model.AccountType;
import edu.cs2340.gatech.waterreport.model.WaterSourceReport;

/**
 * Fragment for controlling the list of water source reports
 * @author  Johnny Lee, Brian Piejak, Yudong Shao, Hui Li, Jimmy Dinh-Nguyen
 * @version 1.0
 * @since   03/1/2017
 */
public class ReportListFragment extends android.support.v4.app.Fragment implements View.OnClickListener{
    private ReportAdapter mAdapter;
    private final ArrayList<WaterSourceReport> waterSourceReports = new ArrayList<>();
    private AccountType accountType;
    // --Commented out by Inspection (4/9/17 7:49 PM):private UserInformation userInformation;


    /**
     * Required empty constructed due to Fragment superclass
     */
    public ReportListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            DatabaseReference userQuery = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUid());
            userQuery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                        Log.e("INFO", userSnapshot.toString());
                        if (userSnapshot.getKey().equals("accountType")) {
                            accountType = userSnapshot.getValue(AccountType.class);
                        }
//                    accountType = userSnapshot.getValue(AccountType.class);
//                    Log.e("USER INFO", userInformation.toString());
                        Log.e("ACCOUNT", accountType + "");
                        mAdapter.setOnItemClickListener(new ReportAdapter.ClickListener() {
                            @Override
                            public void onItemClick(int position, View v) {
                                Intent intent = new Intent(getActivity(), ReportActivity.class);
                                intent.putExtra("REPORT_LAT", waterSourceReports.get(position).getLocation().getLatitude());
                                intent.putExtra("REPORT_LONG", waterSourceReports.get(position).getLocation().getLongitude());
                                startActivity(intent);
                            }
                        });
                        mAdapter.setOnItemLongClickListener(new ReportAdapter.LongClickListener() {
                            @Override
                            public void onItemLongClick(final int position, View v) {
                                PopupMenu popupMenu = new PopupMenu(getContext(), v);

                                popupMenu.inflate(R.menu.report_list_popup_menu);
                                Menu menu = popupMenu.getMenu();

                                // Hiding menu options depending on user type
                                if (accountType != AccountType.MANAGER) {
                                    if (accountType != AccountType.WORKER) {
                                        menu.findItem(R.id.nav_purity_report).setVisible(false);
                                    }
                                    menu.findItem(R.id.nav_historical_graph).setVisible(false);
                                }

                                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                    @Override
                                    public boolean onMenuItemClick(MenuItem item) {
                                        if (item.getItemId() == R.id.nav_source_report) {
                                            Intent intent = new Intent(getActivity(), ReportActivity.class);
                                            intent.putExtra("REPORT_LAT", waterSourceReports.get(position).getLocation().getLatitude());
                                            intent.putExtra("REPORT_LONG", waterSourceReports.get(position).getLocation().getLongitude());
                                            startActivity(intent);
                                        } else if (item.getItemId() == R.id.nav_purity_report) {
                                            Intent intent = new Intent(getActivity(), PurityActivity.class);
                                            intent.putExtra("REPORT_LAT", waterSourceReports.get(position).getLocation().getLatitude());
                                            intent.putExtra("REPORT_LONG", waterSourceReports.get(position).getLocation().getLongitude());
                                            startActivity(intent);
                                        } else {
                                            Intent intent = new Intent(getActivity(), HistoricalGraphActivity.class);
                                            intent.putExtra("REPORT_LAT", waterSourceReports.get(position).getLocation().getLatitude());
                                            intent.putExtra("REPORT_LONG", waterSourceReports.get(position).getLocation().getLongitude());
                                            startActivity(intent);
                                        }
                                        return true;
                                    }
                                });

                                popupMenu.show();
                            }
                        });
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    Log.w("Cancel", "loadPost:onCancelled", databaseError.toException());
                    // ...
                }
            });
        }


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_report_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().findViewById(R.id.create_report_button).setOnClickListener(this);

        DatabaseReference reportsDB = FirebaseDatabase.getInstance().getReference().child("sourceReports");
        reportsDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("Count " , "" + dataSnapshot.getChildrenCount());
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    WaterSourceReport report = postSnapshot.getValue(WaterSourceReport.class);
                    waterSourceReports.add(report);
                }
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("The read failed: " , databaseError.getMessage());
            }
        });

        RecyclerView mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.report_recycler_view);

        RecyclerView.LayoutManager mManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mManager);

        mAdapter = new ReportAdapter(waterSourceReports);

        mRecyclerView.setAdapter(mAdapter);



//        mAdapter.setOnItemClickListener(new ReportAdapter.ClickListener() {
//            @Override
//            public void onItemClick(int position, View v) {
//                if (userInformation.getAccountType() != AccountType.DEFAULT
//                        || userInformation.getAccountType() != AccountType.USER) {
//                    Intent intent = new Intent(getActivity(), PurityActivity.class);
//                    intent.putExtra("REPORT_LAT", waterSourceReports.get(position).getLocation().getLatitude());
//                    intent.putExtra("REPORT_LONG", waterSourceReports.get(position).getLocation().getLongitude());
//                    startActivity(intent);
//                }
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.create_report_button) {
            Intent i = new Intent(getContext(), ReportActivity.class);
            startActivity(i);
        }

    }
}
