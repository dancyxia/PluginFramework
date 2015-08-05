package com.example.myplugindemo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.myplugindemo.lib.PluginColumns;

/**
 * Created by dancy on 5/1/14.
 */
public class PluginRegDBOpenHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME="plugins.db";
    private static final int DATABASE_VERSION = 2;
    private final static String PLUGIN = "plugin";

    public PluginRegDBOpenHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DBOpenHelper", "go to create DB");
        initDB(db, 2);
    }

    private void initDB(SQLiteDatabase db, int version) {
        switch(version) {
            case 1:
                String createStatement = String.format("create table %s (%s integer primary key autoincrement, %s text, %s text UNIQUE NOT NULL, %s text UNIQUE NOT NULL, %s text, %s text, %s text);", PLUGIN, PluginColumns._ID, PluginColumns.PLUGIN_TYPE, PluginColumns.PLUGIN_PACKAGE, PluginColumns.SERVICE_NAME, PluginColumns.LABEL, PluginColumns.PLUGIN_UUID, PluginColumns.DESCRIPTION);
                db.execSQL(createStatement);
                ContentValues values = new ContentValues();
                values.put(PluginColumns.PLUGIN_PACKAGE, "com.example.testplugin");
//                values.put(PluginColumns.PLUGIN_STATUS, 0);
                db.insert(PLUGIN, "",values);
//                long id = db.insert(dbHelper.getPluginTable(),"", values);
                break;
            default:
                createStatement = String.format("create table %s (%s integer primary key autoincrement, %s text, %s text UNIQUE NOT NULL, %s text, %s text, %s text UNIQUE NOT NULL, %s text, %s text, %s text);", PLUGIN, PluginColumns._ID, PluginColumns.PLUGIN_TYPE, PluginColumns.PLUGIN_PACKAGE, PluginColumns.PLUGIN_STATUS, PluginColumns.PLUGIN_LOCATION, PluginColumns.SERVICE_NAME, PluginColumns.LABEL, PluginColumns.PLUGIN_UUID, PluginColumns.DESCRIPTION);
                db.execSQL(createStatement);

                values = new ContentValues();
                values.put(PluginColumns.PLUGIN_PACKAGE, "com.example.myplugin1.service.impl");
                values.put(PluginColumns.PLUGIN_LOCATION, "file:///sdcard/bundles/MyPlugin1.apk");
                values.put(PluginColumns.SERVICE_NAME, "plugin1");
                values.put(PluginColumns.PLUGIN_STATUS, 0);
                db.insert(PLUGIN, "",values);

                values.clear();
                values.put(PluginColumns.PLUGIN_PACKAGE, "com.example.plugin.calendarevent");
                values.put(PluginColumns.PLUGIN_LOCATION, "file:///sdcard/bundles/MyNADiaryService.jar");
                values.put(PluginColumns.SERVICE_NAME, "Calendar Event");
                values.put(PluginColumns.PLUGIN_STATUS, 0);
                db.insert(PLUGIN, "",values);
                break;
        }
//        db.execSQL("create table ? (? integer primary key autoincrement, ? text, ? text UNIQUE NOT NULL, ? text UNIQUE NOT NULL, ? text, ? text, ? text);", new String[]{PLUGIN, PluginColumns._ID, PluginColumns.PLUGIN_TYPE, PluginColumns.PLUGIN_PACKAGE, PluginColumns.SERVICE_NAME, PluginColumns.LABEL, PluginColumns.PLUGIN_UUID, PluginColumns.DESCRIPTION});

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	Log.d("database", "onUpgrade is called, newVersion: "+newVersion);
        if (newVersion <= oldVersion) {
            return;
        }

        switch (newVersion) {
            case 1: onCreate(db);
                break;
            default:
                db.execSQL("drop table if exists "+PLUGIN);
//                db.execSQL("drop table if exists ?", new Object[]{PLUGIN});
                
                initDB(db, newVersion);
                break;
        }
    }

    public String getPluginTable() {
        return PLUGIN;
    }


}
