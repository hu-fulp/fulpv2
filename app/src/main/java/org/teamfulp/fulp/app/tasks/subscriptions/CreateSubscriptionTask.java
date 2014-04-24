package org.teamfulp.fulp.app.tasks.subscriptions;


import org.json.JSONException;
import org.json.JSONObject;
import org.teamfulp.fulp.app.domain.Subscription;
import org.teamfulp.fulp.app.listeners.WebserviceListener;
import org.teamfulp.fulp.app.tasks.WebserviceRequestTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by royfokker on 02-04-14.
 */

public class CreateSubscriptionTask extends WebserviceRequestTask {

    private final WebserviceListener listener;

    public CreateSubscriptionTask(WebserviceListener listener, Subscription subscription) {
        super("Subscription/create");
        this.listener = listener;

        parameters.put("name", subscription.getName());
        parameters.put("amount", String.valueOf(subscription.getAmount()));
        parameters.put("start", subscription.getStart());
        parameters.put("interval", subscription.getInterval());
        parameters.put("end", subscription.getEnd());
        parameters.put("category", subscription.getCategory());
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
