package com.utkarsh;

import com.utkarsh.thread.AutoSaveThread;
import picocli.CommandLine.Command;

@Command(name = "watch", description = "Start background task monitor")
public class WatchCommand implements Runnable{

    @Override
    public void run(){
        TaskManager manager = new TaskManager();
        AutoSaveThread autoSave = new AutoSaveThread(manager, 30);
        System.out.println("🔍 Task Monitor Started");
        System.out.println("Auto-saving every 30 seconds...");
        System.out.println("Press Ctrl+C to stop.\n");
        autoSave.start();
        try{
            Thread.sleep(Long.MAX_VALUE);
        }catch(InterruptedException e){
            autoSave.stopThread();
        }
    }
}
