package com.example.myplugindemo.plugin;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.database.ContentObserver;
import android.util.Log;

import com.example.myplugindemo.PluginDemoApplication;
import com.example.myplugindemo.db.Plugin;
import com.example.myplugindemo.db.PluginDataSource;
import com.example.myplugindemo.lib.ServiceViewFactory;

/**
 * Created by dancy on 5/21/14.
 */
public class PluginController {
	
	private static PluginController instance;

    private PluginDataSource mPluginDataSource;
    private Activity context;
    private PluginManager[] pluginManagers = new PluginManager[PluginManagerFactory.TYPE_MAX];
//    List<Plugin> pluginList;

    private PluginController() {
        this.context = PluginDemoApplication.getLaunchActivity();
        initDatabase();
        loadPlugins();
    }
    
    public static PluginController getInstance() {
    	if (instance == null) {
    		instance = new PluginController();
    	}
    	
    	return instance;
    }
    
    
    public Activity getContext() {
    	return context;
    }
    
    public List<Plugin> getInstalledPluginList() {
		List<Plugin> pluginList = mPluginDataSource.loadPlugins();
		
		List<Plugin> installedPluginList = new ArrayList<Plugin>();
		for (Plugin plugin:pluginList) {
			if (plugin.getStatus() == 1) {
				installedPluginList.add(plugin);
			}
		}
    	return installedPluginList;
    }

	public Plugin getPlugin(ServiceViewFactory viewFactory) {
		List<Plugin> pluginList = mPluginDataSource.loadPlugins();
		for (Plugin plugin: pluginList) {
			Log.d("PluginController", "pluginpackage name: "+plugin.getPackageName()+", viewPackagename: "+viewFactory.getClass().getPackage().getName());
			if (plugin.getPackageName().equals(viewFactory.getClass().getPackage().getName())){
				return plugin;
			}
		}
		return null;
	}
	
	public PluginManager getPluginManager(int type) {
		return pluginManagers[type];
	}

	private void initDatabase() {
        mPluginDataSource = new PluginDataSource(context);
    }

    private void loadPlugins() {
        List<Plugin> pluginList = mPluginDataSource.loadPlugins();
        for(Plugin plugin:pluginList) {
            int type = plugin.getType();
            if (type < PluginManagerFactory.TYPE_MAX && pluginManagers[type] == null) {
                pluginManagers[type] = PluginManagerFactory.getPluginManager(type, this);
            }
        }
    }

	public void registerPluginListObserver(
			ContentObserver mPluginListObserver) {
		for (PluginManager manager: pluginManagers) {
			if (manager != null) {
				manager.registerPluginListObserver(mPluginListObserver);
			}
		}
		// TODO Auto-generated method stub
		
	}

	public PluginDataSource getDataSource() {
		// TODO Auto-generated method stub
		return mPluginDataSource;
	}

}
