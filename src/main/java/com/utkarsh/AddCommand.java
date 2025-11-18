package com.utkarsh;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;
import java.util.HashSet;
import java.util.Arrays;

@Command(name = "add", description = "Add a new task to the list.")
public class AddCommand implements Runnable{

    @Parameters(index = "0", description = "The Description of the new task")
    private String description;

    @Option(names = {"--tags"}, description = "Comma-separated tags", split = ",")
    private String[] tags;

    @Option(names = {"--priority"}, description = "Priority (high|medium|low)", defaultValue = "medium")
    private String priority;

    @Override
    public void run(){
        TaskManager manager = new TaskManager();
        manager.addTask(description);
        if(tags != null && tags.length > 0){
            try{
                Task task = manager.getTasks().get(manager.getTasks().size() - 1);
                task.setTags(new HashSet<>(Arrays.asList(tags)));
                task.setPriority(priority.toUpperCase());
                manager.saveTasks();
            }catch(Exception e){
                System.err.println("Error adding tags/priority: " + e.getMessage());
            }
        }
        System.out.println("Task added: \"" + description + "\"");
    }
}
