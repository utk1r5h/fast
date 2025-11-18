package com.utkarsh.thread;
import com.utkarsh.Task;
import java.util.LinkedList;
public class TaskQueue{
    private LinkedList<Task> queue = new LinkedList<>();
    public synchronized void addTask(Task task){
        queue.add(task);
        System.out.println("[Queue] Task #" + task.getId() + " added to queue");
        notify();
    }
    public synchronized Task getTask() throws InterruptedException{
        while(queue.isEmpty()){
            wait();
        }
        return queue.removeFirst();
    }
    public synchronized int getQueueSize(){
        return queue.size();
    }
    public synchronized boolean isEmpty(){
        return queue.isEmpty();
    }
}
