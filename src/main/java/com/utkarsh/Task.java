// src/main/java/com/utkarsh/Task.java

package com.utkarsh;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter; // Import this

public class Task {
  private long id;
  private String description;
  private Status status;
  private LocalDateTime creationDate;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public LocalDateTime getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(LocalDateTime creationDate) {
    this.creationDate = creationDate;
  }
  public Task(){
    //jackson need this empty constructor to create the object before 
    //filling it with data from the json file
    
  }


  public Task(long id, String description, Status status, LocalDateTime creationDate) {
    this.id = id;
    this.description = description;
    this.status = status;
    this.creationDate = creationDate;
  }
  
  // ==================================================================
  // PRECISE ADDITION: Add this method
  // This tells the GUI list (JList) how to display the task object
  // ==================================================================
  @Override
  public String toString() {
    // Formats the date to be more readable
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    
    // Example: "[DONE] (ID: 1) Buy milk - Created: 2025-11-17 20:30"
    return String.format("[%s] (ID: %d) %s - Created: %s", 
        this.status, 
        this.id, 
        this.description, 
        this.creationDate.format(formatter));
  }
}