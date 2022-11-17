/**
 * A First Level Division Data Access Object class for First Level divisions.
 *
 * @author Kevin Miller
 */

package utilities;

import DataBaseConnection.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivision;

import java.sql.*;

/**
 * A First Level Division Data Access Object class for First Level divisions.
 */
public class FirstLevelDivisionDAO {


    /**
     * Logic to insert a new FirstLevelDivision into the database.
     *
     * @param name of First Level Division.
     * @param createdDate of First Level Division.
     * @param createdBy of First Level Division.
     * @param lastUpdated of First Level Division.
     * @param lastUpdatedBy of First Level Division.
     * @param country_id of First Level Division.
     * @return the number of rows affected by the query.
     * @throws SQLException throw if SQL statement fails to resolve.
     */
    public static int insert(String name, Timestamp createdDate, String createdBy, Timestamp lastUpdated, String lastUpdatedBy, int country_id) throws SQLException {
        String sql = "INSERT INTO first_level_divisions (Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) VALUES(?,?,?,?,?,?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, name);
        ps.setTimestamp(2, createdDate);
        ps.setString(3, createdBy);
        ps.setTimestamp(4, lastUpdated);
        ps.setString(5, lastUpdatedBy);
        ps.setInt(6, country_id);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Logic to update a FirstLevelDivision in the database.
     *
     * @param firstLevelDivisionID of First Level Division.
     * @param name of First Level Division.
     * @param createdDate of First Level Division.
     * @param createdBy of First Level Division.
     * @param lastUpdated of First Level Division.
     * @param lastUpdatedBy of First Level Division.
     * @param country_id of First Level Division.
     * @return the number of rows affected by the query.
     * @throws SQLException throw if SQL statement fails to resolve.
     */
    public static int update(int firstLevelDivisionID, String name, Timestamp createdDate, String createdBy, Timestamp lastUpdated, String lastUpdatedBy, int country_id) throws SQLException {
        String sql = "UPDATE first_level_divisions SET Division = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Country_ID = ? WHERE Division_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, name);
        ps.setTimestamp(2, createdDate);
        ps.setString(3, createdBy);
        ps.setTimestamp(4, lastUpdated);
        ps.setString(5, lastUpdatedBy);
        ps.setInt(6, country_id);
        ps.setInt(7, firstLevelDivisionID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }


    /**
     * Logic to delete a User from the database.
     *
     * @param id of First Level Division
     * @return the number of rows affected by the query.
     * @throws SQLException throw if SQL statement fails to resolve.
     */
    public static int delete(int id) throws SQLException {
        String sql = "DELETE FROM first_level_divisions WHERE Division_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1,id);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }


    /**
     * Logic to select all First Level Divisions from the database.
     *
     * @return ObservableList of all First Level Divisions
     * @throws SQLException throw if SQL statement fails to resolve.
     */
    public static ObservableList<FirstLevelDivision> select() throws SQLException {
        ObservableList<FirstLevelDivision> allFirstLevelDivisions = FXCollections.observableArrayList();
        String sql = "SELECT * FROM first_level_divisions";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int firstLevelDivisionID = rs.getInt("Division_ID");
            String name = rs.getString("Division");
            Timestamp createdDate = rs.getTimestamp("Create_Date");
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int country_id = rs.getInt("Country_ID");

            FirstLevelDivision fld = new FirstLevelDivision(firstLevelDivisionID, name, createdDate,createdBy,lastUpdate,lastUpdatedBy,country_id);
            allFirstLevelDivisions.add(fld);
        }

        return allFirstLevelDivisions;
    }
}
