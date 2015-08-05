package com.example.myplugindemo.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import com.example.myplugindemo.db.PluginRegDBOpenHelper;
import com.example.myplugindemo.lib.PluginColumns;

/**
 * Created by dancy on 5/1/14.
 */


public class MyPluginRegister extends ContentProvider {
    private static final int ALLPLUGINS = 1;
    private static final int SINGLE_ROW = 2;

    private static final String PACKAGE_NAME = "com.example.myplugindemo";

    UriMatcher umPlugin = new UriMatcher(UriMatcher.NO_MATCH);

    private PluginRegDBOpenHelper dbHelper;
    @Override
    public boolean onCreate() {
        Log.d("application","creating provider");
        umPlugin.addURI(PluginColumns.CONTENT_AUTHORITY, "plugins", ALLPLUGINS);
        umPlugin.addURI(PluginColumns.CONTENT_AUTHORITY, "plugins/#", SINGLE_ROW);

        dbHelper = new PluginRegDBOpenHelper(this.getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(dbHelper.getPluginTable());
        if(umPlugin.match(uri) == SINGLE_ROW) {
                String rowID = uri.getPathSegments().get(1);
                qb.appendWhere(String.format("?=?", new String[]{PluginColumns._ID, rowID}));
        }

        return qb.query(db, projection,selection, selectionArgs,"","", sortOrder);
    }

    @Override
    public String getType(Uri uri) {
        switch (umPlugin.match(uri)) {
            case ALLPLUGINS:
                return PluginColumns.CONTENT_TYPE;
            case SINGLE_ROW:
                return PluginColumns.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unsupported URI: "+uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d("PluginRegister", "insert is called...");
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id = db.insert(dbHelper.getPluginTable(),"", values);
        Uri insertedUri = null;
        if (id > -1) {
            insertedUri = ContentUris.withAppendedId(uri, id);
            getContext().getContentResolver().notifyChange(insertedUri, null);
        }
        Log.d("PluginRegister", "a record is inserted "+insertedUri);
        return insertedUri;
    }

    @Override
    public int delete(Uri uri, String selection, String
            [] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.d("PluginRegister", "update is called...");
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int updatedRows = db.update(dbHelper.getPluginTable(),values,selection, selectionArgs );
        if (updatedRows > 0) {
            getContext().getContentResolver().notifyChange(PluginColumns.CONTENT_URI, null);
        }
//        long id = db.insert(dbHelper.getPluginTable(),"", values);
//        Uri insertedUri = null;
//        if (id > -1) {
//            insertedUri = ContentUris.withAppendedId(uri, id);
//            getContext().getContentResolver().notifyChange(insertedUri, null);
//        }
        return updatedRows;
    }
}
