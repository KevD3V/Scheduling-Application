/**
 * A Contact Data Access Object class for contacts.
 *
 * @author Kevin Miller
 */
package utilities;

import DataBaseConnection.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import java.sql.*;

/**
 * A Contact Data Access Object class for contacts.
 */
public class ContactDAO {

    /**
     * Logic to insert a new Contact into the database.
     *
     * @param name of contact.
     * @param email of contact.
     * @return the number of rows affected by the query.
     * @throws SQLException throw if SQL statement fails to resolve.
     */
    public static int insert(String name, String email) throws SQLException {
        String sql = "INSERT INTO contacts (Contact_Name, Email) VALUES(?,?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2,email);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Logic to update a Contact in the database.
     *
     * @param id of contact.
     * @param name of contact.
     * @param email of contact.
     * @return the number of rows affected by the query.
     * @throws SQLException throw if SQL statement fails to resolve.
     */
    public static int update(int id, String name, String email) throws SQLException {
        String sql = "UPDATE contacts SET Contact_Name=?, Email=? WHERE Contact_ID=?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2,email);
        ps.setInt(3,id);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }


    /**
     * Logic to delete a Contact from the database.
     *
     * @param id of contact.
     * @return the number of rows affected by the query.
     * @throws SQLException throw if SQL statement fails to resolve.
     */
    public static int delete(int id) throws SQLException {
        String sql = "DELETE FROM contacts WHERE Contact_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1,id);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }


    /**
     *  Logic to select all the Contacts from the database to store in memory.
     *
     * @return ObservableList of Contacts to be held by the Contact class.
     * @throws SQLException throw if SQL statement fails to resolve.
     */
    public static ObservableList<Contact> select() throws SQLException {
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();
        String sql = "SELECT * FROM contacts";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int contactID = rs.getInt("Contact_ID");
            String name = rs.getString("Contact_Name");
            String email = rs.getString("Email");
            Contact contact = new Contact(contactID, name, email);
            allContacts.add(contact);
        }

        return allContacts;
    }
}
