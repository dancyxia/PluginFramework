package com.example.myplugindemo.plugin;

import android.database.ContentObserver;

import com.example.myplugindemo.db.Plugin;
import com.example.myplugindemo.lib.ServiceViewFactory;

/**
 * Created by dancy on 5/21/14.
 */
public abstract class PluginManager {
    public PluginMonitor pluginMonitor;
    protected final PluginController controller;
    
    public PluginManager(PluginController controller) {
    	this.controller = controller;
    }
    
    public abstract boolean installPlugin(Plugin plugin);
    public abstract void uninstallPlugin(Plugin plugin);
    public abstract ServiceViewFactory getViewFactory();
    public abstract void stopManager();

	public abstract void registerPluginListObserver(ContentObserver mPluginListObserver);
    
//	public Activity getContext() {
//		return context;
//	}

}
