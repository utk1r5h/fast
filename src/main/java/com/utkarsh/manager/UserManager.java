package com.utkarsh.manager;
import com.utkarsh.model.*;
import java.util.HashMap;
import java.util.Map;
public class UserManager{
    private Map<String, User> users;
    private User currentUser;
    public UserManager(){
        this.users = new HashMap<>();
        this.currentUser = new AdminUser("default");
    }
    public void createUser(String username, String type){
        User user = switch(type.toUpperCase()){
            case "ADMIN" -> new AdminUser(username);
            case "POWER" -> new PowerUser(username);
            case "GUEST" -> new GuestUser(username);
            default -> throw new IllegalArgumentException("Invalid user type: " + type);
        };
        users.put(username, user);
        System.out.println("✓ User created: " + username + " (" + type + ")");
    }
    public void switchUser(String username){
        User user = users.get(username);
        if(user == null){
            throw new IllegalArgumentException("User not found: " + username);
        }
        currentUser = user;
        System.out.println("✓ Switched to user: " + username);
    }
    public void listUsers(){
        if(users.isEmpty()){
            System.out.println("No users found.");
            return;
        }
        System.out.println("\n=== Users ===");
        for(User user : users.values()){
            String current = user.equals(currentUser) ? " (current)" : "";
            System.out.println("• " + user.getUsername() + " - " + user.getUserType() + current);
        }
    }
    public User getCurrentUser(){
        return currentUser;
    }
}
