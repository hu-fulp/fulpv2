package org.teamfulp.fulp.app.tasks.insurance;


import org.json.JSONException;
import org.json.JSONObject;
import org.teamfulp.fulp.app.domain.Insurance;
import org.teamfulp.fulp.app.listeners.WebserviceListener;
import org.teamfulp.fulp.app.tasks.WebserviceRequestTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by royfokker on 02-04-14.
 */

public class CreateInsuranceTask extends WebserviceRequestTask {

    private final WebserviceListener listener;

    public CreateInsuranceTask(WebserviceListener listener, Insurance insurance) {
        super("Insurance/create");
        this.listener = listener;

        parameters.put("name", insurance.getName());
        parameters.put("amount", String.valueOf(insurance.getAmount()));
        parameters.put("start", insurance.getStart());
        parameters.put("interval", insurance.getInterval());
        parameters.put("end", insurance.getEnd());
        parameters.put("category", insurance.getCategory());
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
