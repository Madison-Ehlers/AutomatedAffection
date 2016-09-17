package com.example.mjehl.myapplication;

/**
 * Created by dshauger on 9/17/2016.
 * A container class to hold name and number of each contact
 */
public class NameNumberContact {
    private String name;
    private String number;

    public NameNumberContact(String name, String number){
        this.name = name;
        this.number = number;
    }

    public String getName(){
        return name;
    }

    public String getNumber(){
        return number;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setNumber(String number){
        this.number = number;
    }

    @Override
    public String toString(){
        return this.name + " " + this.number;
    }
}
