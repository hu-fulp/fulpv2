package org.teamfulp.fulp.app.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.teamfulp.fulp.app.R;
import org.teamfulp.fulp.app.domain.Account;
import org.teamfulp.fulp.app.domain.User;
import org.teamfulp.fulp.app.fragments.AddIncomeFragment;
import org.teamfulp.fulp.app.fragments.AddInsuranceFragment;
import org.teamfulp.fulp.app.fragments.AddSubscriptionFragment;
import org.teamfulp.fulp.app.fragments.DashboardFragment;
import org.teamfulp.fulp.app.fragments.SummaryIncomeFragment;
import org.teamfulp.fulp.app.fragments.SummaryInsuranceFragment;
import org.teamfulp.fulp.app.fragments.SummarySubscriptionFragment;
import org.teamfulp.fulp.app.listeners.DateClickListener;
import org.teamfulp.fulp.app.listeners.WebserviceListener;
import org.teamfulp.fulp.app.tasks.user.UserSwitchAccountTask;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity implements WebserviceListener {
	
	private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mMenuItems;

    private User user;
	
    //Fragment
    private AddIncomeFragment addIncome;
    private AddInsuranceFragment addInsurance;
    private AddSubscriptionFragment addSubscription;
    private DashboardFragment dashboard;
    private SummaryIncomeFragment summaryIncome;
    private SummaryInsuranceFragment summaryInsurance;
    private SummarySubscriptionFragment summarySubscription;

    private RelativeLayout mDrawerRelative;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mTitle = mDrawerTitle = getTitle();
		mMenuItems = getResources().getStringArray(R.array.nav_drawer_items);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerRelative = (RelativeLayout) findViewById(R.id.slide_layout);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, mMenuItems));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        
        // enable ActionBar app icon to behave as action to toggle nav drawer

        getActionBar().setIcon(R.drawable.logo_small);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);


        Intent i = getIntent();
        user = (User)i.getSerializableExtra("user");

        if(user != null) {
            ((TextView)findViewById(R.id.label_current_user)).setText(user.getName());
            ((TextView)findViewById(R.id.label_current_account)).setText(user.getCurrentAccount().getName());
        }

        final MainActivity a = this;


        final TextView switchUserLabel = (TextView)findViewById(R.id.label_switch_user);
        switchUserLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder b = new AlertDialog.Builder(a);

                AlertDialog.Builder builder = new AlertDialog.Builder(a);
                builder.setTitle("Wissel van account");
                final ArrayAdapter<Account> arrayAdapter = new ArrayAdapter<Account>(a, android.R.layout.select_dialog_singlechoice);

                List<Account> accounts = user.getAccounts();

                for(Account a : accounts) {
                    arrayAdapter.add(a);
                }

                builder.setAdapter(arrayAdapter,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Account selectedAccount = arrayAdapter.getItem(which);

                                Toast.makeText(a.getApplicationContext(), String.valueOf(selectedAccount.getId()), Toast.LENGTH_LONG).show();

                                a.getUser().setCurrentAccount(selectedAccount);

                                UserSwitchAccountTask userSwitchAccountTask = new UserSwitchAccountTask(a, a.getUser());
                                userSwitchAccountTask.execute();
                            }
                        });
                builder.show();


            }
        });

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer1,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerRelative);
        
        return super.onPrepareOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch(item.getItemId()) {
        
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    public User getUser() {
        return this.user;
    }

    @Override
    public void onBackPressed() {
        finish();//go back to the previous Activity
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }
    
    private void selectItem(int position) {
        // update the main content by replacing fragments
        
        FragmentManager fragmentManager = getFragmentManager();
        switch(position){
        		//dashboard
        	case 0:
        		dashboard = new DashboardFragment();
        		fragmentManager.beginTransaction().replace(R.id.content_frame, dashboard).commit();
        		break;
        	case 1:
        		//Income
                summaryIncome = new SummaryIncomeFragment();
                fragmentManager.beginTransaction().replace(R.id.content_frame, summaryIncome).commit();

                /* Old reference, delete later */
                // addIncome = new AddIncomeFragment();
                // dateSetListener = addIncome;
                // dateSelectionListener = addIncome;
                // fragmentManager.beginTransaction().replace(R.id.content_frame, addIncome).commit();

        		break;
        	case 2:
        		//Insurance
                summaryInsurance = new SummaryInsuranceFragment();
                fragmentManager.beginTransaction().replace(R.id.content_frame, summaryInsurance).commit();

                /* Old reference, delete later */
        		// addInsurance = new AddInsuranceFragment();
                // dateSetListener = addInsurance;
                // dateSelectionListener = addInsurance;
        		// fragmentManager.beginTransaction().replace(R.id.content_frame, addInsurance).commit();

        		break;
            case 3:
                //Subscription
                summarySubscription = new SummarySubscriptionFragment();
                fragmentManager.beginTransaction().replace(R.id.content_frame, summarySubscription).commit();

                /* Old reference, delete later */
                // addSubscription = new AddSubscriptionFragment();
                // dateSetListener = addSubscription;
                // dateSelectionListener = addSubscription;
                // fragmentManager.beginTransaction().replace(R.id.content_frame, addSubscription).commit();

                break;
        	default:
        		dashboard = new DashboardFragment();
        		fragmentManager.beginTransaction().replace(R.id.content_frame, dashboard).commit();
        		break;
        }
        
        
    	 
        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mMenuItems[position]);
        mDrawerLayout.closeDrawer(mDrawerRelative);
    }
    
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);

    }
    
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onComplete(List<?> data) {

    }

    @Override
    public void onFailure(String msg) {

    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }
}
