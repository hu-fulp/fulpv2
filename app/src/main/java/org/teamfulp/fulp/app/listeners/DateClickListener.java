package org.teamfulp.fulp.app.listeners;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class DateClickListener implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private Activity activity;
    private Fragment fragment;
    private EditText editText;

    public DateClickListener(Activity activity, Fragment fragment, EditText editText) {
        this.activity = activity;
        this.fragment = fragment;
        this.editText = editText;
    }

    @Override
    public void onClick(View view) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        new DatePickerDialog(activity, this, year, month, day).show();

        //DialogFragment newFragment = new DatePickerFragment();
        //newFragment.setTargetFragment(fragment, 1);
        //newFragment.show(fragment.getFragmentManager(), "datePicker");
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        monthOfYear++;
        editText.setText("" + year + "-" + monthOfYear + "-" + dayOfMonth);
    }
}
