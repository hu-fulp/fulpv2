package org.teamfulp.fulp.app.fragments;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment 
{
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		
		// Create a new instance of DatePickerDialog and return it

        Fragment fragment = getTargetFragment();
		return new DatePickerDialog(getActivity(), (OnDateSetListener)getTargetFragment(), year, month, day);
	}
}