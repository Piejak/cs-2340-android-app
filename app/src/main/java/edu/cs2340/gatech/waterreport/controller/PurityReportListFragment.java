package edu.cs2340.gatech.waterreport.controller;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import edu.cs2340.gatech.waterreport.model.WaterPurityReport;

/**
 * Fragment for controlling the list of water source reports
 * @author  Johnny Lee, Brian Piejak, Yudong Shao, Hui Li, Jimmy Dinh-Nguyen
 * @version 1.0
 * @since   03/1/2017
 */
public class PurityReportListFragment extends android.support.v4.app.Fragment {
    private PurityReportAdapter mAdapter;
    private final ArrayList<WaterPurityReport> waterPurityReports = new ArrayList<>();
    // --Commented out by Inspection (4/9/17 7:48 PM):private AccountType accountType;
    // --Commented out by Inspection (4/9/17 7:48 PM):private UserInformation userInformation;


    /**
     * Required empty constructed due to Fragment superclass
     */
    public PurityReportListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_purity_report_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        DatabaseReference reportsDB = FirebaseDatabase.getInstance().getReference().child("purityReports");
        reportsDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("Count " , "" + dataSnapshot.getChildrenCount());
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    WaterPurityReport report = postSnapshot.getValue(WaterPurityReport.class);
                    waterPurityReports.add(report);
//                    Log.e("Condition", report.getWaterOverallCondition().toString());
                }
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("The read failed: " , databaseError.getMessage());
            }
        });

        RecyclerView mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.purity_report_recycler_view);

        RecyclerView.LayoutManager mManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mManager);

        mAdapter = new PurityReportAdapter(waterPurityReports);

        mRecyclerView.setAdapter(mAdapter);
    }
}
