package edu.cs2340.gatech.waterreport.controller;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import edu.cs2340.gatech.waterreport.model.WaterPurityReport;

/**
 * Adapter that handles the recycler view for reports
 * @author  Johnny Lee, Brian Piejak, Yudong Shao, Hui Li, Jimmy Dinh-Nguyen
 * @version 1.0
 * @since   03/1/2017
 */
public class PurityReportAdapter extends RecyclerView.Adapter<PurityReportAdapter.ViewHolder> {
    private final ArrayList<WaterPurityReport> mDataset;

    /**
     * Inner view holder class that assigns view elements for each card in the recycler view
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mVirusPPMText;
        public final TextView mContaminantPPMText;
//        public TextView mPurityReporterText;
        public final TextView mDateText;

        public ViewHolder(View v) {
            super(v);

            mContaminantPPMText = (TextView) v.findViewById(R.id.contaminant_PPM_text);
            mDateText = (TextView) v.findViewById(R.id.purity_date_text);
//            mPurityReporterText = (TextView) v.findViewById(R.id.purity_reporter_text);
            mVirusPPMText = (TextView) v.findViewById(R.id.virus_PPM_text);
        }
    }

    /**
     * Creates a new report adapter
     * @param waterPurityReports the list of source reports that should be viewed in a list on the
     *                           main screen
     */
    public PurityReportAdapter(ArrayList<WaterPurityReport> waterPurityReports) {
        mDataset = waterPurityReports;
    }

    @Override
    public PurityReportAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.purity_report_card_view,
                parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
//        holder.mPurityReporterText.setText(mDataset.get(position).getReporter().email);

//        holder.mOverallConditionText.setText(mDataset.get(position).getWaterOverallCondition().toString());
        holder.mDateText.setText(mDataset.get(position).getDate().toString());
        holder.mVirusPPMText.setText("Virus PPM: " + mDataset.get(position).getVirusPPM() + "");
        holder.mContaminantPPMText.setText("Contaminant PPM: " + mDataset.get(position).getContaminantPPM() + "");
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
