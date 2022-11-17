/**
 * A model class for the Contact
 *
 *
 * @author Kevin Miller
 */



package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utilities.ContactDAO;
import java.sql.SQLException;

/**
 *  A model class for the Contact
 */
public class Contact {

    /**
     * A static ObservableList<Contact> that contains all of the contacts.
     */
    private static ObservableList<Contact> allContacts = FXCollections.observableArrayList();

    /**
     * id of contact.
     */
    private int id;

    /**
     * name of contact.
     */
    private String name;

    /**
     * email of contact.
     */
    private String email;


    /**
     *  Constructor.
     *
     * @param id  of contact.
     * @param name of contact.
     * @param email of contact.
     */
    public Contact(int id, String name, String email){
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     * Get contact ID.
     *
     * @return ID of Contact.
     */
    public int getId() {
        return id;
    }

    /**
     * Set contact ID.
     *
     * @param id of Contact to be set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get contact name.
     *
     * @return Name of Contact.
     */
    public String getName() {
        return name;
    }

    /**
     * Set contact name.
     *
     * @param name of Contact to be set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get contact email.
     *
     * @return Email of Contact
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set contact email.
     *
     * @param email of Contact to be set.
     */
    public void setEmail(String email) {
        this.email = email;
    }


    /**
     * Logic to load all Contacts from database into the Contact container.
     *
     * @throws SQLException throw if SQL statement fails to resolve.
     */
    public static void setAllContacts() throws SQLException {
        allContacts = ContactDAO.select();
    }

    /**
     * Get all contacts.
     *
     * @return all Contacts from the observable list.
     */
    public static ObservableList<Contact> getAllContacts(){
        return allContacts;
    }


    /**
     * Logic to search the container of Contacts by their ID
     *
     * @param id of the Contact to search for.
     * @return the Contact that matches the id.
     */
    public static Contact searchById(int id){
        Contact result = null;
        for(Contact c: allContacts){
            if(c.getId() == id){
                result = c;
            }
        }
        return result;
    }




    /**
     * Prints the ID and name of contact.
     *
     * override ToString to print ID and Name.
     * @return the ID and name of the Contact.
     */
    public String toString(){
        return this.id + " - " + this.name;
    }

}
