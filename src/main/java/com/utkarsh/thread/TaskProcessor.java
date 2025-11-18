package com.utkarsh.thread;
import com.utkarsh.Task;
public class TaskProcessor implements Runnable{
    private TaskQueue taskQueue;
    private volatile boolean processing = true;
    private int processorId;
    public TaskProcessor(TaskQueue taskQueue, int id){
        this.taskQueue = taskQueue;
        this.processorId = id;
    }
    @Override
    public void run(){
        while(processing){
            try{
                Task task = taskQueue.getTask();
                processTask(task);
            }catch(InterruptedException e){
                break;
            }
        }
    }
    private void processTask(Task task){
        try{
            System.out.println("[Thread-" + processorId + "] Processing: " + task.getDescription());
            Thread.sleep(1000);
            System.out.println("[Thread-" + processorId + "] Completed: Task #" + task.getId());
        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
    public void stopProcessing(){
        processing = false;
    }
}
