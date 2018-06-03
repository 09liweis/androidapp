package com.samli.samli.models;

public class Todo {
    String name;
    String _id;
    String status;

    public Todo(String _id, String name, String status) {
        this._id = _id;
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
