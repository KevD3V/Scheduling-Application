/**
 *  The Main Class is the starting point for the application.
 *  The Login Screen will be the first screen that is loaded, and act as the login screen.
 *
 *  FUTURE ENHANCEMENT Refactor code to include a screen loading class, apply DRY principles.
 *  FUTURE ENHANCEMENT Add Logout functionality.
 *  FUTURE ENHANCEMENT
 *
 *  JAVADOCS located in /SupportingDocs
 *  @author Kevin Miller
 */

package main;

import DataBaseConnection.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;
import java.io.FileNotFoundException;
import java.sql.SQLException;

/**
 * The entry point for the application.
 */
public class Main extends Application {
    /**
     *  Initializes MainScreen view and MainScreen controller.
     *
     * @param stage of the login screen.
     * @throws Exception if parameters fail to resolve.
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Login_screen.fxml"));
        stage.setTitle("Scheduling App");
        stage.setScene(new Scene(root, 900, 600));
        stage.show();
    }

    /**
     * Start Point.
     * Responsible for opening the Java Database Connection, launching the application,
     * and closing the Java Database Connection.
     *
     * @param args passed to the main function.
     * @throws SQLException throw if SQL statement fails to resolve.
     * @throws FileNotFoundException throws if File cannot be found.
     */
    public static void main(String[] args) throws SQLException, FileNotFoundException {
        JDBC.openConnection();

        // Load all objects from database into the application.
        User.setAllUsers();
        Customer.setAllCustomers();
        Appointment.setAllAppointments();
        Contact.setAllContacts();
        FirstLevelDivision.setAllFirstLevelDivisions();
        Country.setAllCountries();
        TransformedCustomer.setAllTransformedCustomers();


        launch(args);

        // Code block to test resource bundle functionality.
        /*
        ResourceBundle rs = ResourceBundle.getBundle("main/Nat", Locale.getDefault());
        if(Locale.getDefault().getLanguage().equals("en")){
            System.out.println("System is in ENGLISH.");
            System.out.println(Locale.getDefault().getCountry());
        }
        if(Locale.getDefault().getLanguage().equals("fr")){
            System.out.println("System is in FRENCH.");
        }
        */

        // Test Data for Timestamp
         // Timestamp newTS = new Timestamp(1000000);

        // Test Customer Select Queries.
        // TEST SUCCESSFUL
        // CustomerQuery.select();

        // Code block to test Customer Insert Queries.
        // TEST SUCCESSFUL
        // int affectedRows = CustomerQuery.insert("bob", "candy cane lane", "10101", "555-555-55", newDate, "Me", newTS, "MEE", 5);

        // test Customer Update Queries
        // TEST SUCCESSFUL
        // int affectedRows = CustomerQuery.update(4, "NEW BOB","candy cane lane", "10101", "555-555-55", newDate, "Me", newTS, "MEE", 5 );

        // Code block to test Customer Delete Queries
        // TEST SUCCESSFUL
        // int affectedRows = CustomerQuery.delete(4);

        // Code block to test Appointment Insert Queries
        // TEST SUCCESSFUL
        // AppointmentDAO.insert("appt test", "not a drill", "inner appness", "new type", newTS, newTS, newTS, "Created This", newTS, "New Updater", 16,2,3 );


        // Code block to test Appointment Update Queries
        // TEST SUCCESSFUL
        // int affectedRows = AppointmentDAO.update(3,"UPDATED appt", "not a drill", "inner appness", "new type", newDate, newDate, newDate, "Created This", newTS, "New Updater", 1,2,3);

        // Code block to test Appointment Delete Queries
        // TEST SUCCESSFUL
        // int affectedRows = AppointmentDAO.delete(4);

        // Code block to test Appointment Select Queries
        // TEST SUCCESSFUL
        // AppointmentDAO.select();

        // Code block to test Country Insert Queries
        // TEST SUCCESSFUL
        // int affectedRows = CountryDAO.insert("New Country", newDate, "Country Creator", newTS, "Created This");

        // Code block to test Country Update Queries
        // TEST SUCCESSFUL
        // int affectedRows = CountryDAO.update(4,"Updated Country", newDate, "Country Creator", newTS, "Created This");

        // Code block to test Country Delete Queries
        // TEST SUCCESSFUL
        // int affectedRows = CountryDAO.delete(4);

        // Code block to test Country Select Queries
        // TEST SUCCESSFUL
        // CountryDAO.select();

        // Code block to test FLD Insert Queries
        // TEST SUCCESSFUL
        // int affectedRows = FirstLevelDivisionDAO.insert("New Division", newDate, "Division Creator", newTS, "Created This", 2);

        // Code block to test FLD Update Queries
        // TEST SUCCESSFUL
        // int affectedRows = FirstLevelDivisionDAO.update(3980,"Updated Division", newDate, "Division Creator", newTS, "Created This", 2);

        // Code block to test FLD Delete Queries
        // TEST SUCCESSFUL
        // int affectedRows = FirstLevelDivisionDAO.delete(3981);

        // Code block to test FLD Select Queries
        // TEST SUCCESSFUL
        // FirstLevelDivisionDAO.select();

        // Code block to test User Insert Queries
        // TEST SUCCESSFUL
        //int affectedRows = UserQuery.insert("New User","New Password", newDate, "Division Creator", newTS, "Created This");
        // TEST FAILED - Database did NOT automatically input Timestamps...
        //int affectedRows = UserQuery.insertNew("Test add", "testPass");

        // Code block to test User Update Queries
        // TEST SUCCESSFUL
        // int affectedRows = UserQuery.update(3,"Updated User","Updated Password", newDate, "Division Creator", newTS, "Created This");

        // Code block to test User Delete Queries
        // TEST SUCCESSFUL
        // UserQuery.delete(3);

        // Code block to test User Select Queries
        // TEST SUCCESSFUL
        // UserQuery.select();

        // Code block to test Contact Insert Queries
        // TEST SUCCESSFUL
        // int affectedRows = ContactDAO.insert("new Contact", "New Email");

        // Code block to  test Contact Update Queries
        // TEST SUCCESSFUL
        // int affectedRows = ContactDAO.update(4, "Updated Name", "Updated Email");

        // Code block to test Contact Delete Queries
        // TEST SUCCESSFUL
        // ContactDAO.delete(4);

        // Code block to test Contact Select Queries
        // TEST SUCCESSFUL
        // ContactDAO.select();


        // Condition to check if queries succeed or fail.
        /*
        if(affectedRows > 0){
            System.out.println("UPDATE SUCCESS");
        }else{
            System.out.println("UPDATE FAILED");
        }
        */

        JDBC.closeConnection();
    }
}
