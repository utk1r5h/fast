package com.utkarsh;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name= "tm",
mixinStandardHelpOptions = true,
description="A fast CLI Task Manager",
subcommands={
    AddCommand.class,
    ListCommand.class 

    
})

public class App implements Runnable{
    @Override
    public void run(){
        // if user just types tm with no commands
        System.out.println("Please specify a command: add, list, etc. Use --help for more info.");

    }
    public static void main(String[] args){
        int exitcode= new CommandLine(new App()).execute(args);
        System.exit(exitcode);
    }
}