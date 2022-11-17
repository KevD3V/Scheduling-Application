/**
 * A model class for the Countries
 *
 * @author Kevin Miller
 */


package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utilities.CountryDAO;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * A model class for the Countries
 */
public class Country {

    /**
     * A static ObservableList<Country> that contains all the countries.
     */
    private static ObservableList<Country> allCountries = FXCollections.observableArrayList();


    /**
     * id of country.
     */
    private int id;

    /**
     * name of country.
     */
    private String name;

    /**
     * created date of country.
     */
    private Timestamp createdDate;

    /**
     * created by of country.
     */
    private String createdBy;

    /**
     * last update of country.
     */
    private Timestamp lastUpdate;

    /**
     * last updated by of country.
     */
    private String lastUpdatedBy;


    /**
     * Constructor.
     *
     * @param id of country.
     * @param name of country.
     * @param createdDate of country.
     * @param createdBy of country.
     * @param lastUpdate of country.
     * @param lastUpdatedBy of country.
     */
    public Country(int id, String name, Timestamp createdDate, String createdBy,Timestamp lastUpdate, String lastUpdatedBy){
        this.id = id;
        this.name = name;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Get country ID.
     *
     * @return the id.
     */
    public int getId(){
        return id;
    }

    /**
     * Get country name.
     *
     * @return the name.
     */
    public String getName(){
        return name;
    }

    /**
     * Get country created date.
     *
     * @return the created Date.
     */
    public Timestamp getCreatedDate(){
        return createdDate;
    }

    /**
     * Get country created by.
     *
     * @return createdBy.
     */
    public String getCreatedBy(){
        return createdBy;
    }

    /**
     * Get country last update.
     *
     * @return the last update.
     */
    public Timestamp getLastUpdate(){
        return lastUpdate;
    }

    /**
     * Get country last updated by.
     *
     * @return last updated by.
     */
    public String getLastUpdatedBy(){
        return lastUpdatedBy;
    }

    /**
     * Set country ID.
     *
     * @param id the new ID to be set.
     */
    public void setId(int id){
        this.id = id;
    }


    /**
     * Set country name.
     *
     * @param name the new Name to be set.
     */
    public void setName(String name){
        this.name = name;
    }


    /**
     * Set country created date.
     *
     * @param createdDate the new Created Date to be set.
     */
    public void setCreatedDate(Timestamp createdDate){
        this.createdDate = createdDate;
    }

    /**
     * Set country created by.
     *
     * @param createdBy the new Created By to be set.
     */
    public void setCreatedBy(String createdBy){
        this.createdBy = createdBy;
    }

    /**
     * Set country last update.
     *
     * @param lastUpdate the new Last Update to be set.
     */
    public void setLastUpdate(Timestamp lastUpdate){
        this.lastUpdate = lastUpdate;
    }

    /**
     * Set country last updated by.
     *
     * @param lastUpdatedBy the new Last Updated By to be set.
     */
    public void setLastUpdatedBy(String lastUpdatedBy){
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Logic to load all Countries from database into the Country container.
     *
     * @throws SQLException throw if SQL statement fails to resolve.
     */
    public static void setAllCountries() throws SQLException {
        allCountries = CountryDAO.select();
    }

    /**
     * Get all countries.
     *
     * @return all Countries from the observable list.
     */
    public static ObservableList<Country> getAllCountries(){
        return allCountries;
    }

    /**
     * Logic to search for Countries by ID.
     *
     * @param id to search the container of countries for.
     * @return the country that matches the id.
     */
    public static Country filterById(int id){
        Country result = null;
        for(Country c : allCountries){
            if(c.getId() == id){
                result = c;
            }
        }
        return result;
    }

    /**
     * Print the country name.
     *
     * override ToString to print only Country Name.
     * @return the name of the Country.
     */
    public String toString(){
        return this.name;
    }
}
