package com.lockerassistant.pha_dev;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.lockerassistant.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Tom on 2017-04-18.
 */

/**
 * Android Application Development for Dummies
 * <p>
 * Creating you Application's SQLite Database
 * Page 285
 * <p>
 * Based on RemindersEditActivity
 */

public class activity_devDbEntryEdit extends Activity {

    /**
     * Date Format
     */
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd kk:mm:ss";
    /**
     * Dialog Constants
     */
    private static final int DATE_PICKER_DIALOG = 0;
    private static final int TIME_PICKER_DIALOG = 1;
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "kk:mm";

    private EditText mTitleText;
    private EditText mBodyText;
    private EditText mSubjectText;
    private EditText mStatusText;

    private Button mDateButton;
    private Button mTimeButton;
    private Button mConfirmButton;

    private sqlAdapter_devDb mDbHelper;

    private Calendar mCalendar;

    private Long mRowId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDbHelper = new sqlAdapter_devDb(this);

        setContentView(R.layout.activity_dev_db_edit);

        mCalendar = Calendar.getInstance();
        mTitleText = (EditText) findViewById(R.id.dev_db_edit_title);
        mBodyText = (EditText) findViewById(R.id.dev_db_edit_body);
        mSubjectText = (EditText) findViewById(R.id.dev_db_edit_subject);
        mStatusText = (EditText) findViewById(R.id.dev_db_edit_status);

        mConfirmButton = (Button) findViewById(R.id.dev_db_edit_confirm);
        mDateButton = (Button) findViewById(R.id.dev_db_edit_date);
        mTimeButton = (Button) findViewById(R.id.dev_db_edit_time);

        mRowId = savedInstanceState != null
                ? savedInstanceState.getLong(sqlAdapter_devDb.KEY_ROWID)
                : null;
        registerButtonListenersAndSetDefaultText();
    }

    private void setRowIdFromIntent() {
        if (mRowId == null) {
            Bundle extras = getIntent().getExtras();
            mRowId = extras != null
                    ? extras.getLong(sqlAdapter_devDb.KEY_ROWID)
                    : null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDbHelper.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDbHelper.open();
        setRowIdFromIntent();
        poplateFields();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_PICKER_DIALOG:
                return showDatePicker();
            case TIME_PICKER_DIALOG:
                return showTimePicker();
        }
        return super.onCreateDialog(id);
    }

    private DatePickerDialog showDatePicker() {


        DatePickerDialog datePicker = new DatePickerDialog(activity_devDbEntryEdit.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, monthOfYear);
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateButtonText();
            }
        }, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
        return datePicker;
    }

    private TimePickerDialog showTimePicker() {

        TimePickerDialog timePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                mCalendar.set(Calendar.MINUTE, minute);
                updateTimeButtonText();
            }
        }, mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), true);

        return timePicker;
    }

    private void registerButtonListenersAndSetDefaultText() {

        mDateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDialog(DATE_PICKER_DIALOG);
            }
        });


        mTimeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDialog(TIME_PICKER_DIALOG);
            }
        });

        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                saveState();
                setResult(RESULT_OK);
                finish();
            }

        });

        updateDateButtonText();
        updateTimeButtonText();
    }

    private void poplateFields() {
        if (mRowId != null) {
            Cursor entry = mDbHelper.fetchEntry(mRowId);
            startManagingCursor(entry);
            mTitleText.setText(entry.getString(
                    entry.getColumnIndexOrThrow(sqlAdapter_devDb.KEY_TITLE)));
            mBodyText.setText(entry.getString(
                    entry.getColumnIndexOrThrow(sqlAdapter_devDb.KEY_BODY)));
            mSubjectText.setText(entry.getString(
                    entry.getColumnIndexOrThrow(sqlAdapter_devDb.KEY_ENTRY_SUBJECT)));
            mStatusText.setText(entry.getString(
                    entry.getColumnIndexOrThrow(sqlAdapter_devDb.KEY_ENTRY_STATUS)));

            SimpleDateFormat dateTimeFormat =
                    new SimpleDateFormat(DATE_TIME_FORMAT);
            Date date = null;
            try {
                String dateString = entry.getString(
                        entry.getColumnIndexOrThrow(sqlAdapter_devDb.KEY_ENTRY_DATE));
                date = dateTimeFormat.parse(dateString);
                mCalendar.setTime(date);
            } catch (ParseException e) {
                Log.e("EntryEditActivity", e.getMessage(), e);
            }

        }

        updateDateButtonText();
        updateTimeButtonText();
    }

    private void updateTimeButtonText() {
        // Set the time button text based upon the value from the database
        SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT);
        String timeForButton = timeFormat.format(mCalendar.getTime());
        mTimeButton.setText(timeForButton);
    }

    private void updateDateButtonText() {
        // Set the date button text based upon the value from the database
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        String dateForButton = dateFormat.format(mCalendar.getTime());
        mDateButton.setText(dateForButton);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(sqlAdapter_devDb.KEY_ROWID, mRowId);
    }

    private void saveState() {
        String title = mTitleText.getText().toString();
        String body = mBodyText.getText().toString();
        String subject = mSubjectText.getText().toString();
        String status = mStatusText.getText().toString();

        SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
        String entryDateTime = dateTimeFormat.format(mCalendar.getTime());

        if (mRowId == null) {
            long id = mDbHelper.createEntry(title, body, entryDateTime, subject, status);
            Toast.makeText(activity_devDbEntryEdit.this, "Entry Created", Toast.LENGTH_SHORT).show();
            if (id > 0) {
                mRowId = id;
            }
        } else {
            mDbHelper.updateEntry(mRowId, title, body, entryDateTime, subject, status);
            Toast.makeText(activity_devDbEntryEdit.this, "Entry Updated", Toast.LENGTH_SHORT).show();
        }

    }

}
