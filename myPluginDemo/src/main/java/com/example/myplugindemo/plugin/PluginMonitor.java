package com.example.myplugindemo.plugin;

import com.example.myplugindemo.lib.ServiceViewFactory;

/**
 * Created by dancy on 5/21/14.
 */
public interface PluginMonitor {
    public void onServiceStarted(ServiceViewFactory viewFactory);
    public void onServiceStopped();
}
