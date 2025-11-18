package com.utkarsh.exception;
public class TaskNotFoundException extends RuntimeException{
    public TaskNotFoundException(long id){
        super("Task #" + id + " not found. Use 'tm list' to see available tasks.");
    }
}
