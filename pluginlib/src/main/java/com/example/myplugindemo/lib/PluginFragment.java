package com.example.myplugindemo.lib;

//import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by dancy on 8/12/2015.
 */
public class PluginFragment extends Fragment {

    public static final String ARG_DATE_NO = "DATE_NO";
    public static final String ARG_PLUGIN_NAME = "PLUGIN_NAME";
//    private View currentView;

    public PluginFragment() {

    }

    public static PluginFragment getInstance(String date, String pluginName) {
        PluginFragment fragment = new PluginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DATE_NO, date);
        args.putString(ARG_PLUGIN_NAME, pluginName);
        fragment.setArguments(args);
        return fragment;
    }

    public String getPluginName() {
        return getArguments().getString(ARG_PLUGIN_NAME);
    }

//    public void setCurrentView(View currentContentView) {
//        currentView = currentContentView;
//    }

    @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //must override it
       return null;
   }

    public void setToday(String today) {

    }
}