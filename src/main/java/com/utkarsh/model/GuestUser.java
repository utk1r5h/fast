package com.utkarsh.model;
public class GuestUser extends User{
    public GuestUser(String username){
        super(username, "GUEST");
    }
    @Override
    public boolean canEditTask(){
        return false;
    }
    @Override
    public boolean canDeleteTask(){
        return false;
    }
}
