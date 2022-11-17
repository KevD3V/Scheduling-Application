/**
 * A Country Data Access Object class for countries.
 *
 * @author Kevin Miller
 */

package utilities;

import DataBaseConnection.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import java.sql.*;

/**
 * A Country Data Access Object class for countries.
 */
public class CountryDAO {

    /**
     * Logic to insert a new Country into the database.
     *
     * @param name of country.
     * @param createdDate of country.
     * @param createdBy of country.
     * @param lastUpdated of country.
     * @param lastUpdatedBy of country.
     * @return the number of rows affected by the query.
     * @throws SQLException throw if SQL statement fails to resolve.
     */
    public static int insert(String name, Timestamp createdDate, String createdBy, Timestamp lastUpdated, String lastUpdatedBy) throws SQLException {
        String sql = "INSERT INTO countries (Country, Create_Date, Created_By, Last_Update, Last_Updated_By) VALUES(?,?,?,?,?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, name);
        ps.setTimestamp(2, createdDate);
        ps.setString(3, createdBy);
        ps.setTimestamp(4, lastUpdated);
        ps.setString(5, lastUpdatedBy);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }


    /**
     * Logic to update a Country in the database.
     *
     * @param id of country.
     * @param name of country.
     * @param createdDate of country.
     * @param createdBy of country.
     * @param lastUpdated of country.
     * @param lastUpdatedBy of country.
     * @return the number of rows affected by the query.
     * @throws SQLException throw if SQL statement fails to resolve.
     */
    public static int update(int id, String name, Timestamp createdDate, String createdBy, Timestamp lastUpdated, String lastUpdatedBy) throws SQLException {
        String sql = "UPDATE COUNTRIES SET Country = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ? WHERE Country_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, name);
        ps.setTimestamp(2, createdDate);
        ps.setString(3, createdBy);
        ps.setTimestamp(4, lastUpdated);
        ps.setString(5, lastUpdatedBy);
        ps.setInt(6,id);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }


    /**
     * Logic to delete a Country from the database.
     *
     * @param id of country.
     * @return the number of rows affected by the query.
     * @throws SQLException throw if SQL statement fails to resolve.
     */
    public static int delete(int id) throws SQLException {
        String sql = "DELETE FROM Countries WHERE Country_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1,id);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }


    /**
     * Logic to select all the Countries from the database to store in memory.
     *
     * @return ObservableList of Countries to be held by the Country class.
     * @throws SQLException throw if SQL statement fails to resolve.
     */
    public static ObservableList<Country> select() throws SQLException {
        ObservableList<Country> allCountries = FXCollections.observableArrayList();
        String sql = "SELECT * FROM countries";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int countryID = rs.getInt("Country_ID");
            String name = rs.getString("Country");
            Timestamp createdDate = rs.getTimestamp("Create_Date");
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");


            Country country = new Country(countryID, name, createdDate,createdBy, lastUpdate,lastUpdatedBy);
            allCountries.add(country);
        }
        return allCountries;
    }
}
