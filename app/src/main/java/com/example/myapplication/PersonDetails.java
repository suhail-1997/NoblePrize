package com.example.myapplication;

/**
 * Designed and Developed by Mohammad suhail ahmed on 26/02/2020
 */
public class PersonDetails {
    private int id;
    private String firstName,surName,motivation;

    public PersonDetails(int id,String firstName,String surName,String motivation)
    {
        this.id = id;
        this.firstName =firstName;
        this.surName =surName;
        this.motivation = motivation;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMotivation() {
        return motivation;
    }

    public String getSurName() {
        return surName;
    }
}
