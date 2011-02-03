package edu.hawaii.ihale.berkeleydb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

/**
 * Simple unit test of the Contact data access object.
 * @author Philip Johnson
 */
public class TestBerkeleyDBContactDAO {
  
  /**
   * Test the DAO interface. 
   */
  @Test
  public void testContactDatabase() {
    String pmjID = "pmj";
    String dsID = "ds";
    BerkeleyDBContact contact1 = new BerkeleyDBContact("Philip", "Johnson", "Professor", pmjID);
    BerkeleyDBContact contact2 = new BerkeleyDBContact("Dan", "Suthers", "Professor", dsID);
    BerkeleyDBContactDAO.putContact(contact1);
    BerkeleyDBContactDAO.putContact(contact2);
    BerkeleyDBContact contact1a = BerkeleyDBContactDAO.getContact(pmjID);
    BerkeleyDBContact contact2a = BerkeleyDBContactDAO.getContact(dsID);
    assertEquals("Check contact1", contact1.toString(), contact1a.toString());
    assertEquals("Check contact2", contact2.toString(), contact2a.toString());
    BerkeleyDBContactDAO.deleteContact(pmjID);
    assertNull("Check deletion", BerkeleyDBContactDAO.getContact(pmjID));
  }
}
