package com.example.myplugin1;

import android.app.Activity;
import android.os.Bundle;

import com.example.myplugin1.service.impl.ViewFactoryImpl;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewFactoryImpl service = new ViewFactoryImpl();
		service.setToday("today");
		setContentView(service.createView(this));
	}
}
