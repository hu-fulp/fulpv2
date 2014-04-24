package org.teamfulp.fulp.app.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.teamfulp.fulp.app.R;
import org.teamfulp.fulp.app.activities.MainActivity;
import org.teamfulp.fulp.app.adapters.IncomeAdapter;
import org.teamfulp.fulp.app.domain.Income;
import org.teamfulp.fulp.app.listeners.WebserviceListener;
import org.teamfulp.fulp.app.tasks.income.ListIncomeTask;

import java.util.List;

public class SummaryIncomeFragment extends Fragment implements WebserviceListener {
    private View mRootView;
    private TextView title;

    public SummaryIncomeFragment() {
        // Empty constructor required for fragment subclasses
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.summary, container, false);
/*
        Button button = (Button)mRootView.findViewById(R.id.add_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                //addInsurance(v);
            }
        });*/


        ListIncomeTask listIncomeTask = new ListIncomeTask(this, ((MainActivity)this.getActivity()).getUser());
        listIncomeTask.execute();

        getActivity().setTitle("Inkomen overzicht");
        setHasOptionsMenu(true);
        title = (TextView)mRootView.findViewById(R.id.summary_title);
        title.setText(getString(R.string.income_summary_title));
        return mRootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.summary, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_add:
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                AddIncomeFragment addIncomeFragment = new AddIncomeFragment();
                transaction.replace(R.id.content_frame, addIncomeFragment);
                transaction.commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onComplete(List<?> data) {
        Income[] incomes = new Income[data.size()];
        for(int i = 0; i < data.size(); i++){
            Income income = (Income) data.get(i);
            incomes[i] = income;
        }
        title.setText(getString(R.string.income_summary_title) + "("+ data.size() +")");
        ListView list = (ListView)mRootView.findViewById(R.id.summary_list);
        list.setAdapter(new IncomeAdapter(getActivity(), R.layout.summary_list_item, incomes));
    }

    @Override
    public void onFailure(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }
}
