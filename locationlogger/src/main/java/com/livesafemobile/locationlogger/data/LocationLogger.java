package com.livesafemobile.locationlogger.data;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.location.Location;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.livesafemobile.locationlogger.R;
import com.livesafemobile.locationlogger.ui.LocationLoggerListActivity;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;

/**
 * Created by jshelly on 10/6/17.
 */

public final class LocationLogger {

    private static final String TAG = LocationLogger.class.getSimpleName();

    private static LocationLogger INSTANCE;
    private LocationTable locationTable;
    private Context context;

    private LocationLogger(Context context) {
        locationTable = new LocationTable(context);
        this.context = context;
    }  //private constructor.

    public static LocationLogger getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new LocationLogger(context);
        }

        return INSTANCE;
    }

    /**
     * Log a location
     */
    public void log(@NonNull Location location) {
        locationTable.insert(location);
    }

    public void clearLogs() {
        locationTable.deleteAll();
    }

    public List<Location> getLogs() {
        return locationTable.getAll();
    }

    public void displayLogs() {
        Intent intent = new Intent(context, LocationLoggerListActivity.class);
        context.startActivity(intent);
    }

    /**
     * Creates a file and prompts you to email it.
     */
    public void sendLogs() {
        final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        final PackageManager pm = context.getPackageManager();
        final List<ResolveInfo> matches = pm.queryIntentActivities(intent, 0);
        ResolveInfo best = null;
        for (final ResolveInfo info : matches)
            if (info.activityInfo.packageName.endsWith(".gm")
                    || info.activityInfo.name.toLowerCase(Locale.getDefault()).contains("gmail"))
                best = info;
        if (best != null)
            intent.setClassName(best.activityInfo.packageName,
                    best.activityInfo.name);
        intent.putExtra(Intent.EXTRA_SUBJECT,
                context.getString(R.string.email_subject));

        //create a file to send
        File file = createFile();

        if (file == null) {
            return;
        }
        Uri uri = Uri.fromFile(file);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        context.startActivity(intent);
    }


    private String getLogsAsString() {
        Type listType = new TypeToken<List<Location>>() {
        }.getType();
        String logString = new Gson().toJson(getLogs(), listType);
        return logString;
    }

    private File createFile() {
        //create a file to send
        File root = Environment.getExternalStorageDirectory();
        File file = new File(root, "logs.json");

        if (file.exists()){
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            Log.d(TAG, "fail creating file");
            e.printStackTrace();
        }

        if (!file.exists() || !file.canRead()) {
            Log.e(TAG, "file doesn\'t exist or can\'t be read");
            return null;
        }

        try {
            FileUtils.writeStringToFile(file, getLogsAsString(), (String) null);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return file;
    }
}
