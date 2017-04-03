package edu.cs2340.gatech.waterreport.controller;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.cs2340.gatech.waterreport.model.Location;
import edu.cs2340.gatech.waterreport.model.WaterPurityReport;
import edu.cs2340.gatech.waterreport.model.WaterSourceReport;


/**
 * Activity to create the historical purity graph for the managers
 */

public class HistoricalGraphActivity extends GenericActivity {

    private int currentYear;
    private Location currentLocation;
    private List<WaterPurityReport> purityReports = new ArrayList<>();
    private List<WaterPurityReport> currentReports = new ArrayList<>();
    LineChart chart;

    // This entire activity sucks for large amounts of reports
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentLocation = new Location(extras.getDouble("REPORT_LAT"),
                    extras.getDouble("REPORT_LONG"));
        }
        chart = (LineChart) findViewById(R.id.chart);
        currentYear = Calendar.getInstance().get(Calendar.YEAR);

        DatabaseReference reportsDB = FirebaseDatabase.getInstance().getReference().child("purityReports");
        reportsDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("Count of children" , "" + dataSnapshot.getChildrenCount());
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    WaterPurityReport report = postSnapshot.getValue(WaterPurityReport.class);
                    Log.e("PurityReportDateString", report.getDate().toString());
                    Log.e("PurityReportServerPull", String.valueOf(report.getDate().getMonth()));
                    purityReports.add(report);
                }
                populatePurityReports();
                createNewGraph(currentReports);
                chart.getLegend().setEnabled(false);
                chart.getXAxis().setGranularity(1f);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("The read failed: " , databaseError.getMessage());
            }
        });


    }

    /**
     * Populate the list where matching lists are stored
     */
    private void populatePurityReports() {
        currentReports = new ArrayList<>();
        for (WaterPurityReport report : purityReports) {
            if (report.locationMatch(currentLocation)) {
                System.out.println("Location Match");
                if (currentYear == (report.getDate().getYear() + 1900)) {
                    Log.e("PurityReport", String.valueOf(report.getDate().getMonth()));
                    currentReports.add(report);
                }
            }
        }
    }

    /**
     * Creates a new graph and updates chart
     * @param entries to be written into chart
     */
    private void createNewGraph(List<WaterPurityReport> entries) {
        TextView currentYearView = (TextView) findViewById(R.id.current_year_view);
        currentYearView.setText(String.valueOf(currentYear));
        if (!entries.isEmpty()) {
            List<Entry> chartEntries = new ArrayList<>();
            Map<Integer, List<Integer>> averages = new HashMap<>();
            // Set up sum and number of reports
            for (int i = 0; i < 12; i++) {
                List<Integer> sumAndCount = new ArrayList<>();
                // Average for month
                sumAndCount.add(0, 0);
                // Count for month
                sumAndCount.add(1, 0);
                averages.put(i, sumAndCount);
            }

            // Update average
            for (WaterPurityReport report : entries) {
                List<Integer> current = averages.get(report.getDate().getMonth());
                Integer count = current.get(1);
                count++;
                current.set(1, count);
                current.set(0, (current.get(0) + (int) report.getVirusPPM()));
            }

            for (int i = 0; i < 12; i++) {
                if (averages.get(i).get(1) != 0) {
                    chartEntries.add(new Entry(i, averages.get(i).get(0) / averages.get(i).get(1)));
                    //chartEntries.add(new Entry(3, 445f));
                }
                LineDataSet lineDataSet = new LineDataSet(chartEntries, "Placeholder");

                chart.setData(new LineData(lineDataSet));
                chart.getXAxis().setAxisMinimum(1f);
                chart.getXAxis().setAxisMaximum(12f);
                chart.invalidate();
            }
        }
    }

    /**
     * Switches to virus PPM
     * @param v the virus ppm button
     */
    public void changeToVirusButtonPressed(View v) {
        createNewGraph(currentReports);
    }

    /**
     * Switches to contaminant PPM and updates chart
     * @param v contaminant PPM button
     */
    public void changeToContaminantButtonPressed(View v) {
        TextView currentYearView = (TextView) findViewById(R.id.current_year_view);
        currentYearView.setText(String.valueOf(currentYear));
        if (!currentReports.isEmpty()) {
            List<Entry> chartEntries = new ArrayList<>();
            Map<Integer, List<Integer>> averages = new HashMap<>();
            for (int i = 0; i < 12; i++) {
                List<Integer> sumAndCount = new ArrayList<>();
                // Average for month
                sumAndCount.add(0, 0);
                // Count for month
                sumAndCount.add(1, 0);
                averages.put(i, sumAndCount);
            }

            for (WaterPurityReport report : currentReports) {
                List<Integer> current = averages.get(report.getDate().getMonth());
                Integer count = current.get(1);
                count++;
                current.set(1, count);
                current.set(0, (current.get(0) + (int) report.getContaminantPPM()));
            }

            for (int i = 0; i < 12; i++) {
                if (averages.get(i).get(1) != 0) {
                    chartEntries.add(new Entry(i, averages.get(i).get(0) / averages.get(i).get(1)));
                }
            }
            LineDataSet lineDataSet = new LineDataSet(chartEntries, "Placeholder");

            chart.setData(new LineData(lineDataSet));
            chart.invalidate();
        }
    }

    /**
     * Takes in the location inputted by user and updates the chart with it
     * @param v the change location button
     */
    public void changeLocationButtonPressed(View v) {
        EditText graph_latitude = (EditText) findViewById(R.id.graph_latitude);
        EditText graph_longitude = (EditText) findViewById(R.id.graph_longitude);
        double latitude = Double.parseDouble(graph_latitude.getText().toString());
        double longitude = Double.parseDouble(graph_longitude.getText().toString());
        currentLocation = new Location(latitude, longitude);
        populatePurityReports();
        createNewGraph(currentReports);
        //System.out.println("Change Location");
    }

    /**
     * Changes chart to the previous year
     * @param v the previous year button
     */
    public void previousYearButtonPressed(View v) {
        currentYear--;
        chart.clear();
        populatePurityReports();
        createNewGraph(currentReports);
    }

    /**
     * Changes chart to the next year
     * @param v the next year button
     */
    public void nextYearButtonPressed(View v) {
        currentYear++;
        chart.clear();
        populatePurityReports();
        createNewGraph(currentReports);
    }
}
