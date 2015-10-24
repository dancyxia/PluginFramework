package com.example.myplugindemo;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myplugindemo.db.Plugin;
import com.example.myplugindemo.plugin.PluginManager;

import java.util.List;

/**
 * Created by dancy on 8/13/2015.
 */
public class PluginManagerListViewAdapter extends RecyclerView.Adapter<PluginManagerListViewAdapter.ViewHolder> {

    final List<PluginManager> pluginManagerList;
    public final static String SelectPluginTypeEvent = "select_plugin_type_event";
    public final static String ARG_PLUGIN_TYPE_ID ="plugin_type_id";

    public PluginManagerListViewAdapter(final List<PluginManager> list) {
        pluginManagerList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.plugin_item,viewGroup, false);
        PluginManagerListViewAdapter.ViewHolder vh = new ViewHolder(view, i);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.plutinTypeText.setText(Plugin.PLUGINTYPE.findType(i).getName());
        viewHolder.pluginTypeId = i;
    }

    @Override
    public int getItemCount() {
        return pluginManagerList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView plutinTypeText;
        int pluginTypeId;

        public ViewHolder(View itemView, int id) {
            super(itemView);
            pluginTypeId = id;
            plutinTypeText =(TextView) itemView.findViewById(R.id.pluginlist_item_name);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(SelectPluginTypeEvent);
            intent.putExtra(ARG_PLUGIN_TYPE_ID, pluginTypeId);
            LocalBroadcastManager.getInstance(v.getContext()).sendBroadcast(intent);
        }
    }
}
