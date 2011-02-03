package edu.hawaii.ihale.berkeleydb;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

import edu.hawaii.ihale.api.Contact;

/**
 * Provides a simple record of information about a person for persistence in a BerkeleyDB 
 * database.
 * @author Philip Johnson
 */
@Entity
public class BerkeleyDBContact {
  /** The contact's first name. */
  private String firstName;
  /** The contact's last name. */
  private String lastName;
  /** All info about the contact. */
  private String info;
  
  /** The contact's unique ID. */
  @PrimaryKey
  private String uniqueID;
  /** The unique ID element name. */
  
  /**
   * Provide the default constructor required by BerkeleyDB.
   */
  public BerkeleyDBContact() {
    // BerkeleyDB requires a default constructor. 
  }
  
  /**
   * Creates a Contact instance given its field values as strings. 
   * @param firstName The first name.
   * @param lastName The last name.
   * @param info The info.
   * @param uniqueID The uniqueID. 
   */
  public BerkeleyDBContact (String firstName, String lastName, String info, String uniqueID) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.info = info;
    this.uniqueID = uniqueID;
  }
  
  /**
   * Constructs a BerkeleyDBContact from the API contact instance. 
   * @param contact The API contact instance. 
   */
  public BerkeleyDBContact (Contact contact) {
    this.firstName = contact.getFirstName();
    this.lastName = contact.getLastName();
    this.info = contact.getInfo();
    this.uniqueID = contact.getUniqueID();
  }
  
  /**
   * Returns the unique ID associated with this contact.
   * @return The unique ID.
   */
  public String getUniqueID() {
    return this.uniqueID;
  }
  
  /**
   * Returns an instance of the Contact API object constructed from this instance. 
   * @return A Contact instance. 
   */
  public Contact toContact() {
    return new Contact(this.firstName, this.lastName, this.info, this.uniqueID);
  }
  

  /**
   * Return this contact as a formatted string.
   * @return The contact as a string. 
   */
  @Override
  public String toString() {
    return String.format("[ID: %s Name: %s %s Info: %s]", this.uniqueID, this.firstName, 
        this.lastName, this.info);
  }
  
}
