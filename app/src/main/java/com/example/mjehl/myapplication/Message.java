package com.example.mjehl.myapplication;

/**
 * Created by mjehl on 9/17/2016.
 */
public class Message {
    private boolean accepted;
    private String message;
    private String category;
    private String id;
    private String timesSent;
    public Message(String givenID, String givenMessage, String givenCategory,String givenTimesSent,  String givenAccepted){
        id = givenID;
        timesSent = givenTimesSent;
        message = givenMessage;
        category = givenCategory;
        accepted = false;
        if(givenAccepted.equals("1")){
            accepted = true;
        }
    }

    public String getID(){
        return id;
    }

    public String getMessage(){
        return message;
    }
    public String getCategory(){
        return category;
    }
    public boolean isAccepted(){
        return accepted;
    }
    public String getTimesSent(){
        return timesSent;
    }
}
