package org.teamfulp.fulp.app.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import org.teamfulp.fulp.app.R;
import org.teamfulp.fulp.app.domain.Subscription;
import org.teamfulp.fulp.app.listeners.DateClickListener;
import org.teamfulp.fulp.app.listeners.WebserviceListener;
import org.teamfulp.fulp.app.tasks.WebserviceRequestTask;
import org.teamfulp.fulp.app.tasks.subscriptions.CreateSubscriptionTask;

import java.util.List;


/**
 * Created by roystraub on 02-04-14.
 */
public class AddSubscriptionFragment extends Fragment implements AdapterView.OnItemSelectedListener, WebserviceListener {
    private View mRootView;
    private EditText mStartDateEdit;
    private EditText mEndDateEdit;
    private DateClickListener startDateClickListener;
    private DateClickListener endDateClickListener;
    private String mSelectedCategory;
    private String mSelectedInterval;
    private WebserviceListener webserviceListener;

    public AddSubscriptionFragment(){
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.add_subscription, container, false);

        getActivity().setTitle(R.string.add_subscription_title);
        setupView();
        webserviceListener = this;

        mStartDateEdit = (EditText)mRootView.findViewById(R.id.add_subscription_startdate);
        startDateClickListener = new DateClickListener(getActivity(), this, mStartDateEdit);
        mStartDateEdit.setOnClickListener(startDateClickListener);
        mEndDateEdit = (EditText)mRootView.findViewById(R.id.add_subscription_enddate);
        endDateClickListener = new DateClickListener(getActivity(), this, mEndDateEdit);
        mEndDateEdit.setOnClickListener(endDateClickListener);

        //Save button
        Button save = (Button)mRootView.findViewById(R.id.add_subscription_save);
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Subscription subscription = createSubscription();
                if(subscription != null){
                    WebserviceRequestTask createSubscriptionTask = new CreateSubscriptionTask(webserviceListener, subscription);
                    createSubscriptionTask.execute();
                }
            }
        });
        return mRootView;
    }

    private void setupView() {
        //Setup spinners
        //Category spinner
        Spinner categorySpinner = (Spinner)mRootView.findViewById(R.id.add_subscription_category_spinner);
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.subscription_categories, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);
        categorySpinner.setOnItemSelectedListener(this);
        //Interval spinner
        Spinner intervalSpinner = (Spinner)mRootView.findViewById(R.id.add_subscription_interval_spinner);
        ArrayAdapter<CharSequence> intervalAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.intervals, android.R.layout.simple_spinner_item);
        intervalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        intervalSpinner.setAdapter(intervalAdapter);
        intervalSpinner.setOnItemSelectedListener(this);
    }

    private Subscription createSubscription(){
        String name = ((EditText)mRootView.findViewById(R.id.add_subscription_name)).getText().toString();
        String category = ((Spinner)mRootView.findViewById(R.id.add_subscription_category_spinner)).getSelectedItem().toString();
        String interval = ((Spinner)mRootView.findViewById(R.id.add_subscription_interval_spinner)).getSelectedItem().toString();
        String amountText = ((EditText)mRootView.findViewById(R.id.add_subscription_amount)).getText().toString();
        Double amount;
        if(amountText == "")
            amount = 0.0;
        else
            amount = Double.parseDouble(amountText);
        String start = ((EditText)mRootView.findViewById(R.id.add_subscription_startdate)).getText().toString();
        String end = ((EditText)mRootView.findViewById(R.id.add_subscription_enddate)).getText().toString();
        try{
            Subscription subscription = new Subscription();
            subscription.setName(name);
            subscription.setCategory(category);
            subscription.setInterval(interval);
            subscription.setAmount(amount);
            subscription.setStart(start);
            subscription.setEnd(end);
            return subscription;
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
        if(spinner.getId() == R.id.add_subscription_category_spinner){
            this.mSelectedCategory = parent.getItemAtPosition(position).toString();
        }
        else if(spinner.getId() == R.id.add_subscription_interval_spinner){
            this.mSelectedInterval = parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
