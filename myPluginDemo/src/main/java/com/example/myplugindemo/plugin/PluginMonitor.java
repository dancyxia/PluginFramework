package com.example.myplugindemo.plugin;

import com.example.myplugindemo.lib.ServiceFragmentFactory;

/**
 * Created by dancy on 5/21/14.
 */
public interface PluginMonitor {
    public void onServiceStarted(ServiceFragmentFactory viewFactory);
    public void onServiceStopped();
}
