package com.utkarsh;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
public class Task{
    private long id;
    private String description;
    private Status status;
    private LocalDateTime creationDate;
    private Set<String> tags;
    private String priority;
    public long getId(){
        return id;
    }
    public void setId(long id){
        this.id = id;
    }
    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public Status getStatus(){
        return status;
    }
    public void setStatus(Status status){
        this.status = status;
    }
    public LocalDateTime getCreationDate(){
        return creationDate;
    }
    public void setCreationDate(LocalDateTime creationDate){
        this.creationDate = creationDate;
    }
    public Set<String> getTags(){
        return tags;
    }
    public void setTags(Set<String> tags){
        this.tags = tags;
    }
    public String getPriority(){
        return priority;
    }
    public void setPriority(String priority){
        this.priority = priority;
    }
    public Task(){
        this.tags = new HashSet<>();
        this.priority = "MEDIUM";
    }
    public Task(long id, String description, Status status, LocalDateTime creationDate){
        this.id = id;
        this.description = description;
        this.status = status;
        this.creationDate = creationDate;
        this.tags = new HashSet<>();
        this.priority = "MEDIUM";
    }
}
