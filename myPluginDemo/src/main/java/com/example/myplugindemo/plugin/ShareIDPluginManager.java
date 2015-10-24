package com.example.myplugindemo.plugin;

import android.database.ContentObserver;

import com.example.myplugindemo.db.Plugin;
import com.example.myplugindemo.lib.ServiceFragmentFactory;

/**
 * Created by dancy on 5/21/14.
 */
public class ShareIDPluginManager extends PluginManager {

	public ShareIDPluginManager(PluginController controller) {
		super(controller);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean installPlugin(Plugin plugin) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void uninstallPlugin(Plugin plugin) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ServiceFragmentFactory getViewFactory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void stopManager() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerPluginListObserver(ContentObserver mPluginListObserver) {
		// TODO Auto-generated method stub
		
	}
}
