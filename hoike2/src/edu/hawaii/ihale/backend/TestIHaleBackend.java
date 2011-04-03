package edu.hawaii.ihale.backend;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.Test;
import org.xml.sax.SAXException;
import edu.hawaii.ihale.api.ApiDictionary.IHaleCommandType;
import edu.hawaii.ihale.api.ApiDictionary.IHaleSystem;
import edu.hawaii.ihale.api.repository.impl.Repository;

/**
 * JUnit tests the IHaleBackend.
 * 
 * @author Bret K. Ikehara, Greg Burgess
 */
public class TestIHaleBackend {

  private static final IHaleBackend backend;
  private static final Repository repo;

  static {
    backend = IHaleBackend.getInstance();
    repo = new Repository();
  }

  /**
   * Checks the parsing the files.
   * 
   * @throws IOException Thrown when parsing URL file fails.
   */
  @Test
  public void testParseURIPropertyFileValid() throws IOException {
    Map<String, String> uri = IHaleBackend.parseURIPropertyFile(IHaleBackend.deviceConfigRef);

    assertEquals("Electrical state", "http://localhost:7002/", uri.get("electrical-state"));
  }
  
  /**
   * Checks the parsing the files.
   * 
   * @throws IOException Thrown when parsing URL file fails.
   */
  @Test (expected=IOException.class)
  public void testParseURIPropertyFileNull() throws IOException {
    Map<String, String> uri = IHaleBackend.parseURIPropertyFile(null);

    assertEquals("Electrical state", "http://localhost:7002/", uri.get("electrical-state"));
  }
  
  /**
   * Checks the parsing the files.
   * 
   * @throws IOException Thrown when parsing URL file fails.
   */
  @Test (expected=IOException.class)
  public void testParseURIPropertyFileInvalid() throws IOException {
    Map<String, String> uri = IHaleBackend.parseURIPropertyFile("invalid location");

    assertEquals("Electrical state", "http://localhost:7002/", uri.get("electrical-state"));
  }

  /**
   * Tests the HVAC doCommand for a successful PUT. Remove @Ignore tag when running with a
   * simulator.
   * 
   * @throws IOException Thrown when URL connection fails.
   * @throws ParserConfigurationException Thrown when building XML document.
   * @throws SAXException Thrown when parsing XML input stream.
   */
  @Test
  public void doCommandHvacSystem() throws IOException, ParserConfigurationException, SAXException {

    Integer value = Integer.valueOf(45);

    backend.doCommand(IHaleSystem.HVAC, null, IHaleCommandType.SET_TEMPERATURE, value);

    assertEquals("HVAC temperature changed", value, repo.getHvacTemperatureCommand().getValue());
  }

  /**
   * Tests the Aquaponics doCommand for a successful PUT. Remove @Ignore tag when running with a
   * simulator.
   */
  @Test
  public void doCommandAquaponicsSystemValid() {

    Integer value = Integer.valueOf(45);

    // Test valid input
    backend.doCommand(IHaleSystem.AQUAPONICS, null, IHaleCommandType.SET_TEMPERATURE, value);
    assertEquals("temperature changed", value, repo.getAquaponicsTemperature().getValue());
  }

  /**
   * Tests the Aquaponics doCommand for a successful PUT. Remove @Ignore tag when running with a
   * simulator.
   */
  @Test (expected=RuntimeException.class)
  public void doCommandAquaponicsSystemNull() {

    Object obj = null;

    // Test valid input
    backend.doCommand(IHaleSystem.AQUAPONICS, null, IHaleCommandType.SET_TEMPERATURE, obj);
  }
  
  /**
   * Tests the Aquaponics doCommand for a successful PUT. Remove @Ignore tag when running with a
   * simulator.
   */
  @Test (expected=RuntimeException.class)
  public void doCommandAquaponicsSystemInvalid() {

    Object obj = "invalid";

    // Test valid input
    backend.doCommand(IHaleSystem.AQUAPONICS, null, IHaleCommandType.SET_TEMPERATURE, obj);
  }
}
