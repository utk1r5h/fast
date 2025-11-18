package com.utkarsh;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_FixedWidth;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
@Command(name="list", description = "List all Pending Tasks")
public class ListCommand implements Runnable{
    @Option(names={"-a", "--all"}, description="Show all tasks, included the completed one")
    private boolean showALL = false;
    @Option(names={"--tag"}, description="Filter by tag")
    private String filterTag;
    @Option(names={"--sort"}, description="Sort by priority")
    private String sortBy;
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
        if(filterTag != null && !filterTag.isEmpty()){
            taskstoShow = taskstoShow.stream()
                .filter(task -> task.getTags() != null && task.getTags().contains(filterTag))
                .collect(Collectors.toList());
        }
        if("priority".equals(sortBy)){
            taskstoShow.sort((t1, t2) -> {
                int p1 = getPriorityValue(t1.getPriority());
                int p2 = getPriorityValue(t2.getPriority());
                return Integer.compare(p2, p1);
            });
        }
        if(taskstoShow.isEmpty()){
            System.out.println("No tasks to show.");
            return;
        }
        AsciiTable at = new AsciiTable();
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
    private int getPriorityValue(String priority){
        if(priority == null) return 1;
        return switch(priority.toUpperCase()){
            case "HIGH" -> 3;
            case "MEDIUM" -> 2;
            case "LOW" -> 1;
            default -> 1;
        };
    }
}
