package edu.hawaii.ihale.api;


/**
 * Provides a simple record of information about a person.
 * @author Philip Johnson
 */
public class Contact {
  /** The contact's first name. */
  private String firstName;
  /** The contact's last name. */
  private String lastName;
  /** All info about the contact. */
  private String info;
  
  /** The contact's unique ID. */
  private String uniqueID;
  /** The unique ID element name. */
  
  /**
   * Creates a Contact instance given its field values as strings. 
   * @param firstName The first name.
   * @param lastName The last name.
   * @param info The info.
   * @param uniqueID The uniqueID. 
   */
  public Contact (String firstName, String lastName, String info, String uniqueID) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.info = info;
    this.uniqueID = uniqueID;
  }
  
  /**
   * Returns the first name associated with this contact.
   * @return The first name.
   */
  public String getFirstName() {
    return this.firstName;
  }
  
  /**
   * Returns the last name associated with this contact.
   * @return The last name.
   */
  public String getLastName() {
    return this.lastName;
  }
  
  /**
   * Returns the info associated with this contact.
   * @return The info.
   */
  public String getInfo() {
    return this.info;
  }
  
  /**
   * Returns the unique ID associated with this contact.
   * @return The unique ID.
   */
  public String getUniqueID() {
    return this.uniqueID;
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
