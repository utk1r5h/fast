package com.utkarsh;

import java.time.LocalDateTime;

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
  
  public Task(long id, String description, Status status, LocalDateTime creationDate) {
    this.id = id;
    this.description = description;
    this.status = status;
    this.creationDate = creationDate;
  }
  


  
}
