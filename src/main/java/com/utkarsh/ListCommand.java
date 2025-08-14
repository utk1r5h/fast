package com.utkarsh;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_FixedWidth;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name="list", description = "List all Pending Tasks")

public class ListCommand implements Runnable{
  // this will help in showing 
    // the tasks that have been completed as well
    @Option(names={"-a", "--all"}, description="Show all tasks, included the completed one")
    private boolean showALL = false;


  @Override
  public void run(){


    TaskManager manager = new TaskManager();

    List<Task> allTasks= manager.getTasks();
    List<Task> taskstoShow;

    if(showALL){
      taskstoShow=allTasks;
    }else{
      taskstoShow=allTasks.stream().filter(task->task.getStatus()==Status.TODO).collect(Collectors.toList());
    }

        if (taskstoShow.isEmpty()) {
            System.out.println("No tasks to show.");
            return;
        }


    AsciiTable at = new AsciiTable();

    // code to get varaiable width of blocks 

    at.getRenderer().setCWC(new CWC_FixedWidth().add(4).add(30).add(18));

    
      at.addRule();
      at.addRow("ID","DESCRIPTION","CREATED-ON");
      at.addRule();

      
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

      for(Task task: taskstoShow){
        at.addRow(task.getId(),task.getDescription(), task.getCreationDate().format(formatter));

        at.addRule();
      }

      System.out.println(at.render());




  

  }

  
}
