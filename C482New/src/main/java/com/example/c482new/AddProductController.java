package com.example.c482new;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Runtime Error: Ran into a similar runtime error here as I did with ModifyPartController where the local observable list was declared as static, the fix was to make it non-static.
 * This class allows the user to create a new Pizza Product object and assign it associated parts and add it to the Inventory.
 * */
public class AddProductController implements Initializable {
    Stage stage;
    Parent scene;
    @FXML
    private TableColumn<Part, Integer> associatedpartidcol;
    @FXML
    private TableColumn<Part, Integer> associatedpartinvcol;
    @FXML
    private TableColumn<Part, String> associatedpartnamecol;
    @FXML
    private TableColumn<Part, Double> associatedpartppucol;
    @FXML
    private TableView<Part> associatedpartstableview;
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
    private TextField prodidTxt;
    @FXML
    private TextField prodinvTxt;
    @FXML
    private TextField prodmaxTxt;
    @FXML
    private TextField prodminTxt;
    @FXML
    private TextField prodnameTxt;
    @FXML
    private TextField prodpriceTxt;
    @FXML
    private TextField searchBar;
    private ObservableList<Part> apcAssParts = FXCollections.observableArrayList();

/**
 * This method is used to add the product object to the Inventory.
 * It is invoked by pressing the save button on the Add Product Controller screen.
 * There are a number of logical checks that will prevent adding a Product with incorrect data.
 * */
    @FXML
    public void onActionSaveProduct (ActionEvent event) throws IOException {

        String error = "";

        try {
            String name = prodnameTxt.getText();

            error = "Price";
            double price = Double.parseDouble(prodpriceTxt.getText());

            error = "Inventory";
            int stock = Integer.parseInt(prodinvTxt.getText());

            error = "Max";
            int max = Integer.parseInt(prodmaxTxt.getText());

            error = "Min";
            int min = Integer.parseInt(prodminTxt.getText());

            if (name.isBlank()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setContentText("Please enter a name for the name text field.");
                alert.showAndWait();

        } else if (min > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Min value cannot be greater than Max value.");
                alert.showAndWait();
            } else if (stock > max || stock < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory amount must be between minimum and maximum values.");
                alert.showAndWait();
            } else {
                Product addProduct = new Product(Integer.parseInt(prodidTxt.getText()), name, price, stock, min, max);

                // This line creates the list of parts for a new product and attaches it!
                addProduct.addAssociatedPart(apcAssParts);

                // This line adds the product to the Inventory.
                Inventory.addProduct(addProduct);

                // This block returns the user the main menu after successfully adding the product to Inventory.
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Object scene = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
                stage.setScene(new Scene((Parent) scene));
                stage.show();
            }

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please enter a valid value for the " + error + " field.");
            alert.showAndWait();
        }
    }

/**
 * This method is invoked when a user selects a part from inventory view port and presses the add part button.
 * It adds the selected part to the local observable list, and refreshes the view of the local observable list.
 * */
    @FXML
    void onActionAddProduct(ActionEvent event) {
        Part selectedPart = partstableview.getSelectionModel().getSelectedItem();
        // Sends information in case part isn't selected but tried to be added to a product.
        if (selectedPart != null) {
            apcAssParts.add(selectedPart);
            refreshAssPartTbl();
        } else {
            MainMenuController.addPartToProductDialog("Select a part", "Select a part", "Select a part to add to the Product");
        }
    }

/**
 * This method is to remove a part from the local observable parts list.
 * It is invoked by the user when they select a part from the local list view (associatedpartstableview) and press the remove button.
 * */
    @FXML
    void onActionRemoveAssPart(ActionEvent event) {
        // sends errors depending on errors in deleting a part from a product.
        if (associatedpartstableview.getSelectionModel().isEmpty()) { // This is the start of the check for an Empty selection, so you need the empty error first...
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error, no associated part selected, please select an associated part and try again.", ButtonType.OK);
            alert.showAndWait();

        } else { //So, if a part was selected, that is, the ".getSelectionModel().isEmpty()) returns as false, indicating that an item has been selected... this is where the delete action happens.
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This associated part will be deleted! Do you want to continue?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // removes an associated part from a product if conditions are met.
                int selectedPart = associatedpartstableview.getSelectionModel().getSelectedIndex();
                apcAssParts.remove(selectedPart);
                refreshAssPartTbl();
            }
        }
    }

/**
 * This method is to return the user to the Main Menu.
 * It is invoked when the user presses the cancel button.
 * All inputs from the Add Product screen are erased upon success, and no changes saved to Product class.
 * */
    @FXML
    void onActionDisplayMainMenu(ActionEvent event) throws IOException {
        // Gives user an option to stay on the current menu.
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all text field values, do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        // Returns user to main menu.
        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

/**
 * This method is a shortcut method for the programmer to make refreshing the local associated parts list a little less cumbersome.
 * That's all it does.
 * */
    void refreshAssPartTbl(){
        // refreshes the associated parts table.
        associatedpartstableview.setItems(apcAssParts);
    }

 /**
  * This method is to allow the user to search for parts.
  * It filters the parts list and provides refreshed views.
  * */
    public void onActionSearchParts(ActionEvent actionEvent) throws IOException {

        // pressing Enter is what does the search after typing in the searchbar!

        String term = searchBar.getText();
        ObservableList<Part> resultsList = searchByPartName(term);

        if (resultsList.size() == 0) {
            try {
                int partID = Integer.parseInt(term);
                Part part = getPartByNumber(partID);
                if (part != null)
                    resultsList.add(part);
                else {
                    // Keeps user on the page to search something valid.
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.initModality(Modality.APPLICATION_MODAL);
                    alert.setTitle("No Match");
                    alert.setHeaderText("Unable to locate '" + term + "' in the ingredient list, showing all ingredients...");
                    alert.showAndWait();
                }
            } catch (NumberFormatException e) {
                // keeps user on the page to search something valid.
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.setTitle("No Match");
                alert.setHeaderText("Unable to locate '" + term + "' in the ingredient list, showing all ingredients...");
                alert.showAndWait();
            }
        }
        // gives results of search.
        partstableview.setItems(resultsList);
    }

/**
 * This method enables the partial name match search function.
 * @return the observable list namedParts to the search function.
 * */
    private ObservableList<Part> searchByPartName(String partialName) {
        ObservableList<Part> namedParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = Inventory.getAllParts();
        // allows the search of partial names too.
        for (Part part : allParts) {
            if (part.getName().contains(partialName)) {
                namedParts.add(part);
            }
        }
        return namedParts;
    }

/**
 * This method extends the search function by allowing a search match to part ID number.
 * @return the part to the search function.
 * */
    private Part getPartByNumber(int partID) {
        ObservableList<Part> allParts = Inventory.getAllParts();
        // allows for searching by part ID.
        for (int i = 0; i < allParts.size(); i++) {
            Part part = allParts.get(i);

            if (part.getId() == partID) {
                return part;
            }
        }
        return null;
    }
/**
 * This method is the initialization method for the Add Product Screen.
 * It sets up the primary table views.
 * */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Setup the primary all parts table view.
        ObservableList<Part> part = Inventory.getAllParts();
        partidcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partnamecol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partinvcol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partppucol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partstableview.setItems(part);

        // Setup the associated parts table view.
        associatedpartidcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedpartnamecol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedpartinvcol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedpartppucol.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Auto assign this new Pizza Product a non-conflicting ID number in sequence.
        prodidTxt.setText(String.valueOf(Inventory.getAllProducts().size() + 1));
    }
}

