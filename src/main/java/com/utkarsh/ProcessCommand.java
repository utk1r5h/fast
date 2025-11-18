package com.utkarsh;
import com.utkarsh.thread.TaskQueue;
import com.utkarsh.thread.TaskProcessor;
import picocli.CommandLine.Command;
import java.util.List;
@Command(name = "process", description = "Process tasks concurrently")
public class ProcessCommand implements Runnable{
    @Override
    public void run(){
        TaskManager manager = new TaskManager();
        List<Task> tasks = manager.getTasks();
        List<Task> todoTasks = tasks.stream()
            .filter(t -> t.getStatus() == Status.TODO)
            .toList();
        if(todoTasks.isEmpty()){
            System.out.println("No pending tasks to process.");
            return;
        }
        System.out.println("🔄 Task Processor Started");
        System.out.println("Processing " + todoTasks.size() + " task(s) with 2 threads...\n");
        TaskQueue queue = new TaskQueue();
        TaskProcessor processor1 = new TaskProcessor(queue, 1);
        TaskProcessor processor2 = new TaskProcessor(queue, 2);
        Thread t1 = new Thread(processor1);
        Thread t2 = new Thread(processor2);
        t1.start();
        t2.start();
        for(Task task : todoTasks){
            queue.addTask(task);
        }
        try{
            while(!queue.isEmpty()){
                Thread.sleep(100);
            }
            Thread.sleep(2000);
            processor1.stopProcessing();
            processor2.stopProcessing();
            t1.interrupt();
            t2.interrupt();
            System.out.println("\n✓ All tasks processed");
        }catch(InterruptedException e){
            System.err.println("Processing interrupted");
        }
    }
}
