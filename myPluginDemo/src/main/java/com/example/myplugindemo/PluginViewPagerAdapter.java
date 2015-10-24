package com.example.myplugindemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.example.myplugindemo.db.Plugin;
import com.example.myplugindemo.lib.PluginFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dancy on 8/12/2015.
 */
public class PluginViewPagerAdapter extends FragmentStatePagerAdapter {

    SparseArray<PluginFragment> registeredFragments = new SparseArray<>();

    List<Plugin> plugins = new ArrayList<>();
    int dateNo;

    public PluginViewPagerAdapter(FragmentManager fm, int dateNo) {
        super(fm);
        this.dateNo = dateNo;
    }

    public Plugin getPlugin(int pos) {
        return plugins.get(pos);
    }

    public void addPlugin(Plugin newPlugin) {
        plugins.add(newPlugin);
    }

    public void resetPlugins() {
        plugins.clear();
    }

    public void addPlugins(List<Plugin> newPlugins) {
        plugins.addAll(newPlugins);
    }

    public PluginFragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        PluginFragment f = (PluginFragment) super.instantiateItem(container, position);
        registeredFragments.put(position, f);
        return f;
    }

    @Override
    public Fragment getItem(int position) {
        Plugin plugin = plugins.get(position);

        Fragment fragment = plugin.getFragment();//(dateNo, plugin.getName());
//        fragment.setCurrentView(plugin.getView(fragment.getActivity()));
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        plugins.remove(position);
        registeredFragments.remove(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return plugins.get(position).getName();
    }

    @Override
    public int getCount() {
        return plugins.size();
    }
}
