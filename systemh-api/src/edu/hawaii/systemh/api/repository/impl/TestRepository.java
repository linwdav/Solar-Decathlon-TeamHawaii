package edu.hawaii.systemh.api.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.Date;
import java.util.List;
import edu.hawaii.systemh.api.ApiDictionary.SystemHRoom;
import edu.hawaii.systemh.api.ApiDictionary.SystemHState;
import edu.hawaii.systemh.api.ApiDictionary.SystemHSystem;
import edu.hawaii.systemh.api.ApiDictionary.SystemStatusMessageType;
import edu.hawaii.systemh.api.repository.SystemStatusMessage;
import edu.hawaii.systemh.api.repository.TimestampDoublePair;
import edu.hawaii.systemh.api.repository.TimestampIntegerPair;

/**
 * Tests the reference implementation of the SystemH repository API.
 * @author Philip Johnson
 */
public class TestRepository {
  
  /**
   * We want all testing data to be saved in a separate directory from the production data.
   * To do this, we set a system property that is read when the repository is initialized.
   */
  @BeforeClass
  public static void setupTestRepoDirectory () {
    String testRepoDirName = "ApiTestRepository";
    String key = Repository.repositoryDirectoryPropertyKey;
    System.getProperties().put(key, testRepoDirName);
  }
  
  /**
   * Test and exercise a small part of the system.
   */
  @Test
  public void testRepository () {
    
    String testingMessage = "Testing number states";
    
    Repository repository = new Repository();
    Long timestamp = (new Date()).getTime();
    // Add some sample data to the repository.
    repository.store(SystemHSystem.AQUAPONICS, SystemHState.DEAD_FISH, timestamp - 2, 0);
    repository.store(SystemHSystem.AQUAPONICS, SystemHState.DEAD_FISH, timestamp - 1, 3);
    repository.store(SystemHSystem.AQUAPONICS, SystemHState.DEAD_FISH, timestamp, 5);

    repository.store(SystemHSystem.AQUAPONICS, SystemHState.PH, timestamp - 2, 0.0);
    repository.store(SystemHSystem.AQUAPONICS, SystemHState.PH, timestamp - 1, 2.4);
    repository.store(SystemHSystem.AQUAPONICS, SystemHState.PH, timestamp, 7.0);

    // Check that we can retrieve the latest values. 
    assertEquals("Testing latest dead fish", Integer.valueOf(5), 
        repository.getAquaponicsDeadFish().getValue());
    assertEquals("Testing latest pH", 7.0, repository.getAquaponicsPh().getValue(), 0.001);

    // Check that we can retrieve a list of values.
    List<TimestampDoublePair> states = repository.getAquaponicsPhSince(timestamp);
    assertEquals(testingMessage, 1, states.size());
    
    // Check that we can retrieve a list of values for a given time interval.
    states = repository.getAquaponicsPhDuringInterval(timestamp - 1, timestamp);   
    assertEquals(testingMessage, 2, states.size());
    states = repository.getAquaponicsPhDuringInterval(timestamp - 2, timestamp);
    assertEquals(testingMessage, 3, states.size());
    states = repository.getAquaponicsPhDuringInterval(timestamp, timestamp);
    assertEquals(testingMessage, 1, states.size());
    states = repository.getAquaponicsPhDuringInterval(timestamp + 1, timestamp + 1);
    assertEquals(testingMessage, 0, states.size());
    
  }
  
