package edu.hawaii.ihale.berkeleydb;

import edu.hawaii.ihale.api.Contact;
import edu.hawaii.ihale.api.ContactDB;

/**
 * Provides an implementation of the ContactDB API using BerkeleyDB.
 * @author Philip Johnson
 */
public class BerkeleyDBContactDB implements ContactDB {

  /** {@inheritDoc} */
  @Override
  public Contact getContact(String uniqueID) {
    BerkeleyDBContact dbcontact = BerkeleyDBContactDAO.getContact(uniqueID);
    return (dbcontact == null) ? null : dbcontact.toContact();
  }

  /** {@inheritDoc} */
  @Override
  public void putContact(Contact contact) {
    BerkeleyDBContactDAO.putContact(new BerkeleyDBContact(contact));
  }

  /** {@inheritDoc} */
  @Override
  public void deleteContact(String uniqueID) {
    BerkeleyDBContactDAO.deleteContact(uniqueID);    
  }

}
