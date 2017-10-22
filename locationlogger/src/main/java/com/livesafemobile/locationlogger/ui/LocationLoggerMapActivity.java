package com.livesafemobile.locationlogger.ui;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.livesafemobile.locationlogger.R;
import com.livesafemobile.locationlogger.data.LocationLogger;

import java.util.List;

/**
 * Created by jshelly on 10/19/17.
 */

public class LocationLoggerMapActivity extends AppCompatActivity implements
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnInfoWindowClickListener, OnMapReadyCallback {

    private GoogleMap googleMap;
    private SupportMapFragment mapFragment;

    public static Intent createIntent(Context context) {
        return new Intent(context, LocationLoggerMapActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logger_map);

         mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
         mapFragment.getMapAsync(this);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        googleMap.setInfoWindowAdapter(new MyInfoWindowAdapter());

        List<Location> locations = LocationLogger.getInstance(this).getLogs();
        for (Location location :
                locations) {
            googleMap.addMarker(
                    new MarkerOptions()
                            .position(new LatLng(location.getLatitude(), location.getLongitude()))
                            .title(location.getProvider())
                            .snippet(LocationPrinter.printLocation(location))
                            .icon(getIcon(location))
            );
        }
    }

    private BitmapDescriptor getIcon(Location location) {
        switch (location.getProvider()) {
            case LocationManager.GPS_PROVIDER:
                return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE);
            case LocationManager.NETWORK_PROVIDER:
                return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET);
            case LocationManager.PASSIVE_PROVIDER:
                return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
            default:
                return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
        }
    }


    class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        private final View myContentsView;

        MyInfoWindowAdapter(){
            myContentsView = getLayoutInflater().inflate(R.layout.location_list_item, null);
        }

        @Override
        public View getInfoContents(Marker marker) {
            TextView tvTitle = myContentsView.findViewById(R.id.tvText);
            tvTitle.setText(marker.getSnippet());
            return myContentsView;
        }

        @Override
        public View getInfoWindow(Marker marker) {
            // TODO Auto-generated method stub
            return null;
        }

    }
}
