package edu.cs2340.gatech.waterreport.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import edu.cs2340.gatech.waterreport.model.AccountType;
import edu.cs2340.gatech.waterreport.model.UserInformation;
import edu.cs2340.gatech.waterreport.model.WaterSourceReport;

/**
 * Fragment for controlling the list of water source reports
 * @author  Johnny Lee, Brian Piejak, Yudong Shao, Hui Li, Jimmy Dinh-Nguyen
 * @version 1.0
 * @since   03/1/2017
 */
public class ReportListFragment extends android.support.v4.app.Fragment implements View.OnClickListener{
    private RecyclerView mRecyclerView;
    private ReportAdapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    private ArrayList<WaterSourceReport> waterSourceReports = new ArrayList<>();
    private AccountType accountType;
    private UserInformation userInformation;


    /**
     * Required empty constructed due to Fragment superclass
     */
    public ReportListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        DatabaseReference userQuery = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
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
                            if (accountType != AccountType.DEFAULT
                                    && accountType != AccountType.USER) {
                                Intent intent = new Intent(getActivity(), PurityActivity.class);
                                intent.putExtra("REPORT_LAT", waterSourceReports.get(position).getLocation().getLatitude());
                                intent.putExtra("REPORT_LONG", waterSourceReports.get(position).getLocation().getLongitude());
                                startActivity(intent);
                            }
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

        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.report_recycler_view);

        mManager = new LinearLayoutManager(getContext());
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
