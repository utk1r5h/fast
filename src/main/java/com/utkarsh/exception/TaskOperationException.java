package com.utkarsh.exception;
import java.io.IOException;
public class TaskOperationException extends Exception{
    public TaskOperationException(String message, IOException cause){
        super(message, cause);
    }
    public TaskOperationException(String message){
        super(message);
    }
}
