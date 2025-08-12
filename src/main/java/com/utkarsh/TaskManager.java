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
      }
