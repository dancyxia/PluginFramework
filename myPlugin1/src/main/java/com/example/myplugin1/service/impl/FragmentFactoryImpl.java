package com.example.myplugin1.service.impl;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.example.myplugin1.R;
import com.example.myplugindemo.lib.PluginFragment;
import com.example.myplugindemo.lib.ServiceFragmentFactory;

public class FragmentFactoryImpl implements ServiceFragmentFactory {

	View pluginView;
	final String PLUGIN_PACKAGE_NAME="com.example.myplugin1";
	private String today = "";
	public FragmentFactoryImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public PluginFragment getFragment() {
		return PluginFragment.getInstance(today, PLUGIN_PACKAGE_NAME);
	}

//	@Override
//	public View createView(Context context) {
//		Context thisContext;
//		if (pluginView == null) {
//			try {
//				thisContext = context.createPackageContext("com.example.myplugin1", Context.CONTEXT_IGNORE_SECURITY);
//				LayoutInflater li = (LayoutInflater) thisContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//				pluginView = li.inflate(R.layout.fragment_main, null);
//			} catch (NameNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				return null;
//			}
//		}
//
//		TextView textView = (TextView)pluginView.findViewById(R.id.fragment_text);
//		textView.setText(new StringBuffer("Hello from plugin1, today is ").append(today));
//		return pluginView;
//
////		.createPackageContext("com.example.myplugin1", Context.CONTEXT_IGNORE_SECURITY)
////		LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
////		activity.getLayoutInflater();
////		View view = li.inflate(R.layout.fragment_main, null);
////		return view;
//	}

	@Override
	public void setToday(String today) {
		this.today = today;
//		if (pluginView != null) {
//			TextView textView = (TextView) pluginView.findViewById(R.id.fragment_text);
//			textView.setText(new StringBuffer("Hello from plugin1, today is ").append(today));
//		}
	}

//	@Override
//	public String getTitle() {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
