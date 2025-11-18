package com.utkarsh.thread;
import com.utkarsh.TaskManager;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class AutoSaveThread extends Thread{
    private TaskManager manager;
    private volatile boolean running = true;
    private int intervalSeconds;
    public AutoSaveThread(TaskManager manager, int intervalSeconds){
        this.manager = manager;
        this.intervalSeconds = intervalSeconds;
        setDaemon(true);
    }
    @Override
    public void run(){
        while(running){
            try{
                Thread.sleep(intervalSeconds * 1000L);
                manager.saveTasks();
                String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
                System.out.println("[AutoSave] Tasks saved at " + time);
            }catch(InterruptedException e){
                break;
            }
        }
    }
    public void stopThread(){
        running = false;
        interrupt();
    }
}
