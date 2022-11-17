/**
 * A model class for the Transformed Customer.
 * Created by combining customer and country data for the customer screen table view of customers.
 *
 * @author Kevin Miller
 */


package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 *  A model class for the Transformed Customer.
 */
public class TransformedCustomer extends Customer{

    /**
     * A static ObservableList<TransformedCustomer> that holds all the Transformed Customers.
     */
    private static ObservableList<TransformedCustomer> allTransformedCustomers = FXCollections.observableArrayList();

    /**
     * country name of transformed customer.
     */
    private String countryName;

    /**
     * Customer Constructor
     *
     * @param id of transformed customer.
     * @param name of transformed customer.
     * @param address of transformed customer.
     * @param postalCode of transformed customer.
     * @param phoneNumber of transformed customer.
     * @param createdDate of transformed customer.
     * @param createdBy of transformed customer.
     * @param lastUpdated of transformed customer.
     * @param lastUpdatedBy of transformed customer.
     * @param divisionID of transformed customer.
     */
    public TransformedCustomer(int id, String name, String address, String postalCode, String phoneNumber, Timestamp createdDate, String createdBy, Timestamp lastUpdated, String lastUpdatedBy, int divisionID) {
        super(id, name, address, postalCode, phoneNumber, createdDate, createdBy, lastUpdated, lastUpdatedBy, divisionID);

        // Use Division ID to get Country ID.
        // Get the FLD.
        FirstLevelDivision oldFld = FirstLevelDivision.searchById(divisionID);

        // Get the Country
        Country oldCountry = Country.filterById(oldFld.getCountryID());

        countryName = oldCountry.getName();
    }

    /**
     * Get the transformed customer country name.
     *
     * @return country name of the transformed customer.
     */
    public String getCountryName() {
        return countryName;
    }


    /**
     * Set the transformed customer country name.
     *
     * @param newCountryName to be set as the country name.
     */
    public void setCountryName(String newCountryName){
        countryName = newCountryName;
    }

    /**
     * Get all transformed customers.
     *
     * @return all transformed customers.
     */
    public static ObservableList<TransformedCustomer> getAllTransformedCustomers(){
        return allTransformedCustomers;
    }


    /**
     * Set all transformed customers.
     *
     * @throws SQLException throw if SQL statement fails to resolve.
     */
    public static void setAllTransformedCustomers() throws SQLException {
        allTransformedCustomers.clear();
        for(Customer c : getAllCustomers()){
            allTransformedCustomers.add(new TransformedCustomer(c.getId(),c.getName(), c.getAddress(), c.getPostalCode(), c.getPhoneNumber(),
                    c.getCreatedDate(), c.getCreatedBy(),c.getLastUpdated(),c.getLastUpdatedBy(), c.getDivisionID()));
        }
    }

    /**
     * Delete a transformed customer.
     *
     * @param tfCustomer to be deleted.
     */
    public static void deleteTransformedCustomer(TransformedCustomer tfCustomer){
        allTransformedCustomers.remove(tfCustomer);
    }


}
