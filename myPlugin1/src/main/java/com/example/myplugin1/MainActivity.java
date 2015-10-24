package com.example.myplugin1
		;

import android.app.Activity;
import android.os.Bundle;

import com.example.myplugin1.service.impl.FragmentFactoryImpl;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FragmentFactoryImpl service = new FragmentFactoryImpl();
		service.setToday("today");
		setContentView(R.layout.fragment_main);
		//setContentView(service.createView(this));
	}
}
