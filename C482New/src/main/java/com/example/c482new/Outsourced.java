package com.example.c482new;

/**
 * This class is an extension of the Part Class, called Outsourced.
 * This is used to identify those parts that are purchased from other suppliers and borught in.
 * Having this sub-class adjusts some display options in the application to assist the user.
 * */
public class Outsourced extends Part{
    private String companyName;

/**
 * This method is the constructor for the Outsourced Part object.
 * */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * This method returns the name of the company that supplied the part.
     * @return companyName is the name of the company that supplied the part.
     * */
    public String getCompanyName() {
        return companyName;
    }
}