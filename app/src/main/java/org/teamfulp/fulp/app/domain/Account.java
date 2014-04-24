package org.teamfulp.fulp.app.domain;

import java.io.Serializable;

/**
 * Created by royfokker on 03-04-14.
 */
public class Account implements Serializable {

    private String name;
    private User user;
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString() {
        return name;
    }
}
