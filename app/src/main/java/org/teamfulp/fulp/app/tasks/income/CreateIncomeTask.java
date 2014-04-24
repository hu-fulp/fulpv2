package org.teamfulp.fulp.app.tasks.income;

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

public class CreateIncomeTask extends WebserviceRequestTask {

    private final WebserviceListener listener;

    public CreateIncomeTask(WebserviceListener listener, Income income, User user) {
        super("Income/create", user.getToken(), String.valueOf(user.getId()));
        this.listener = listener;

        parameters.put("name", income.getName());
        parameters.put("start", income.getStart());
        parameters.put("end", income.getEnd());
        parameters.put("interval", income.getInterval());
        parameters.put("amount", String.valueOf(income.getAmount()));
        parameters.put("type", income.getType());


    }

    @Override
    protected void onPostExecute(String sJson) {
        if(sJson == null) {
            if(listener != null) listener.onFailure(msg);
            return;
        }

        List<String> output = new ArrayList<String>();

        try {
            JSONObject json = new JSONObject(sJson);

            output.add(json.getString("type"));
            output.add(json.getString("message"));

        }
        catch(JSONException e) {
            msg = "Invalid response";
            if(listener != null) listener.onFailure(msg);
            return;
        }

        if(listener != null) listener.onComplete(output);
    }
}
