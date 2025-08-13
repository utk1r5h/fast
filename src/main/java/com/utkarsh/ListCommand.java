package com.utkarsh;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_FixedWidth;
import picocli.CommandLine.Command;

@Command(name="list", description = "List all Pending Tasks")

public class ListCommand implements Runnable{

  @Override
  public void run(){
    TaskManager manager = new TaskManager();
    List<Task> tasks= manager.getTasks().stream().filter(task->task.getStatus()==Status.TODO).collect(Collectors.toList());

    if(tasks.isEmpty()){
       System.out.println("no pending tasks");      
       return;
    }

    AsciiTable at = new AsciiTable();

    // code to get varaiable width of blocks 

    at.getRenderer().setCWC(new CWC_FixedWidth().add(4).add(30).add(18));

    
      at.addRule();
      at.addRow("ID","DESCRIPTION","CREATED-ON");
      at.addRule();

      
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

      for(Task task: tasks){
        at.addRow(task.getId(),task.getDescription(), task.getCreationDate().format(formatter));

        at.addRule();
      }

      System.out.println(at.render());




  

  }

  
}
