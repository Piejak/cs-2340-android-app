package edu.cs2340.gatech.waterreport.controller;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import edu.cs2340.gatech.waterreport.model.WaterSourceReport;

/**
 * Adapter for the Recycler
 */

public class ReportAdapter extends RecyclerView.Adapter<ReportViewHolder> {

    private List<WaterSourceReport> reportList;

    public ReportAdapter(List<WaterSourceReport> reportList) {
        this.reportList = reportList;
    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }

    @Override
    public void onBindViewHolder(ReportViewHolder reportViewHolder, int i) {
        WaterSourceReport ri = reportList.get(i);
        reportViewHolder.vUserEmail.setText(ri.getReporter().getEmail());
        reportViewHolder.vWaterCondition.setText(ri.getWaterCondition().toString());
        reportViewHolder.vWaterType.setText(ri.getWaterType().toString());
        reportViewHolder.vLocation.setText(ri.getLocation().toString());
        reportViewHolder.vTitle.setText("Report " + ri.getNumber());
    }

    @Override
    public ReportViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_view, viewGroup, false);

        return new ReportViewHolder(itemView);
    }
}
