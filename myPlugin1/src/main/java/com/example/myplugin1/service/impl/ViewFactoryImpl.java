package com.example.myplugin1.service.impl;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.myplugin1.R;
import com.example.myplugindemo.lib.ServiceViewFactory;

public class ViewFactoryImpl implements ServiceViewFactory {

	private String today = "";
	public ViewFactoryImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View createView(Context context) {
		Context thisContext;
		try {
			thisContext = context.createPackageContext("com.example.myplugin1", Context.CONTEXT_IGNORE_SECURITY);
			LayoutInflater li = (LayoutInflater)thisContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
			View view = li.inflate(R.layout.fragment_main, null);
			TextView textView = (TextView)view.findViewById(R.id.fragment_text);
			textView.setText(new StringBuffer("Hello from plugin1, today is ").append(today));
			return view;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
//		.createPackageContext("com.example.myplugin1", Context.CONTEXT_IGNORE_SECURITY)
//		LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		activity.getLayoutInflater(); 
//		View view = li.inflate(R.layout.fragment_main, null);
//		return view;
	}

	@Override
	public void regenView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setToday(String today) {
		this.today = today;
		// TODO Auto-generated method stub
		
	}

//	@Override
//	public String getTitle() {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
