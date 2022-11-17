/**
 * A controller class for managing the appointments.
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
import model.Appointment;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * A controller class for managing the appointments.
 */
public class AppointmentScreen implements Initializable {

    /**
     * Static ObservableList<Appointment> to hold appointments of the current week.
     */
    private static ObservableList<Appointment> weekAppointments = FXCollections.observableArrayList();

    /**
     * Static ObservableList<Appointment> to hold appointments of the current month.
     */
    private static ObservableList<Appointment> monthAppointments = FXCollections.observableArrayList();

    /**
     * A button to add appointments.
     */
    public Button addAppointmentBTN;

    /**
     * A button to update appointments.
     */
    public Button updateAppointmentBTN;

    /**
     * A button to delete appointments.
     */
    public Button deleteAppointmentBTN;

    /**
     * A button to go to the customer screen.
     */
    public Button appointmentsScreen_Customers_BTN;

    /**
     * A button to go to the report screen.
     */
    public Button appointmentsScreen_Reports_BTN;

    /**
     * A table view of the appointments
     */
    public TableView appointmentTable;

    /**
     * A column representing the appointment ID.
     */
    public TableColumn appointmentIdCOL;

    /**
     * A column representing the appointment title.
     */
    public TableColumn appointmentTitleCOL;

    /**
     * A column representing the appointment description.
     */
    public TableColumn appointmentDescriptionCOL;

    /**
     * A column representing the appointment location.
     */
    public TableColumn appointmentLocationCOL;

    /**
     * A column representing the appointment contact.
     */
    public TableColumn appointmentContactCOL;

    /**
     * A column representing the appointment type.
     */
    public TableColumn appointmentTypeCOL;

    /**
     * A column representing the appointment start time.
     */
    public TableColumn appointmentStartCOL;

    /**
     * A column representing the appointment end time.
     */
    public TableColumn appointmentEndCOL;

    /**
     * A column representing the appointment customer ID.
     */
    public TableColumn appointmentCustomerIdCOL;

    /**
     * A column representing the appointment user ID.
     */
    public TableColumn appointmentUserIdCOL;

    /**
     * A text field for updating the user on changes.
     */
    public TextField appointmentScreen_Message_TF;

    /**
     * The toggle group for the appointment views.
     */
    public ToggleGroup appointmentView;


    /**
     * Lambda expression used to update the message to the user.
     * The lambda can also be modified in other classes to customize messages displayed to the user.
     */
    MessageUpdate update = () ->{
        Appointment selectedAppointment = (Appointment) appointmentTable.getSelectionModel().getSelectedItem();

        String message = "Appointment " + selectedAppointment.getId() + " - " + selectedAppointment.getType() + " has been deleted.";
        return  message;
    };

    /**
     * Initalizes the Appointment screen.
     *
     * @param url The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Appointment Screen Initialized.");
        // Initialize Table
        appointmentTable.setItems(Appointment.getAllAppointments());
        appointmentIdCOL.setCellValueFactory(new PropertyValueFactory<>("id"));
        appointmentTitleCOL.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentDescriptionCOL.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointmentLocationCOL.setCellValueFactory(new PropertyValueFactory<>("location"));
        appointmentContactCOL.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        appointmentTypeCOL.setCellValueFactory(new PropertyValueFactory<>("type"));
        appointmentStartCOL.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        appointmentEndCOL.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        appointmentCustomerIdCOL.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        appointmentStartCOL.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        appointmentUserIdCOL.setCellValueFactory(new PropertyValueFactory<>("userID"));
    }

    /**
     * Go to Add Appointment screen.
     *
     * @param actionEvent the Add button action.
     * @throws IOException thrown if params cannot be resolved.
     */
    public void onAddAppointment(ActionEvent actionEvent) throws IOException {
        loadAddAppointmentScreen(actionEvent);
    }

    /**
     * Go to the Update Appointment screen.
     *
     * @param actionEvent the Update button action.
     * @throws IOException thrown if params fail to resolve.
     */
    public void onUpdateAppointment(ActionEvent actionEvent) throws IOException {
        Appointment updatedAppointment = (Appointment)appointmentTable.getSelectionModel().getSelectedItem();
        if(updatedAppointment == null){
            System.out.println("No Appointment Selected.");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Null Selection");
            alert.setContentText("Select an Appointment.");
            alert.showAndWait();
            return;
        }
        UpdateAppointmentScreen.setUpdatedAppointed(updatedAppointment);
        loadUpdateAppointmentScreen(actionEvent);
    }

