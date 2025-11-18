package com.utkarsh;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.utkarsh.exception.TaskNotFoundException;
public class TaskManager{
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
                tasks = mapper.readValue(file,  new TypeReference<List<Task>>() {});
            }catch(IOException e){
                System.err.println("error: could not load tasks from file");
                e.printStackTrace();
                tasks = new ArrayList<>();
            }
        }else{
            tasks = new ArrayList<>();
        }
    }
    public void saveTasks(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try{
            File file= new File(TASKS_FILE_PATH);
            file.getParentFile().mkdirs();
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, tasks);
        }catch(IOException e){
            System.err.println("Error: Could not save tasks to file.");
            e.printStackTrace();
        }
    }
    public void addTask(String description){
        long nextID=1;
        if(!this.tasks.isEmpty()){
            long maxID= this.tasks.stream()
                .mapToLong(Task::getId)
                .max()
                .orElse(0);
            nextID=maxID+1;
        }
        Task newTask = new Task(nextID, description, Status.TODO, java.time.LocalDateTime.now());
        this.tasks.add(newTask);
        this.saveTasks();
    }
    public List<Task> getTasks(){
        return this.tasks;
    }
    public void markTaskAsDone(long id){
        for(Task task: this.tasks){
            if(task.getId()==id){
                task.setStatus(Status.DONE);
                this.saveTasks();
                System.out.println("markes task" + id + "as done ");
                return;
            }
        }
        System.err.println("Could not finfn the task with the id" + id);
    }
    public void clearDoneTasks(){
        this.tasks.removeIf(tasks->tasks.getStatus()==Status.DONE);
        this.saveTasks();
        System.out.println("cleared all completed tasks");
    }
    public void editTask(long id, String newdescription){
        for(Task task: this.tasks){
            if(task.getId()==id){
                task.setDescription(newdescription);
                this.saveTasks();
                System.out.println("Edited task"+ id);
                return;
            }
        }
        System.err.println("Error: Could not find task with ID " + id);
    }
    public Set<String> getAllTags(){
        Set<String> allTags = new TreeSet<>();
        for(Task task : tasks){
            if(task.getTags() != null){
                allTags.addAll(task.getTags());
            }
        }
        return allTags;
    }
    public Task getTaskById(long id){
        return tasks.stream()
            .filter(t -> t.getId() == id)
            .findFirst()
            .orElseThrow(() -> new TaskNotFoundException(id));
    }
}