  /**
   * Test that we can add a state entry to all defined repository stores. 
   * This also shows how to add state entries (and appropriate value types).
   */
  @Test
  public void testAllEntityStores() {
    Repository repo = new Repository();
    Long timestamp = (new Date()).getTime();
    
    // Here's examples of storing persistent data for all valid system and state combinations.
    
    // Store example Aquaponics state information (gathered from sensors).
    repo.store(SystemHSystem.AQUAPONICS, SystemHState.CIRCULATION, timestamp, 0.34);
    repo.store(SystemHSystem.AQUAPONICS, SystemHState.DEAD_FISH, timestamp, 22);
    repo.store(SystemHSystem.AQUAPONICS, SystemHState.ELECTRICAL_CONDUCTIVITY, timestamp, 0.3);
    repo.store(SystemHSystem.AQUAPONICS, SystemHState.NUTRIENTS, timestamp, 22.5);
    repo.store(SystemHSystem.AQUAPONICS, SystemHState.OXYGEN, timestamp, 1456.23);
    repo.store(SystemHSystem.AQUAPONICS, SystemHState.PH, timestamp, 7.0);
    repo.store(SystemHSystem.AQUAPONICS, SystemHState.TEMPERATURE, timestamp, 31);
    repo.store(SystemHSystem.AQUAPONICS, SystemHState.TURBIDITY, timestamp, 14.1);
    repo.store(SystemHSystem.AQUAPONICS, SystemHState.WATER_LEVEL, timestamp, 48);
    
    // Store example Aquaponics commmands (supplied from user).
    repo.store(SystemHSystem.AQUAPONICS, SystemHState.FEED_FISH_COMMAND, timestamp, 25.1);
    repo.store(SystemHSystem.AQUAPONICS, SystemHState.HARVEST_FISH_COMMAND, timestamp, 3);
    repo.store(SystemHSystem.AQUAPONICS, SystemHState.SET_NUTRIENTS_COMMAND, timestamp, 30.1);
    repo.store(SystemHSystem.AQUAPONICS, SystemHState.SET_PH_COMMAND, timestamp, 7.6);
    repo.store(SystemHSystem.AQUAPONICS, SystemHState.SET_TEMPERATURE_COMMAND, timestamp, 28);
    repo.store(SystemHSystem.AQUAPONICS, SystemHState.SET_WATER_LEVEL_COMMAND, timestamp, 320);

    // Store example HVAC state (from sensors) and command (from user)
    repo.store(SystemHSystem.HVAC, SystemHState.TEMPERATURE, timestamp, 27);
    repo.store(SystemHSystem.HVAC, SystemHState.SET_TEMPERATURE_COMMAND, timestamp, 32);

    // Store example Electric and PV state (no commands for these two systems).
    repo.store(SystemHSystem.ELECTRIC, SystemHState.ENERGY, timestamp, 456);
    repo.store(SystemHSystem.ELECTRIC, SystemHState.POWER, timestamp, 8763);
    repo.store(SystemHSystem.PHOTOVOLTAIC, SystemHState.ENERGY, timestamp, 34);
    repo.store(SystemHSystem.PHOTOVOLTAIC, SystemHState.POWER, timestamp, 7896);

    // Store example state for Lighting systems.
    // Note that for the "Lighting" system, you specify the Room and "Lighting" is implied.
    // We assume that user can mechanically alter lighting state via physical switches in
    // house, so lighting "commands" do not solely control lighting "state".
    repo.store(SystemHRoom.BATHROOM, SystemHState.LIGHTING_LEVEL, timestamp, 50);
    repo.store(SystemHRoom.BATHROOM, SystemHState.LIGHTING_COLOR, timestamp, "#FF9900");
    repo.store(SystemHRoom.BATHROOM, SystemHState.LIGHTING_ENABLED, timestamp, true);

    repo.store(SystemHRoom.LIVING, SystemHState.LIGHTING_LEVEL, timestamp, 100);
    repo.store(SystemHRoom.LIVING, SystemHState.LIGHTING_COLOR, timestamp, "#FFFF00");
    repo.store(SystemHRoom.LIVING, SystemHState.LIGHTING_ENABLED, timestamp, false);

    repo.store(SystemHRoom.DINING, SystemHState.LIGHTING_LEVEL, timestamp, 1);
    repo.store(SystemHRoom.DINING, SystemHState.LIGHTING_COLOR, timestamp, "#779900");
    repo.store(SystemHRoom.DINING, SystemHState.LIGHTING_ENABLED, timestamp, true);

    repo.store(SystemHRoom.KITCHEN, SystemHState.LIGHTING_LEVEL, timestamp, 50);
    repo.store(SystemHRoom.KITCHEN, SystemHState.LIGHTING_COLOR, timestamp, "#66FF00");
    repo.store(SystemHRoom.KITCHEN, SystemHState.LIGHTING_ENABLED, timestamp, false);
    
    repo.store(SystemHRoom.BATHROOM, SystemHState.LIGHTING_LEVEL, timestamp, 50);
    repo.store(SystemHRoom.BATHROOM, SystemHState.LIGHTING_COLOR, timestamp, "#FF9900");
    repo.store(SystemHRoom.BATHROOM, SystemHState.LIGHTING_ENABLED, timestamp, true);

    // Store example commands to alter lighting state. 
    repo.store(SystemHRoom.LIVING, SystemHState.SET_LIGHTING_LEVEL_COMMAND, timestamp, 100);
    repo.store(SystemHRoom.LIVING, SystemHState.SET_LIGHTING_COLOR_COMMAND, timestamp, "#FFFF00");
    repo.store(SystemHRoom.LIVING, SystemHState.SET_LIGHTING_ENABLED_COMMAND, timestamp, false);

    repo.store(SystemHRoom.DINING, SystemHState.SET_LIGHTING_LEVEL_COMMAND, timestamp, 1);
    repo.store(SystemHRoom.DINING, SystemHState.SET_LIGHTING_COLOR_COMMAND, timestamp, "#779900");
    repo.store(SystemHRoom.DINING, SystemHState.SET_LIGHTING_ENABLED_COMMAND, timestamp, true);

    repo.store(SystemHRoom.KITCHEN, SystemHState.SET_LIGHTING_LEVEL_COMMAND, timestamp, 50);
    repo.store(SystemHRoom.KITCHEN, SystemHState.SET_LIGHTING_COLOR_COMMAND, timestamp, "#66FF00");
    repo.store(SystemHRoom.KITCHEN, SystemHState.SET_LIGHTING_ENABLED_COMMAND, timestamp, false);
    
    // Here's examples of retrieving current state. 
    // We do not show examples of retrieving all possible state. The SystemHRepository interface
    // documents all methods available for retrieving state. There is a method to retrieve the
    // state for all combinations of storing state shown above.
    
    // This first example shows how to get both the timestamp and the value. 
    TimestampDoublePair currAquaCirc = repo.getAquaponicsCirculation();
    assertEquals("Aqua/Circ", timestamp, currAquaCirc.getTimestamp());
    assertEquals("Aqua/Circ", 0.34, currAquaCirc.getValue(), 0.01);

    // The following examples show just retrieval of the value.
    TimestampIntegerPair currAquaFish = repo.getAquaponicsDeadFish();
    assertEquals("Aqua/Fish", Integer.valueOf(22), currAquaFish.getValue());
    
    TimestampDoublePair currAquaEc = repo.getAquaponicsElectricalConductivity();
    assertEquals("Aqua/EC", 0.3, currAquaEc.getValue(), 0.01);
    
    TimestampDoublePair currAquaNutr = repo.getAquaponicsNutrients();
    assertEquals("Aqua/Nutr", 22.5, currAquaNutr.getValue(), 0.01);
    
    TimestampDoublePair currAquaOxy = repo.getAquaponicsOxygen();
    assertEquals("Aqua/Oxy", 1456.23, currAquaOxy.getValue(), 0.01);
  
    TimestampDoublePair currAquaPh = repo.getAquaponicsPh();
    assertEquals("Aqua/pH", 7.0, currAquaPh.getValue(), 0.01);
    
    TimestampIntegerPair currAquaTemp = repo.getAquaponicsTemperature();
    assertEquals("Aqua/Temp", Integer.valueOf(31), currAquaTemp.getValue());

    TimestampDoublePair currAquaTurbidity = repo.getAquaponicsTurbidity();
    assertEquals("Aqua/Turbidity", 14.1, currAquaTurbidity.getValue(), 0.01);
 
    TimestampIntegerPair currAquaWater = repo.getAquaponicsWaterLevel();
    assertEquals("Aqua/Water", Integer.valueOf(48), currAquaWater.getValue());
 
    // For the remainder, we won't assign result to a variable to save typing.
    assertEquals("HVAC/Temp", Integer.valueOf(27), repo.getHvacTemperature().getValue());
    assertEquals("ELEC/Power", Integer.valueOf(8763), repo.getElectricalPower().getValue());
    assertEquals("ELEC/Energy", Integer.valueOf(456), repo.getElectricalEnergy().getValue());

    assertEquals("PV/Power", Integer.valueOf(7896), repo.getPhotovoltaicPower().getValue());
    assertEquals("PV/Energy", Integer.valueOf(34), repo.getPhotovoltaicEnergy().getValue());
    
    assertEquals("Living/Level", Integer.valueOf(100), 
        repo.getLightingLevel(SystemHRoom.LIVING).getValue());
    assertEquals("Living/Color", "#FFFF00",
        repo.getLightingColor(SystemHRoom.LIVING).getValue());
    assertEquals("Living/Power", false, 
        repo.getLightingEnabled(SystemHRoom.LIVING).getValue());

    assertEquals("Bath/Level", Integer.valueOf(50), 
        repo.getLightingLevel(SystemHRoom.BATHROOM).getValue());
    assertEquals("Bath/Color", "#FF9900",
        repo.getLightingColor(SystemHRoom.BATHROOM).getValue());
    assertEquals("Bath/Power", true, 
        repo.getLightingEnabled(SystemHRoom.BATHROOM).getValue());

    assertEquals("Dining/Level", Integer.valueOf(1), 
        repo.getLightingLevel(SystemHRoom.DINING).getValue());
    assertEquals("Dining/Color", "#779900",
        repo.getLightingColor(SystemHRoom.DINING).getValue());
    assertEquals("Dining/Power", true, 
        repo.getLightingEnabled(SystemHRoom.DINING).getValue());
    
    assertEquals("Kitchen/Level", Integer.valueOf(50), 
        repo.getLightingLevel(SystemHRoom.KITCHEN).getValue());
    assertEquals("Kitchen/Color", "#66FF00",
        repo.getLightingColor(SystemHRoom.KITCHEN).getValue());
    assertEquals("Kitchen/Power", false, 
        repo.getLightingEnabled(SystemHRoom.KITCHEN).getValue());

    // Finally, examples of how to retrieve historical state.
    // The current interface allows you to get a list of Timestamp-Value pairs from 
    // any time in the past up to the present.  This makes it easy to generate simple charts. 
    
    // The following retrieves all data about each system/state pair for the past 24 hours.
    Long yesterday = (new Date()).getTime() - (1000 * 60 * 60 * 24);
    List<TimestampDoublePair> circulationHistory = repo.getAquaponicsCirculationSince(yesterday);
    assertFalse("Circulation history is non-empty", circulationHistory.isEmpty());
    
    // Here are some other examples. 
    repo.getAquaponicsDeadFishSince(yesterday);
    repo.getAquaponicsElectricalConductivitySince(yesterday);
    repo.getAquaponicsNutrientsSince(yesterday);
    repo.getAquaponicsOxygenSince(yesterday);
    repo.getAquaponicsPhSince(yesterday);
    repo.getAquaponicsTemperatureSince(yesterday);
    repo.getAquaponicsTurbiditySince(yesterday);
    repo.getAquaponicsWaterLevelSince(yesterday);
    repo.getElectricalEnergySince(yesterday);
    repo.getElectricalPowerSince(yesterday);
    repo.getHvacTemperatureSince(yesterday);
    repo.getHvacTemperatureCommandSince(yesterday);
    
    // Examples of historical lighting data for one room. It works for all rooms, of course.
    repo.getLightingColorSince(SystemHRoom.DINING, yesterday);
    repo.getLightingLevelSince(SystemHRoom.DINING, yesterday);
    repo.getLightingEnabledSince(SystemHRoom.DINING, yesterday);
  }
  
