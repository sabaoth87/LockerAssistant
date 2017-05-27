package com;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.lockerassistant.R;
import com.pha_dev.activity_devDbList;

//Description

/**
 * #METHODS
 * [M00]onCreate
 * {v0.0} - Default
 *
 * [M01]onBackPressed
 * {v0.0} - Default
 *
 * [M02]onCreateOptionsMenu
 * {v0.0} - Default
 *
 * [M03]onOptionsItemSelected
 * {v0.0} - Default
 *
 * [M04]onNavigationItemSelected
 * {v0.0} - Default
 *
 */


public class activity_lockassist_main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "LockerAssistant ";
    private static final int ACTIVITY_CREATE = 0;
    private static final int ACTIVITY_EDIT = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*
        #[M00]onCreate[begin]
        * {v0.0} - Default
        */
        Log.v(TAG, "STARTED :: onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainactivity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with my ownaction", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        /*
        #[M00]onCreate[end]
        */
    }


    @Override
    public void onBackPressed() {
        /*
        #[M01]onBackPressed[begin]
        * {v0.0} - Default
        */
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        /*
        #[M01]onBackPressed[end]
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*
        #[M02]onCreateOptionsMenu[begin]
        * {v0.0} - Default
        */
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
        /*
        #[M02]onCreateOptionsMenu[end]
        */
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*
        #[M03]onOptionsItemSelected[begin]
        * {v0.0} - Default
        */
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
        /*
        #[M03]onOptionsItemSelected[end]
        */
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        /*
        #[M04]onNavigationItemSelected[begin]
        * {v0.0} - Default
        */

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_la_add) {
            // Handle the camera action
        } else if (id == R.id.nav_la_flags) {

        } else if (id == R.id.nav_dev_db) {
            Intent i = new Intent(this, activity_devDbList.class);
            startActivityForResult(i, ACTIVITY_CREATE);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
        /*
        #[M04]onNavigationItemSelected[end]
        */
    }
}
