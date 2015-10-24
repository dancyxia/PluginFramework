package com.example.myplugindemo.db;

import org.osgi.framework.Bundle;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.example.myplugindemo.lib.ServiceFragmentFactory;

/**
 * Created by dancy on 5/21/14.
 */
public class Plugin {
    private String name;
    private PLUGINTYPE type;
    private String packageName;
    private String location;
    private int status;
    
    private Bundle bundle;
    private ServiceFragmentFactory service;
    private View view;

	public enum PLUGINTYPE {
		OSGIPLUGIN("OSGI Plugin", 0),
		SHAREIDPLUGIN("Share ID Plugin", 1);

		final private String pluginTypeName;
		final private int pluginTypeID;

		PLUGINTYPE(String name, int id) {
			pluginTypeName = name;
			pluginTypeID = id;
		}

		public String getName() {
			return pluginTypeName;
		}

		public int getID() {
			return pluginTypeID;
		}

		public static PLUGINTYPE findType(int id) {
			for (PLUGINTYPE type : values()) {
				if (type.getID() == id)
					return type;
			}
			return null;
		}
	}

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

    public PLUGINTYPE getType() {
        return type;
    }

    public void setType(PLUGINTYPE type) {
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
	public Fragment getFragment() {
//		if (view == null && service != null) {
//			view = service.createView(context);
//		}
//		return view;
		return service.getFragment();
	}

	/**
	 * @param view the view to set
	 */
//	public void setView(View view) {
//		Log.d("Plugin", "SetView is called,view: "+view);
////		this.view = view;
//	}

	/**
	 * @return the service
	 */
	public ServiceFragmentFactory getService() {
		return service;
	}

	public void setService(ServiceFragmentFactory service) {
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
