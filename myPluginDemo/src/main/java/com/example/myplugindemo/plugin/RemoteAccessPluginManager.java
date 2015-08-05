package com.example.myplugindemo.plugin;

import android.app.Activity;
import android.database.ContentObserver;

import com.example.myplugindemo.db.Plugin;
import com.example.myplugindemo.lib.PluginColumns;
import com.example.myplugindemo.lib.ServiceViewFactory;

/**
 * Created by dancy on 5/21/14.
 */
public class RemoteAccessPluginManager extends PluginManager {

	Activity context;
	public RemoteAccessPluginManager(PluginController controller) {
		super(controller);
		context = controller.getContext();
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
	public ServiceViewFactory getViewFactory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void stopManager() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerPluginListObserver(ContentObserver mPluginListObserver) {
        context.getContentResolver().registerContentObserver(PluginColumns.CONTENT_URI, true, mPluginListObserver);
		
	}
}
