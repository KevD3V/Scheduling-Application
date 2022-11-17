/**
 * A model class for the User
 *
 * @author Kevin Miller
 */


package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utilities.UserDAO;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * A model class for the User.
 */
public class User {


    /**
     * A static ObservableList<User> that contains all users.
     */
    private static ObservableList<User> allUsers = FXCollections.observableArrayList();


    /**
     * id of user.
     */
    private int id;

    /**
     * name of user.
     */
    private String name;

    /**
     * password of user.
     */
    private String password;

    /**
     * created date of user.
     */
    private Date createdDate;

    /**
     * created by of user.
     */
    private String createdBy;

    /**
     * last update of user.
     */    private Timestamp lastUpdate;

    /**
     * last updated by of user.
     */    private String lastUpdatedBy;


    /**
     * Constructor.
     *
     * @param id of user.
     * @param name of user.
     * @param password of user.
     * @param createdDate of user.
     * @param createdBy of user.
     * @param lastUpdate of user.
     * @param lastUpdatedBy of user.
     */
    public User(int id, String name, String password, Date createdDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy){
        this.id = id;
        this.name = name;
        this.password = password;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Get user ID.
     *
     * @return ID of User.
     */
    public int getId() {
        return id;
    }

    /**
     * Set user ID.
     *
     * @param id of user to be set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get user name.
     *
     * @return Name of User.
     */
    public String getName() {
        return name;
    }

    /**
     * Set user name.
     *
     * @param name of User to be set.
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * Get user password.
     *
     * @return Password of User.
     */
    public String getPassword() {
        return password;
    }


    /**
     * Set user password.
     *
     * @param password of User to be set.
     */
    public void setPassword(String password) {
        this.password = password;
    }


    /**
     * Get user created date.
     *
     * @return Created Date of User.
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * Set user created date.
     *
     * @param createdDate of User to be set.
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Get user created by.
     *
     * @return Created By of User.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Set user created by.
     *
     * @param createdBy of User to be set.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Get user last update.
     *
     * @return Last Update of User.
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Set user last update.
     *
     * @param lastUpdate of User to be set.
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Get user last updated by.
     *
     * @return Last Updated By of User.
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Set user last updated by.
     *
     * @param lastUpdatedBy of User to be Set.
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Logic to load all Users from database into the User container.
     *
     * @throws SQLException throw if SQL statement fails to resolve.
     */
    public static void setAllUsers() throws SQLException {
        allUsers = UserDAO.select();
    }

    /**
     * Get all users.
     *
     * @return all Users from the observable list.
     */
    public static ObservableList<User> getAllUsers(){
        return allUsers;
    }


    /**
     * Logic to search all users by username.
     *
     * @param username to search for
     * @return User based on search criteria.
     */
    public static User searchUsernames(String username){
        User user = null;
        for(User u: allUsers){
            if(u.getName().equals(username)){
                user = u;
            }
        }
        return user;
    }
}
