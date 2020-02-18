package com.example.androidlabs;

public class Message {

    private String message;
    private String type;
    private long id;

    public Message(String message, String type , long id){
        this.message = message;
        this.type = type;
        this.id = id;

    }
    public Message(String n,String type) { this(n, type ,0);}

    public  String getType() {
        return type;
    }

    public  String getMessage() {
        return message;
    }
    public long getId() {
        return id;
    }

    public void update(String msg)
    {
        message = msg;

    }
}