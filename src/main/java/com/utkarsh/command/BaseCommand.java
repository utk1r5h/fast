package com.utkarsh.command;
import com.utkarsh.TaskManager;
public abstract class BaseCommand implements Runnable{
    protected TaskManager manager;
    public BaseCommand(){
        this.manager = new TaskManager();
    }
    protected abstract void validate() throws Exception;
    protected abstract void execute();
    @Override
    public void run(){
        try{
            validate();
            execute();
        }catch(Exception e){
            System.err.println("❌ " + e.getMessage());
        }
    }
}
