package com.lockerassistant.pha_dev;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toolbar;

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

public class activity_devDbList extends ListActivity {

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
        setContentView(R.layout.list_dev_db_entries);
        mDbHelper = new sqlAdapter_devDb(this);
        mDbHelper.open();
        fillData();
        registerForContextMenu(getListView());
        /**
         * @TOOLBAR
         */
        //getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_dev_db_list);
        //setActionBar(myToolbar);
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
        setListAdapter(entries);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_dev_db_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will automatically
        // handle clicks on the Home/Up button, so long as you specify a parent
        // activity in AndroidManifest.xml
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case R.id.menu_dev_db_add:
                Log.v(TAG, "SELECTED:: AppBar - /dev.add");
                Intent i = new Intent(this, activity_devDbEntryEdit.class);
                startActivityForResult(i, ACTIVITY_CREATE);
                return true;
            case R.id.menu_dev_db_view:
                Log.v(TAG, "SELECTED:: AppBar - /dev.view");
                //Intent iViewDev = new Intent(this, activity_devDbList.class);
                //startActivity(iViewDev);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_dev_db_add:
                Log.v(TAG, "SELECTED :: Dev Menu Insert");
                createEntry();
                return true;
            case R.id.menu_dev_db_view:
                Log.v(TAG, "SELECTED :: Dev Menu View");
                //Intent i = new Intent(this, TaskPreferences.class);
                return true;
        }

        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_lp_devdblist_child, menu);
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
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent i = new Intent(this, activity_devDbEntryEdit.class);
        i.putExtra(sqlAdapter_devDb.KEY_ROWID, id);
        startActivityForResult(i, ACTIVITY_EDIT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        fillData();
    }

}
