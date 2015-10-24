package com.example.myplugindemo;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.RecyclerView;
//import android.support.v7.app.ActionBar;
import android.support.v4.widget.DrawerLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;

import com.example.myplugindemo.db.Plugin;
import com.example.myplugindemo.db.PluginDataSource;
import com.example.myplugindemo.plugin.PluginController;

//import android.support.v4.app.Fragment;
public class MainActivity extends AppCompatActivity implements DateListFragment.ListItemClickListener {
    private PluginDataSource mPluginDataSource;

    private CharSequence mTitle;
    private CharSequence mDrawerTitle;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private DateListFragment dateListFragment;
//    private ListView mPluginListView;
//    private List<Plugin> mPluginList;
//    private ArrayAdapter<Plugin> mPluginListAdapter;
    PluginListObserver mPluginListObserver;

//    private Plugin currentPlugin;
//    private int currentDate;
    
    private PluginController pluginController;
//    View currentContentView = null;


    @Override
    protected void onPause() {
        super.onPause();
        mPluginDataSource.close();
        pluginController.detachDataSource();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPluginDataSource.open();
        pluginController.attachDatasource(mPluginDataSource);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPluginDataSource = new PluginDataSource(this);
        mPluginDataSource.open();
        pluginController = PluginController.getInstance();

        initView(savedInstanceState);
    }

    private void initView(Bundle savedInstanceState) {

//        ActionBar  actionBar = getSupportActionBar();
//        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            //init drawer UI and action bar
            setupDrawer();
            RecyclerView mPluginManagerList = (RecyclerView) this.findViewById(R.id.left_drawer);
            PluginManagerListViewAdapter mPluginListAdapter = new PluginManagerListViewAdapter(pluginController.getPluginManagers());
            mPluginManagerList.setAdapter(mPluginListAdapter);

            //setup view pager
            setupTopFragment();

            //setup content viewpager
            setupPluginFragements();

//        mPluginListObserver = new PluginListObserver(new Handler());
//        pluginController.registerPluginListObserver(mPluginListObserver);

//        getActionBar().setDisplayHomeAsUpEnabled(true);
//        getActionBar().setHomeButtonEnabled(true);
//        if (savedInstanceState == null) {
//            currentPlugin = mPluginList.isEmpty()? null : mPluginList.get(0);
//            currentDate = 0;
//            getFragmentManager().beginTransaction().add(R.id.toplist, DateListFragment.getInstance(currentDate, 0)).commit();
//            invalidateItem();
//        }
    }

    private void setupDrawer() {
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mTitle = mDrawerTitle = getTitle();
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                toolbar, R.string.drawer_open, R.string.drawer_close) {

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
        mDrawerToggle.syncState();
    }

    private void setupTopFragment() {
       dateListFragment =  DateListFragment.getInstance(0);
        getSupportFragmentManager().beginTransaction().add(R.id.toplist, dateListFragment).commit();
    }

    @Override
    public void onItemClicked(int position) {
        Plugin plugin = pluginViewPagerAdapter.getPlugin(position);
        if (plugin.getService() != null) {
            Log.d("Two-Frag_test", "setToday and reset view...");
            plugin.getService().setToday(getResources().getStringArray(R.array.date)[position]);
            pluginViewPagerAdapter.getRegisteredFragment(position).setToday(getResources().getStringArray(R.array.date)[position]);
        }
    }

    PluginPageManager pluginPageManager;
    PluginViewPagerAdapter pluginViewPagerAdapter;

    public void setupPluginFragements() {
        ViewPager vp = (ViewPager)findViewById(R.id.plugin_pager);
        pluginViewPagerAdapter = new PluginViewPagerAdapter(getSupportFragmentManager(),dateListFragment.getSelectedItemPosition());
        pluginViewPagerAdapter.addPlugins(pluginController.getInstalledPluginList(mPluginDataSource));
        vp.setAdapter(pluginViewPagerAdapter);
        pluginPageManager = new PluginPageManager();
        vp.addOnPageChangeListener(pluginPageManager);

        TabLayout tab = (TabLayout)findViewById(R.id.tabs);
        tab.setupWithViewPager(vp);
    }
//    public void invalidateItem() {
//    	PluginFragment fragment = (PluginFragment)this.getFragmentManager().findFragmentByTag("contentfrag");
//        if (currentPlugin != null) {
//            currentContentView = currentPlugin.getView();
//        }
//        if (fragment == null) {
//            fragment = PluginFragment.getInstance(currentDate, currentPlugin == null ? "" : currentPlugin.getName());
//            fragment.setCurrentView(currentContentView);
//            this.getFragmentManager().beginTransaction()
//                    .add(R.id.fragment_content, fragment, "contentfrag")
//                    .commit();
//        } else {
//            Log.d("mainActivity", "currentPlaugin: "+currentPlugin+",currentView: "+currentContentView);
//            fragment.setCurrentView(currentContentView);
//        }
//    }

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
//            mPluginList.clear();
//            mPluginList.addAll(pluginController.getInstalledPluginList());
//            mPluginListAdapter.notifyDataSetChanged();
        }
    }

    public static class DefaultContentFragment extends Fragment {
        /**
         * A placeholder fragment containing a simple view.
         */
        public static final String ARG_PLUGIN_NAME = "PLUGIN_NAME";

        public DefaultContentFragment() {  }

            @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {

            if (container == null) {
                return null;
            }

            View view = inflater.inflate(R.layout.fragment_main, container, false);
            return view;
        }
    }

}
