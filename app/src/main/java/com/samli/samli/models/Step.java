package com.samli.samli.models;

public class Step {
    String name;
    String status;
    Transaction transaction;

    public Step() {

    }
    public Step(String name,String status,Transaction transaction) {
        this.name = name;
        this.status = status;
        this.transaction = transaction;
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

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
