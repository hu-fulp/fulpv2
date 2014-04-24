package org.teamfulp.fulp.app.tasks.income;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.teamfulp.fulp.app.domain.Income;
import org.teamfulp.fulp.app.domain.User;
import org.teamfulp.fulp.app.listeners.WebserviceListener;
import org.teamfulp.fulp.app.tasks.WebserviceRequestTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by royfokker on 02-04-14.
 */

public class ListIncomeTask extends WebserviceRequestTask {

    private final WebserviceListener listener;

    public ListIncomeTask(WebserviceListener listener, User user) {
        super("Income", user.getToken(), String.valueOf(user.getId()));
        this.listener = listener;
    }

    @Override
    protected void onPostExecute(String sJson) {
        if(sJson == null) {
            if(listener != null) listener.onFailure(msg);
            return;
        }

        List<Income> output = new ArrayList<Income>();

        try {
            JSONObject json = new JSONObject(sJson);
            JSONArray items = json.getJSONArray("items");


            for(int i = 0; i < items.length(); i++) {
                JSONObject o =  items.getJSONObject(i);

                Income income = new Income();
                income.setType(o.getString("type"));
                income.setInterval(o.getString("interval"));
                income.setName(o.getString("name"));
                income.setAmount(o.getDouble("amount"));
                income.setStart(o.getString("start"));
                income.setEnd(o.getString("end"));

                output.add(income);
            }

        }
        catch(JSONException e) {
            msg = "Invalid response";
            if(listener != null) listener.onFailure(msg);
            return;
        }

        if(listener != null) listener.onComplete(output);
    }
}
