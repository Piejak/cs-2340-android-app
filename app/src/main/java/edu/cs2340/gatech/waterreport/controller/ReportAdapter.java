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
 * @author  Johnny Lee, Brian Piejak, Yudong Shao, Hui Li, Jimmy Dinh-Nguyen
 * @version 1.0
 * @since   03/1/2017
 */
public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {
    private final ArrayList<WaterSourceReport> mDataset;
    private static ClickListener clickListener;
    private static LongClickListener longClickListener;

    /**
     * Inner view holder class that assigns view elements for each card in the recycler view
     */
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener

    {
        public final TextView mUserText;
        public final TextView mDateText;
        public final TextView mConditionText;
        public final TextView mTypeText;
        public final View v;

        public ViewHolder(View v) {
            super(v);
            this.v = v;

            v.setOnClickListener(this);
            v.setOnLongClickListener(this);
            mUserText = (TextView) v.findViewById(R.id.user_text);
            mDateText = (TextView) v.findViewById(R.id.date_text);
            mConditionText = (TextView) v.findViewById(R.id.water_condition_text);
            mTypeText = (TextView) v.findViewById(R.id.water_type_text);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }

        @Override
        public boolean onLongClick(View v) {
            longClickListener.onItemLongClick(getAdapterPosition(), v);
            return true;
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

    public void setOnItemClickListener(ClickListener clickListener) {
        ReportAdapter.clickListener = clickListener;
    }

    public void setOnItemLongClickListener(LongClickListener longClickListener) {
        ReportAdapter.longClickListener = longClickListener;
    }

    @Override
    public ReportAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.report_card_view,
                parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mUserText.setText(mDataset.get(position).getReporter().toString());
        holder.mDateText.setText(mDataset.get(position).getDate().toString());
        holder.mConditionText.setText("Water Condition: " + mDataset.get(position).getWaterCondition().toString());
        holder.mTypeText.setText("Water Type: " + mDataset.get(position).getWaterType().toString());
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    public interface  LongClickListener {
        void onItemLongClick(int position, View v);
    }
}