    /**
     * Logic to delete appointments.
     *
     * @param actionEvent the Delete button action.
     * @throws SQLException throw if SQL statement fails to resolve.
     */
    public void onDeleteAppointment(ActionEvent actionEvent) throws SQLException {
        System.out.println("DELETE Appointment clicked.");
        // Get the selected Customer.
        Appointment selectedAppointment = (Appointment) appointmentTable.getSelectionModel().getSelectedItem();
        if(selectedAppointment == null){
            System.out.println("No Appointment Selected.");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Null Selection");
            alert.setContentText("Select an Appointment.");
            alert.showAndWait();
            return;
        }
        else{
            // Confirm that user really wants to delete the product.
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the Appointment, are you sure?");
            Optional<ButtonType> results =  alert.showAndWait();

            if(results.isPresent() && results.get() == ButtonType.OK){
                appointmentScreen_Message_TF.setText(update.updateMessage());


                // Delete the appointment from the database and the container of appointments.
                Appointment.deleteAppointment(selectedAppointment);

                // Set the weekly and monthly views to keep them current.
                setWeekAppointments();
                setMonthAppointments();
            }
        }
    }

    /**
     * Go to the Customers screen.
     *
     * @param actionEvent the Customer button action.
     * @throws IOException thrown if params cannot be resolved.
     */
    public void onCustomers(ActionEvent actionEvent) throws IOException {
        System.out.println("Customers CLICKED!");
        loadCustomerScreen(actionEvent);
    }

    /**
     * Go to Reports screen.
     *
     * @param actionEvent the Report button action.
     * @throws IOException thrown if params cannot be resolved.
     */
    public void onReports(ActionEvent actionEvent) throws IOException {
        loadReportsScreen(actionEvent);
    }



    /**
     * Logic to handle loading the customer screen.
     *
     * @param actionEvent the Customer screen action.
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
     * Logic to handle loading the Add Appointment screen.
     *
     * @param actionEvent the Add button acttion.
     * @throws IOException thrown if params cannot be resolved.
     */
    private void loadAddAppointmentScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Add_Appointment_screen.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900,600);
        stage.setTitle("Basic Scheduler");
        stage.setScene(scene);
        stage.show();
    }


    /**
     * Logic to handle loading the Update Appointment screen.
     *
     * @param actionEvent the Update button action.
     * @throws IOException thrown if params cannot be resolved.
     */
    private void loadUpdateAppointmentScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Update_Appointment_screen.fxml"));
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
     * Set to view all appointments.
     *
     * @param actionEvent the All radio button selected action.
     */
    public void onAll(ActionEvent actionEvent) {

        appointmentTable.setItems(Appointment.getAllAppointments());
    }

    /**
     * Set to view appointments happening in the current week.
     *
     * @param actionEvent the Week radio button selected action.
     */
    public void onWeek(ActionEvent actionEvent) {
        setWeekAppointments();
        appointmentTable.setItems(weekAppointments);
    }

    /**
     * Set to view appointments happening in the current month.
     *
     * @param actionEvent the Month radio button selected.
     */
    public void onMonth(ActionEvent actionEvent) {
        setMonthAppointments();
        appointmentTable.setItems(monthAppointments);
    }


    /**
     *  Logic to fill the ObservableList of appointments for the same month.
     */
    private static void setMonthAppointments(){
        monthAppointments.clear();
        for(Appointment p : Appointment.getAllAppointments()){
            if(p.getStartDate().toLocalDateTime().getMonthValue() == LocalDateTime.now().getMonthValue()){
                if(!monthAppointments.contains(p)){
                    monthAppointments.add(p);
                }
            }
        }
    }

    /**
     * Logic to fill the ObservableList of appointments for the same week.
     *
     * Appointments of the same week are defined as occurring within the same 7-day period starting with the first 7 days of the year.
     * This is done using modulo operations to test that appointment date and current date are in the same 7-day time span.
     */
    private static void setWeekAppointments(){
        weekAppointments.clear();
        for(Appointment p : Appointment.getAllAppointments()){
            double currentWeekOfYear = Math.floor(LocalDateTime.now().getDayOfYear()/7);
            double appointmentWeek = Math.floor(p.getStartDate().toLocalDateTime().getDayOfYear()/7);
            if(appointmentWeek == currentWeekOfYear){
                if(!weekAppointments.contains(p)){
                    weekAppointments.add(p);
                }
            }
        }

    }
}
