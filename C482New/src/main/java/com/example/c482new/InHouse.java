package com.example.c482new;

/**
 * This class is an extension of the Part class, called InHouse.
 * This is for those parts that are made internal to the business.
 * Having this sub-class will adjust some display features to assist the user.
 * */
public class InHouse extends Part{

    private int machineId;

/**
 * This method is the full constructor for a Part object  with the In House sub-class.
 * */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * This method retrieves the In House Part Object's Machine ID
     * @return machineID is the number assigned to a machine that makes an In House part.
     */
    public int getMachineId() {
        return machineId;
    }
}