package com.samli.samli.models;

public class Todo {
    String name;
    String _id;
    String status;
    Transaction transaction;

    public Todo() {

    }

    public Todo(String _id, String name, String status) {
        this._id = _id;
        this.name = name;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
