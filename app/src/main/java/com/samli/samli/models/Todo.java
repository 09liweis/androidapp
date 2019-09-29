package com.samli.samli.models;
import java.util.ArrayList;

public class Todo {
    String name;
    String _id;
    String status;
    ArrayList<Step> steps;
    String date;
    public Todo() {

    }

    public Todo(String _id, String name) {
        this._id = _id;
        this.name = name;
    }

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
}
