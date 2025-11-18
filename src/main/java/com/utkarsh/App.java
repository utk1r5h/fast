package com.utkarsh;

import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = "tm",
mixinStandardHelpOptions = true,
description = "A fast CLI Task Manager",
subcommands = {
    AddCommand.class,
    ListCommand.class,
    DoneCommand.class,
    ClearCommand.class,
    EditCommand.class,
    SearchCommand.class,
    StatsCommand.class,
    TagsCommand.class,
    UserCommand.class,
    WatchCommand.class,
    NotifyCommand.class,
    ProcessCommand.class,
    ExportCommand.class
})
public class App implements Runnable{

    @Override
    public void run(){
        System.out.println("Please specify a command: add, list, etc. Use --help for more info.");
    }

    public static void main(String[] args){
        int exitcode = new CommandLine(new App()).execute(args);
        System.exit(exitcode);
    }
}
