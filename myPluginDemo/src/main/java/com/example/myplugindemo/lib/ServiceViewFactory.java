package com.example.myplugindemo.lib;

import android.content.Context;
import android.view.View;

public interface ServiceViewFactory {
	public View createView(Context context);
	public void regenView();
	public void setToday(String today);
}
