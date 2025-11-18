package com.utkarsh.model;
public class PowerUser extends AdminUser{
    public PowerUser(String username){
        super(username);
        this.userType = "POWER";
    }
    @Override
    public boolean canManageUsers(){
        return false;
    }
    public boolean canBatchEdit(){
        return true;
    }
}
