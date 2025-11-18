package com.utkarsh;

import com.utkarsh.util.StringUtil;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;
import java.util.List;

@Command(name = "search", description = "Search tasks by keyword")
public class SearchCommand implements Runnable{

    @Parameters(index = "0", description = "Search keyword", defaultValue = "")
    private String keyword;

    @Option(names = {"--palindrome"}, description = "Find palindrome descriptions")
    private boolean palindrome;

    @Override
    public void run(){
        TaskManager manager = new TaskManager();
        List<Task> tasks = manager.getTasks();
        if(palindrome){
            System.out.println("\n=== Palindrome Tasks ===");
            tasks.stream()
                .filter(t -> StringUtil.isPalindrome(t.getDescription()))
                .forEach(t -> System.out.println(t.getId() + ": " + t.getDescription()));
        }else if(!keyword.isEmpty()){
            System.out.println("\n=== Search Results for: " + keyword + " ===");
            tasks.stream()
                .filter(t -> StringUtil.contains(t.getDescription(), keyword))
                .forEach(t -> System.out.println(t.getId() + ": " + t.getDescription()));
        }else{
            System.out.println("Please provide a search keyword or use --palindrome flag");
        }
    }
}
