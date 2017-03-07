package edu.cs2340.gatech.waterreport.controller;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import edu.cs2340.gatech.waterreport.model.WaterSourceReport;

/**
 * Adapter that handles the recycler view for reports
 */
public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {
    private ArrayList<WaterSourceReport> mDataset;

    /**
     * Inner view holder class that assigns view elements for each card in the recycler view
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mUserText;
        public TextView mDateText;
        public TextView mConditionText;
        public TextView mTypeText;

        public ViewHolder(View v) {
            super(v);

            mUserText = (TextView) v.findViewById(R.id.user_text);
            mDateText = (TextView) v.findViewById(R.id.date_text);
            mConditionText = (TextView) v.findViewById(R.id.water_condition_text);
            mTypeText = (TextView) v.findViewById(R.id.water_type_text);
        }
    }

    /**
     * Creates a new report adapter
     * @param waterSourceReports the list of source reports that should be viewed in a list on the
     *                           main screen
     */
    public ReportAdapter(ArrayList<WaterSourceReport> waterSourceReports) {
        mDataset = waterSourceReports;
    }

    @Override
    public ReportAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.report_card_view,
                parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mUserText.setText(mDataset.get(position).getReporter().toString());
        holder.mDateText.setText(mDataset.get(position).getDate().toString());
        holder.mConditionText.setText(mDataset.get(position).getWaterCondition().toString());
        holder.mTypeText.setText(mDataset.get(position).getWaterType().toString());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
