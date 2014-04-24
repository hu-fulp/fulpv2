package org.teamfulp.fulp.app.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import org.teamfulp.fulp.app.R;
import org.teamfulp.fulp.app.activities.MainActivity;
import org.teamfulp.fulp.app.domain.Income;
import org.teamfulp.fulp.app.helpers.LoginSession;
import org.teamfulp.fulp.app.listeners.DateClickListener;
import org.teamfulp.fulp.app.listeners.WebserviceListener;
import org.teamfulp.fulp.app.tasks.WebserviceRequestTask;
import org.teamfulp.fulp.app.tasks.income.CreateIncomeTask;

import java.util.List;

public class AddIncomeFragment extends Fragment implements OnItemSelectedListener, WebserviceListener {

	private String mSelectedInkomstenType;
	private String mSelectedInterval;
	private View mRootView;
	private EditText mStartDateEdit;
    private EditText mEndDateEdit;
    private WebserviceListener webserviceListener;
    private DateClickListener startDateClickListener;
    private DateClickListener endDateClickListener;
	
	public AddIncomeFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	mRootView = inflater.inflate(R.layout.add_income, container, false);
        
        getActivity().setTitle(R.string.add_income_title);
        setupView();

        //Save button
        Button save = (Button)mRootView.findViewById(R.id.income_save_button);
        webserviceListener = this;

        mStartDateEdit = (EditText)mRootView.findViewById(R.id.add_income_startdate_edit);
        startDateClickListener = new DateClickListener(getActivity(), this, mStartDateEdit);
        mStartDateEdit.setOnClickListener(startDateClickListener);
        mEndDateEdit = (EditText)mRootView.findViewById(R.id.add_income_enddate_edit);
        endDateClickListener = new DateClickListener(getActivity(), this, mEndDateEdit);
        mEndDateEdit.setOnClickListener(endDateClickListener);


        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Income income = createIncome();
                if(income != null){
                    WebserviceRequestTask createIncomeTask = new CreateIncomeTask(webserviceListener, income, LoginSession.getInstance().getUser());
                    createIncomeTask.execute();
                }
            }
        });
        return mRootView;
    }

    private void setupView() {
        //Setup spinners
        //Incometype spinner
        Spinner incomeTypeSpinner = (Spinner)mRootView.findViewById(R.id.add_income_type_spinner);
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.income_types, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        incomeTypeSpinner.setAdapter(typeAdapter);
        incomeTypeSpinner.setOnItemSelectedListener(this);
        //Interval spinner
        Spinner intervalSpinner = (Spinner)mRootView.findViewById(R.id.add_income_interval_spinner);
        ArrayAdapter<CharSequence> intervalAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.intervals, android.R.layout.simple_spinner_item);
        intervalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        intervalSpinner.setAdapter(intervalAdapter);
        intervalSpinner.setOnItemSelectedListener(this);
    }

    private Income createIncome() {
        String name = ((EditText)mRootView.findViewById(R.id.add_income_description)).getText().toString();
        String amountText = ((EditText) mRootView.findViewById(R.id.add_income_amount)).getText().toString();
        Double amount;
        if(!amountText.equals("")){
            amount = Double.parseDouble(amountText);
        }
        else{
            amount = 0.0;
        }
        String type = ((Spinner)mRootView.findViewById(R.id.add_income_type_spinner)).getSelectedItem().toString();
        String interval = ((Spinner)mRootView.findViewById(R.id.add_income_interval_spinner)).getSelectedItem().toString();
        String start = ((EditText)mRootView.findViewById(R.id.add_income_startdate_edit)).getText().toString();
        String end = ((EditText)mRootView.findViewById(R.id.add_income_enddate_edit)).getText().toString();
        try{
            Income income = new Income();
            income.setName(name);
            income.setAmount(amount);
            income.setType(type);
            income.setInterval(interval);
            income.setStart(start);
            income.setEnd(end);
            return income;
        }
        catch(IllegalArgumentException iae){
            Toast toast = Toast.makeText(getActivity(), iae.getMessage(), Toast.LENGTH_SHORT);
            toast.show();
        }
        return null;
    }

    @Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    	Spinner spinner = (Spinner) parent;
    	if(spinner.getId() == R.id.add_income_type_spinner){
    		this.mSelectedInkomstenType = parent.getItemAtPosition(position).toString();
    	}
    	else if(spinner.getId() == R.id.add_income_interval_spinner){
    		this.mSelectedInterval = parent.getItemAtPosition(position).toString();
    	}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

    @Override
    public void onComplete(List<?> data) {
        Toast.makeText(getActivity(), data.get(0).toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailure(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }
}
