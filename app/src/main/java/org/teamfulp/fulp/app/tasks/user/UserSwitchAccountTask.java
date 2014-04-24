package org.teamfulp.fulp.app.tasks.user;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.teamfulp.fulp.app.domain.Account;
import org.teamfulp.fulp.app.domain.User;
import org.teamfulp.fulp.app.listeners.WebserviceListener;
import org.teamfulp.fulp.app.tasks.WebserviceRequestTask;

/**
 * Created by royfokker on 02-04-14.
 */

public class UserSwitchAccountTask extends WebserviceRequestTask {

    private final WebserviceListener listener;
    private User user;

    public UserSwitchAccountTask(WebserviceListener listener, User user) {
        super("User/switch", user.getToken(), String.valueOf(user.getId()));

        this.user = user;
        this.listener = listener;

        parameters.put("account_id", String.valueOf(user.getCurrentAccount().getId()));
    }

    @Override
    protected void onPostExecute(String sJson) {
        if(sJson == null) {
            if(listener != null) listener.onFailure(msg);
            return;
        }
    /*
        try {
            // TO DO
            // if success then load new account....reload function... etc..

        }
        catch(JSONException e) {
            msg = "Invalid response";
            if(listener != null) listener.onFailure(msg);
            return;
        }
*/
        if(listener != null) listener.onComplete(null);
    }
}
