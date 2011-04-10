package edu.hawaii.ihale.backend;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull; 

import java.io.IOException;
import java.util.Map;
import org.junit.Test;
import edu.hawaii.ihale.api.ApiDictionary.IHaleCommandType;
import edu.hawaii.ihale.api.ApiDictionary.IHaleRoom;
import edu.hawaii.ihale.api.ApiDictionary.IHaleSystem;
import edu.hawaii.ihale.api.repository.impl.Repository;
import edu.hawaii.ihale.backend.restserver.RestServer;

/**
 * JUnit tests the IHaleBackend. The House Simulator or Actual House *must* be running so that the
 * doCommand can successfully pass the command XML. If it isn't running, these tests will fail.
 * 
 * @author Bret K. Ikehara, Greg Burgess
 */
public class TestIHaleBackend {

  private static IHaleBackend backend;
  private static Repository repo = new Repository();

  private static Map<String,String> uri;
  private static RestServer server;
  private static final Object invalidObj = "invalid";
  private static final Object nullObj = null;
  private String invalid = "Command is invalid";

  
  /**
   * Initializes the test.  Called by testResource().
   */
   public static final void init() {
     backend = IHaleBackend.getInstance();
     uri = IHaleBackend.getURImap();
     server = IHaleBackend.getServer();
   }
   
  /**
   * Ensures that major variables in the IHaleBackend class are
   * initialized before being returned.
   */
  @Test
  public void testResource() {
    boolean uriCheck = false, serverCheck = false, backendCheck = false;
    init();
    if (uri != null) {
      uriCheck = true;
    }
    if (backend != null) {
      backendCheck = true;
    }
    if (server != null) {
      serverCheck = true;
    }
    
    assertEquals("Backend not null",backendCheck,true);
    assertEquals("Server not null",serverCheck,true);
    assertEquals("URI list not null",uriCheck,true);
  }
  /**
   * Checks the parsing the files.
   * 
   * @throws IOException Thrown when parsing URL file fails.
   */
  @Test
  public void testParseURIPropertyFileValid() throws IOException {
    Map<String, String> uri = IHaleBackend.parseURIPropertyFile(IHaleBackend.deviceConfigRef);

    assertNotNull("Electric state", uri.get("aquaponics-state"));
  }

  /**
   * Checks the parsing the files.
   * 
   * @throws IOException Thrown when parsing URL file fails.
   */
  @Test 
  public void testParseURIPropertyFileNull() throws IOException {
    boolean exceptionThrown = false;
    Map<String, String> uri = null;
    try {
      uri = IHaleBackend.parseURIPropertyFile(null);
    }
    catch (Exception e) {
      exceptionThrown = true;
    }
    assertEquals("Exception caught",exceptionThrown,true); 
    assertEquals("Map is null",uri,null);
  }

  /**
   * Checks the parsing the files.
   * 
   * @throws IOException Thrown when parsing URL file fails.
   */
  @Test
  public void testParseURIPropertyFileInvalid() throws IOException {
    boolean errorCaught = false;
    Map<String, String> uri = null;
    try {
      IHaleBackend.parseURIPropertyFile("invalid location");
    }
    catch (Exception e) {
      errorCaught = true;
    }
    
    assertEquals("Error caught",errorCaught,true);
    assertEquals("Map is null", uri,null);
  }

  /**
   * Tests the HVAC doCommand for a successful PUT.
   * Only passes with a simulator running, remove @Ignore
   * tag when simulator is present.
   * 
   */
  @Test
  public void doCommandHvacSystemVaild() {

    Object value = Integer.valueOf(45);

    backend.doCommand(IHaleSystem.HVAC, null, IHaleCommandType.SET_TEMPERATURE, value);

    assertEquals("HVAC temperature changed", value, repo.getHvacTemperatureCommand().getValue());
  }

  /**
   * Tests the HVAC doCommand for a unsuccessful PUT.
   */
  @Test
  public void doCommandHvacSystemNull() {
    boolean expectedThrown = false;

    try {
      backend.doCommand(IHaleSystem.HVAC, null, IHaleCommandType.SET_TEMPERATURE, nullObj);
    }
    catch (RuntimeException e) {
        expectedThrown = true;
    }

    assertEquals(invalid,expectedThrown,true);
  }

  /**
   * Tests the HVAC doCommand for a unsuccessful PUT.
   */
  @Test
  public void doCommandHvacSystemInvalid() {
    boolean expectedThrown = false;

    try {
      backend.doCommand(IHaleSystem.HVAC, null, IHaleCommandType.SET_TEMPERATURE, invalidObj);
    }
    catch (RuntimeException e) {
        expectedThrown = true;
    }

    assertEquals(invalid,expectedThrown,true);
  }

