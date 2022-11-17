/**
 * A controller class for updating an appointment.
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
import model.Appointment;
import model.Contact;
import utilities.TimeManager;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
import java.util.ResourceBundle;

/**
 * A controller class for updating an appointment.
 */
public class UpdateAppointmentScreen implements Initializable {

    /**
     * A static Appointment to reference the current appointment being updated.
     */
    private static Appointment updatedAppointment = null;


    /**
     * An ObservableList<String> to represent hours.
     */
    ObservableList<String> hours = FXCollections.observableArrayList();

    /**
     * An ObservableList<String> to represent minutes.
     */
    ObservableList<String> minutes = FXCollections.observableArrayList();

    /**
     * A text field for appointment ID.
     */
    public TextField updateAppointment_Id_TF;

    /**
     * A text field for appointment title.
     */
    public TextField updateAppointment_Title_TF;

    /**
     * A text field for appointment description.
     */
    public TextField updateAppointment_Description_TF;

    /**
     * A text field for appointment location.
     */
    public TextField updateAppointment_Location_TF;

    /**
     * A text field for appointment type.
     */
    public TextField updateAppointment_Type_TF;

    /**
     * A text field for appointment customer ID.
     */
    public TextField updateAppointment_CustomerId_TF;

    /**
     * A text field for appointment user ID.
     */
    public TextField updateAppointment_UserID_TF;

    /**
     * A combo box of contacts for the appointment.
     */
    public ComboBox<Contact> updateAppointment_Contact_CB;

    /**
     * A date picker to choose appointment start date.
     */
    public DatePicker updateAppointment_StartDate_DP;

    /**
     * A date picker to choose appointment end date.
     */
    public DatePicker updateAppointment_EndDate_DP;

    /**
     * A combo box of Strings to represent the start hour for the appointment.
     */
    public ComboBox<String> updateAppointment_StartHour_CB;

    /**
     * A combo box of Strings to represent the start minute for the appointment.
     */
    public ComboBox<String> updateAppointment_StartMinute_CB;

    /**
     * A combo box of Strings to represent the end hour for the appointment.
     */
    public ComboBox<String> updateAppointment_EndHour_CB;

    /**
     * A combo box of Strings to represent the end minute for the appointment.
     */
    public ComboBox<String> updateAppointment_EndMinute_CB;

    /**
     * A button to save the appointment
     */
    public Button onSave;

    /**
     * A button to cancel the update.
     */
    public Button onCancel;

