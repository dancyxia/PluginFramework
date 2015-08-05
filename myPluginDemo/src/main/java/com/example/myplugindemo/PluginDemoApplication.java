package com.example.myplugindemo;

import android.app.Activity;
import android.app.Application;

public class PluginDemoApplication extends Application {
//	private PluginController pluginController;
	public static Activity launchActivity;
//	private PluginDataSource pluginDataSource;

	public PluginDemoApplication() {
		// TODO Auto-generated constructor stub
	}
	

//	/**
//	 * @return the pluginController
//	 */
//	public PluginController getPluginController() {
//		if (pluginController == null && launchActivity != null) {
//			pluginController = new PluginController(launchActivity);
//		}
//		return pluginController;
//	}
//
//
//	public PluginDataSource getPluginDataSource() {
//		
//	}
	
	public void setLaunchActivity(MainActivity mainActivity) {
		launchActivity = mainActivity;
	}


	/**
	 * @return the launchActivity
	 */
	public static Activity getLaunchActivity() {
		return launchActivity;
	}


}
