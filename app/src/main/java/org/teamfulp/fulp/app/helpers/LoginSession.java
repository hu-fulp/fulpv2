package org.teamfulp.fulp.app.helpers;

import org.teamfulp.fulp.app.domain.User;

/**
 * Created by roystraub on 24-04-14.
 */
public class LoginSession {

    private static LoginSession instance;
    private User user;

    private LoginSession(){
    }

    public static LoginSession getInstance(){
        if(instance == null)
            instance = new LoginSession();
        return instance;
    }

    public void setUser(User user){
        this.user = user;
    }

    public User getUser(){
        return this.user;
    }
}
