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

public class UserLoginTask extends WebserviceRequestTask {

    private final WebserviceListener listener;
    private User user;

    public UserLoginTask(WebserviceListener listener, User user) {
        super("User/login", user.getEmail(), user.getPassword());

        this.user = user;

        this.listener = listener;

        parameters.put("android_device_id", user.getAndroidId());
    }

    @Override
    protected void onPostExecute(String sJson) {
        if(sJson == null) {
            if(listener != null) listener.onFailure(msg);
            return;
        }

        try {
            JSONObject json = new JSONObject(sJson);

            JSONObject auth = json.getJSONObject("auth");
            user.setToken(auth.getString("token"));
            user.setId(auth.getInt("user_id"));

            JSONObject userObject = json.getJSONObject("user");
            user.setName(userObject.getString("name"));

            JSONObject currentAccountObject = json.getJSONObject("current_account");
            Account account = new Account();
            account.setId(currentAccountObject.getInt("id"));
            account.setName(currentAccountObject.getString("name"));
            account.setUser(user);

            user.setCurrentAccount(account);


            JSONArray accounts = json.getJSONArray("Account");

            for(int i = 0; i < accounts.length(); i++) {
                JSONObject o =  accounts.getJSONObject(i);

                account = new Account();
                account.setId(o.getInt("id"));
                account.setName(o.getString("name"));

                user.addAccount(account);
            }
         }
        catch(JSONException e) {
            msg = "Invalid response";
            if(listener != null) listener.onFailure(msg);
            return;
        }

        if(listener != null) listener.onComplete(null);
    }
}
