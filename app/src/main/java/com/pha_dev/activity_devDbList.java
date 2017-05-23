package com.pha_dev;

import android.content.Intent;
import android.database.Cursor;
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
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;

import com.lockerassistant.R;

/**
 * Created by Tom on 2017-04-18.
 */

/**
 * Android Application Development for Dummies
 * <p>
 * Creating you Application's SQLite Database
 * Page 281
 * <p>
 * Based on RemindersListActivity
 */

public class activity_devDbList extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
        {

    private static final String TAG = "PHA:: DevList ";
    private static final int ACTIVITY_CREATE = 0;
    private static final int ACTIVITY_EDIT = 1;

    private sqlAdapter_devDb mDbHelper;

    /**
     * Called when the activity is first created.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_db_entries);
        mDbHelper = new sqlAdapter_devDb(this);
        mDbHelper.open();
        fillData();
        //registerForContextMenu(getListView());

        /*
        #[M00]onCreate[begin]
        */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
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

    private void fillData() {
        Log.v(TAG, "STARTED :: fillData()");
        Cursor entriesCursor = mDbHelper.fetchAllEntries();
        Log.v(TAG, "Cursor received all entries successfully!");
        startManagingCursor(entriesCursor);

        //Create an array to specify the field we want
        //only the TITLE
        String[] from = new String[]{sqlAdapter_devDb.KEY_TITLE};

        // and an array of the fields we want to bind
        // to the view
        int[] to = new int[]{R.id.text1};

        // Now create a simple cursor adapter and set it
        // to display
        SimpleCursorAdapter entries =
                new SimpleCursorAdapter(this, R.layout.row_dev_db_entry,
                        entriesCursor, from, to);
        //setListAdapter(entries);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_lp_devdblist_child, menu);
    }

    @Override
    public void onBackPressed() {
        /*
        #[M01]onBackPressed[begin]
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
        */
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

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

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_delete:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                mDbHelper.deleteEntry(info.id);
                fillData();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void createEntry() {
        Intent i = new Intent(this, activity_devDbEntryEdit.class);
        startActivityForResult(i, ACTIVITY_CREATE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        fillData();
    }

}
