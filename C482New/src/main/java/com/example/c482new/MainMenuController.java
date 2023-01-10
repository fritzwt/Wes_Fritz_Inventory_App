package com.example.c482new;

// This project was done by Wesley Fritz, SID 1375213.
// JavaDocs folder is in location C482New/javadocs.

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/** Future Enhancement: make methods for just a few alerts, and call them in multiple places, instead of making a new alert each time.
 * This class initializes the Main Menu screen for the application.
 * The MainMenuController gives the user the ability to jump between multiple screens.
 * This is the central hub of the application. */
public class MainMenuController implements Initializable {
    Stage stage;
    Parent scene;
    @FXML
    private TableColumn<Part, Integer> partidcol;
    @FXML
    private TableColumn<Part, Integer> partinvcol;
    @FXML
    private TableColumn<Part, String> partnamecol;
    @FXML
    private TableColumn<Part, Double> partppucol;
    @FXML
    private TableView<Part> partstableview;
    @FXML
    private TableColumn<Product, Integer> prodidcol;
    @FXML
    private TableColumn<Product, Integer> prodinvcol;
    @FXML
    private TableColumn<Product, String> prodnamecol;
    @FXML
    private TableColumn<Product, Double> prodppucol;
    @FXML
    private TableView<Product> productstableview;
    @FXML
    public TextField txtsearchpart;
    @FXML
    public TextField txtsearchproduct;

    /** This method allows the parts to be added to the associated parts table in Add/Modify Product.
     * This method is used in the case that a user tries to add a part without selecting it first.
     * @param select_a_part this parameter is a title of a message to the user prompting to select a part.
     * @param select_a_part1 this parameter is an alert to the user noting to select a part.
     * @param s this parameter shows a string to the user prompting to select a part. */
    public static void addPartToProductDialog(String select_a_part, String select_a_part1, String s) {
    }

    /** This method allows the user to exit the application with a click of the exit button. */
    @FXML
    void OnActionExitApp(ActionEvent event) {
        System.exit(0);
    }

    /** This method brings the user to the Add Part screen.
     * This happens when a user clicks the 'Add' button on the Part side. */
    @FXML
    void onActionAddPart(ActionEvent event) throws IOException {
        // sends user to Add Part page.
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AddPart.fxml")));
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** This method brings the user to the Add Product screen.
     * This happens when a user clicks the 'Add' button on the Product side. */
    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException {
        // sends user to Add Product page.
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** This method deletes a part from the inventory.
     * This happens when the 'Delete' button is clicked on the Part side.
     * There are alerts and confirmations to ensure input is valid and wanted by the user. */
    @FXML
    public void onActionDeletePart(ActionEvent event) throws IOException {
        if (partstableview.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error, no part selected, please select a part and try again.", ButtonType.OK);
            alert.showAndWait();
            partstableview.setItems(Inventory.getAllParts());
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this part?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                int selectedPart = partstableview.getSelectionModel().getSelectedIndex();
                partstableview.getItems().remove(selectedPart);
                partstableview.setItems(Inventory.getAllParts());
            }
        }
    }

    /** This method deletes a product from the inventory.
     * This happens when the 'Delete' button is clicked on the Product side.
     * There are alerts and confirmations to ensure input is valid and wanted by the user.
     * This also ensures a product with parts attached doesn't get deleted. */
    @FXML
    public void onActionDeleteProduct(ActionEvent event) throws IOException {
        Product deleteSelectedProduct = productstableview.getSelectionModel().getSelectedItem();
        if (productstableview.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error, no product selected, please select a product and try again.", ButtonType.OK);
            alert.showAndWait();
            productstableview.setItems(Inventory.getAllProducts());
        }
        ObservableList<Part> partCheck = deleteSelectedProduct.getAllAssociatedParts();
        if (partCheck.size() != 0) {
            Alert anotherAlert = new Alert(Alert.AlertType.ERROR, "Product has an associated part attached, cannot delete.");
            anotherAlert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this product?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                int selectedProduct = productstableview.getSelectionModel().getSelectedIndex();
                productstableview.getItems().remove(selectedProduct);
                productstableview.setItems(Inventory.getAllProducts());
            }
        }
    }

