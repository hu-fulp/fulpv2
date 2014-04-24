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

import org.teamfulp.fulp.app.R;


/**
 * Created by Daantie on 4-4-14.
 */
public class SummaryInsuranceFragment extends Fragment {
    private View mRootView;

    public SummaryInsuranceFragment() {
        // Empty constructor required for fragment subclasses
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.summary, container, false);
        getActivity().setTitle("Verzekeringen overzicht");
        setHasOptionsMenu(true);

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
                AddInsuranceFragment addInsuranceFragment = new AddInsuranceFragment();
                transaction.replace(R.id.content_frame, addInsuranceFragment);
                transaction.commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}