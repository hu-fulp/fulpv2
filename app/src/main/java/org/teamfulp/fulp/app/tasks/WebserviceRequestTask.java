package org.teamfulp.fulp.app.tasks;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by royfokker on 02-04-14.
 */

public abstract class WebserviceRequestTask extends AsyncTask<String, Void, String> {

    protected String msg;
    protected Map<String, String> parameters = new HashMap<String, String>();
    protected String endpoint = "http://149.210.161.130/fulp_webservice/public/1/";
    private String resource;
    private String authUser;
    private String authPassword;
    protected abstract void onPostExecute(String sJson);

    public WebserviceRequestTask(String resource) {
        this.resource = resource;
        this.authUser = "test";
        this.authPassword = "password";
    }

    public WebserviceRequestTask(String resource, String authUser, String authPassword) {
        this.resource = resource;
        this.authUser = authUser;
        this.authPassword = authPassword;
    }

    @Override
    protected String doInBackground(String... args) {
        String url = this.endpoint + this.resource;

        if(this.resource == null) return null;

        try {
            Log.d("test", url);

            DefaultHttpClient httpClient = new DefaultHttpClient();
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            HttpPost postMethod = new HttpPost(url);

            List<NameValuePair> params = new LinkedList<NameValuePair>();

            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }

            postMethod.setEntity(new UrlEncodedFormEntity(params));
            String encoded = Base64.encodeToString((authUser + ":" + authPassword).getBytes(), Base64.NO_WRAP);
            postMethod.setHeader("Authorization", "Basic " + encoded);
            String output = httpClient.execute(postMethod,responseHandler);

            Log.d("API response: ", output);

            return output;
        }
        catch(IOException e){
            e.printStackTrace();
            msg = "Foutje: "  + e.getMessage() + " url = " + url;
        }

        return null;
    }


    /**
     * This function will convert response stream into json string
     * @param is respons string
     * @return json string
     * @throws java.io.IOException
     */
    public String streamToString(final InputStream is) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;

        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        }
        catch (IOException e) {
            throw e;
        }
        finally {
            try {
                is.close();
            }
            catch (IOException e) {
                throw e;
            }
        }

        return sb.toString();
    }
}