    /** This method brings the user to the Modify Part screen.
     * This happens when a user clicks the 'Modify' button on the Part side.
     * All fields will populate on the Modify Part screen with information stored in the part. */
    @FXML
    public void onActionModifyPart(ActionEvent event) throws IOException {
        Part selectedPart = partstableview.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("No Match");
            alert.setHeaderText("No part selected.");
            alert.showAndWait();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyPart.fxml"));
            loader.load();
            ModifyPartController sendPartController = loader.getController();
            sendPartController.sendPart(partstableview.getSelectionModel().getSelectedItem());
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
        }
    }

    /** This method brings the user to the Modify Product screen.
     * This happens when a user clicks the 'Modify' button on the Product side.
     * All fields will populate on the Modify Product screen with information stored in the product.
     * Among the information included will be any parts attached to the product as well. */
    @FXML
    void onActionModifyProduct(ActionEvent event) throws IOException {
        Product selectedProduct = productstableview.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("No Match");
            alert.setHeaderText("No product selected.");
            alert.showAndWait();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyProduct.fxml"));
            loader.load();
            ModifyProductController sendProductController = loader.getController();
            sendProductController.sendProduct(productstableview.getSelectionModel().getSelectedItem());
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
        }
    }

    /** This method searches for a Part using the search bar on the Part side.
     * Users can search by Part ID, partial and full text inputs for their part.
     * Multiple alerts are included to verify correct information is searched for.*/
    public void onTextSearchPart(ActionEvent actionEvent) throws IOException {

        // Pressing enter after typing in the search bar is what completes the search!

        String term = txtsearchpart.getText();
        ObservableList<Part> resultsList = searchByPartName(term);
        if (resultsList.size() == 0) {
            try {
                int partID = Integer.parseInt(term);
                Part part = getPartByNumber(partID);
                if (part != null)
                    resultsList.add(part);
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.initModality(Modality.APPLICATION_MODAL);
                    alert.setTitle("No Match");
                    alert.setHeaderText("Unable to locate '" + term + "' in the ingredient list, showing all ingredients...");
                    alert.showAndWait();
                }
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.setTitle("No Match");
                alert.setHeaderText("Unable to locate '" + term + "' in the ingredient list, showing all ingredients...");
                alert.showAndWait();
            }
        }
        // brings back search results.
        partstableview.setItems(resultsList);
    }

    /** This method pairs up with the onTextSearchPart method to allow searching by a part's name.
     * @param partialName This parameter allows the searching of a partial name as well as full.
     * @return namedParts. */
    private ObservableList<Part> searchByPartName(String partialName) {
        ObservableList<Part> namedParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = Inventory.getAllParts();
        for (Part part : allParts) {
            if (part.getName().contains(partialName)) {
                namedParts.add(part);
            }
        }
        return namedParts;
    }

    /** This method pairs up with the onTextSearchPart method to allow searching by a part's ID number.
     * @param partID This parameter allows the searching of a partID number as well as its name.
     * @return part.
     * @return null.*/
    private Part getPartByNumber(int partID) {
        ObservableList<Part> allParts = Inventory.getAllParts();
        for (int i = 0; i < allParts.size(); i++) {
            Part part = allParts.get(i);
            if (part.getId() == partID) {
                return part;
            }
        }
        return null;
    }

    /** This method searches for a Product using the search bar on the Product side.
     * Users can search by Product ID, partial and full text inputs for their product.
     * Multiple alerts are included to verify correct information is searched for.*/
    public void onTextSearchProduct(ActionEvent actionEvent) throws IOException {

        // Pressing enter is what finishes the search after typing in the searchbar!

        String term = txtsearchproduct.getText();
        ObservableList<Product> resultsList = searchByProductName(term);
        if (resultsList.size() == 0) {
            try {
                int productID = Integer.parseInt(term);
                Product product = getProductByNumber(productID);
                if (product != null)
                    resultsList.add(product);
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.initModality(Modality.APPLICATION_MODAL);
                    alert.setTitle("No Match");
                    alert.setHeaderText("Unable to locate '" + term + "' in the product list, showing all products...");
                    alert.showAndWait();
                }
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.setTitle("No Match");
                alert.setHeaderText("Unable to locate '" + term + "' in the product list, showing all products...");
                alert.showAndWait();
            }
        }
        productstableview.setItems(resultsList);
    }

    /** This method pairs up with the onTextSearchProduct method to allow searching by a product's name.
     * @param partialName This parameter allows the searching of a partial name as well as full.
     * @return namedProducts.*/
    private ObservableList<Product> searchByProductName(String partialName) {
        ObservableList<Product> namedProducts = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        for (Product product : allProducts) {
            if (product.getName().contains(partialName)) {
                namedProducts.add(product);
            }
        }
        return namedProducts;
    }

    /** This method pairs up with the onTextSearchProduct method to allow searching by a product's ID number.
     * @param productID This parameter allows the searching of a productID number as well as its name.
     * @return product.
     * @return null.*/
    private Product getProductByNumber(int productID) {
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        for (int i = 0; i < allProducts.size(); i++) {
            Product product = allProducts.get(i);
            if (product.getId() == productID) {
                return product;
            }
        }
        return null;
    }

    /** This method initializes the values in the Part and Product tables.
     * This is based off the values in the inventory.
     * This also creates observable lists for the parts and products pages.*/
    @Override
    public void initialize(URL url, ResourceBundle resourcebundle) {

        // This is what populates the tables!

        ObservableList<Part> part = Inventory.getAllParts();
        ObservableList<Product> product = Inventory.getAllProducts();

        partstableview.setItems(Inventory.getAllParts());
        partidcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partnamecol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partppucol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partinvcol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        productstableview.setItems(Inventory.getAllProducts());
        prodidcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodnamecol.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodppucol.setCellValueFactory(new PropertyValueFactory<>("price"));
        prodinvcol.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }
}
