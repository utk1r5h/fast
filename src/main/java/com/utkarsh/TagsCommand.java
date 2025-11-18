package com.utkarsh;
import picocli.CommandLine.Command;
import java.util.Set;
@Command(name = "tags", description = "Show all unique tags")
public class TagsCommand implements Runnable{
    @Override
    public void run(){
        TaskManager manager = new TaskManager();
        Set<String> tags = manager.getAllTags();
        if(tags.isEmpty()){
            System.out.println("No tags found.");
            return;
        }
        System.out.println("\n=== All Tags ===");
        for(String tag : tags){
            System.out.println("• " + tag);
        }
    }
}
