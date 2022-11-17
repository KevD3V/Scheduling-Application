/**
 * A model class for the Customer
 *
 * @author Kevin Miller
 */


package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utilities.CustomerDAO;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * A model class for the Customer
 */
public class Customer {


    /**
     * A static ObservableList<Customer> that contains all customers.
     *
     */
    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();


    /**
     * id of customer.
     */
    private int id;

    /**
     * name of customer.
     */
    private String name;

    /**
     * address of customer.
     */
    private String address;

    /**
     * postal code of customer.
     */
    private String postalCode;

    /**
     * phone number of customer.
     */
    private String phoneNumber;

    /**
     * created date of customer.
     */
    private Timestamp createdDate;

    /**
     * created by of customer.
     */
    private String createdBy;

    /**
     * last updated of customer.
     */
    private Timestamp lastUpdated;

    /**
     * last updated by of customer.
     */
    private String lastUpdatedBy;

    /**
     * division ID of customer.
     */
    private int divisionID;


    /**
     * Constructor.
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
     */
    public Customer(int id, String name, String address, String postalCode, String phoneNumber, Timestamp createdDate, String createdBy, Timestamp lastUpdated, String lastUpdatedBy, int divisionID){
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionID = divisionID;
    }

    /**
     * Get customer ID.
     *
     * @return ID of Customer.
     */
    public int getId() {
        return id;
    }

    /**
     * Get customer name.
     *
     * @return Name of Customer.
     */
    public String getName() {
        return name;
    }

    /**
     * Get customer address.
     *
     * @return Address of Customer.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Get customer postal code.
     *
     * @return Postal Code of Customer.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Get customer phone number.
     *
     * @return Phone Number of Customer.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Get customer created date.
     *
     * @return Created Date of Customer.
     */
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    /**
     * Get customer created by.
     *
     * @return Created By of Customer.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Get customer last updated.
     *
     * @return Last Updated of Customer.
     */
    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    /**
     * Get customer last updated by.
     *
     * @return Last Updated By of Customer.
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Get customer division ID.
     *
     * @return Division ID of Customer.
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * Set customer ID.
     *
     * @param id of Customer to be set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Set customer name.
     *
     * @param name of Customer to be set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set customer address.
     *
     * @param address of Customer to be set.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Set customer postal code.
     *
     * @param postalCode of Customer to be set.
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Set customer phone number.
     *
     * @param phoneNumber of Customer to be set.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Set customer created date.
     *
     * @param createdDate of Customer to be set.
     */
    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Set customer created by.
     *
     * @param createdBy of Customer to be set.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Set customer last updated.
     *
     * @param lastUpdated of Customer to be set.
     */
    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /**
     * Set customer last updated by.
     *
     * @param lastUpdatedBy of Customer to be set.
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }


    /**
     * Set customer division ID.
     *
     * @param divisionID of Customer to be set.
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }



    /**
     * Logic to load all Customers from database into the User container.
     *
     * @throws SQLException throw if SQL statement fails to resolve.
     */
    public static void setAllCustomers() throws SQLException {
        allCustomers = CustomerDAO.select();
    }

    /**
     * Get all customers.
     *
     * @return all Customers from the observable list.
     */
    public static ObservableList<Customer> getAllCustomers(){
        return allCustomers;
    }


    /**
     * Logic to add customer to the database and then to the container of customers.
     *
     * @param newCustomer to be added to the database and container.
     * @throws SQLException throw if SQL statement fails to resolve.
     */
    public static void addCustomer(Customer newCustomer) throws SQLException {

        int affectedRows = CustomerDAO.insert(newCustomer.getName(), newCustomer.getAddress(), newCustomer.getPostalCode(),
                newCustomer.getPhoneNumber(), newCustomer.getCreatedDate(), newCustomer.getCreatedBy(),newCustomer.getLastUpdated() , newCustomer.getLastUpdatedBy(),
                newCustomer.getDivisionID());

        if(affectedRows > 0){
            allCustomers = CustomerDAO.select();
        }
    }

    /**
     * Logic to update the customer
     *
     * @param updatedCustomer object that contains changes
     * @throws SQLException throw if SQL statement fails to resolve.
     */
    public static void updateCustomer(Customer updatedCustomer) throws SQLException{
        int affectedRows = CustomerDAO.update(updatedCustomer.getId(), updatedCustomer.getName(), updatedCustomer.getAddress(),
                updatedCustomer.getPostalCode(), updatedCustomer.getPhoneNumber(), updatedCustomer.getCreatedDate(),
                updatedCustomer.getCreatedBy(), updatedCustomer.getLastUpdated(), updatedCustomer.getLastUpdatedBy(), updatedCustomer.getDivisionID());
        if(affectedRows > 0){
            allCustomers = CustomerDAO.select();
        }

    }


    /**
     * Logic to remove customer from Database then from ObservableList.
     *
     * @param customer to be deleted.
     * @throws SQLException throw if SQL statement fails to resolve.
     */
    public static void deleteCustomer(Customer customer) throws SQLException {
        int affectedRows = CustomerDAO.delete(customer.getId());
        if(affectedRows > 0){
            allCustomers.remove(customer);
        }
    }

}
