package org.teamfulp.fulp.app.listeners;

import java.util.List;

/**
 * Created by royfokker on 02-04-14.
 */
public interface WebserviceListener {
    public void onComplete(List<?> data);
    public void onFailure(String msg);
}
