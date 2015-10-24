package com.example.myplugindemo;

import android.support.v4.view.ViewPager;

/**
 * Created by dancy on 8/13/2015.
 */
public class PluginPageManager extends ViewPager.SimpleOnPageChangeListener{
    private int currentPlugin;
    public PluginPageManager() {}

    @Override
    public void onPageSelected(int position) {
        super.onPageSelected(position);
        currentPlugin = position;
    }

    public int getCurrentPlugin() {
        return currentPlugin;
    }
}
