package com.utkarsh;

import com.utkarsh.thread.ReminderTask;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Command(name = "notify", description = "Set a reminder for a task")
public class NotifyCommand implements Runnable{

    @Parameters(index = "0", description = "Task ID")
    private long taskId;

    @Parameters(index = "1", description = "Minutes until reminder")
    private int minutes;

    @Override
    public void run(){
        try{
            TaskManager manager = new TaskManager();
            Task task = manager.getTaskById(taskId);
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            scheduler.schedule(new ReminderTask(task), minutes, TimeUnit.MINUTES);
            System.out.println("✓ Reminder set for task #" + taskId + " in " + minutes + " minute(s)");
            scheduler.shutdown();
        }catch(Exception e){
            System.err.println("❌ " + e.getMessage());
        }
    }
}
