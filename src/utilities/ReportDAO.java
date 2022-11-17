/**
 * A Report Data Access Object class for reports.
 *
 * @author Kevin Miller
 */

package utilities;

import DataBaseConnection.JDBC;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * A Report Data Access Object class for reports.
 */
public class ReportDAO {


    /**
     * Logic to generate the customers report by type and month.
     *
     * @return String representation of the report.
     * @throws SQLException throw if SQL statement fails to resolve.
     */
    public static String customersByTypeMonth() throws SQLException {
        String report = "Quantity - Type - Month\n";
        String sql = "SELECT COUNT(*) AS Quantity, Type , month(start) AS Month  FROM appointments group by Type, Start";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int quantity = rs.getInt("Quantity");
            String type = rs.getString("Type");
            int month = rs.getInt("Month");
            report += quantity + " - " + type + " - " + month + "\n\n";
        }
        return report;
    }


    /**
     * Logic to generate the contact's schedule.
     *
     * @return String representation of the report.
     * @throws SQLException throw if SQL statement fails to resolve.
     */
    public static String contactSchedule() throws SQLException {
        String report = "Contact Schedule\n" + "Name - Appointment ID - Title - Type - Description - Start - End - Customer ID\n\n";
        String sql = "SELECT Contact_Name, Appointment_ID, Title, Type, Description, Start, End, Customer_ID FROM appointments INNER JOIN contacts ON Contacts.Contact_ID=Appointments.Contact_ID Order BY Contact_Name, Start";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            String name = rs.getString("Contact_Name");
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String type = rs.getString("Type");
            String description = rs.getString("Description");
            Timestamp start = rs.getTimestamp("Start");
            Timestamp end = rs.getTimestamp("End");
            int customerID = rs.getInt("Customer_ID");

            report += name + " - " + appointmentID + " - " + title  + " - " + type + " - " + description + " - " + start + " - " + end + " - " + customerID + "\n";
        }
        return report;
    }


    /**
     * Logic to generate customer's total schedule time report.
     *
     * @return String representation of the report.
     * @throws SQLException throw if SQL statement fails to resolve.
     */
    public static String customerTotalTime() throws SQLException {
        String report = "Customer's Total Schedule Time\n" + "Customer ID - Total Time(Minutes)\n";
        String sql = "SELECT  Customer_ID, SUM(time_to_sec(end)-time_to_sec(start)) / 60 AS Time FROM appointments GROUP BY customer_ID ORDER BY TIME desc;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int id = rs.getInt("Customer_ID");
            float time = rs.getFloat("Time");


            report += id + " - " + time + "\n";
        }
        return report;
    }





}


