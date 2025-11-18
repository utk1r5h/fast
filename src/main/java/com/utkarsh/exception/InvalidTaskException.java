package com.utkarsh.exception;
public class InvalidTaskException extends RuntimeException{
    public InvalidTaskException(String message){
        super("Invalid task: " + message);
    }
}
