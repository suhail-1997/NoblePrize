package com.example.myapplication;

/**
 * Designed and Developed by Mohammad suhail ahmed on 27/02/2020
 */
public class TopPeopleItem {
    private String name,years;

    public String getName() {
        return name;
    }

    public String getYears() {
        return years;
    }
    public TopPeopleItem(String name,String years)
    {
        this.years = years;
        this.name = name;
    }
}
