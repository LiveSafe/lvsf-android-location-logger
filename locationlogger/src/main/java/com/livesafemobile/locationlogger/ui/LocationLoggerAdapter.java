package com.livesafemobile.locationlogger.ui;

import android.content.Context;
import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.livesafemobile.locationlogger.R;
import com.livesafemobile.locationlogger.data.LocationLogger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jshelly on 10/17/17.
 */

public class LocationLoggerAdapter extends RecyclerView.Adapter<LocationLoggerAdapter.ViewHolder> {

    Context context;
    List<Location> locationList = new ArrayList<>();
    public LocationLoggerAdapter(Context context) {
        this.context = context;
        locationList = LocationLogger.getInstance(context).getLogs();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.tvText);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.location_list_item, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        Location location = locationList.get(position);
        viewHolder.textView.setText(LocationPrinter.printLocation(location));
    }

    @Override
    public long getItemId(int arg0) {
        return RecyclerView.NO_ID;
    }

    @Override public int getItemCount() {
        return locationList.size();
    }
}
