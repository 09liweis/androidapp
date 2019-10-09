package com.samli.samli.models;
import java.util.ArrayList;

public class Todo {
  private String name;
  private String _id;
  private String status;
  private ArrayList<Todo> steps;
  private String date;
  private Transaction transaction;
  public Todo() {}

  public String getId() {
    return _id;
  }

  public void setId(String id) {
    this._id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
  public String getStatus() {return status;}
  public void setStatus(String status) {this.status = status;}
  public String getDate(){return date;}
  public void setDate(String date) {this.date = date;}
  public Transaction getTransaction() {return transaction;}
  public void setTransaction(Transaction transaction) {this.transaction = transaction;}
}
