package com.example.myplugindemo.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.myplugindemo.PluginDemoApplication;
import com.example.myplugindemo.lib.PluginColumns;

/**
 * Created by dancy on 5/6/14.
 */
public class PluginDataSource {
    private PluginRegDBOpenHelper dbHelper;
    private SQLiteDatabase db;
    private static List<Plugin> pluginList = null;
    
    public PluginDataSource(Context context) {
        dbHelper = new PluginRegDBOpenHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public List<Plugin> getPluginList() {
        if (pluginList == null)
            return loadPlugins();

        return pluginList;
    }

    public void updatePlugin(Plugin plugin) {
        if (db != null) {
        	ContentValues values = new ContentValues();
        	values.put(PluginColumns.PLUGIN_STATUS, plugin.getStatus());
            db.update(dbHelper.getPluginTable(), values, PluginColumns.PLUGIN_PACKAGE+"=?", new String[]{plugin.getPackageName()});
        }
    }
    
//    public List<Plugin> refreshPluginList(List<Plugin> pluginList, boolean installed) {
//        if (pluginList == null) {
//            pluginList = new ArrayList<Plugin>();
//        } else if (!pluginList.isEmpty()) {
//            pluginList.clear();
//        }
//
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        Cursor cursor = null;
//        if (db != null) {
//            cursor = db.query(true, dbHelper.getPluginTable(), new String[]{PluginColumns.PLUGIN_PACKAGE, PluginColumns.SERVICE_NAME, PluginColumns.PLUGIN_TYPE, PluginColumns.PLUGIN_LOCATION},PluginColumns.PLUGIN_STATUS+"=?",new String[]{Integer.toString(installed?1:0,10)},null,null,null,null);
//            if (cursor.getCount() > 0) {
//                while(cursor.moveToNext()) {
////                pluginList.add(cursor.getString(cursor.getColumnIndex(PluginColumns.PLUGIN_PACKAGE)));
//                    Plugin plugin = new Plugin();
//                    plugin.setPackageName(cursor.getString(cursor.getColumnIndex(PluginColumns.PLUGIN_PACKAGE)));
//                    plugin.setName(cursor.getString(cursor.getColumnIndex(PluginColumns.SERVICE_NAME)));
//                    plugin.setType(cursor.getInt(cursor.getColumnIndex(PluginColumns.PLUGIN_TYPE)));
//                    plugin.setLocation(cursor.getString(cursor.getColumnIndex(PluginColumns.PLUGIN_LOCATION)));
//                    pluginList.add(plugin);
//                }
//            }
//        }
//
//        return pluginList;
//    }

    private List<Plugin> loadPlugins() {
        Cursor cursor = null;
        if (pluginList == null) {
        	pluginList = new ArrayList<Plugin>();
            if (db != null) {
                cursor = db.query(true, dbHelper.getPluginTable(), new String[]{PluginColumns.PLUGIN_PACKAGE, PluginColumns.SERVICE_NAME, PluginColumns.PLUGIN_TYPE, PluginColumns.PLUGIN_LOCATION, PluginColumns.PLUGIN_STATUS},null,null,null,null,null,null);
                Log.d("PluginDataSource", "query data base, count: "+cursor.getCount());
                if (cursor.getCount() > 0) {
                    while(cursor.moveToNext()) {
                        Plugin plugin = new Plugin();
                        plugin.setPackageName(cursor.getString(cursor.getColumnIndex(PluginColumns.PLUGIN_PACKAGE)));
                        plugin.setName(cursor.getString(cursor.getColumnIndex(PluginColumns.SERVICE_NAME)));
                        plugin.setType(Plugin.PLUGINTYPE.findType(cursor.getInt(cursor.getColumnIndex(PluginColumns.PLUGIN_TYPE))));
                        plugin.setLocation(cursor.getString(cursor.getColumnIndex(PluginColumns.PLUGIN_LOCATION)));
                        plugin.setStatus(cursor.getInt(cursor.getColumnIndex(PluginColumns.PLUGIN_STATUS)));
                        Log.d("PluginDataSource", "add plugin "+plugin.getName());
                        pluginList.add(plugin);
                    }
                }
                cursor.close();
            }
        }
        
        return pluginList;
    }
}
