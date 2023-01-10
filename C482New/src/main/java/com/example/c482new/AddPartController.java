package com.example.c482new;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class initializes the Add Part screen.
 * The AddPartController allows a user to add a new part to the inventory using all fields, except for the part ID.
 * The part ID is generated automatically.*/
public class AddPartController implements Initializable {
    Stage stage;
    Parent scene;
    @FXML
    private RadioButton inhousebtn;
    @FXML
    private RadioButton outsourcedbtn;
    @FXML
    private TextField partidTxt;
    @FXML
    private TextField partinvTxt;
    @FXML
    private TextField partmaxTxt;
    @FXML
    private TextField partminTxt;
    @FXML
    private TextField partnameTxt;
    @FXML
    private TextField partpriceTxt;
    @FXML
    private Label parttypelabel;
    @FXML
    private TextField parttypelabelTxt;

    /** This method allows a user to go back to the main menu by clicking the cancel button.
     * This method also contains a confirmation alert to confirm the desired action.*/
    @FXML
    void onActionDisplayMainMenu(ActionEvent event) throws IOException {
        // creates an alert to get back to the main menu or stay on the screen.
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all text field values, do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /** This method saves a part to the program's inventory.
     * Multiple errors can be thrown to verify the correct data types are used for input.
     * The user can put in custom values for all fields except for the part ID, which is generated automatically. */
    @FXML
    void onActionSavePart(ActionEvent event) throws IOException {
        String error = "";
        try {
            // lists errors for input to alerts below.
        String name = partnameTxt.getText();
        error = "Inventory";
        int inventory = Integer.parseInt(partinvTxt.getText());
        error = "Price";
        double priceCost = Double.parseDouble(partpriceTxt.getText());
        error = "Max";
        int max = Integer.parseInt(partmaxTxt.getText());
        error = "Min";
        int min = Integer.parseInt(partminTxt.getText());
        // alerts pertaining to valid inputs for parts.
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
                if (inhousebtn.isSelected()) {
                    error = "Machine ID";
                    int machineID = Integer.parseInt(parttypelabelTxt.getText());
                    InHouse addPart = new InHouse(Integer.parseInt(partidTxt.getText()), name, priceCost, inventory, min, max, machineID);
                    Inventory.addPart(addPart);
                }
                if (outsourcedbtn.isSelected()) {
                    error = "Company Name";
                    String companyName = parttypelabelTxt.getText();
                    Outsourced addPart = new Outsourced(Integer.parseInt(partidTxt.getText()), name, priceCost, inventory, min, max, companyName);
                    Inventory.addPart(addPart);
                }
                // sends user back to main menu after saving their part.
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

    /** This method sets the label for an InHouse part to refer to its Machine ID.
     * This happens when the InHouse radio button is selected. */
    public void oninhouse(ActionEvent actionEvent) {
        parttypelabel.setText("Machine ID");
    }

    /** This method sets the label for an Outsourced part to refer to its Company Name.
     * This happens when the Outsourced radio button is selected. */
    public void onoutsourced(ActionEvent actionEvent) {
        parttypelabel.setText("Company Name");
    }

    /** This method automatically generates the partID for the user.
     * This is to ensure duplicate part IDs aren't used. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // auto generates new part ID.
        partidTxt.setText(String.valueOf(Inventory.getAllParts().size() + 1));
    }
}