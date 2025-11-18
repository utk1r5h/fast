package com.utkarsh;
import picocli.CommandLine.Command;
import java.util.List;
@Command(name = "stats", description = "Show task statistics")
public class StatsCommand implements Runnable{
    @Override
    public void run(){
        TaskManager manager = new TaskManager();
        List<Task> tasks = manager.getTasks();
        long total = tasks.size();
        long done = tasks.stream().filter(t -> t.getStatus() == Status.DONE).count();
        long todo = total - done;
        double completion = total > 0 ? (done * 100.0 / total) : 0;
        StringBuilder stats = new StringBuilder();
        stats.append("\n=== Task Statistics ===\n");
        stats.append("Total Tasks: ").append(total).append("\n");
        stats.append("Completed: ").append(done).append("\n");
        stats.append("Pending: ").append(todo).append("\n");
        stats.append(String.format("Completion Rate: %.1f%%\n", completion));
        System.out.println(stats.toString());
    }
}