  /**
   * Tests the Aquaponics doCommand for a successful PUT. Only passes with a simulator running,
   * remove @Ignore tag when simulator is present.
   * 
   */
  @Test
  public void doCommandAquaponicsSystemValid() {

    Object value = Integer.valueOf(45);

    // Test valid input
    backend.doCommand(IHaleSystem.AQUAPONICS, null, IHaleCommandType.SET_TEMPERATURE, value);
    assertEquals("temperature changed", value, repo.getAquaponicsTemperatureCommand().getValue());
  }

  /**
   * Tests the Aquaponics doCommand for a unsuccessful PUT.
   */
  @Test
  public void doCommandAquaponicsSystemNull() {
    boolean expectedThrown = false;

    try {
      // Test valid input
      backend.doCommand(IHaleSystem.AQUAPONICS, null, IHaleCommandType.SET_TEMPERATURE, nullObj);
    }
    catch (RuntimeException e) {
        expectedThrown = true;
    }

    assertEquals(invalid,expectedThrown,true);
  }

  /**
   * Tests the Aquaponics doCommand for a unsuccessful PUT.
   */
  @Test
  public void doCommandAquaponicsSystemInvalid() {
    boolean expectedThrown = false;

    try {
      // Test valid input
      backend.doCommand(IHaleSystem.AQUAPONICS, null, IHaleCommandType.SET_TEMPERATURE, invalidObj);
    }
    catch (RuntimeException e) {
        expectedThrown = true;
    }

    assertEquals(invalid,expectedThrown,true);
  }

  /**
   * Tests the Lighting doCommand for a successful PUT.
   * Only passes with a simulator running, remove @Ignore
   * tag when simulator is present.
   * 
   */
  @Test
  public void doCommandLightingSystemValid() {

    Object value = Integer.valueOf(45);

    // Test valid input
    backend.doCommand(IHaleSystem.LIGHTING, IHaleRoom.BATHROOM,
        IHaleCommandType.SET_LIGHTING_LEVEL, value);
    assertEquals("temperature changed", value, repo.getLightingLevelCommand(IHaleRoom.BATHROOM)
        .getValue());
  }

  /**
   * Tests the Lighting doCommand for a unsuccessful PUT.
   */
  @Test
  public void doCommandLightingSystemNull() {
    boolean expectedThrown = false;

    try {
      // Test valid input
      backend.doCommand(IHaleSystem.LIGHTING, IHaleRoom.BATHROOM,
          IHaleCommandType.SET_LIGHTING_LEVEL, nullObj);
    }
    catch (RuntimeException e) {
        expectedThrown = true;
    }

    assertEquals(invalid,expectedThrown,true);
  }

  /**
   * Tests the Lighting doCommand for a unsuccessful PUT.
   */
  @Test
  public void doCommandLightingSystemInvalid() {
    boolean expectedThrown = false;

    try {
      // Test valid input
      backend.doCommand(IHaleSystem.LIGHTING, IHaleRoom.BATHROOM,
          IHaleCommandType.SET_LIGHTING_LEVEL, invalidObj);
    }
    catch (RuntimeException e) {
        expectedThrown = true;
    }

    assertEquals(invalid,expectedThrown,true);
  }

  /**
   * Tests the Lighting doCommand for a unsuccessful PUT.
   */
  @Test
  public void doCommandLightingSystemInvalidRoom() {

    Object value = Integer.valueOf(45);
    boolean expectedThrown = false;

    try {
      // Test valid input
      backend.doCommand(IHaleSystem.LIGHTING, null, IHaleCommandType.SET_LIGHTING_LEVEL, value);
    }
    catch (RuntimeException e) {
        expectedThrown = true;
    }

    assertEquals(invalid,expectedThrown,true);
  }

  /**
   * Tests the Invalid system doCommand for a unsuccessful PUT. Remove @Ignore tag when running with
   * a simulator.
   */
  @Test
  public void doCommandInvalidSystem() {

    Object value = Integer.valueOf(45);
    boolean expectedThrown = false;

    try {
      // Test valid input
      backend.doCommand(null, null, IHaleCommandType.SET_LIGHTING_LEVEL, value);
    }
    catch (RuntimeException e) {
        expectedThrown = true;
    }

    assertEquals(invalid,expectedThrown,true);
  }
}
