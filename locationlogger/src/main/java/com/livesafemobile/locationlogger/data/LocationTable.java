package com.livesafemobile.locationlogger.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jshelly on 10/5/17.
 */
class LocationTable {
    private static final String TAG = LocationTable.class.getSimpleName();

    private SQLiteDatabase database;

    private static final String DATABASE_NAME = "com.livesafe.location.logger.db";
    private static final int VERSION = 1;

    private static final String TABLE_NAME = "locationTable";


    private static final String ID_COLUMN = "_id";
    private static final String LATITUDE_COLUMN = "latitude";
    private static final String LONGITUDE_COLUMN = "longitude";
    private static final String ACCURACY_COLUMN = "accuracy";
    private static final String ALTITUDE_COLUMN = "altitude";
    private static final String SPEED_COLUMN = "speed";
    private static final String TIME_COLUMN = "time";
    private static final String PROVIDER_COLUMN = "provider";

    LocationTable(Context context) {
        LocationTable.DatabaseHelper helper = new LocationTable.DatabaseHelper(context);

        database = helper.getWritableDatabase();
    }

    private String getCreateTableSql() {
        return String.format(
                "CREATE TABLE %s (%s integer PRIMARY KEY AUTOINCREMENT, %s double, %s double, %s float, %s double, %s float, %s datetime, %s string);",
                TABLE_NAME,
                ID_COLUMN,
                LATITUDE_COLUMN,
                LONGITUDE_COLUMN,
                ACCURACY_COLUMN,
                ALTITUDE_COLUMN,
                SPEED_COLUMN,
                TIME_COLUMN,
                PROVIDER_COLUMN
        );
    }

    List<Location> getAll() {
        String[] columns = {LONGITUDE_COLUMN, LATITUDE_COLUMN, PROVIDER_COLUMN, ACCURACY_COLUMN, ALTITUDE_COLUMN, SPEED_COLUMN, TIME_COLUMN};

        List<Location> locations = new ArrayList<>();

        Cursor c = database.query(TABLE_NAME, columns, null, null, null, null, null);

        c.moveToFirst();

        while (!c.isAfterLast()) {

            Location location = new Location(c.getString(c.getColumnIndex(PROVIDER_COLUMN)));
            location.setLatitude(c.getDouble(c.getColumnIndex(LATITUDE_COLUMN)));
            location.setLongitude(c.getDouble(c.getColumnIndex(LONGITUDE_COLUMN)));
            location.setAccuracy(c.getFloat(c.getColumnIndex(ACCURACY_COLUMN)));
            location.setAltitude(c.getDouble(c.getColumnIndex(ALTITUDE_COLUMN)));
            location.setSpeed(c.getFloat(c.getColumnIndex(SPEED_COLUMN)));
            location.setTime(c.getLong(c.getColumnIndex(TIME_COLUMN)));

            locations.add(location);

            c.moveToNext();
        }

        return locations;
    }

    void insert(Location location) {
        ContentValues values = new ContentValues();
        values.put(LATITUDE_COLUMN, location.getLatitude());
        values.put(LONGITUDE_COLUMN, location.getLongitude());
        values.put(ACCURACY_COLUMN, location.getAccuracy());
        values.put(ALTITUDE_COLUMN, location.getAltitude());
        values.put(SPEED_COLUMN, location.getSpeed());
        values.put(TIME_COLUMN, location.getTime());
        values.put(PROVIDER_COLUMN, location.getProvider());


        long ret = database.insert(TABLE_NAME, null, values);

        if (ret == -1) {
            Log.d(TAG, "error insert location: " + location.toString());
        } else {
            Log.d(TAG, "inserted location: " + location.toString());
        }
    }

    void deleteAll() {
        if (tableExists()) {
            database.execSQL(String.format("DELETE FROM %s", TABLE_NAME));
        }
    }

    private boolean tableExists() {
        String table = "sqlite_master";
        String selection = "type = ? AND name = ?";
        String[] selectionArgs = {"table", TABLE_NAME};

        Cursor c = database.query(table, null, selection, selectionArgs, null, null, null);

        return c.getCount() > 0;
    }

    private class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(getCreateTableSql());
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            String dropTable = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
            db.execSQL(dropTable);
            db.execSQL(getCreateTableSql());
        }
    }
}
