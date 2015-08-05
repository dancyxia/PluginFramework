package com.example.myplugindemo.plugin;

import org.apache.felix.framework.Felix;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.Constants;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.example.myplugindemo.db.Plugin;
import com.example.myplugindemo.lib.ServiceViewFactory;
/**
 * Created by dancy on 5/21/14.
 */
/**
 * Created by dancy on 5/21/14.
 */
public class OSGIPluginManager extends PluginManager {
    private Felix felix;
    private FelixProperties felixProperties;
	private ServiceTracker<ServiceViewFactory, ServiceViewFactory> serviceTracker;
	private Activity context;
	private ContentObserver observer = null;
	
    
    public OSGIPluginManager(PluginController controller) {
    	super(controller);
    	context = controller.getContext();
        felixProperties = new FelixProperties(context.getFilesDir().getAbsolutePath());
        felix = new Felix(felixProperties);
        try {
			felix.start();
		} catch (BundleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//pluginMonitor = 
        new OSGIPluginMonitor(this);
    }
    
    private boolean appInstalled(String uri) {
        PackageManager pm = context.getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }
        catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed ;
    }

    @Override
    public boolean installPlugin(Plugin plugin) {
    	Log.d("OSGI pluginManager", "install plugin");
    		Log.d("OSGI pluginManager","plugin location: "+plugin.getLocation());
    	String location = plugin.getLocation();
    	//TODO: save OSGI package name to database
    	if (!appInstalled("com.example.myplugin1")) {
    		Log.d("OSGIPluginManager", "install APK plugin");
			Toast.makeText(context, "Plugin com.example.myplugin1 is not installed. Need to install the app first", Toast.LENGTH_LONG).show();
        	Intent promptInstall = new Intent(Intent.ACTION_VIEW)
    	    .setDataAndType(Uri.parse(location), 
    	                    "application/vnd.android.package-archive");
        	context.startActivity(promptInstall); 
        	return false;
    	} else {
	    	try {
				Bundle bundle = felix.getBundleContext().installBundle(plugin.getLocation());
				if (bundle != null) {
					plugin.setBundle(bundle);
					plugin.setStatus(1);
					bundle.start();
					controller.getDataSource().updatePlugin(plugin);
				}
			} catch (BundleException e) {
				// TODO Auto-generated catch block
				Toast.makeText(context, "Failed to install bundle from "+plugin.getLocation(), Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
    	}
    	return true;

    }

    @Override
    public void uninstallPlugin(Plugin plugin) {
    	Log.d("osgipluginmanager","uninstall plugin "+plugin.getName());
    	Bundle bundle = plugin.getBundle();
    	if (bundle == null) { //restart the app
			bundle = felix.getBundleContext().getBundle(plugin.getLocation());
    	}
		if (bundle != null) {
	    	try {
	    		Log.d("osgipluginmanager", "it's about to uninstall bundle");
				plugin.setStatus(0);
				plugin.setBundle(null);
				bundle.uninstall();
				controller.getDataSource().updatePlugin(plugin);
			} catch (BundleException e) {
				Toast.makeText(context, "Failed to uninstall bundle "+bundle.getSymbolicName(), Toast.LENGTH_SHORT).show();;
				e.printStackTrace();
			}
		} else {
		}
//			bundleItems.remove(bundleItem);
//			BundleItems.this.notifyDataSetChanged();
    }

    @Override
    public ServiceViewFactory getViewFactory() {
        return serviceTracker.getService();
    }

	@Override
	public void stopManager() {
		try {
			felix.stop();
		} catch (BundleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private class OSGIPluginMonitor implements ServiceTrackerCustomizer<ServiceViewFactory, ServiceViewFactory> {

		public OSGIPluginMonitor(PluginManager manager) {
			BundleContext bundleContext = felix.getBundleContext();
			try {
				serviceTracker = new ServiceTracker<ServiceViewFactory, ServiceViewFactory>(bundleContext, bundleContext.createFilter(String.format("(%s=%s)", Constants.OBJECTCLASS, ServiceViewFactory.class.getName())), this);
				Log.d("OSGIPluginManager", "service tracker is open");
				serviceTracker.open();
			} catch (InvalidSyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
		}
//		@Override
//		public void onServiceStarted() {
//			
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void onServiceStopped() {
//			// TODO Auto-generated method stub
//			
//		}

		@Override
		public ServiceViewFactory addingService(ServiceReference<ServiceViewFactory> ref) {
			Log.d("OSGIPluginManager", "new service is added");
			final ServiceViewFactory viewFactory = felix.getBundleContext().getService(ref);
			Plugin plugin = controller.getPlugin(viewFactory);
			if (plugin != null) {
				plugin.setService(viewFactory);
//				plugin.setView(viewFactory.createView(context));
				if (observer != null) {
					observer.onChange(true);
				}
			}
	
//			((PluginMonitor)context).onServiceStarted(viewFactory);
			// TODO Auto-generated method stub
			return viewFactory;
		}

		@Override
		public void modifiedService(ServiceReference<ServiceViewFactory> arg0, ServiceViewFactory arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void removedService(ServiceReference<ServiceViewFactory> service, ServiceViewFactory serviceImpl) {
			Log.d("OSGIPluginManager", "service is removed");
			Plugin plugin = controller.getPlugin(serviceImpl);
			if (plugin != null) {
				if (observer != null) {
					observer.onChange(true);
				}
			}
		}
	}

	@Override
	public void registerPluginListObserver(ContentObserver mPluginListObserver) {
		observer = mPluginListObserver;
	}

}
