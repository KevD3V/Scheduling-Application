/**
 * A model class for the Appointment
 *
 * @author Kevin Miller
 */


package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utilities.AppointmentDAO;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 *  A model class for the Appointment
 */
public class Appointment {

    /**
     * A static ObservableList<Appointment> that contains all of the appointments.
     */
    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();


    /**
     * id of the appointment.
     */
    private int id;

    /**
     * title of the appointment.
     */
    private String title;

    /**
     * description of the appointment.
     */
    private String description;

    /**
     * location of the appointment.
     */
    private String location;

    /**
     * type of the appointment.
     */
    private String type;


    /**
     * start date of the appointment.
     */
    private Timestamp startDate;


    /**
     * end date of the appointment.
     */
    private Timestamp endDate;


    /**
     * created date of the appointment.
     */
    private Timestamp createdDate;


    /**
     * user who created the appointment.
     */
    private String createdBy;


    /**
     * last update of the appointment.
     */
    private Timestamp lastUpdate;


    /**
     * user who last updated the appointment.
     */
    private String lastUpdatedBy;


    /**
     * customer ID of the appointment.
     */
    private int customerID;


    /**
     * user ID of the appointment.
     */
    private int userID;

    /**
     * contact ID of the appointment.
     */
    private int contactID;

    /**
     * Constructor.
     *
     * @param id of new appointment.
     * @param title of new appointment.
     * @param description of new appointment.
     * @param location of new appointment.
     * @param type of new appointment.
     * @param startDate of new appointment.
     * @param endDate of new appointment.
     * @param createdDate of new appointment.
     * @param createdBy of new appointment.
     * @param lastUpdate of new appointment.
     * @param lastUpdatedBy of new appointment.
     * @param customerID of new appointment.
     * @param userID of new appointment.
     * @param contactID of new appointment.
     */
    public Appointment(int id, String title, String description, String location, String type, Timestamp startDate, Timestamp endDate, Timestamp createdDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int customerID, int userID, int contactID){
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    /**
     * Get the appointment ID.
     *
     * @return ID of Appointment.
     */
    public int getId() {
        return id;
    }

    /**
     * Set the appointment ID.
     *
     * @param id of Appointment to be set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the appointment title.
     *
     * @return Title of Appointment.
     */
    public String getTitle() {
        return title;
    }


    /**
     * Set the appointment title.
     *
     * @param title of Appointment to be set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get the appointment description.
     *
     * @return Description of Appointment.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the appointment description.
     *
     * @param description of Appointment to be set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the appointment location.
     *
     * @return Location of Appointment.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Set the appointment location.
     *
     * @param location of Appointment to be set.
     */
    public void setLocation(String location) {
        this.location = location;
    }


    /**
     * Get the appointment type.
     *
     * @return Type of Appointment.
     */
    public String getType() {
        return type;
    }

    /**
     * Set the appointment type.
     *
     * @param type of Appointment to be set.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Get the appointment start date.
     *
     * @return Start Date of Appointment.
     */
    public Timestamp getStartDate() {
        return startDate;
    }

    /**
     * Set the appointment start date.
     *
     * @param startDate of Appointment to be set.
     */
    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    /**
     * Get the appointment end date.
     *
     * @return End Date of Appointment.
     */
    public Timestamp getEndDate() {
        return endDate;
    }

    /**
     * Set the appointment end date.
     *
     * @param endDate of Appointment to be set.
     */
    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    /**
     * Get the appointment created date.
     *
     * @return Created Date of Appointment.
     */
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    /**
     * Set the appointment created date.
     *
     * @param createdDate of Appointment to be set.
     */
    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Get the appointment created by.
     *
     * @return Created By of Appointment.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Set the appointment created by.
     *
     * @param createdBy of Appointment to be set.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Get the appointment last update.
     *
     * @return Last Update of Appointment.
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Set the appointment last update.
     *
     * @param lastUpdate of Appointment to be set.
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Get the appointment last updated by.
     *
     * @return Last Updated By of Appointment.
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Set the appointment last updated by.
     *
     * @param lastUpdatedBy of Appointment to be set.
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Get the appointment customer ID.
     *
     * @return Customer ID of Appointment.
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Set the appointment customer ID.
     *
     * @param customerID of Appointment to be set.
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Get the appointment user ID.
     *
     * @return User ID of Appointment.
     */
    public int getUserID() {
        return userID;
    }


    /**
     * Set the appointment user ID.
     *
     * @param userID of Appointment to be set.
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Get the appointment contact ID.
     *
     * @return Contact ID of Appointment.
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * Set the appointment contact ID.
     *
     * @param contactID of Appointment to be set.
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }


    /**
     * Logic to load all Appointments from database into the Appointment container.
     *
     * @throws SQLException throw if SQL statement fails to resolve.
     */
    public static void setAllAppointments() throws SQLException {
        allAppointments = AppointmentDAO.select();
    }

    /**
     * Get all appointments.
     *
     * @return all Customers from the observable list.
     */
    public static ObservableList<Appointment> getAllAppointments(){
        return allAppointments;
    }



    /**
     * Logic to add appointment to the database and then to the container of appointments.
     *
     * @param newAppointment Appointment to be added to the container and database.
     * @throws SQLException throw if SQL statement fails to resolve.
     */
    public static void addAppointment(Appointment newAppointment) throws SQLException {

        int affectedRows = AppointmentDAO.insert(newAppointment.getTitle(), newAppointment.getDescription(), newAppointment.getLocation(),
                newAppointment.getType(), newAppointment.getStartDate(), newAppointment.getEndDate(), newAppointment.getCreatedDate(),
                newAppointment.getCreatedBy(), newAppointment.getLastUpdate(), newAppointment.getLastUpdatedBy(), newAppointment.getCustomerID(),
                newAppointment.getUserID(), newAppointment.getContactID());

        if(affectedRows > 0){
            allAppointments = AppointmentDAO.select();
        }
    }


    /**
     * Logic to update the appointment
     *
     * @param updatedAppointment object that contains changes
     * @throws SQLException throw if SQL statement fails to resolve.
     */
    public static void updateAppointment(Appointment updatedAppointment) throws SQLException{
        int affectedRows = AppointmentDAO.update(updatedAppointment.getId(), updatedAppointment.getTitle(), updatedAppointment.getDescription(),
                updatedAppointment.getLocation(), updatedAppointment.getType(), updatedAppointment.getStartDate(),updatedAppointment.getEndDate(),
                updatedAppointment.getCreatedDate(),
                updatedAppointment.getCreatedBy(), updatedAppointment.getLastUpdate(), updatedAppointment.getLastUpdatedBy(), updatedAppointment.getCustomerID(), updatedAppointment.getUserID(),
                updatedAppointment.getContactID());
        if(affectedRows > 0){
            allAppointments = AppointmentDAO.select();
        }

    }

    /**
     * Logic to remove appointment from Database then from ObservableList.
     *
     * @param appointment to be deleted.
     *  @throws SQLException throw if SQL statement fails to resolve.
     */
    public static void deleteAppointment(Appointment appointment) throws SQLException {
        int affectedRows = AppointmentDAO.delete(appointment.getId());
        if(affectedRows > 0){
            allAppointments.remove(appointment);
        }
    }


    /**
     * Logic to delete Appointments for a specific customer.
     *
     * @param customerID of customer to delete.
     * @throws SQLException throw if SQL statement fails to resolve.
     */
    public static void deleteCustomersAppointments(Integer customerID) throws SQLException {
        int affectedRows = AppointmentDAO.deleteAppointmentsFromCustomer(customerID);
        if(affectedRows > 0){
            allAppointments = AppointmentDAO.select();
        }
    }


    /**
     * Logic to compare appointment times for overlapping appointments for a specific user.
     *
     * @param customerId ID of Customer.
     * @param appointmentId ID of appointment.
     * @param startTS Timestamp that represents the start of the appointment.
     * @param endTS Timestamp that represents the end of the appointment.
     * @return A boolean value representing if parameters pass the "overlap" check. False flags mean there are overlaps and the appointments cannot be created.
     */
    public static boolean checkAppointmentOverlaps(Integer customerId, Integer appointmentId,Timestamp startTS, Timestamp endTS){
        // Get customers appointments
        ObservableList<Appointment> customersAppointments =  FXCollections.observableArrayList();
        for(Appointment p : allAppointments){
            if(p.getCustomerID() == customerId && p.getId() != appointmentId){
                customersAppointments.add(p);
            }
        }

        for(Appointment p : customersAppointments){
            if(startTS.after(p.getStartDate()) && startTS.before(p.getEndDate())){
                return false;
            }
            if(endTS.after(p.getStartDate()) && endTS.before(p.getEndDate())) {
                return false;
            }
            if(startTS.after(p.getStartDate()) && endTS.before(p.getEndDate())){
                return false;
            }
            if(startTS.before(p.getStartDate()) && endTS.after(p.getEndDate())){
                return false;
            }
            if(startTS.before(p.getStartDate()) && endTS.after(p.getStartDate())){
                return false;
            }
            if(startTS.equals(p.getStartDate()) || endTS.equals(p.getEndDate())){
                return false;
            }
        }
        return true;
    }
}
