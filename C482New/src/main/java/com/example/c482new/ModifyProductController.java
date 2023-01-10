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
 * This class initializes the Modify Product Screen.
 * The ModifyProductController allows modifications to Pizza Products, with the exception of the ID number, which is generated automatically.
 * */
public class ModifyProductController implements Initializable {
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

    /**
     * This method permits the User to interact with the search function.
     * */
    public TextField searchBar;

    // Need to init the variable that holds the product information that will be updated.
    Product prodToDelete = null;

    // Important! This is the temporary list of associated parts, that this controller works with. It needs to be initialized here, and it starts off empty.
    private ObservableList<Part> mpcassociatedparts = FXCollections.observableArrayList();

    /**
     * This method takes a selected part from the parts table view and adds it to the associated parts list mpcassociatedparts.
     * */
    @FXML
    void onActionAddModProduct(ActionEvent event) {
        Part selectedPart = partstableview.getSelectionModel().getSelectedItem();

        if (selectedPart != null) {
            mpcassociatedparts.add(selectedPart);
            associatedpartstableview.setItems(mpcassociatedparts);
        } else {
            MainMenuController.addPartToProductDialog("Select a part", "Select a part", "Select a part to add to the Product");
        }
    }

/**
 * This method removes an associated part from the product.
 * It provides an alert about it as well, requiring users to confirm the action.
 * It then calls for a refresh of the associated parts table view.
 * */
    @FXML
    void onActionRemoveAssPart(ActionEvent event) {
        if (associatedpartstableview.getSelectionModel().isEmpty()) { // This is the start of the check for an Empty selection, so you need the empty error first...
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error, no associated part selected, please select an associated part and try again.", ButtonType.OK);
            alert.showAndWait();

        } else { //So, if a part was selected, that is, the ".getSelectionModel().isEmpty()) returns as false, indicating that an item has been selected... this is where the delete action happens.
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This associated part will be deleted! Do you want to continue?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                int selectedPart = associatedpartstableview.getSelectionModel().getSelectedIndex();
                mpcassociatedparts.remove(selectedPart);
                updateAssocPartsView();
            }
        }
    }

/**
 * This method is for searching the available parts for a specific part.
 * It refreshes the Available parts table view.
 * */
    @FXML
    public void onActionSearchPart(ActionEvent event) throws IOException {
        // the event is the pressing of "Enter" key... so nothing happens when something is typed in until someone presses enter...

        String term = searchBar.getText();

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
                    partstableview.setItems(Inventory.getAllParts());
                }
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.setTitle("No Match");
                alert.setHeaderText("Unable to locate '" + term + "' in the ingredient list, showing all ingredients...");
                alert.showAndWait();
                partstableview.setItems(Inventory.getAllParts());
            }
        }
        partstableview.setItems(resultsList);
    }

/**
 * This method further extends the search function by enabling a partial name search.
 * @return a list of parts that have partial name matches to the main search function.
 * */
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

/**
 * This method further extends the search function by enabling a ID number match.
 * @return a part that matches the ID number that was searched.
 * */
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

/**
 * This method saves the modifications made by the user.
 * This method is invoked upon pressing the save button on the Modify Part Screen.
 * This method */
    @FXML
    void onActionSaveModProduct(ActionEvent event) throws IOException {

        String error = "";

        try {
            int productID = Integer.parseInt(prodidTxt.getText());
            String name = prodnameTxt.getText();

            error = "Inventory";
            int inventory = Integer.parseInt(prodinvTxt.getText());

            error = "Price";
            double priceCost = Double.parseDouble(prodpriceTxt.getText());

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
            } else if (inventory > max || inventory < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory amount must be between minimum and maximum values.");
                alert.showAndWait();
            } else {

                //This line invokes the addProduct method from the Product class, it must be done before appending the Associated Parts List!
                Product addProduct = new Product(productID, name, priceCost, inventory, min, max);

                //This line adds Associated Parts list to the product, it must be done before the product object is added to Inventory!
                addProduct.addAssociatedPart(mpcassociatedparts);

                //This line adds the Product object to Inventory!
                Inventory.addProduct(addProduct);

                // This line down below deletes the part in the variable that was made when it was modified.
                Inventory.getAllProducts().remove(prodToDelete);

                // Return the user to the main menu with this section.
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
 * This method cancels the inputs on the Modify Parts screen, and returns the user to the Main Menu screen.
 * There are no changes recorded if a user clocks the Cancel button.
 * */
    @FXML
    void onActionDisplayMainMenu(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all text field values, do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

/**
 * A shortcut method.
 * This method is used to save the programmer some time when he needs to update the Associated Parts Table view by making it a little less cumbersome to type.
 * */
    public void updateAssocPartsView(){
        associatedpartstableview.setItems(mpcassociatedparts);
    }

/**
 * This method is what populates the various fields and associatd parts list of the Modify Products window.
 * It is invoked by the user on the Main Menu, when they have selected a product and clicked the modify button.
 * */
    public void sendProduct(Product product) {
        System.out.println(product.getName());

        prodidTxt.setText(String.valueOf(product.getId()));
        prodnameTxt.setText(String.valueOf(product.getName()));
        prodinvTxt.setText(String.valueOf(product.getStock()));
        prodmaxTxt.setText(String.valueOf(product.getMax()));
        prodminTxt.setText(String.valueOf(product.getMin()));
        prodpriceTxt.setText(String.valueOf(product.getPrice()));
        mpcassociatedparts = product.getAllAssociatedParts();
        prodToDelete = product;
        updateAssocPartsView();
    }
/**
 * This method initializes the Modify Parts window.
 * That's all it does.
 * */
    public void initialize(URL url, ResourceBundle resources) {

        // All Parts Table View Section
        partidcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partnamecol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partinvcol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partppucol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partstableview.setItems(Inventory.getAllParts());

        // Associated Parts Table View Section
        associatedpartidcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedpartnamecol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedpartinvcol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedpartppucol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}

