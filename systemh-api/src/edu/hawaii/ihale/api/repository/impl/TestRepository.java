package edu.hawaii.ihale.api.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.Date;
import java.util.List;
import edu.hawaii.ihale.api.ApiDictionary.IHaleRoom;
import edu.hawaii.ihale.api.ApiDictionary.IHaleState;
import edu.hawaii.ihale.api.ApiDictionary.IHaleSystem;
import edu.hawaii.ihale.api.ApiDictionary.SystemStatusMessageType;
import edu.hawaii.ihale.api.repository.SystemStatusMessage;
import edu.hawaii.ihale.api.repository.TimestampDoublePair;
import edu.hawaii.ihale.api.repository.TimestampIntegerPair;

/**
 * Tests the reference implementation of the iHale repository API.
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
    Repository repository = new Repository();
    Long timestamp = (new Date()).getTime();
    // Add some sample data to the repository.
    repository.store(IHaleSystem.AQUAPONICS, IHaleState.DEAD_FISH, timestamp - 2, 0);
    repository.store(IHaleSystem.AQUAPONICS, IHaleState.DEAD_FISH, timestamp - 1, 3);
    repository.store(IHaleSystem.AQUAPONICS, IHaleState.DEAD_FISH, timestamp, 5);

    repository.store(IHaleSystem.AQUAPONICS, IHaleState.PH, timestamp - 2, 0.0);
    repository.store(IHaleSystem.AQUAPONICS, IHaleState.PH, timestamp - 1, 2.4);
    repository.store(IHaleSystem.AQUAPONICS, IHaleState.PH, timestamp, 7.0);

    // Check that we can retrieve the latest values. 
    assertEquals("Testing latest dead fish", Integer.valueOf(5), 
        repository.getAquaponicsDeadFish().getValue());
    assertEquals("Testing latest pH", 7.0, repository.getAquaponicsPh().getValue(), 0.001);

    // Check that we can retrieve a list of values.
    List<TimestampDoublePair> states = repository.getAquaponicsPhSince(timestamp);
    assertEquals("Testing number states", 1, states.size());
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
    repo.store(IHaleSystem.AQUAPONICS, IHaleState.CIRCULATION, timestamp, 0.34);
    repo.store(IHaleSystem.AQUAPONICS, IHaleState.DEAD_FISH, timestamp, 22);
    repo.store(IHaleSystem.AQUAPONICS, IHaleState.ELECTRICAL_CONDUCTIVITY, timestamp, 0.3);
    repo.store(IHaleSystem.AQUAPONICS, IHaleState.NUTRIENTS, timestamp, 22.5);
    repo.store(IHaleSystem.AQUAPONICS, IHaleState.OXYGEN, timestamp, 1456.23);
    repo.store(IHaleSystem.AQUAPONICS, IHaleState.PH, timestamp, 7.0);
    repo.store(IHaleSystem.AQUAPONICS, IHaleState.TEMPERATURE, timestamp, 31);
    repo.store(IHaleSystem.AQUAPONICS, IHaleState.TURBIDITY, timestamp, 14.1);
    repo.store(IHaleSystem.AQUAPONICS, IHaleState.WATER_LEVEL, timestamp, 48);
    
    // Store example Aquaponics commmands (supplied from user).
    repo.store(IHaleSystem.AQUAPONICS, IHaleState.FEED_FISH_COMMAND, timestamp, 25.1);
    repo.store(IHaleSystem.AQUAPONICS, IHaleState.HARVEST_FISH_COMMAND, timestamp, 3);
    repo.store(IHaleSystem.AQUAPONICS, IHaleState.SET_NUTRIENTS_COMMAND, timestamp, 30.1);
    repo.store(IHaleSystem.AQUAPONICS, IHaleState.SET_PH_COMMAND, timestamp, 7.6);
    repo.store(IHaleSystem.AQUAPONICS, IHaleState.SET_TEMPERATURE_COMMAND, timestamp, 28);
    repo.store(IHaleSystem.AQUAPONICS, IHaleState.SET_WATER_LEVEL_COMMAND, timestamp, 320);

    // Store example HVAC state (from sensors) and command (from user)
    repo.store(IHaleSystem.HVAC, IHaleState.TEMPERATURE, timestamp, 27);
    repo.store(IHaleSystem.HVAC, IHaleState.SET_TEMPERATURE_COMMAND, timestamp, 32);

    // Store example Electric and PV state (no commands for these two systems).
    repo.store(IHaleSystem.ELECTRIC, IHaleState.ENERGY, timestamp, 456);
    repo.store(IHaleSystem.ELECTRIC, IHaleState.POWER, timestamp, 8763);
    repo.store(IHaleSystem.PHOTOVOLTAIC, IHaleState.ENERGY, timestamp, 34);
    repo.store(IHaleSystem.PHOTOVOLTAIC, IHaleState.POWER, timestamp, 7896);

    // Store example state for Lighting systems.
    // Note that for the "Lighting" system, you specify the Room and "Lighting" is implied.
    // We assume that user can mechanically alter lighting state via physical switches in
    // house, so lighting "commands" do not solely control lighting "state".
    repo.store(IHaleRoom.BATHROOM, IHaleState.LIGHTING_LEVEL, timestamp, 50);
    repo.store(IHaleRoom.BATHROOM, IHaleState.LIGHTING_COLOR, timestamp, "#FF9900");
    repo.store(IHaleRoom.BATHROOM, IHaleState.LIGHTING_ENABLED, timestamp, true);

    repo.store(IHaleRoom.LIVING, IHaleState.LIGHTING_LEVEL, timestamp, 100);
    repo.store(IHaleRoom.LIVING, IHaleState.LIGHTING_COLOR, timestamp, "#FFFF00");
    repo.store(IHaleRoom.LIVING, IHaleState.LIGHTING_ENABLED, timestamp, false);

    repo.store(IHaleRoom.DINING, IHaleState.LIGHTING_LEVEL, timestamp, 1);
    repo.store(IHaleRoom.DINING, IHaleState.LIGHTING_COLOR, timestamp, "#779900");
    repo.store(IHaleRoom.DINING, IHaleState.LIGHTING_ENABLED, timestamp, true);

    repo.store(IHaleRoom.KITCHEN, IHaleState.LIGHTING_LEVEL, timestamp, 50);
    repo.store(IHaleRoom.KITCHEN, IHaleState.LIGHTING_COLOR, timestamp, "#66FF00");
    repo.store(IHaleRoom.KITCHEN, IHaleState.LIGHTING_ENABLED, timestamp, false);
    
    repo.store(IHaleRoom.BATHROOM, IHaleState.LIGHTING_LEVEL, timestamp, 50);
    repo.store(IHaleRoom.BATHROOM, IHaleState.LIGHTING_COLOR, timestamp, "#FF9900");
    repo.store(IHaleRoom.BATHROOM, IHaleState.LIGHTING_ENABLED, timestamp, true);

    // Store example commands to alter lighting state. 
    repo.store(IHaleRoom.LIVING, IHaleState.SET_LIGHTING_LEVEL_COMMAND, timestamp, 100);
    repo.store(IHaleRoom.LIVING, IHaleState.SET_LIGHTING_COLOR_COMMAND, timestamp, "#FFFF00");
    repo.store(IHaleRoom.LIVING, IHaleState.SET_LIGHTING_ENABLED_COMMAND, timestamp, false);

    repo.store(IHaleRoom.DINING, IHaleState.SET_LIGHTING_LEVEL_COMMAND, timestamp, 1);
    repo.store(IHaleRoom.DINING, IHaleState.SET_LIGHTING_COLOR_COMMAND, timestamp, "#779900");
    repo.store(IHaleRoom.DINING, IHaleState.SET_LIGHTING_ENABLED_COMMAND, timestamp, true);

    repo.store(IHaleRoom.KITCHEN, IHaleState.SET_LIGHTING_LEVEL_COMMAND, timestamp, 50);
    repo.store(IHaleRoom.KITCHEN, IHaleState.SET_LIGHTING_COLOR_COMMAND, timestamp, "#66FF00");
    repo.store(IHaleRoom.KITCHEN, IHaleState.SET_LIGHTING_ENABLED_COMMAND, timestamp, false);
    
    // Here's examples of retrieving current state. 
    // We do not show examples of retrieving all possible state. The IHaleRepository interface
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
        repo.getLightingLevel(IHaleRoom.LIVING).getValue());
    assertEquals("Living/Color", "#FFFF00",
        repo.getLightingColor(IHaleRoom.LIVING).getValue());
    assertEquals("Living/Power", false, 
        repo.getLightingEnabled(IHaleRoom.LIVING).getValue());

    assertEquals("Bath/Level", Integer.valueOf(50), 
        repo.getLightingLevel(IHaleRoom.BATHROOM).getValue());
    assertEquals("Bath/Color", "#FF9900",
        repo.getLightingColor(IHaleRoom.BATHROOM).getValue());
    assertEquals("Bath/Power", true, 
        repo.getLightingEnabled(IHaleRoom.BATHROOM).getValue());

    assertEquals("Dining/Level", Integer.valueOf(1), 
        repo.getLightingLevel(IHaleRoom.DINING).getValue());
    assertEquals("Dining/Color", "#779900",
        repo.getLightingColor(IHaleRoom.DINING).getValue());
    assertEquals("Dining/Power", true, 
        repo.getLightingEnabled(IHaleRoom.DINING).getValue());
    
    assertEquals("Kitchen/Level", Integer.valueOf(50), 
        repo.getLightingLevel(IHaleRoom.KITCHEN).getValue());
    assertEquals("Kitchen/Color", "#66FF00",
        repo.getLightingColor(IHaleRoom.KITCHEN).getValue());
    assertEquals("Kitchen/Power", false, 
        repo.getLightingEnabled(IHaleRoom.KITCHEN).getValue());

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
    repo.getLightingColorSince(IHaleRoom.DINING, yesterday);
    repo.getLightingLevelSince(IHaleRoom.DINING, yesterday);
    repo.getLightingEnabledSince(IHaleRoom.DINING, yesterday);
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
    IHaleSystem system = IHaleSystem.AQUAPONICS;
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
    IHaleState stateVar = IHaleState.DEAD_FISH;
    assertEquals("Checking type var", Integer.class, stateVar.getType());
    
    assertTrue("Checking isType 1", IHaleState.DEAD_FISH.isType("7"));
    assertFalse("Checking isType 2", IHaleState.DEAD_FISH.isType("7.0"));
    
    assertTrue("Checking isType 3", IHaleState.CIRCULATION.isType("7.0"));
    assertTrue("Checking isType 4", IHaleState.CIRCULATION.isType("7"));
    assertFalse("Checking isType 5", IHaleState.CIRCULATION.isType("true"));
    
    assertTrue("Checking isType 6", IHaleState.LIGHTING_ENABLED.isType("true"));
    assertFalse("Checking isType 7", IHaleState.LIGHTING_ENABLED.isType("7.0"));
    
    assertFalse("Checking isType 8", IHaleState.LIGHTING_COLOR.isType("7"));
    assertTrue("Checking isType 9", IHaleState.LIGHTING_COLOR.isType("#FF0000"));
  }
}
