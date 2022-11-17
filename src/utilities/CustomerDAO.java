/**
 * A Customer Data Access Object class for customers.
 *
 * @author Kevin Miller
 */


package utilities;

import DataBaseConnection.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * A Customer Data Access Object class for customers.
 */
public class CustomerDAO {


    /**
     * Logic to insert a new Customer into the database.
     *
     * @param name of customer.
     * @param address of customer.
     * @param postalCode of customer.
     * @param phoneNumber of customer.
     * @param createdDate of customer.
     * @param createdBy of customer.
     * @param lastUpdated of customer.
     * @param lastUpdatedBy of customer.
     * @param divisionID of customer.
     * @return the number of rows affected by the query.
     * @throws SQLException throw if SQL statement fails to resolve.
     */
    public static int insert(String name, String address, String postalCode, String phoneNumber, Timestamp createdDate, String createdBy, Timestamp lastUpdated, String lastUpdatedBy, int divisionID) throws SQLException {
        String sql = "INSERT INTO CUSTOMERS (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES(?, ?, ?,?, ?,  ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2,address);
        ps.setString(3, postalCode);
        ps.setString(4, phoneNumber);
        ps.setTimestamp(5, createdDate);
        ps.setString(6, createdBy);
        ps.setTimestamp(7,  lastUpdated);
        ps.setString(8, lastUpdatedBy);
        ps.setInt(9, divisionID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Logic to update a Customer in the database.
     *
     * @param id of customer.
     * @param name of customer.
     * @param address of customer.
     * @param postalCode of customer.
     * @param phoneNumber of customer.
     * @param createdDate of customer.
     * @param createdBy of customer.
     * @param lastUpdated of customer.
     * @param lastUpdatedBy of customer.
     * @param divisionID of customer.
     * @return the number of rows affected by the query.
     * @throws SQLException throw if SQL statement fails to resolve.
     */
    public static int update(int id,String name, String address, String postalCode, String phoneNumber, Timestamp createdDate, String createdBy, Timestamp lastUpdated, String lastUpdatedBy, int divisionID) throws SQLException {
        String sql = "UPDATE CUSTOMERS SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2,address);
        ps.setString(3, postalCode);
        ps.setString(4, phoneNumber);
        ps.setTimestamp(5, createdDate);
        ps.setString(6, createdBy);
        ps.setTimestamp(7, lastUpdated);
        ps.setString(8, lastUpdatedBy);
        ps.setInt(9, divisionID);
        ps.setInt(10, id);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Logic to delete a Customer from the database.
     *
     * @param id of the Customer to delete.
     * @return the number of rows affected by the query.
     * @throws SQLException throw if SQL statement fails to resolve.
     */
    public static int delete(int id) throws SQLException {
        String sql = "DELETE FROM CUSTOMERS WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1,id);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }


    /**
     * Logic to select all the Customers from the database to store in memory.
     *
     * @return ObservableList of Customers to be held by the Customer class.
     * @throws SQLException throw if SQL statement fails to resolve.
     */
    public static ObservableList<Customer> select() throws SQLException {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        String sql = "SELECT * FROM customers";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int customerID = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phoneNumber = rs.getString("Phone");
            Timestamp createdDate =  TimeManager.fromUTCtoLocalTS(rs.getTimestamp("Create_Date"));
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = TimeManager.fromUTCtoLocalTS(rs.getTimestamp("Last_Update"));
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int divisionID = rs.getInt("Division_ID");

            Customer customer = new Customer(customerID, customerName,address,postalCode,phoneNumber, createdDate,createdBy, lastUpdate,lastUpdatedBy,divisionID);
            allCustomers.add(customer);
        }
        return allCustomers;
    }
}
