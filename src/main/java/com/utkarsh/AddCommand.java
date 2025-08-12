package com.utkarsh;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "add", description = "Add a new task to the list.")

public class AddCommand implements Runnable{
  @Parameters
  (index="0",description = "The Description of the new task")
  private String description;
  @Override                
  public void run(){
    System.out.println("Adding a new task:"+ description );

  
  }

}