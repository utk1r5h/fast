package com.utkarsh.model;
public class AdminUser extends User{
    public AdminUser(String username){
        super(username, "ADMIN");
    }
    @Override
    public boolean canEditTask(){
        return true;
    }
    @Override
    public boolean canDeleteTask(){
        return true;
    }
    public boolean canManageUsers(){
        return true;
    }
}
