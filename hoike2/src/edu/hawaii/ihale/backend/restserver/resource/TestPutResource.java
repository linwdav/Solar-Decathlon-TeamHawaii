package edu.hawaii.ihale.backend.restserver.resource;

import static org.junit.Assert.assertEquals;
import java.util.logging.Level;
import org.junit.Test;
import org.restlet.resource.ClientResource;
import edu.hawaii.ihale.api.ApiDictionary.IHaleRoom;

/**
 * Tests the aquaponics data to ensure that the XML representation is correct.
 * 
 * @author Bret K. Ikehara
 * @author Michael Cera
 */
public class TestPutResource extends SystemDataTest {

  /**
   * Tests PUT command with aquaponics. Command: SET_TEMPERATURE; Arg: 25.
   * 
   * @throws Exception Thrown if server fails to run.
   */
  @Test
  public void testAquaponics() throws Exception {

    // Send PUT command to server.
    String uri = "http://localhost:8111/AQUAPONICS/command/SET_TEMPERATURE?arg=25";
    ClientResource client = new ClientResource(uri);
    client.getLogger().setLevel(Level.OFF);
    client.put(uri);

    assertEquals("Checking sent argument", Integer.valueOf(25), repository
        .getAquaponicsTemperatureCommand().getValue());
  }

  /**
   * Tests PUT command with HVAC. Command: SET_TEMPERATURE; Arg: 25. Won't work until we test with a
   * simulator.
   * 
   * @throws Exception Thrown if server fails to run.
   */
  @Test
  public void testHvac() throws Exception {
    // Send PUT command to server.
    String uri = "http://localhost:8111/HVAC/command/SET_TEMPERATURE?arg=25";
    ClientResource client = new ClientResource(uri);
    client.getLogger().setLevel(Level.OFF);
    client.put(uri);

    assertEquals("Checking sent argument", Integer.valueOf(25), repository
        .getHvacTemperatureCommand().getValue());
  }

  /**
   * Tests PUT command with lighting. Command: SET_LIGHTING_LEVEL; Arg: 50; Room: LIVING. Won't work
   * until we test with a simulator.
   * 
   * @throws Exception Thrown if server fails to run.
   */
  @Test
  public void testLighting() throws Exception {
    // Send PUT command to server.
    String uri = "http://localhost:8111/LIGHTING/command/SET_LIGHTING_LEVEL?arg=50&room=LIVING";
    ClientResource client = new ClientResource(uri);
    client.getLogger().setLevel(Level.OFF);
    client.put(uri);

    assertEquals("Checking sent argument", Integer.valueOf(50),
        repository.getLightingLevelCommand(IHaleRoom.LIVING).getValue());
  }

}