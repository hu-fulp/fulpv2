package org.teamfulp.fulp.app.tasks.insurance;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.teamfulp.fulp.app.domain.Insurance;
import org.teamfulp.fulp.app.domain.User;
import org.teamfulp.fulp.app.listeners.WebserviceListener;
import org.teamfulp.fulp.app.tasks.WebserviceRequestTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by royfokker on 02-04-14.
 */

public class ListInsuranceTask extends WebserviceRequestTask {

    private final WebserviceListener listener;

    public ListInsuranceTask(WebserviceListener listener, User user) {
        super("Insurance", user.getToken(), String.valueOf(user.getId()));
        this.listener = listener;
    }

    @Override
    protected void onPostExecute(String sJson) {
        if(sJson == null) {
            if(listener != null) listener.onFailure(msg);
            return;
        }

        List<Insurance> output = new ArrayList<Insurance>();

        try {
            JSONObject json = new JSONObject(sJson);
            JSONArray items = json.getJSONArray("items");


            for(int i = 0; i < items.length(); i++) {
                JSONObject o =  items.getJSONObject(i);

                Insurance insurance = new Insurance();
                insurance.setStart(o.getString("start"));
                insurance.setEnd(o.getString("end"));
                insurance.setAmount(o.getDouble("amount"));
                insurance.setCategory(o.getString("category"));
                insurance.setInterval(o.getString("interval"));
                insurance.setName(o.getString("name"));

                output.add(insurance);
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
