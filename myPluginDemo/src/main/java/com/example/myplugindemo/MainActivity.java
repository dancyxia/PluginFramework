package com.example.myplugindemo;

import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myplugindemo.db.Plugin;
import com.example.myplugindemo.plugin.PluginController;

//import android.support.v4.app.Fragment;

public class MainActivity extends /*ActionBar*/Activity{
    private CharSequence mTitle;
    private CharSequence mDrawerTitle;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private ListView mPluginListView;
    private List<Plugin> mPluginList;
    private ArrayAdapter<Plugin> mPluginListAdapter;
//    private PluginDataSource mPluginDataSource;
    PluginListObserver mPluginListObserver;

    private Plugin currentPlugin;
    private int currentDate;
    
    private PluginController pluginController;
    View currentContentView = null;

    @Override
    protected void onPause() {
        super.onPause();
        pluginController.getDataSource().close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        PluginDemoApplication app = (PluginDemoApplication)getApplicationContext();
        //this setting must be done before constructing PluginController  
        app.setLaunchActivity(this);
        pluginController = PluginController.getInstance();
        //should never be null
        assert pluginController != null;
        initView(savedInstanceState);
    }

    private void initView(Bundle savedInstanceState) {

        //init drawer UI
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_launcher, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        //init installed plugin list UI
        mPluginListView = (ListView) this.findViewById(R.id.left_drawer);
        mPluginList = pluginController.getInstalledPluginList(); 
        //    new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.plugin_array)
        mPluginListAdapter = new ArrayAdapter<Plugin>(this, R.layout.plugin_item, mPluginList);        
        mPluginListView.setAdapter(mPluginListAdapter);
        mPluginListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentPlugin = mPluginList.get(position);
//                currentContentView = currentPlugin.getView();
//                Log.d("MainActivity", "currentContentView: "+currentContentView);
                invalidateItem();
//                Intent intent = new Intent();
//                intent.setComponent(new ComponentName(currentPlugin, currentPlugin+".TransparentActivity"));
//                startActivity(intent);
                mDrawerLayout.closeDrawer(mPluginListView);
            }
        });

        mPluginListObserver = new PluginListObserver(new Handler());
        pluginController.registerPluginListObserver(mPluginListObserver);

        getActionBar().setDisplayHomeAsUpEnabled(true);
//        getActionBar().setHomeButtonEnabled(true);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.toplist, new DateListFragment()).commit();
            currentPlugin = mPluginList.isEmpty()? null : mPluginList.get(0);
            currentDate = 0;
            invalidateItem();
        }
    }

    public void setCurrentDate(int currentDate) {
        this.currentDate = currentDate;
    }

    public void invalidateItem() {
    	ContentFragment fragment = new ContentFragment();
    	if (currentPlugin != null) {
    		currentContentView = currentPlugin.getView();
    	}
        fragment.setCurrentView(currentContentView);
        Bundle args = new Bundle();
        args.putString(ContentFragment.ARG_PLUGIN_NAME, currentPlugin == null?"":currentPlugin.getName());
        args.putInt(ContentFragment.ARG_DATE_NO, currentDate);
        fragment.setArguments(args);
        this.getFragmentManager().beginTransaction()
                .replace(R.id.fragment_content, fragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch(id) {
            case R.id.action_settings:
                return true;
            case R.id.action_pluginmanager:
            	this.startActivity(new Intent(this.getApplicationContext(), PluginManagerActivity.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class PluginListObserver extends ContentObserver{

        public PluginListObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            Log.d("MainActivity", "plugin dataChange is received");
            mPluginList.clear();
            mPluginList.addAll(pluginController.getInstalledPluginList());
            mPluginListAdapter.notifyDataSetChanged();
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class ContentFragment extends Fragment {

        public static final String ARG_DATE_NO = "DATE_NO";
        public static final String ARG_PLUGIN_NAME = "PLUGIN_NAME";
        private View currentView;

        public ContentFragment() {
        }

        public void setCurrentView(View currentContentView) {
        	currentView = currentContentView;
		}

		@Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            int date_idx = this.getArguments().getInt(ARG_DATE_NO);
            Log.d("Two-Frag_test", "get position: "+date_idx);

            View rootView = currentView;
            
            if (rootView == null) {
	            String pluginPackage = this.getArguments().getString(ARG_PLUGIN_NAME);
	            rootView = inflater.inflate(R.layout.fragment_main, container, false);
	            TextView textView = (TextView) rootView.findViewById(R.id.fragment_text);
	            textView.setText(new StringBuffer("current selection is ").append(pluginPackage/*getResources().getStringArray(R.array.plugin_array)[plugin_idx]*/).append(" today is ").append(getResources().getStringArray(R.array.date)[date_idx]));
            } 
            return rootView;
        }
    }

    public static class DateListFragment extends ListFragment {

        public static final String ARG_DATE_NO = "DATE_NO";
        public static final String ARG_PLUGIN_NO = "PLUGIN_NO";

        public DateListFragment() {

        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            super.onListItemClick(l, v, position, id);
            MainActivity owner = ((MainActivity) getActivity());
            Log.d("Two-Frag_test", "click position  1: "+position);
            owner.setCurrentDate(position);
            if (owner.currentPlugin.getService() != null) {
            	Log.d("Two-Frag_test", "setToday and reset view...");
                owner.currentPlugin.getService().setToday(getResources().getStringArray(R.array.date)[position]);
                owner.currentPlugin.setView(null);
            }
            owner.invalidateItem();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            ListView topList;

            View rootView = inflater.inflate(R.layout.fragment_date_list, container, false);

            topList = (ListView) rootView.findViewById(android.R.id.list);
            topList.setAdapter(new ArrayAdapter<String>(this.getActivity(), R.layout.date_item, getResources().getStringArray(R.array.date)));
            topList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    MainActivity owner = ((MainActivity) getActivity());
                    Log.d("Two-Frag_test", "click position: " + position);
                    owner.setCurrentDate(position);
                    owner.invalidateItem();
                }
            });

            return rootView;
        }
    }
}
