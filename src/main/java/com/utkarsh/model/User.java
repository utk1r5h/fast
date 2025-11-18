package com.utkarsh.model;
public class User{
    protected String username;
    protected String userType;
    public User(String username, String userType){
        this.username = username;
        this.userType = userType;
    }
    public String getUsername(){
        return username;
    }
    public String getUserType(){
        return userType;
    }
    public boolean canEditTask(){
        return false;
    }
    public boolean canDeleteTask(){
        return false;
    }
}
