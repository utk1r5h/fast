package com.utkarsh;

import picocli.CommandLine.Command;

@Command(name ="clear", description="clears all completed tasks from the list")

public class ClearCommand implements Runnable {
  @Override
  public void run(){
    TaskManager manager = new TaskManager();
    manager.clearDoneTasks();

  }
  
}
