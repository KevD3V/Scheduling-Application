/**
 * A User Data Access Object class for users.
 *
 * @author Kevin Miller
 */
package utilities;

import DataBaseConnection.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;
import java.sql.*;

/**
 * A User Data Access Object class for users.
 */
public class UserDAO {

    /**
     * Logic to insert a new User into the database.
     *
     * @param name of user.
     * @param password of user.
     * @param createdDate of user.
     * @param createdBy of user.
     * @param lastUpdated of user.
     * @param lastUpdatedBy of user.
     * @return the number of rows affected by the query.
     * @throws SQLException throw if SQL statement fails to resolve.
     */
    public static int insert(String name, String password, Date createdDate, String createdBy, Timestamp lastUpdated, String lastUpdatedBy) throws SQLException {
        String sql = "INSERT INTO users (User_Name, Password, Create_Date, Created_By, Last_Update, Last_Updated_By) VALUES(?,?,?,?,?,?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2,password);
        ps.setDate(3, createdDate);
        ps.setString(4, createdBy);
        ps.setTimestamp(5, lastUpdated);
        ps.setString(6, lastUpdatedBy);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }


    /**
     * Logic to update a User in the database.
     *
     * @param userID of user.
     * @param name of user.
     * @param password of user.
     * @param createdDate of user.
     * @param createdBy of user.
     * @param lastUpdated of user.
     * @param lastUpdatedBy of user.
     * @return the number of rows affected by the query.
     * @throws SQLException throw if SQL statement fails to resolve.
     */
    public static int update(int userID, String name,String password, Date createdDate, String createdBy, Timestamp lastUpdated, String lastUpdatedBy) throws SQLException {
        String sql = "UPDATE users SET User_Name=?, Password=?, Create_Date=?, Created_By=?, Last_Update=?, Last_Updated_By=? WHERE User_ID=?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2,password);
        ps.setDate(3, createdDate);
        ps.setString(4, createdBy);
        ps.setTimestamp(5, lastUpdated);
        ps.setString(6, lastUpdatedBy);
        ps.setInt(7, userID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Logic to delete a User from the database.
     *
     * @param id of user.
     * @return the number of rows affected by the query.
     * @throws SQLException throw if SQL statement fails to resolve.
     */
    public static int delete(int id) throws SQLException {
        String sql = "DELETE FROM USERS WHERE User_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1,id);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }


    /**
     *  Logic to select all the Users from the database to store in memory.
     *
     * @return ObservableList of Users to be held by the User class.
     * @throws SQLException throw if SQL statement fails to resolve.
     */
    public static ObservableList<User> select() throws SQLException {
        ObservableList<User> allUsers = FXCollections.observableArrayList();
        String sql = "SELECT * FROM USERS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int userID = rs.getInt("User_ID");
            String name = rs.getString("User_Name");
            String password = rs.getString("Password");
            Date createdDate = rs.getDate("Create_Date");
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");


            User user = new User(userID,name,password,createdDate,createdBy,lastUpdate,lastUpdatedBy);
            allUsers.add(user);
        }
        return allUsers;
    }
}
