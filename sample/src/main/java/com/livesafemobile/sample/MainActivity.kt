package com.livesafemobile.sample

import android.location.Location
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.livesafemobile.locationlogger.data.LocationLogger
import com.livesafemobile.locationlogger.ui.LocationLoggerListActivity

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val location1 = Location("gps")
        location1.latitude = 38.895708
        location1.longitude = -77.075313
        location1.accuracy = 1f
        location1.altitude = 1.0
        location1.speed = 1f
        location1.time = System.currentTimeMillis()

        val location2 = Location("gps")
        location2.latitude = 38.895708
        location2.longitude = -77.075313
        location2.accuracy = 1f
        location2.altitude = 1.0
        location2.speed = 2f
        location2.time = System.currentTimeMillis()

        LocationLogger.getInstance(this).log(location1)
        LocationLogger.getInstance(this).log(location2)


        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val intent = LocationLoggerListActivity.createIntent(this)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
