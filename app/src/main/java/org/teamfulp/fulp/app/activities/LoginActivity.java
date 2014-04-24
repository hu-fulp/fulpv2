package org.teamfulp.fulp.app.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.teamfulp.fulp.app.R;
import org.teamfulp.fulp.app.domain.User;
import org.teamfulp.fulp.app.listeners.WebserviceListener;
import org.teamfulp.fulp.app.tasks.WebserviceRequestTask;
import org.teamfulp.fulp.app.tasks.user.UserLoginTask;

import java.util.List;

public class LoginActivity extends Activity implements WebserviceListener{

    private WebserviceListener listener;
    private User user;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        listener = this;
        Button submit = (Button) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                user = new User();
                user.setEmail(((EditText) findViewById(R.id.email)).getText().toString());
                user.setPassword(((EditText) findViewById(R.id.password)).getText().toString());

                WebserviceRequestTask userLoginTask = new UserLoginTask(listener, user);
                userLoginTask.execute();

                pDialog = ProgressDialog.show((Context) listener, "", "Inloggen...");
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onComplete(List<?> data) {
        if(pDialog != null)  pDialog.dismiss();

        Toast.makeText(this, "Welkom12 "  + user.getName(), Toast.LENGTH_LONG).show();
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("user", user);
        startActivity(i);

        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    @Override
    public void onFailure(String msg) {
        if(pDialog != null)  pDialog.dismiss();
        Toast.makeText(this, "Inloggen niet gelukt", Toast.LENGTH_LONG).show();
    }



}
