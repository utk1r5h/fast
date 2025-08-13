package com.utkarsh;
import java.util.List;
import picocli.CommandLine.Command;

@Command(name="list", description = "List all Pending Tasks")

public class ListCommand implements Runnable{

  @Override
  public void run(){
    TaskManager manager = new TaskManager();
    List<Task> tasks= manager.getTasks();

     System.out.println("--- Your Tasks ---");

     for(Task task: tasks){
      // onle the ones that are still left to do

      if(task.getStatus()==Status.TODO){
        System.out.printf("[%d] %s%n", task.getId(), task.getDescription());


      }
     }
      System.out.println("------------------");

  }

  
}
