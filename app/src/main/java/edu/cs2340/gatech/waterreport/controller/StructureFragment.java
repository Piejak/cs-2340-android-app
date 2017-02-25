package edu.cs2340.gatech.waterreport.controller;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class StructureFragment extends android.support.v4.app.Fragment {
    // the fragment initialization parameters
    public static final String ARG_PAGE = "page";

    public StructureFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        int i = getArguments().getInt(ARG_PAGE);
        return inflater.inflate(i, container, false);

    }

}
