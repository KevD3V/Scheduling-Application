/**
 * A controller class for adding a customer.
 *
 * @author Kevin Miller
 */

package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.FirstLevelDivision;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * A controller class for adding a customer.
 */
public class AddCustomerScreen implements Initializable {

    /**
     * A combo box of countries for the customer.
     */
    public ComboBox<Country> newCustomerCountry_ComboBox;

    /**
     * A combo box of first level diviisions for the customer.
     */
    public ComboBox<FirstLevelDivision> newCustomerFirstLevelDivision_ComboBox;

    /**
     * A text field for customer ID. This field is not editable.
     */
    public TextField newCustomerId_TextField;

    /**
     * A text field for customer name.
     */
    public TextField newCustomerName_TextField;

    /**
     * A text field for customer address.
     */
    public TextField newCustomerAddress_TextField;

    /**
     * A text field for customer postal code.
     */
    public TextField newCustomerPostalCode_TextField;

    /**
     * A text field for customer phone number.
     */
    public TextField newCustomerPhoneNumber_TextField;

    /**
     * A button to save the new customer.
     */
    public Button newCustomerSave_Button;

    /**
     * A button to cancel creating a new customer.
     */
    public Button addCustomer_Cancel_BTN;

    /**
     * Initializes the Add Customer screen.
     *
     * @param url The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize the country drop-down combo box
        newCustomerCountry_ComboBox.setItems(Country.getAllCountries());

    }


    /**
     * Save changes to customer, and returns to the customer screen.
     *
     * @param actionEvent the Save button action.
     */
    public void onSave(ActionEvent actionEvent) {
        System.out.println("Save Clicked.");
        try{
            // Collect data from inputs
            String newName = newCustomerName_TextField.getText();
            String newAddress = newCustomerAddress_TextField.getText();
            String newPostalCode = newCustomerPostalCode_TextField.getText();
            String newPhoneNumber = newCustomerPhoneNumber_TextField.getText();
            Country country = newCustomerCountry_ComboBox.getValue();
            FirstLevelDivision fld = newCustomerFirstLevelDivision_ComboBox.getValue();

            // Calculate Timestamp Created
            LocalDateTime newLDT = LocalDateTime.now();
            Timestamp newTS = Timestamp.valueOf(newLDT);

            // Retrieve the created_by.
            String createdBy = LoginScreen.getCurrentUser().getName();

            // Retrieve the lastUpdatedBy.
            String lastUpdatedBy = createdBy;

            // Create the customer in memory, use ZERO as a placeholder for ID until the object is created in database.
            Customer newCustomer = new Customer(0, newName, newAddress, newPostalCode, newPhoneNumber, newTS, createdBy,newTS, lastUpdatedBy,fld.getId());

            // Try to insert the new Customer into the database.
            Customer.addCustomer(newCustomer);

            // Return to customer screen
            loadCustomerScreen(actionEvent);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Returns user to the Customer screen without saving changes to the customer.
     *
     * @param actionEvent the Cancel button action.
     * @throws IOException thrown if params cannot be resolved.
     */
    public void onCancel(ActionEvent actionEvent) throws IOException {
        System.out.println("Cancel Clicked.");
        loadCustomerScreen(actionEvent);
    }


    /**
     * Logic to handle loading the customer screen.
     *
     * @param actionEvent loading new screen action.
     * @throws IOException thrown if params cannot be resolved.
     */
    private void loadCustomerScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Customer_screen.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900,600);
        stage.setTitle("Basic Scheduler");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Logic to filter the First Level Division Combo box based on the value of the Country Combo Box.
     *
     * @param actionEvent Detect when user has selected a Country.
     * @throws SQLException throw if SQL statement fails to resolve.
     */
    public void onCountrySelected(ActionEvent actionEvent) throws SQLException {
        // Filter the First Level Division Combo Box based on the selection of the Country.
        // Get the countryID of the Country selected
        int selectedCountryID = newCustomerCountry_ComboBox.getValue().getId();
        newCustomerFirstLevelDivision_ComboBox.setItems(FirstLevelDivision.filterByCountry(selectedCountryID));

        // Clear selection of First Level Division
        newCustomerFirstLevelDivision_ComboBox.setValue(null);
    }
}
