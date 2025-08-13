package com.utkarsh;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
@Command(name= "done", description = "Mark a task as complete")


public class DoneCommand implements Runnable {
  @Parameters(index ="0",description = "The ID of the task to mark as done")
  private long taskID;

  @Override
  public void run(){
    TaskManager manager = new TaskManager();
    manager.markTaskAsDone(taskID);
    
  }


  
}
