package edu.cs2340.gatech.waterreport.model;

/**
 * Created by brianpiejak on 2/24/17.
 */

public class NavDrawerItem {
    private String mTitle;
    private int mIcon;
    private int mLayout;

    public NavDrawerItem(String title, int icon, int layout) {
        mTitle = title;
        mIcon = icon;
        mLayout = layout;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public int getIcon() {
        return mIcon;
    }

    public void setIcon(int mIcon) {
        this.mIcon = mIcon;
    }

    public int getLayout() {
        return mLayout;
    }

    public String toString() {
        return this.mTitle;
    }

}