    /**
     * Initializes the Update Appointment screen.
     *
     * @param url The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize the Combo Boxes
        hours.addAll("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
                "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
        minutes.addAll("00", "15", "30", "45");
        updateAppointment_Contact_CB.setItems(Contact.getAllContacts());
        updateAppointment_StartHour_CB.setItems(hours);
        updateAppointment_StartMinute_CB.setItems(minutes);
        updateAppointment_EndHour_CB.setItems(hours);
        updateAppointment_EndMinute_CB.setItems(minutes);

        // Load the selected appointment text field data into the form.
        updateAppointment_Id_TF.setText(String.valueOf(updatedAppointment.getId()));
        updateAppointment_Title_TF.setText(updatedAppointment.getTitle());
        updateAppointment_Description_TF.setText(updatedAppointment.getDescription());
        updateAppointment_Location_TF.setText(updatedAppointment.getLocation());
        updateAppointment_Type_TF.setText(updatedAppointment.getType());
        updateAppointment_CustomerId_TF.setText(String.valueOf(updatedAppointment.getCustomerID()));
        updateAppointment_UserID_TF.setText(String.valueOf(updatedAppointment.getUserID()));

        // Obtain the Date and Time data and convert from UTC out of the database to system default into the form.
        Timestamp inputStartTS = updatedAppointment.getStartDate();
        Timestamp inputEndTS = updatedAppointment.getEndDate();
        LocalDateTime inputStartLDT = inputStartTS.toLocalDateTime();
        LocalDateTime inputEndLDT = inputEndTS.toLocalDateTime();


        // Set the values of the combox boxes.
        updateAppointment_Contact_CB.setValue(Contact.searchById(updatedAppointment.getContactID()));
        updateAppointment_StartHour_CB.setValue(String.valueOf(inputStartLDT.getHour()));
        updateAppointment_StartMinute_CB.setValue(String.valueOf(inputStartLDT.getMinute()));
        updateAppointment_EndHour_CB.setValue(String.valueOf(inputEndLDT.getHour()));
        updateAppointment_EndMinute_CB.setValue(String.valueOf(inputEndLDT.getMinute()));

        // Set the values of the Date picker
        LocalDate startLocalDate = LocalDate.of(inputEndLDT.getYear(),inputStartLDT.getMonthValue(),inputStartLDT.getDayOfMonth());
        updateAppointment_StartDate_DP.setValue(startLocalDate);

        LocalDate endLocalDate = LocalDate.of(inputEndLDT.getYear(),inputEndLDT.getMonthValue(),inputEndLDT.getDayOfMonth());
        updateAppointment_EndDate_DP.setValue(endLocalDate);
    }

    /**
     * Save changed to the appointment and return to the appointment screen.
     *
     * @param actionEvent the Save button action.
     */
    public void onSave(ActionEvent actionEvent) {
        try {
            // Check that DatePicker is not empty.
            if(updateAppointment_StartDate_DP.getValue() == null || updateAppointment_EndDate_DP.getValue() == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Incomplete Form");
                alert.setContentText("Please fill in all Dates of the form.");
                alert.showAndWait();
                return;
            }

            // Check that Combo Boxes for times are not empty.
            if(updateAppointment_StartHour_CB.getSelectionModel().isEmpty() || updateAppointment_StartMinute_CB.getSelectionModel().isEmpty()
                    || updateAppointment_EndHour_CB.getSelectionModel().isEmpty() || updateAppointment_EndMinute_CB.getSelectionModel().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Incomplete Form");
                alert.setContentText("Please fill in all Times of the form.");
                alert.showAndWait();
                return;
            }

            if(updateAppointment_Contact_CB.getSelectionModel().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Incomplete Form");
                alert.setContentText("Please select a contact.");
                alert.showAndWait();
                return;
            }

            // Collect data from text fields
            String newTitle = updateAppointment_Title_TF.getText();
            String newDescription = updateAppointment_Description_TF.getText();
            String newLocation = updateAppointment_Location_TF.getText();
            String newType = updateAppointment_Type_TF.getText();
            String newCustomerId = updateAppointment_CustomerId_TF.getText();
            String newUserId = updateAppointment_UserID_TF.getText();

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
            LocalDate newStartDate = updateAppointment_StartDate_DP.getValue();
            LocalDate newEndDate = updateAppointment_EndDate_DP.getValue();

            // Collect data from the combo boxes.
            String newStartHour = updateAppointment_StartHour_CB.getValue();
            String newStartMinute = updateAppointment_StartMinute_CB.getValue();
            String newEndHour = updateAppointment_EndHour_CB.getValue();
            String newEndMinute = updateAppointment_EndMinute_CB.getValue();
            Contact newContact = updateAppointment_Contact_CB.getValue();

            // Convert local date data and time data to local date time
            LocalDateTime newStartLDT = LocalDateTime.of(newStartDate.getYear(), newStartDate.getMonthValue(), newStartDate.getDayOfMonth(), Integer.parseInt(newStartHour), Integer.parseInt(newStartMinute));
            LocalDateTime newEndLDT = LocalDateTime.of(newEndDate.getYear(), newEndDate.getMonthValue(), newEndDate.getDayOfMonth(), Integer.parseInt(newEndHour), Integer.parseInt(newEndMinute));

            // Convert LDT to TS
            Timestamp newStartTS = Timestamp.valueOf(newStartLDT);
            Timestamp newEndTS = Timestamp.valueOf(newEndLDT);

            // Get created Timestamp.
            Timestamp newCreatedTS = updatedAppointment.getCreatedDate();

            // Retrieve the created_by.
            String createdBy = updatedAppointment.getCreatedBy();

            // Retrieve the lastUpdatedBy.
            String lastUpdatedBy = LoginScreen.getCurrentUser().getName();

            if(TimeManager.checkStartBeforeEnd(newStartTS, newEndTS)) {
                System.out.println("START AND END MAKE SENSE!!!");

                if(TimeManager.checkWithinBusinessHours(newStartTS, newEndTS)) {
                    System.out.println("Business hours are fine!");

                    // Check for overlapping appointments.
                    if(Appointment.checkAppointmentOverlaps(Integer.parseInt(newCustomerId),updatedAppointment.getId(),newStartTS,newEndTS)) {
                        System.out.println("No Overlaps!!!");

                        // Create the appointment in memory, use ZERO as a placeholder for ID until the object is created in database.
                        Appointment newAppointment = new Appointment(updatedAppointment.getId(), newTitle, newDescription, newLocation, newType, newStartTS, newEndTS, newCreatedTS, createdBy, newCreatedTS, lastUpdatedBy,  Integer.parseInt(newCustomerId), Integer.parseInt(newUserId), newContact.getId());

                        // Try to insert the new Appointment into the database.
                        Appointment.updateAppointment(newAppointment);

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
     * Return to the appointment screen without saving changes.
     *
     * @param actionEvent the Cancel button action.
     * @throws IOException thrown if params cannot be resolved.
     */
    public void onCancel(ActionEvent actionEvent) throws IOException {
        loadAppointmentScreen(actionEvent);
    }

    /**
     * @return appointment that is being updated.
     */
    public static Appointment getUpdatedAppointment(){
        return updatedAppointment;
    }

    /**
     * @param appointment to set as the updated appointment.
     */
    public static void setUpdatedAppointed(Appointment appointment){
        updatedAppointment = appointment;
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
}
