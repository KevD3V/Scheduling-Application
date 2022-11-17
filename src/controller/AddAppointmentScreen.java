/**
 * A controller class for adding an appointment.
 *
 * @author Kevin Miller
 */


package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;
import utilities.TimeManager;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
import java.util.ResourceBundle;

/**
 * A controller class for adding an appointment.
 */
public class AddAppointmentScreen implements Initializable {

    /**
     * An ObservableList<String> to represent hours.
     */
    ObservableList<String> hours = FXCollections.observableArrayList();

    /**
     * An ObservableList<String> to represent minutes.
     */
    ObservableList<String> minutes = FXCollections.observableArrayList();

    /**
     * A text field for appointment title.
     */
    public TextField addAppointment_Title_TF;

    /**
     * A text field for appointment description.
     */
    public TextField addAppointment_Description_TF;

    /**
     * A text field for appointment location.
     */
    public TextField addAppointment_Location_TF;

    /**
     * A text field for appointment type.
     */
    public TextField addAppointment_Type_TF;

    /**
     * A text field for appointment customer ID.
     */
    public TextField addAppointment_CustomerID_TF;

    /**
     * A text field for appointment user ID.
     */
    public TextField addAppointment_UserID_TF;

    /**
     * A comboxbox of contacts for the appointment.
     */
    public ComboBox<Contact> addAppointment_Contact_CB;

    /**
     * A date picker to choose appointment start date.
     */
    public DatePicker addAppointment_StartDate_DP;

    /**
     * A date picker to choose appointment end date.
     */
    public DatePicker addAppointment_EndDate_DP;

    /**
     * A combo box of Strings to represent the start hour for the appointment.
     */
    public ComboBox<String> addAppointment_StartHour_CB;

    /**
     * A combo box of Strings to represent the start minute for the appointment.
     */
    public ComboBox<String> addAppointment_StartMinute_CB;

    /**
     * A combo box of Strings to represent the end hour for the appointment.
     */
    public ComboBox<String> addAppointment_EndHour_CB;

    /**
     * A combo box of Strings to represent the end minute for the appointment.
     */
    public ComboBox<String> addAppointment_EndMinute_CB;

