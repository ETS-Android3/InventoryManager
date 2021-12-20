package com.snhu.p2_guilherme_pereira;

import java.util.ArrayList;

public class UserModel {
    private int id;
    private String username;
    private String password;

    /*** Constructors ***/
    public UserModel(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public UserModel(){

    }

    /*** Setters & Getters ***/
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /*** Functions ***/

    @Override
    public String toString(){
        return  "[ User Model ]\n" +
                "[ ID: " + getId() + " ]\n" +
                "[ Name: " + getUsername() + " ]\n" +
                "[ Pass: " + getPassword() + " ]";
    }
}
