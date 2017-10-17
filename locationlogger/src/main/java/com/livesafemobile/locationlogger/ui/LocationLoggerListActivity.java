package com.livesafemobile.locationlogger.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.livesafemobile.locationlogger.R;

/**
 * Created by jshelly on 10/17/17.
 */

public class LocationLoggerListActivity extends Activity{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.location_logger_list_activity);


        RecyclerView rvLocationList = findViewById(R.id.rvLocations);
        rvLocationList.setLayoutManager(new LinearLayoutManager(this));
        rvLocationList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvLocationList.setAdapter(new LocationLoggerAdapter(this));
    }
}
