package com.example.myplugindemo.plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.database.ContentObserver;
import android.util.Log;

import com.example.myplugindemo.db.Plugin;
import com.example.myplugindemo.db.PluginDataSource;
import com.example.myplugindemo.lib.ServiceFragmentFactory;

/**
 * Created by dancy on 5/21/14.
 */
public class PluginController {
	
	private static PluginController instance;
    private PluginManager[] pluginManagers = new PluginManager[Plugin.PLUGINTYPE.values().length];
	private PluginDataSource activeDataSource;

    private PluginController() {
		for (int i = 0; i < pluginManagers.length; i++)
			pluginManagers[i] = PluginManagerFactory.getPluginManager(Plugin.PLUGINTYPE.findType(i), this);
    }
    
    public static PluginController getInstance() {
    	if (instance == null) {
    		instance = new PluginController();
    	}
    	
    	return instance;
    }

    public List<Plugin> getInstalledPluginList(PluginDataSource pluginDataSource) {
		List<Plugin> pluginList = pluginDataSource.getPluginList();
		List<Plugin> installedPluginList = new ArrayList<Plugin>();
		for (Plugin plugin:pluginList) {
			if (plugin.getStatus() == 1) {
				installedPluginList.add(plugin);
			}
		}
    	return installedPluginList;
    }

	public Plugin getPlugin(ServiceFragmentFactory viewFactory) {
		if (activeDataSource != null) {
			List<Plugin> pluginList = activeDataSource.getPluginList();
			for (Plugin plugin : pluginList) {
				Log.d("PluginController", "pluginpackage name: " + plugin.getPackageName() + ", viewPackagename: " + viewFactory.getClass().getPackage().getName());
				if (plugin.getPackageName().equals(viewFactory.getClass().getPackage().getName())) {
					return plugin;
				}
			}
		}
		return null;
	}

	public List<PluginManager> getPluginManagers() {
		return Arrays.asList(pluginManagers);
	}

	public PluginManager getPluginManager(int type) {
		return pluginManagers[type];
	}

	public void attachDatasource(PluginDataSource dataSource) {
		activeDataSource = dataSource;
	}

	public void detachDataSource() {
		activeDataSource = null;
	}

	public PluginDataSource getActiveDataSource() {
		return activeDataSource;
	}

	public void registerPluginListObserver(
			ContentObserver mPluginListObserver) {
		for (PluginManager manager: pluginManagers) {
			if (manager != null) {
				manager.registerPluginListObserver(mPluginListObserver);
			}
		}
	}
}
