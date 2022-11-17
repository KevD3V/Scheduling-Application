/**
 * A controller class for generating reports.
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.Report;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * A controller class for generating report
 */
public class ReportsScreen implements Initializable {


    /**
     * An ObservableList<String> to represent the report.
     */
    ObservableList<String> reports = FXCollections.observableArrayList();


    /**
     * A button to go to the customer screen.
     */
    public Button reportsScreen_Customers_BTN;

    /**
     * A button to go to the appointment screen.
     */
    public Button reportsScreen_Appointments_BTN;

    /**
     * A combox box of Strings to describe available reports to generate.
     */
    public ComboBox<String> reportScreen_Report_CB;

    /**
     * A text area to display the reports.
     */
    public TextArea reportScreen_Report_TA;

    /**
     * Initialize the Reports screen.
     *
     * @param url The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reports.addAll("Total number of customer appointments by type and month",
                        "Schedule for each contact",
                        "Total number of customer appointments by customer");
        reportScreen_Report_CB.setItems(reports);
    }

    /**
     * Go to the Customers screen.
     *
     * @param actionEvent the Customer button action.
     * @throws IOException thrown if params cannot be resolved.
     */
    public void onCustomers(ActionEvent actionEvent) throws IOException {
        loadCustomerScreen(actionEvent);
    }

    /**
     * Go to the Appointments screen.
     *
     * @param actionEvent the Appointment button action.
     * @throws IOException thrown if params cannot be resolved.
     */
    public void onAppointments(ActionEvent actionEvent) throws IOException {
        loadAppointmentScreen(actionEvent);
    }


    /**
     * Logic to select and generate different reports.
     * @param actionEvent the Report combo box selection action.
     * @throws SQLException throw if SQL statement fails to resolve.
     */
    public void onSelectReport(ActionEvent actionEvent) throws SQLException {
        if(reportScreen_Report_CB.getSelectionModel().isSelected(0)){
            reportScreen_Report_TA.setText(Report.getCustomerByTypeMonthReport());
        }
        else if(reportScreen_Report_CB.getSelectionModel().isSelected(1)){
            reportScreen_Report_TA.setText(Report.getContactSchedule());
        }
        else if(reportScreen_Report_CB.getSelectionModel().isSelected(2)){
            reportScreen_Report_TA.setText(Report.getCustomerTotalAppointmentTime());
        }
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
