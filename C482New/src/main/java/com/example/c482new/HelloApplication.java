package com.example.c482new;

// This project was done by Wesley Fritz, SID 1375213.
// JavaDocs folder is in location C482New/javadocs.

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * I encountered a runtime error dealing with the Product class- I had coded the associated parts listing as private static, which prevented the app from running- I corrected it by taking out the static keyword.
 * This class creates the main Pizza Management System Application, and populates the base data
 * */
public class HelloApplication extends Application {

    /** This is the start method. This method invokes the FXML system for the application. */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Wes Fritz C482 Project");
        stage.setScene(scene);
        stage.show();
    }

    /** This is the main method. This method loads the test data in this application, and launches the application itself. A Future Enhancement would be to populate associated parts of the initial products. */
    public static void main(String[] args) {
// TEST DATA -- Used pizza as an example to make it easy to understand.
        Part inhouse1 = new InHouse(1, "Pepperoni", 1.99, 10, 0, 20, 125);
        Part inhouse2 = new InHouse(2, "Olives", 2.99, 27, 0, 30, 124);
        Part outsourced1 = new Outsourced(3, "Mushrooms", 2.59, 5, 0, 15, "Sysco");
        Part outsourced2 = new Outsourced(4, "Peppers", 3.79, 22, 0, 25, "GFS");
        Product product1 = new Product(1, "Basic", 20.99, 10, 0, 10);
        Product product2 = new Product(2, "Deluxe", 25.99, 5, 0, 8);

        Inventory.addPart(inhouse1);
        Inventory.addPart(inhouse2);
        Inventory.addPart(outsourced1);
        Inventory.addPart(outsourced2);
        Inventory.addProduct(product1);
        Inventory.addProduct(product2);

        launch();
    }
}