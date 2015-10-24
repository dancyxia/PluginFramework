package com.example.myplugindemo.lib;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.view.View;

public interface ServiceFragmentFactory {
//	public View createView(Context context);
	public PluginFragment getFragment();
	public void setToday(String today);
}
