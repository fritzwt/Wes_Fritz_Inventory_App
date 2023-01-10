package com.example.c482new;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/** This is the Inventory class. This class contains all of the Part and Product data for this managment system. */
public class Inventory {

    private static int partId = 0;
    private static int productId = 0;
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    private static ObservableList<Part> filteredParts =FXCollections.observableArrayList();
    private static ObservableList<Product> filteredProducts= FXCollections.observableArrayList();

    /**
     * This method adds a Part object to the allParts observable list.
     * @param newPart is the part object.
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * This method adds a product object to the Inventory observable list allProducts.
     * @param newProduct is the product object that is being added.
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }


    /***/
    public static Part lookupPart(int partId) {
        Part partFound = null;

        for (Part part : allParts) {
            if (part.getId() == partId) {
                partFound = part;
            }
        }

        return partFound;
    }

    /***/
    public static Product lookupProduct(int productId) {
        Product productFound = null;

        for (Product product : allProducts) {
            if (product.getId() == productId) {
                productFound = product;
            }
        }

        return productFound;
    }

    /***/
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> partsFound = FXCollections.observableArrayList();

        for (Part part : allParts) {
            if (part.getName().equals(partName)) {
                partsFound.add(part);
            }
        }

        return partsFound;
    }

    /***/
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> productsFound = FXCollections.observableArrayList();

        for (Product product : allProducts) {
            if (product.getName().equals(productName)) {
                productsFound.add(product);
            }
        }

        return productsFound;
    }

    /***/
    public static void updatePart(int index, Part selectedPart) {

        allParts.set(index, selectedPart);
    }

    /***/
    public static void updateProduct(int index, Product selectedProduct) {

        allProducts.set(index, selectedProduct);
    }

    /***/
    public static boolean deletePart(Part selectedPart) {
        if (allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        }
        else {
            return false;
        }
    }

    /***/
    public boolean deleteProduct(Product selectedProduct) {
        if (allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        }
        else {
            return false;
        }
    }



    /**
     * This method returns all parts from the Parts list, allParts.
     * @return allParts observable list.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<Part> getAllFilteredParts() {
        return filteredParts;
    }

    /**
     * This method returns all products from the Products list.
     * @return allProducts observable list.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }


    public static ObservableList<Product> getAllFilteredProducts() {
        return filteredProducts;
    }






    public static int getNewPartId() {
        return ++partId;
    }

    public static int getNewProductId() {
        return ++productId;
    }

// make all static in this class -- IF A PROBLEM, DROP STATICS BELOW HERE

















    public int partListSize() {
        return allParts.size();
    }
}

