package com.pha_dev;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.lockerassistant.R;

/**
 * Created by Tom on 2017-05-27.
 */


/*
https://guides.codepath.com/android/Populating-a-ListView-with-a-CursorAdapter

cursor walkthrough::basis
 */

public class curAdapter_devDbList extends CursorAdapter {
    public curAdapter_devDbList(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }


    /*
    The newView method is used to inflate a new view and return it,
    you don't bind any data to the view at this point.
    */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.row_dev_db_entry, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //TODO Link the bindView to somehow?? populate the ListView
        // Find fields to populate in inflated template
        TextView tvBody = (TextView) view.findViewById(R.id.text1);
        //TextView tvPriority = (TextView) view.findViewById(R.id.tvPriority);
        // Extract properties from cursor
        String body = cursor.getString(cursor.getColumnIndexOrThrow(sqlAdapter_devDb.KEY_TITLE));
        //int priority = cursor.getInt(cursor.getColumnIndexOrThrow("priority"));
        // Populate fields with extracted properties
        tvBody.setText(body);
        //tvPriority.setText(String.valueOf(priority));
    }
}
