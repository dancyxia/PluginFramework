package com.example.myplugindemo.lib;

import android.content.Context;
import android.view.View;

public interface ServiceViewFactory {
	public View createView(Context activity);
	public void regenView();
	public String getTitle();
}
