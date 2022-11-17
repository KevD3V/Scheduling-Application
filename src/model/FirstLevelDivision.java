/**
 * A model class for First Level Division (FLD)
 *
 * @author Kevin Miller
 */


package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utilities.FirstLevelDivisionDAO;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * A model class for First Level Division (FLD)
 */
public class FirstLevelDivision {

    /**
     * A static ObservableList<FirstLevelDivision> that contains all first level divisions.
     */
    private static ObservableList<FirstLevelDivision> allFirstLevelDivisions = FXCollections.observableArrayList();

    /**
     * id of first level division.
     */
    private int id;

    /**
     * division name of first level division.
     */
    private String division;

    /**
     * created date of first level division.
     */
    private Timestamp createdDate;

    /**
     * created by of first level division.
     */
    private String createdBy;


    /**
     * last update of first level division.
     */
    private Timestamp lastUpdate;

    /**
     * last updated by of first level division.
     */
    private String lastUpdatedBy;

    /**
     * country ID of first level division.
     */
    private int countryID;

    /**
     * Constructor.
     *
     * @param id of first level division
     * @param division of first level division
     * @param createdDate of first level division
     * @param createdBy of first level division
     * @param lastUpdate of first level division
     * @param lastUpdatedBy of first level division
     * @param countryID of first level division
     */
    public FirstLevelDivision(int id, String division, Timestamp createdDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int countryID){
        this.id = id;
        this.division = division;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.countryID = countryID;
    }

    /**
     * Get first level division ID.
     *
     * @return id of FLD
     */
    public int getId(){
        return id;
    }

    /**
     * Get first level division division name.
     *
     * @return division name of FLD
     */
    public String getDivision(){
        return division;
    }

    /**
     * Get first level division created date.
     *
     * @return Created Date of FLD
     */
    public Timestamp getCreatedDate(){
        return createdDate;
    }

    /**
     * Get first level division created By.
     *
     * @return Created By of FLD
     */
    public String getCreatedBy(){
        return createdBy;
    }

    /**
     * Get first level division last upate.
     *
     * @return Last Update of FLD
     */
    public Timestamp getLastUpdate(){
        return lastUpdate;
    }

    /**
     * Get first level division last updated by.
     *
     * @return Last Updated By of FLD
     */
    public String getLastUpdatedBy(){
        return lastUpdatedBy;
    }

    /**
     * Get first level division country ID.
     *
     * @return Country ID of FLD
     */
    public int getCountryID(){
        return countryID;
    }

    /**
     * Set first level division ID.
     *
     * @param id of FLD to be set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Set first level division division name.
     *
     * @param division of FLD to be set.
     */
    public void setDivision(String division){
        this.division = division;
    }

    /**
     * Set first level division created date.
     *
     * @param createdDate of FLD to be set.
     */
    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Set first level division created by.
     *
     * @param createdBy of FLD to be set.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Set first level division last update.
     *
     * @param lastUpdate of FLD to be set.
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Set first level division last updated by.
     *
     * @param lastUpdatedBy of FLD to be set.
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Set first level division country ID.
     *
     * @param countryID of FLD to be set.
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }



    /**
     * Logic to load all First Level Divisions from database into the FirstLevelDivision container.
     *
     * @throws SQLException throw if SQL statement fails to resolve.
     */
    public static void setAllFirstLevelDivisions() throws SQLException {
        allFirstLevelDivisions = FirstLevelDivisionDAO.select();
    }

    /**
     * Get all first level divisions.
     *
     * @return all First Level Divisions from the observable list.
     */
    public static ObservableList<FirstLevelDivision> getAllFirstLevelDivisions(){
        return allFirstLevelDivisions;
    }

    /**
     * Logic to search the container of First Level Divisions by their ID
     *
     * @param id of the First Level Division to search for.
     * @return the First Level Division that matches the id.
     */
    public static FirstLevelDivision searchById(int id){
        FirstLevelDivision result = null;
        for(FirstLevelDivision fld: allFirstLevelDivisions){
            if(fld.getId() == id){
                result = fld;
            }
        }
        return result;

    }


    /**
     * Logic to filter First Level Divisions by Country ID
     *
     * @param countryID to filter First Level Divisions
     * @return Observable List of First Level Divisions
     */
    public static ObservableList<FirstLevelDivision> filterByCountry(int countryID){
        ObservableList<FirstLevelDivision> filteredDivisions = FXCollections.observableArrayList();
        for(FirstLevelDivision fld : allFirstLevelDivisions){
            if(fld.getCountryID() == countryID){
                filteredDivisions.add(fld);
            }
        }
        return filteredDivisions;
    }



    /**
     * Print division name.
     *
     * override ToString to print only First Level Division Name.
     * @return the name of the First Level Division.
     */
    public String toString(){
        return this.division;
    }

}
