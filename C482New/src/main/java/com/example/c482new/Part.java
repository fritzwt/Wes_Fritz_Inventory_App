package com.example.c482new;

/**
 * This class is for Part Objects! It has two sub classes, Outsourced and InHouse that extend it.
 * */
public abstract class Part {

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;


/**
 * This is the constructor method for Part objects.
 * @param id is the part ID number.
 * @param name is the part name.
 * @param price is the part price.
 * @param stock is the part current inventory level.
 * @param min is the minimum inventory level for this part.
 * @param max is the maximum inventory level for this part.
 * */
    public Part(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

/**
 * This is the empty constructor for a part object.
 * */
    public Part() {

    }


    // Setters and Getters
/**
 * This method retrieves the ID number of a part.
 * @return the ID number.
 * */
    public int getId() {
        return id;
    }

/**
 * This method sets an ID number of a part.
 * @param id the id to set
 */
    public void setId(int id) {
        this.id = id;
    }

/**
 * This method retrieves the part name.
 * @return the name
 */
    public String getName() {
        return name;
    }

/**
 * This method sets a name to a part,
 * @param name the name of part to set.
 */
    public void setName(String name) {
        this.name = name;
    }

/**
 * This method retrives the price of a part.
 * @return the price of a part.
 */
    public double getPrice() {
        return price;
    }

/**
 * This method sets a new price for a part.
 * @param price the new price to set.
 */
    public void setPrice(double price) {
        this.price = price;
    }

/**
 * This method retrives the current stock level of a part.
 * @return the stock
 */
    public int getStock() {
        return stock;
    }

/**
 * This method sets the stock level of a part.
 * @param stock the stock to set
 */
    public void setStock(int stock) {
        this.stock = stock;
    }

/**
 * This method retrieves the minimum stock level of a part.
 * @return the minnimum stock level of a part.
 */
    public int getMin() {
        return min;
    }

/**
 * This method sets a minimum stock level for a part.
 * @param min the minimum stock level of a part to set.
 */
    public void setMin(int min) {
        this.min = min;
    }

/**
 * This method retrieves the maximum stock level of a part.
 * @return the maximum stock level of a part.
 */
    public int getMax() {
        return max;
    }

/**
 * This method sets the maximum stock level of a part.
 * @param max the maximum stock level of a part to set.
 */
    public void setMax(int max) {
        this.max = max;
    }
}
