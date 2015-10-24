package com.example.myplugindemo.plugin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dancy on 8/7/2015.
 */
public class PackageInstallReceiver extends BroadcastReceiver {

    public List<PluginManager> listeners = new ArrayList<PluginManager>();

    public void addListener(PluginManager pm) { listeners.add(pm);}

    @Override
    public void onReceive(Context context, Intent intent) {

        for (PluginManager pm : listeners) {
        }
    }
}
