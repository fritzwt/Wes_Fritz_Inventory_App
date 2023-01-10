package com.example.c482new;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class creates the Modify Part page.
 * The ModifyPartController allows a user to modify a part and save it back to the Inventory.
 * All fields populate on this form from the Inventory.
 * A user can change all fields, except for the partID. */
public class ModifyPartController {
    Stage stage;
    Parent scene;
    @FXML
    private RadioButton inhousebtn;
    @FXML
    private RadioButton outsourcedbtn;
    @FXML
    public TextField partidTxt;
    @FXML
    private TextField partinvTxt;
    @FXML
    private TextField partmaxTxt;
    @FXML
    private TextField partminTxt;
    @FXML
    public TextField partnameTxt;
    @FXML
    private TextField partpriceTxt;
    @FXML
    private Label parttypelabel;
    @FXML
    private TextField parttypeTxt;
    @FXML
    private static Part sendPart = null;
    Part partToDelete = null;

    /** This method allows a user to go back to the main menu by clicking the cancel button.
     * This method also contains a confirmation alert to confirm the desired action.*/
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


    // Use this one if the other ones don't work!!
    /** This method saves a modified part back to the program's inventory.
     * Multiple errors can be thrown to verify the correct data types are used for input.
     * The user can put in custom values for all fields except for the part ID, which is generated automatically. */
    @FXML
    void onActionSaveModPart(ActionEvent event) throws IOException {
        String error = "";
        try {
        int partID = Integer.parseInt(partidTxt.getText());
        String name = partnameTxt.getText();
        error = "Inventory";
        int inventory = Integer.parseInt(partinvTxt.getText());
        error = "Price";
        double priceCost = Double.parseDouble(partpriceTxt.getText());
        error = "Max";
        int max = Integer.parseInt(partmaxTxt.getText());
        error = "Min";
        int min = Integer.parseInt(partminTxt.getText());
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
                    int machineID = Integer.parseInt(parttypeTxt.getText());
                    InHouse addPart = new InHouse(partID, name, priceCost, inventory, min, max, machineID);
                    Inventory.addPart(addPart);
                }
                if (outsourcedbtn.isSelected()) {
                    String companyName = parttypeTxt.getText();
                    Outsourced addPart = new Outsourced(partID, name, priceCost, inventory, min, max, companyName);
                    Inventory.addPart(addPart);
                }
                Inventory.getAllParts().remove(partToDelete);
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

    /** This method allows part informtation to be passed screen to screen.
     * This covers both instances of having an InHouse part as well as an Outsourced part.
     * @param part This parameter takes information from part objects, allowing information to pass between screens.*/
    public void sendPart(Part part) {
        System.out.println(part.getName());
        partidTxt.setText(String.valueOf(part.getId()));
        partnameTxt.setText(String.valueOf(part.getName()));
        partinvTxt.setText(String.valueOf(part.getStock()));
        partmaxTxt.setText(String.valueOf(part.getMax()));
        partminTxt.setText(String.valueOf(part.getMin()));
        partpriceTxt.setText(String.valueOf(part.getPrice()));
        partToDelete = part;
        if (part instanceof InHouse) {
            parttypelabel.setText("Machine ID");
            inhousebtn.setSelected(true);
            InHouse inHouse = (InHouse) part;
            parttypeTxt.setText(String.valueOf(inHouse.getMachineId()));
        } else {
            parttypelabel.setText("Company Name");
            outsourcedbtn.setSelected(true);
            Outsourced outsourced = (Outsourced) part;
            parttypeTxt.setText(String.valueOf(outsourced.getCompanyName()));
        }
    }

    /** This method changes a modified part to an InHouse part when selected.
     * The part is then able to be saved back to the Inventory as an InHouse part. */
    public void oninhouse(ActionEvent actionEvent) {
        parttypelabel.setText("Machine ID");
    }
    /** This method changes a modified part to an Outsourced part when selected.
     * The part is then able to be saved back to the Inventory as an Outsourced part. */
    public void onoutsourced(ActionEvent actionEvent) {
        parttypelabel.setText("Company Name");
    }
}