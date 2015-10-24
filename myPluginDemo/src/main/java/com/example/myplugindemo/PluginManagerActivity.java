/**
 * 
 */
package com.example.myplugindemo;

import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.myplugindemo.db.Plugin;
import com.example.myplugindemo.db.PluginDataSource;
import com.example.myplugindemo.plugin.PluginController;
import com.example.myplugindemo.plugin.PluginManager;

/**
 * @author dancy
 *
 */
public class PluginManagerActivity extends ListActivity {
	private PluginDataSource mPluginDataSource;

	/**
	 * 
	 */
	public PluginManagerActivity() {
		// TODO Auto-generated constructor stub
	}

	
	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mPluginDataSource.close();
	}


	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        mPluginDataSource = new PluginDataSource(this);
		
        //TODO: consider cache mechanism
		List<Plugin> plugins = mPluginDataSource.getPluginList();
		PluginListAdapter adapter = new PluginListAdapter(this, plugins);
		setListAdapter(adapter);
	}
	
	private final class PluginListAdapter extends ArrayAdapter<String> {
		final Context context;
		final String[] pluginNames;
		final List<Plugin> plugins;

		public PluginListAdapter(Context context,List<Plugin> plugins) {
			super(context, R.layout.install_plugin_item);
			pluginNames = new String[plugins.size()];
			int i = 0;
			for (Plugin plugin: plugins) {
				pluginNames[i++] = plugin.getName();
			}
			this.addAll(pluginNames);
			this.context = context;
			this.plugins = plugins;
		}
		
		/* (non-Javadoc)
		 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.install_plugin_item, parent, false);
			TextView pluginNameView = (TextView)view.findViewById(R.id.plugin_name);
			pluginNameView.setText(pluginNames[position]);
			final Plugin plugin = plugins.get(position);
			
			//TODO: enable/disable install button and uninstall button according to the plugin's installation status
			final Button installButton = (Button)view.findViewById(R.id.install_plugin);
			final Button uninstallButton = (Button)view.findViewById(R.id.uninstall_plugin);
			
			installButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					PluginManager manager = PluginController.getInstance().getPluginManager(plugin.getType().getID());
					if (manager.installPlugin(plugin)) {
						installButton.setEnabled(false);
						uninstallButton.setEnabled(true);
					};
					Log.d("InstallpluginList", "installButton is clicked");
					
				}
			});
			
			uninstallButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					PluginManager manager = PluginController.getInstance().getPluginManager(plugin.getType().getID());
					manager.uninstallPlugin(plugin);
					installButton.setEnabled(true);
					uninstallButton.setEnabled(false);
					
					Log.d("InstallpluginList", "uninstallButton is clicked");
					
				}
			});
 
			return view;
		}

	}
	
}
