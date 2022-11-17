/**
 * An Appointment Data Access Object class for appointments.
 *
 * @author Kevin Miller
 */

package utilities;

import DataBaseConnection.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import java.sql.*;

/**
 *  An Appointment Data Access Object class for appointments.
 */
public class AppointmentDAO {

    /**
     * Logic to insert a new Appointment into the database.
     *
     * @param title of appointment.
     * @param description of appointment.
     * @param location of appointment.
     * @param type of appointment.
     * @param start of appointment.
     * @param end of appointment.
     * @param create_Date of appointment.
     * @param created_By of appointment.
     * @param last_Update of appointment.
     * @param last_Updated_By of appointment.
     * @param customer_ID of appointment.
     * @param user_ID of appointment.
     * @param contact_ID of appointment.
     * @return the number of rows affected by the query.
     * @throws SQLException throw if SQL statement fails to resolve.
     */
    public static int insert(String title, String description, String location, String type, Timestamp start, Timestamp end, Timestamp create_Date, String created_By, Timestamp last_Update, String last_Updated_By, int customer_ID, int user_ID, int contact_ID) throws SQLException {
        String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES(?, ?, ?,?, ?,  ?, ?, ?, ?,?,?,?,?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2,description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, start);
        ps.setTimestamp(6, end);
        ps.setTimestamp(7, create_Date);
        ps.setString(8, created_By);
        ps.setTimestamp(9,  last_Update);
        ps.setString(10, last_Updated_By);
        ps.setInt(11, customer_ID);
        ps.setInt(12, user_ID);
        ps.setInt(13, contact_ID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Logic to update an Appointment in the database.
     *
     * @param id of appointment.
     * @param title of appointment.
     * @param description of appointment.
     * @param location of appointment.
     * @param type of appointment.
     * @param start of appointment.
     * @param end of appointment.
     * @param create_Date of appointment.
     * @param created_By of appointment.
     * @param last_Update of appointment.
     * @param last_Updated_By of appointment.
     * @param customer_ID of appointment.
     * @param user_ID of appointment.
     * @param contact_ID of appointment.
     * @return the number of rows affected by the query.
     * @throws SQLException throw if SQL statement fails to resolve.
     */
    public static int update(int id,String title, String description, String location, String type, Timestamp start, Timestamp end, Timestamp create_Date, String created_By, Timestamp last_Update, String last_Updated_By, int customer_ID, int user_ID, int contact_ID) throws SQLException {
        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2,description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, start);
        ps.setTimestamp(6, end);
        ps.setTimestamp(7, create_Date);
        ps.setString(8, created_By);
        ps.setTimestamp(9, last_Update);
        ps.setString(10, last_Updated_By);
        ps.setInt(11, customer_ID);
        ps.setInt(12, user_ID);
        ps.setInt(13, contact_ID);
        ps.setInt(14, id);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }


    /**
     * Logic to delete an Appointment from the database.
     *
     * @param id of the Appointment to delete.
     * @return the number of rows affected by the query.
     * @throws SQLException throw if SQL statement fails to resolve.
     */
    public static int delete(int id) throws SQLException {
        String sql = "DELETE FROM APPOINTMENTS WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1,id);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }


    /**
     * Logic to delete all appointments from a specific customer.
     *
     * @param customerId to search for when deleting appointments.
     * @return the number of rows affected by the query.
     * @throws SQLException throw if SQL statement fails to resolve.
     */
    public static int deleteAppointmentsFromCustomer(int customerId) throws SQLException {
        String sql = "DELETE FROM APPOINTMENTS WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1,customerId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }



    /**
     * Logic to select all the Appointments from the database to store in memory.
     *
     * @return ObservableList of Appointments to be held by the Appointment class.
     * @throws SQLException throw if SQL statement fails to resolve.
     */
    public static ObservableList<Appointment> select() throws SQLException {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            Timestamp start = rs.getTimestamp("Start");
            Timestamp end = rs.getTimestamp("End");
            Timestamp createdDate = rs.getTimestamp("Create_Date");
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contactID = rs.getInt("Contact_ID");

            Appointment appointment = new Appointment(appointmentID, title, description ,location ,type, start ,end, createdDate,createdBy,lastUpdate, lastUpdatedBy,customerID,userID,contactID);
            allAppointments.add(appointment);
        }
        return allAppointments;
    }
}
