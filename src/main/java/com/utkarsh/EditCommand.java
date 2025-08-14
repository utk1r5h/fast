package com.utkarsh;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "edit", description= "edit an already added Command") 


public class EditCommand implements Runnable {

  @Parameters(index="0", description= "The ID of the task to edit")
  private long taskID;

  @Parameters(index="1", description="The edited task")
  private String newdescription;

  @Override
  public void run(){
    TaskManager manager = new TaskManager();
    
    manager.editTask(taskID,newdescription);
  }

  
}
 