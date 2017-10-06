package com.livesafemobile.locationlogger.data;

import android.location.Location;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LocationTableTest {
    private LocationTable locationTable;

    private Location location1;
    private Location location2;

    @Before
    public void setup() {
        locationTable = new LocationTable(InstrumentationRegistry.getContext());
        location1 = new Location("gps");
        location1.setLatitude(38.895708);
        location1.setLongitude(-77.075313);
        location1.setAccuracy(1);
        location1.setAltitude(1);
        location1.setSpeed(1);
        location1.setTime(1);

        location2 = new Location("gps2");
        location2.setLatitude(29.895708);
        location2.setLongitude(-60.075313);
        location2.setAccuracy(1);
        location2.setAltitude(1);
        location2.setSpeed(1);
        location2.setTime(1);
    }

    @Test
    public void testPreConditions() {
        assertNotNull(locationTable);
    }

    @Test
    public void testDeleteAll() {
        locationTable.deleteAll();
    }

    @Test
    public void testAddAndDelete() {
        locationTable.deleteAll();

        locationTable.insert(location1);

        List<Location> locations = locationTable.getAll();
        assertThat(locations.size(), is(1));

        locationTable.deleteAll();

        locations = locationTable.getAll();
        assertThat(locations.size(), is(0));
    }

    @Test
    public void testData() {
        locationTable.deleteAll();

        locationTable.insert(location1);
        locationTable.insert(location2);

        List<Location> locations = locationTable.getAll();

        assertEquals("gps", locations.get(0).getProvider());
        assertEquals(location1.getLatitude(), locations.get(0).getLatitude());
        assertEquals(location1.getLongitude(), locations.get(0).getLongitude());
        assertEquals(location1.getAccuracy(), locations.get(0).getAccuracy());
        assertEquals(location1.getAltitude(), locations.get(0).getAltitude());
        assertEquals(location1.getSpeed(), locations.get(0).getSpeed());
        assertEquals(location1.getTime(), locations.get(0).getTime());

        assertEquals("gps2", locations.get(1).getProvider());
        assertEquals(location2.getLatitude(), locations.get(1).getLatitude());
        assertEquals(location2.getLongitude(), locations.get(1).getLongitude());
        assertEquals(location2.getAccuracy(), locations.get(1).getAccuracy());
        assertEquals(location2.getAltitude(), locations.get(1).getAltitude());
        assertEquals(location2.getSpeed(), locations.get(1).getSpeed());
        assertEquals(location2.getTime(), locations.get(1).getTime());
    }
}
