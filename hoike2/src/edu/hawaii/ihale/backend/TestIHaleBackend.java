package edu.hawaii.ihale.backend;

import org.junit.BeforeClass;
import org.junit.Test;
import edu.hawaii.ihale.api.ApiDictionary.IHaleSystem;

/**
 * JUnit tests the IHaleBackend.
 * 
 * @author Bret K. Ikehara
 */
public class TestIHaleBackend {

  public static IHaleBackend backend;
  
  /**
   * Sets up the backend
   */
  @BeforeClass
  public static void beforeClass() {
    backend = new IHaleBackend();
  }
  
  /**
   * Tests the HVAC doCommand.
   */
  @Test
  public void doCommandHvac() {
    backend.doCommand(IHaleSystem.HVAC, null, null, null);    
  }
}
