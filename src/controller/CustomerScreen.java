/**
 * A controller class for managing customers.
 *
 * @author Kevin Miller
 */

package controller;

import interfaces.MessageUpdate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * A controller class for managing customers.
 */
public class CustomerScreen implements Initializable {

    /**
     * A button to add customers.
     */
    public Button addCustomerBTN;

    /**
     * A button to update customers.
     */
    public Button updateCustomerBTN;

    /**
     * A button to delete customers.
     */
    public Button deleteCustomerBTN;


    /**
     * A table view of customers.
     */
    public TableView customerTable;

    /**
     * A column representing the customer ID.
     */
    public TableColumn customerIdCOL;

    /**
     * A column representing the customer name.
     */
    public TableColumn customerNameCOL;

    /**
     * A column representing the customer address.
     */
    public TableColumn customerAddressCOL;

    /**
     * A column representing the customer postal code.
     */
    public TableColumn customerPostalCodeCOL;

    /**
     * A column representing the customer phone number.
     */
    public TableColumn customerPhoneNumberCOL;

    /**
     * A column representing the customer division ID.
     */
    public TableColumn customerDivisionIdCOL;

    /**
     * A column representing the customer country.
     */
    public TableColumn customerCountryCOL;

    /**
     * A text field for updating the user on changes.
     */
    public TextField customerScreen_Message_TF;

    /**
     * A flag to control checking is done only once after logging in.
     */
    private static boolean approachingChecked = false;


    /**
     * Lambda expression used to update the message to the user.
     * The lambda can also be modified in other classes to customize messages displayed to the user.
     */
    MessageUpdate update = () ->{
        Customer selectedCustomer = (Customer)customerTable.getSelectionModel().getSelectedItem();

        String message = "Customer " + selectedCustomer.getId() + " - " + selectedCustomer.getName() + " has been deleted.";
        return  message;
    };

    /**
     * Initializes the Customer screen.
     *
     * @param url The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        System.out.println("Customer screen is initialized.");

        // Check if there are any appointments starting in the next 15 minutes.
        // Display error.
       if(!approachingChecked){
           Alert alert = new Alert(Alert.AlertType.WARNING);
           alert.setTitle("Upcoming Appointments");
           alert.setContentText(checkApproachingAppointments());
           alert.showAndWait();
           approachingChecked = true;
       }

        // Initialize Table
        customerTable.setItems(TransformedCustomer.getAllTransformedCustomers());
        customerIdCOL.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerNameCOL.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerAddressCOL.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPostalCodeCOL.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerPhoneNumberCOL.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        customerDivisionIdCOL.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
        customerCountryCOL.setCellValueFactory(new PropertyValueFactory<>("countryName"));

    }

    /**
     * Transition to the Appointments screen.
     *
     * @param actionEvent the Appointment button action.
     * @throws IOException thrown if params cannot be resolved.
     */
    public void onAppointments(ActionEvent actionEvent) throws IOException {
        System.out.println("Appointments Clicked!");
        loadAppointmentScreen(actionEvent);
    }

    /**
     * Transition to the Reports screen.
     *
     * @param actionEvent Report button action.
     * @throws IOException thrown if params cannot be resolved.
     */
    public void onReports(ActionEvent actionEvent) throws IOException {
        loadReportsScreen(actionEvent);
    }


    /**
     * Transition to the Add Customer screen.
     *
     * @param actionEvent the Add button action.
     * @throws IOException thrown if params cannot be resolved.
     */
    public void onAddCustomer(ActionEvent actionEvent) throws IOException {

        System.out.println("ADD Customer clicked.");
        // Load the add customer screen
        loadAddCustomerScreen(actionEvent);
    }

