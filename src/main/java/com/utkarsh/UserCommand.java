package com.utkarsh;

import com.utkarsh.manager.UserManager;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "user", description = "User management",
    subcommands = {
        UserCommand.Create.class,
        UserCommand.Switch.class,
        UserCommand.List.class
    })
public class UserCommand implements Runnable{
    private static UserManager userManager = new UserManager();

    @Override
    public void run(){
        System.out.println("Use: tm user <create|switch|list>");
    }

    public static UserManager getUserManager(){
        return userManager;
    }

    @Command(name = "create", description = "Create a new user")
    static class Create implements Runnable{
        @Parameters(index = "0", description = "Username")
        private String username;

        @Parameters(index = "1", description = "User type (admin|power|guest)")
        private String type;

        @Override
        public void run(){
            try{
                userManager.createUser(username, type);
            }catch(Exception e){
                System.err.println("❌ " + e.getMessage());
            }
        }
    }

    @Command(name = "switch", description = "Switch to a different user")
    static class Switch implements Runnable{
        @Parameters(index = "0", description = "Username")
        private String username;

        @Override
        public void run(){
            try{
                userManager.switchUser(username);
            }catch(Exception e){
                System.err.println("❌ " + e.getMessage());
            }
        }
    }

    @Command(name = "list", description = "List all users")
    static class List implements Runnable{
        @Override
        public void run(){
            userManager.listUsers();
        }
    }
}
