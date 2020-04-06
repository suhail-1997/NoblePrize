package com.example.myapplication;

import java.util.List;

/**
 * Designed and Developed by Mohammad suhail ahmed on 26/02/2020
 */
public class WinnerDetails {
    private String year;
    private String category;
    private List<PersonDetails> personDetails;

    public WinnerDetails(String year,String category,List<PersonDetails> personDetails)
    {
        this.year = year;
        this.category = category;
        this.personDetails = personDetails;
    }

    public String getYear() {
        return year;
    }

    public List<PersonDetails> getPersonDetails() {
        return personDetails;
    }

    public String getCategory() {
        return category;
    }
}
