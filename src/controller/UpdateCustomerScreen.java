/**
 * A controller class for updating a customer.
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
import model.TransformedCustomer;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.util.ResourceBundle;

/**
 * A controller class for updating a customer.
 */
public class UpdateCustomerScreen implements Initializable {

    /**
     * A static Customer to reference the current customer being updated.
     */
    private static Customer updatedCustomer = null;


    /**
     * A combo box of countries.
     */
    public ComboBox<Country> newCustomerCountry_ComboBox;

    /**
     * A combo box of first level divisions.
     */
    public ComboBox<FirstLevelDivision> newCustomerFirstLevelDivision_ComboBox;

    /**
     * A text field for customer ID.
     */
    public TextField updateCustomerId_TextField;

    /**
     * A text field for customer name.
     */
    public TextField updateCustomerName_TextField;

    /**
     * A text field for customer address.
     */
    public TextField updateCustomerAddress_TextField;

    /**
     * A text field for customer postal code.
     */
    public TextField updateCustomerPostalCode_TextField;

    /**
     * A text field for customer phone number.
     */
    public TextField updateCustomerPhoneNumber_TextField;

    /**
     * A button to save updates.
     */
    public Button updateCustomer_Save_Button;

    /**
     * A button to cancel updates.
     */
    public Button updateCustomer_Cancel_Button;

    /**
     * Initializes the Update Customer screen.
     *
     * @param url The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Load the selected customer data into the form.
        updateCustomerId_TextField.setText(String.valueOf(updatedCustomer.getId()));
        updateCustomerName_TextField.setText(updatedCustomer.getName());
        updateCustomerAddress_TextField.setText(updatedCustomer.getAddress());
        updateCustomerPostalCode_TextField.setText(updatedCustomer.getPostalCode());
        updateCustomerPhoneNumber_TextField.setText(updatedCustomer.getPhoneNumber());
        newCustomerCountry_ComboBox.setItems(Country.getAllCountries());

        // Use Division ID to get Country ID.
        // Get the FLD.
        FirstLevelDivision oldFld = FirstLevelDivision.searchById(updatedCustomer.getDivisionID());

        // Get the Country
        Country oldCountry = Country.filterById(oldFld.getCountryID());

        // Set the combo boxes
        newCustomerFirstLevelDivision_ComboBox.setItems(FirstLevelDivision.filterByCountry(oldFld.getCountryID()));

        newCustomerCountry_ComboBox.setValue(oldCountry);
        newCustomerFirstLevelDivision_ComboBox.setValue(oldFld);
    }


    /**
     * Save changes and load customer screen.
     *
     * @param actionEvent the Save button action.
     */
    public void onSave(ActionEvent actionEvent) {

        System.out.println("Save clicked.");

        try{
            // Collect data from inputs
            String newName = updateCustomerName_TextField.getText();
            String newAddress = updateCustomerAddress_TextField.getText();
            String newPostalCode = updateCustomerPostalCode_TextField.getText();
            String newPhoneNumber = updateCustomerPhoneNumber_TextField.getText();
            Country country = newCustomerCountry_ComboBox.getValue();
            FirstLevelDivision fld = newCustomerFirstLevelDivision_ComboBox.getValue();

            // Collect the updated customer ID
            int id = updatedCustomer.getId();
            // Keep Date Created when updating;

            // Calculate timestamp
            Timestamp updatedTS = Timestamp.valueOf(LocalDateTime.now());
            LocalDateTime updatedLDT = updatedTS.toLocalDateTime();
            ZonedDateTime updatedZDT = updatedLDT.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
            ZonedDateTime updatedZDT_UTC = updatedZDT.withZoneSameInstant(ZoneId.of("UTC"));
            LocalDateTime updatedLDT_UTC = updatedZDT_UTC.toLocalDateTime();

            Timestamp convertedTS = Timestamp.valueOf(updatedLDT_UTC);

            // Retrieve the lastUpdatedBy.
            String lastUpdatedBy = LoginScreen.getCurrentUser().getName();

            // Create updated customer
            Customer newerCustomer = new Customer(id, newName,newAddress,newPostalCode,newPhoneNumber, updatedCustomer.getCreatedDate(),updatedCustomer.getCreatedBy(),convertedTS,lastUpdatedBy,fld.getId());

            // Insert Customer into the database
            Customer.updateCustomer(newerCustomer);

            // Refresh Transformed Customers
            TransformedCustomer.setAllTransformedCustomers();

            // Return to customer screen
            loadCustomerScreen(actionEvent);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Load customer screen without saving changes.
     *
     * @param actionEvent the Cancel button action.
     * @throws IOException thrown if params cannot be resolved.
     */
    public void onCancel(ActionEvent actionEvent) throws IOException {
        System.out.println("Back clicked.");
        loadCustomerScreen(actionEvent);
    }


    /**
     * Logic to handle loading the customer screen.
     *
     * @param actionEvent the Customer button action.
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
     * Return the customer currently being updated.
     *
     * @return the updatedCustomer if needed.
     */
    public static Customer getUpdatedCustomer(){
        return updatedCustomer;
    }

    /**
     * Set the customer currently being updated.
     *
     * @param customer to be set as the customer currently being updated.
     */
    public static void setUpdatedCustomer(Customer customer){
        updatedCustomer = customer;
    }

    /**
     * Updates the available First Level Divisions based on the Country that is selected.
     *
     * @param actionEvent the country combox box selection action.
     * @throws SQLException thrown if SQL statment cannot be resolved.
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
