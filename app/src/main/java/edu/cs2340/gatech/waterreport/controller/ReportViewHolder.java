package edu.cs2340.gatech.waterreport.controller;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Holds the TextViews for the RecyclerView
 */

public class ReportViewHolder extends RecyclerView.ViewHolder {

    protected CardView vCardView;
    protected TextView vUserEmail;
    protected TextView vWaterType;
    protected TextView vLocation;
    protected TextView vWaterCondition;
    protected TextView vTitle;

    public ReportViewHolder(View v) {
        super(v);
        vCardView = (CardView) v.findViewById(R.id.card_view);
        vUserEmail =  (TextView) v.findViewById(R.id.user_email);
        vWaterType =  (TextView) v.findViewById(R.id.water_type);
        vLocation =  (TextView) v.findViewById(R.id.location);
        vWaterCondition =  (TextView) v.findViewById(R.id.water_condition);
        vTitle =  (TextView) v.findViewById(R.id.title);
    }

}
