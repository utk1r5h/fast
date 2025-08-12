package com.utkarsh;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "add", description = "Add a new task to the list.")

public class AddCommand implements Runnable{

  @Parameters
  (index="0",description = "The Description of the new task")
  // isme store kar deta he 
  private String description;

  @Override                
  public void run(){
    TaskManager manager= new TaskManager();
    manager.addTask(description);
    System.out.println("Task added: \"" + description + "\"");

  
  }

}