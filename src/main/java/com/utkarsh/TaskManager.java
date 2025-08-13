package com.utkarsh;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


public class TaskManager {
  private List<Task> tasks;

  private static final String TASKS_FILE_PATH =System.getProperty("user.home")+ "/.taskmaster/tasks.json";


  public TaskManager(){
    this.loadTasks();

  }
  private void loadTasks(){
    File file = new File(TASKS_FILE_PATH);

    if(file.exists()){
      ObjectMapper mapper = new ObjectMapper();
      mapper.registerModule(new JavaTimeModule());

      try{
        // read the file and convert json into list 
        tasks = mapper.readValue(file,  new TypeReference<List<Task>>() {});

      } catch(IOException e) {
        System.err.println("error: could not load tasks from file");
        e.printStackTrace();
        // file corrupt he toh new banado
        tasks = new ArrayList<>();

      }
    } else{
        // aur agar new he user he toh fresh start

        tasks = new ArrayList<>();

    }




      
          
        }
  private void saveTasks(){
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());

    try{
      File file= new File(TASKS_FILE_PATH);

      // safety check to find parent file
      file.getParentFile().mkdirs();

      // write the tasks in a nice format
       mapper.writerWithDefaultPrettyPrinter().writeValue(file, tasks);

      

      
    }
    catch(IOException e){
      System.err.println("Error: Could not save tasks to file.");
      e.printStackTrace();
    }

  }


 public void addTask(String description){

  // determine a unique id for a task.
  // default to 1 if the list is empty 
  long nextID=1;
   
  if(!this.tasks.isEmpty()){
    long maxID= this.tasks.stream()
                          .mapToLong(Task::getId)
                          .max()
                          .orElse(0);
    
      nextID=maxID+1;
  }
  
  // create a new task object 

  Task newTask = new Task(nextID, description, Status.TODO, java.time.LocalDateTime.now());

  // in memory list mein
  this.tasks.add(newTask);

  //permanent list mein
  this.saveTasks();
 }

public List<Task> getTasks(){
  return this.tasks;
}


}