  /**
   * Test to show that storage and retrieval of status messages works. 
   */
  @Test
  public void testSystemStatusMessages() {
    // Create a repository.
    Repository repo = new Repository();
    
    // Create a system status message.
    Long timestamp = (new Date()).getTime();
    SystemHSystem system = SystemHSystem.AQUAPONICS;
    SystemStatusMessageType type = SystemStatusMessageType.ALERT;
    String message = "Oxygen has fallen below 12 ppm. All the fish will die.";
    SystemStatusMessage statusMessage = new SystemStatusMessage(timestamp, system, type, message);
    
    // Add the message to the repository.
    repo.store(statusMessage);
    
    // Retrieve it.
    List<SystemStatusMessage> messages = repo.getSystemStatusMessageSince(timestamp - 1);
    assertEquals("Message retrieval", 1, messages.size());
  }
  
  /**
   * Illustrate the use of retrieving the type of a state variable.
   */
  @Test
  public void testEnumeratedType() {
    SystemHState stateVar = SystemHState.DEAD_FISH;
    assertEquals("Checking type var", Integer.class, stateVar.getType());
    
    assertTrue("Checking isType 1", SystemHState.DEAD_FISH.isType("7"));
    assertFalse("Checking isType 2", SystemHState.DEAD_FISH.isType("7.0"));
    
    assertTrue("Checking isType 3", SystemHState.CIRCULATION.isType("7.0"));
    assertTrue("Checking isType 4", SystemHState.CIRCULATION.isType("7"));
    assertFalse("Checking isType 5", SystemHState.CIRCULATION.isType("true"));
    
    assertTrue("Checking isType 6", SystemHState.LIGHTING_ENABLED.isType("true"));
    assertFalse("Checking isType 7", SystemHState.LIGHTING_ENABLED.isType("7.0"));
    
    assertFalse("Checking isType 8", SystemHState.LIGHTING_COLOR.isType("7"));
    assertTrue("Checking isType 9", SystemHState.LIGHTING_COLOR.isType("#FF0000"));
  }
}