    /**
     * Transition to the Update Customer screen.
     *
     * @param actionEvent the Update button action.
     * @throws IOException thrown if params cannot be resolved.
     * @throws SQLException throw if SQL statement fails to resolve.
     */
    public void onUpdateCustomer(ActionEvent actionEvent) throws IOException, SQLException {
        System.out.println("UPDATE Customer clicked.");
        Customer updatedCustomer = (Customer)customerTable.getSelectionModel().getSelectedItem();
        if(updatedCustomer == null){
            System.out.println("No Customer Selected.");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Null Selection");
            alert.setContentText("Select a customer.");
            alert.showAndWait();
            return;
        }
        UpdateCustomerScreen.setUpdatedCustomer(updatedCustomer);

        loadUpdateCustomerScreen(actionEvent);
    }

    /**
     * Deletes a selected Customer and all of their Appointments.
     *
     * @param actionEvent the Delete button action.
     * @throws SQLException throw if SQL statement fails to resolve.
     */
    public void onDeleteCustomer(ActionEvent actionEvent) throws SQLException {
        System.out.println("DELETE Customer clicked.");
        // Get the selected Customer.
        Customer selectedCustomer = (Customer)customerTable.getSelectionModel().getSelectedItem();
        TransformedCustomer selectedTransformedCustomer = (TransformedCustomer) customerTable.getSelectionModel().getSelectedItem();
        if(selectedCustomer == null){
            System.out.println("No Customer Selected.");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Null Selection");
            alert.setContentText("Select a customer.");
            alert.showAndWait();
            return;
        }
        else{
            // Confirm that user really wants to delete the product.
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the Customer, are you sure?");
            Optional<ButtonType> results =  alert.showAndWait();

            if(results.isPresent() && results.get() == ButtonType.OK){
                // Set the Customer Deleted Message
                customerScreen_Message_TF.setText(update.updateMessage());

                // Delete all appointments for that customer.
                Appointment.deleteCustomersAppointments(selectedCustomer.getId());

                // Delete the customer from the database and the container of customers.
                Customer.deleteCustomer(selectedCustomer);

                // Refresh Transformed Customers
                TransformedCustomer.deleteTransformedCustomer(selectedTransformedCustomer);
            }
        }
    }

    /**
     * Logic to handle loading the Add Customer screen.
     *
     * @param actionEvent the Add button action.
     * @throws IOException thrown if params cannot be resolved.
     */
    private void loadAddCustomerScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Add_Customer_screen.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900,600);
        stage.setTitle("Basic Scheduler");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Logic to handle loading the Update Customer screen.
     *
     * @param actionEvent the Update button action.
     * @throws IOException thrown if params cannot be resolved.
     */
    private void loadUpdateCustomerScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Update_Customer_screen.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900,600);
        stage.setTitle("Basic Scheduler");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Logic to handle loading the Appointment screen.
     *
     * @param actionEvent the Appointment button action.
     * @throws IOException thrown if params cannot be resolved.
     */
    private void loadAppointmentScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Appointment_screen.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900,600);
        stage.setTitle("Basic Scheduler");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Logic to handle loading the Reports screen.
     *
     * @param actionEvent the Report button action.
     * @throws IOException thrown if params cannot be resolved.
     */
    private void loadReportsScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Reports_screen.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900,600);
        stage.setTitle("Basic Scheduler");
        stage.setScene(scene);
        stage.show();
    }


    /**
     * Logic to check if there are appointments within the next 15 minutes.
     *
     * @return String representing a message based on upcoming appointments.
     */
    private String checkApproachingAppointments(){
        ObservableList<Appointment> approachingAppointments = FXCollections.observableArrayList();;
        String message = "";
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime soon = now.plusMinutes(15L);
        for(Appointment p : Appointment.getAllAppointments()){
            if(p.getStartDate().toLocalDateTime().isAfter(now) && p.getStartDate().toLocalDateTime().isBefore(soon)){
                approachingAppointments.add(p);
                message += p.getId() + " " + p.getStartDate().toLocalDateTime().toLocalDate() + " " + p.getStartDate().toLocalDateTime().toLocalTime() + "\n";
            }
        }
        if(message.isEmpty()){
            message = "There are no upcoming appointments";
        }
        return message;
    }
}
