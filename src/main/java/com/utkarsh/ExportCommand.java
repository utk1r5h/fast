package com.utkarsh;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import java.util.List;
@Command(name = "export", description = "Export tasks to CSV format")
public class ExportCommand implements Runnable{
    @Option(names = {"--format"}, description = "Export format (csv)", defaultValue = "csv")
    private String format;
    @Override
    public void run(){
        TaskManager manager = new TaskManager();
        List<Task> tasks = manager.getTasks();
        if(tasks.isEmpty()){
            System.out.println("No tasks to export.");
            return;
        }
        System.out.println("ID,Description,Status,Priority,Tags,CreatedOn");
        for(Task task : tasks){
            String tags = task.getTags() != null ? String.join(";", task.getTags()) : "";
            System.out.println(String.format("%d,\"%s\",%s,%s,\"%s\",%s",
                task.getId(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                tags,
                task.getCreationDate()
            ));
        }
    }
}
