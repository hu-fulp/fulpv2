package org.teamfulp.fulp.app.domain;

import android.os.Build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by royfokker on 03-04-14.
 */
public class User implements Serializable {

    private String email;
    private String password;
    private String name;
    private String androidId;
    private List<Account> accounts;
    private Account currentAccount;
    private int id;
    private String token;

    public User() {
        this.accounts = new ArrayList<Account>();
        this.setAndroidId(Build.MODEL);
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAndroidId() {
        return androidId;
    }

    public void setAndroidId(String androidId) {
        this.androidId = androidId;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }

    public void setCurrentAccount(Account currentAccount) {
        this.currentAccount = currentAccount;
    }
}
