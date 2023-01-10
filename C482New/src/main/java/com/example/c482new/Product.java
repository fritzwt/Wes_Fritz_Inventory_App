package com.example.c482new;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *  Runtime Error: I tried calling the associatedParts as this: "private static ObservableList Part  associatedParts" and I fixed it by making it non-static.
 *  This class creates the Product Objects for the Inventory Class.
 *  */
public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

/**
 * This is the method that is invoked when creating a new Pizza Product Option, or Modifying a Pizza Product.
 * @param id - id number of the Pizza.
 * @param name - name of the Pizza.
 * @param price - price of the Pizza.
 * @param stock - current stock level of the Pizza.
 * @param min - minimum stock level of the Pizza.
 * @param max - maximum stock level of the Pizza.
 * */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

/**
 * This method retrieves all parts associated with the Product.
 * Called by invoking the product id first, like this:
 * ListName = PizzaProductID.getAllAssociatedParts()
 * Note: You must declare your ListName as an Observable List for this to work correctly.
 * @return the associatedParts list of the Pizza product
 * */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

/**
 * This method retrieves the ID Number of the Pizza Product.
 * @return the ID number.
 * */
    public int getId() {
        return id;
    }

/**
 * This method sets the ID Number of a Pizza Product.
 * @param id is the ID number to set.
 * */
    public void setId(int id) {
        this.id = id;
    }

/**
 * This method retrieves the Name of a Pizza Product.
 * @return the Name of the Pizza.
 * */
    public String getName() {
        return name;
    }

/**
 * This method sets the Name of a Pizza Product.
 * @param name  is the new name to give to a Pizza.
 * */
    public void setName(String name) {
        this.name = name;
    }

/**
 * This method retrieves the Price of a Pizza Product.
 * @return the price of a Pizza.
 * */
    public double getPrice() {
        return price;
    }

/**
 * This method sets the Price of a Pizza Product.
 * @param price  is the new price to set for a Pizza Product.
 * */
    public void setPrice(double price) {
        this.price = price;
    }

/**
 * This method retrieves the Stock level of a Pizza Product
 * @return the stock level of a Pizza.
 * */
    public int getStock() {
        return stock;
    }

/**
 * This method sets the Stock level of a Pizza Product.
 * @param stock is the new stock level to set.
 * */
    public void setStock(int stock) {
        this.stock = stock;
    }

/**
 * This method retrieves the minimum stock level of a Pizza Product.
 * @return the minimum stock allowed for a Pizza.
 * */
    public int getMin() {
        return min;
    }

/**
 * This method sets the minimum stock level of a Pizza Product.
 * @param min  is the new minimum stocking level of the Pizza Product.
 * */
    public void setMin(int min) {
        this.min = min;
    }

/**
 * This method retrieves the maximum stock level of a Pizza Product.
 * @return the maximum stock allowed for a Pizza Product.
 * */
    public int getMax() {
        return max;
    }

/**
 * This method sets the maximum stock level of a Pizza Product.
 * @param max is the new maximum stock level to set to.
 * */
    public void setMax(int max) {
        this.max = max;
    }


/**
 * This method adds a list of Ingredient parts to a Pizza Product.
 * It is called from the ModifyProductController.
 * It is invoked upon pressing the "Save" button on the Modify Product screen.
 * The ModifyProductController has a function that will delete the information of the previous part list.
 * This method serves to repopulate the part list
 * @param part is the list of parts passed from the ModifyProductController.
 * */
    public void addAssociatedPart(ObservableList<Part> part) {
        associatedParts.addAll(part);
    }
}



