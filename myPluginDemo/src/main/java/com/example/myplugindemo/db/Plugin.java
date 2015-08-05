package com.example.myplugindemo.db;

import org.osgi.framework.Bundle;

import android.util.Log;
import android.view.View;

import com.example.myplugindemo.PluginDemoApplication;
import com.example.myplugindemo.lib.ServiceViewFactory;

/**
 * Created by dancy on 5/21/14.
 */
public class Plugin {
    private String name;
    private int type;
    private String packageName;
    private String location;
    private int status;
    
    private Bundle bundle;
    private ServiceViewFactory service;
    private View view;

    /* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (o.getClass().getPackage().getName().equals(packageName))
			return true;
		
		return false;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

	/**
	 * @return the bundle
	 */
	public Bundle getBundle() {
		return bundle;
	}

	/**
	 * @param bundle the bundle to set
	 */
	public void setBundle(Bundle bundle) {
		this.bundle = bundle;
	}

	/**
	 * @return the view
	 */
	public View getView() {
		if (view == null && service != null) {
			view = service.createView(PluginDemoApplication.getLaunchActivity());
		}
		return view;
	}

	/**
	 * @param view the view to set
	 */
	public void setView(View view) {
		Log.d("Plugin", "SetView is called,view: "+view);
		this.view = view;
	}

	/**
	 * @return the service
	 */
	public ServiceViewFactory getService() {
		return service;
	}

	public void setService(ServiceViewFactory service) {
		this.service = service;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getName();
	}
    
    
}