    /**
     * Initializes the Add Appointment screen.
     *
     * @param url The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize all Combo Boxes
        hours.addAll("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
                "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
        minutes.addAll("00", "15", "30", "45");
        addAppointment_StartHour_CB.setItems(hours);
        addAppointment_EndHour_CB.setItems(hours);
        addAppointment_StartMinute_CB.setItems(minutes);
        addAppointment_EndMinute_CB.setItems(minutes);
        addAppointment_Contact_CB.setItems(Contact.getAllContacts());
    }

    /**
     * Save the appointment and return to the Appointment screen.
     *
     * @param actionEvent the Save button action.
     */
    public void onSave(ActionEvent actionEvent) {
        System.out.println("Save Clicked.");
        try{
            // Check that DatePicker is not empty.
            if(addAppointment_StartDate_DP.getValue() == null || addAppointment_EndDate_DP.getValue() == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Incomplete Form");
                alert.setContentText("Please fill in all Dates of the form.");
                alert.showAndWait();
                return;
            }

            // Check that Combo Boxes for times are not empty.
            if(addAppointment_StartHour_CB.getSelectionModel().isEmpty() || addAppointment_StartMinute_CB.getSelectionModel().isEmpty()
                || addAppointment_EndHour_CB.getSelectionModel().isEmpty() || addAppointment_EndMinute_CB.getSelectionModel().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Incomplete Form");
                alert.setContentText("Please fill in all Times of the form.");
                alert.showAndWait();
                return;
            }

            if(addAppointment_Contact_CB.getSelectionModel().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Incomplete Form");
                alert.setContentText("Please select a contact.");
                alert.showAndWait();
                return;
            }

            // Collect data from text fields
            String newTitle = addAppointment_Title_TF.getText();
            String newDescription = addAppointment_Description_TF.getText();
            String newLocation = addAppointment_Location_TF.getText();
            String newType = addAppointment_Type_TF.getText();
            String newCustomerId = addAppointment_CustomerID_TF.getText();
            String newUserId = addAppointment_UserID_TF.getText();

            // Check if any strings were left empty.
            if(newTitle.isEmpty() || newDescription.isEmpty() || newLocation.isEmpty()
                    || newType.isEmpty()
                    || newCustomerId.isEmpty()
                    || newUserId.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Incomplete Form");
                alert.setContentText("Please fill out all fields of the form.");
                alert.showAndWait();
                return;
            }


            // Collect data from the date pickers.
            LocalDate newStartDate = addAppointment_StartDate_DP.getValue();
            LocalDate newEndDate = addAppointment_EndDate_DP.getValue();

            // Collect data from the combo boxes.
            String newStartHour = addAppointment_StartHour_CB.getValue();
            String newStartMinute = addAppointment_StartMinute_CB.getValue();
            String newEndHour = addAppointment_EndHour_CB.getValue();
            String newEndMinute = addAppointment_EndMinute_CB.getValue();
            Contact newContact = addAppointment_Contact_CB.getValue();

            // Convert local date data and time data to local date time
            LocalDateTime newStartLDT = LocalDateTime.of(newStartDate.getYear(), newStartDate.getMonthValue(), newStartDate.getDayOfMonth(),Integer.parseInt(newStartHour), Integer.parseInt(newStartMinute));
            LocalDateTime newEndLDT = LocalDateTime.of(newEndDate.getYear(), newEndDate.getMonthValue(), newEndDate.getDayOfMonth(),Integer.parseInt(newEndHour), Integer.parseInt(newEndMinute));

            Timestamp newStartTS = Timestamp.valueOf(newStartLDT);
            Timestamp newEndTS = Timestamp.valueOf(newEndLDT);

            // Calculate Timestamp of creation.
            Timestamp newCreatedTS = Timestamp.valueOf(LocalDateTime.now());

            // Retrieve the created_by.
            String createdBy = LoginScreen.getCurrentUser().getName();

            // Retrieve the lastUpdatedBy.
            String lastUpdatedBy = createdBy;

            // Check to make sure EndTS comes AFTER StartTS
            if(TimeManager.checkStartBeforeEnd(newStartTS, newEndTS)){
                System.out.println("START AND END MAKE SENSE!!!");

                if(TimeManager.checkWithinBusinessHours(newStartTS, newEndTS)){
                    System.out.println("Business hours are fine!");

                    // Check for overlapping appointments.
                    if(Appointment.checkAppointmentOverlaps(Integer.parseInt(newCustomerId),0,newStartTS,newEndTS)){
                        System.out.println("No Overlaps!!!");

                        // Create the appointment in memory, use ZERO as a placeholder for ID until the object is created in database.
                        Appointment newAppointment = new Appointment(0,newTitle, newDescription,newLocation,newType, newStartTS,newEndTS, newCreatedTS,createdBy,newCreatedTS,lastUpdatedBy,Integer.parseInt(newCustomerId), Integer.parseInt(newUserId),newContact.getId());

                        // Try to insert the new Appointment into the database.
                        Appointment.addAppointment(newAppointment);

                        // Return to customer screen
                        loadAppointmentScreen(actionEvent);
                    }
                    else{
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Overlapping Appointments");
                        alert.setContentText("Appointment schedules cannot overlap. Check your appointments and try again.");
                        alert.showAndWait();
                        return;
                    }
                }
                else{
                    // Display error for Appointment not being scheduled during business hours.
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Sorry, we're closed!");
                    alert.setContentText("Appointments must be scheduled on the same day during business hours: 8AM - 10PM EST.");
                    alert.showAndWait();
                    return;
                }
            }
            else{
                // Display error for Start and End Times not being in logical order.
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Logic Error");
                alert.setContentText("Start Time must come before End Time.");
                alert.showAndWait();
                return;
            }
        }
        catch(Exception e){
            e.printStackTrace();

            // Any errors left over are from selecting a userID or customerID that is not in the database.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Something went wrong");
            alert.setContentText("Please confirm the user ID and customer ID, then try again.");
            alert.showAndWait();
            return;
        }
    }

    /**
     * Return to the Appointment screen without saving changes.
     *
     * @param actionEvent the Cancel button action.
     * @throws IOException thrown if params fail to resolve.
     */
    public void onCancel(ActionEvent actionEvent) throws IOException {
        loadAppointmentScreen(actionEvent);
    }

    /**
     * Logic to handle loading the Appointment screen.
     *
     * @param actionEvent loading new screen action.
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
}
