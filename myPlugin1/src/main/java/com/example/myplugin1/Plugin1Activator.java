package com.example.myplugin1;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.example.myplugin1.service.impl.FragmentFactoryImpl;
import com.example.myplugindemo.lib.ServiceFragmentFactory;

public class Plugin1Activator implements BundleActivator {

	BundleContext bundleContext;
	
	public Plugin1Activator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void start(BundleContext context) throws Exception {
		bundleContext = context;
		FragmentFactoryImpl viewFactory = new FragmentFactoryImpl();
//        bundleContext.registerService(
//        		ViewFactory.class.getName(),vi , props2);

		context.registerService(ServiceFragmentFactory.class.getName(), viewFactory, null);
	}

	@Override
	public void stop(BundleContext arg0) throws Exception {
		 bundleContext = null;
	}

}
