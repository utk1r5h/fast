package com.utkarsh.thread;
import com.utkarsh.Task;
public class ReminderTask implements Runnable{
    private Task task;
    public ReminderTask(Task task){
        this.task = task;
    }
    @Override
    public void run(){
        System.out.println("\n⏰ REMINDER: " + task.getDescription() + " (Task #" + task.getId() + ")");
    }
}
