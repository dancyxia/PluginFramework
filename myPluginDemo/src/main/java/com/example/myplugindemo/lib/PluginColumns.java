package com.example.myplugindemo.lib;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by dancy on 5/3/14.
 */
public class PluginColumns implements BaseColumns {

    private PluginColumns() {};
    public static final String CONTENT_AUTHORITY = "com.example.myplugindemo.provider";
    public static final Uri BASE_CONTENT_URI=Uri.parse(String.format("content://%s", new String[]{CONTENT_AUTHORITY}));
    public static final Uri CONTENT_URI=Uri.parse(String.format("content://%s/plugin", new String[]{CONTENT_AUTHORITY}));

    //define MIME type
    public static final String CONTENT_TYPE="vnd.android.cursor.dir/plugin";
    public static final String CONTENT_ITEM_TYPE="vnd.android.cursor.item/plugin";

    //define columns
    public static final String PLUGIN_UUID = "plugin_uuid";
    public static final String PLUGIN_TYPE = "plugin_type";
    public static final String PLUGIN_PACKAGE = "package";
    public static final String PLUGIN_LOCATION = "plugin_location";
    public static final String PLUGIN_STATUS = "plugin_status";
    public static final String SERVICE_NAME = "name";
    public static final String LABEL = "label";
    public static final String DESCRIPTION = "description";
    public static final String[] FIELDS = new String[]{BaseColumns._ID, PLUGIN_TYPE, PLUGIN_PACKAGE, SERVICE_NAME, LABEL, PLUGIN_UUID, DESCRIPTION};
}
