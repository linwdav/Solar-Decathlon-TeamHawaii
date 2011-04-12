package edu.hawaii.ihale.api.repository;

import java.util.List;
import edu.hawaii.ihale.api.ApiDictionary.IHaleRoom;

/**
 * Specifies the interface used by the iHale front-end to get data from the back-end repository.
 * @author Philip Johnson
 */
public interface IHaleRepository {
  
  /**
   * Returns a (timestamp, double) pair indicating the most recently collected circulation data.
   * Circulation is defined as the number of hours the pump has been on divided by the total 
   * number of gallons of water in the tank.
   * @return The most recent data concerning circulation and its timestamp,  
   * or null if there is no data or errors occurred retrieving it.
   */
  public TimestampDoublePair getAquaponicsCirculation();
  
  /**
   * Returns a list of (timestamp, double) pairs indicating circulation data from the passed
   * timestamp up to the present. 
   * @param timestamp The timestamp indicating when data collection should begin.
   * @return A list of (timestamp, double) pairs with circulation data,
   * or null if there is no data or errors occurred retrieving it.
   */
  public List<TimestampDoublePair> getAquaponicsCirculationSince(Long timestamp);
  
  /**
   * Returns a (timestamp, integer) pair indicating the most recently collected data on the 
   * number of dead fish in the tank.
   * @return The most recent data concerning dead fish,
   * or null if there is no data or errors occurred retrieving it.
   */
  public TimestampIntegerPair getAquaponicsDeadFish();
  
  /**
   * Returns a list of (timestamp, double) pairs indicating circulation data from the passed
   * timestamp up to the present. 
   * @param timestamp The timestamp indicating when data collection should begin.
   * @return A list of (timestamp, double) pairs with circulation data, 
   * or null if there is no data or errors occurred retrieving it.
   */
  public List<TimestampIntegerPair> getAquaponicsDeadFishSince(Long timestamp);
  
  /**
   * Returns a (timestamp, double) pair indicating the most recently collected data on 
   * electrical conductivity measured as uS/cm.
   * @return The most recent data on electrical conductivity,
   * or null if there is no data or errors occurred retrieving it.
   */
  public TimestampDoublePair getAquaponicsElectricalConductivity();
  
  /**
   * Returns a list of (timestamp, double) pairs indicating electrical conductivity data from 
   * the passed timestamp up to the present. 
   * @param timestamp The timestamp indicating when data collection should begin.
   * @return A list of (timestamp, double) pairs with electrical conductivity data, 
   * or null if there is no data or errors occurred retrieving it.
   */
  public List<TimestampDoublePair> getAquaponicsElectricalConductivitySince(Long timestamp);
  
  /**
   * Returns a (timestamp, double) pair indicating the most recently collected data on 
   * nutrients in the water. 
   * @return The most recent data on nutrients,
   * or null if there is no data or errors occurred retrieving it.
   */
  public TimestampDoublePair getAquaponicsNutrients();
  
  /**
   * Returns a list of (timestamp, double) pairs indicating nutrient data from the passed
   * timestamp up to the present. 
   * @param timestamp The timestamp indicating when data collection should begin.
   * @return A list of (timestamp, double) pairs with nutrient data, 
   * or null if there is no data or errors occurred retrieving it.
   */
  public List<TimestampDoublePair> getAquaponicsNutrientsSince(Long timestamp);
  
  /**
   * Returns a (timestamp, double) pair with the most recently collected data on 
   * dissolved oxygen in ppm.
   * @return The most recent data on dissolved oxygen,
   * or null if there is no data or errors occurred retrieving it.
   */
  public TimestampDoublePair getAquaponicsOxygen();
  
  /**
   * Returns a list of (timestamp, double) pairs indicating oxygen data from the passed
   * timestamp up to the present. 
   * @param timestamp The timestamp indicating when data collection should begin.
   * @return A list of (timestamp, double) pairs with oxygen data, 
   * or null if there is no data or errors occurred retrieving it.
   */
  public List<TimestampDoublePair> getAquaponicsOxygenSince(Long timestamp);
  
  /**
   * Returns a (timestamp, double) pair with the most recently collected data on pH.
   * @return The most recent data on pH,
   * or null if there is no data or errors occurred retrieving it.
   */
  public TimestampDoublePair getAquaponicsPh();
  
  /**
   * Returns a list of (timestamp, double) pairs indicating circulation data from the passed
   * timestamp up to the present. 
   * @param timestamp The timestamp indicating when data collection should begin.
   * @return A list of (timestamp, double) pairs with circulation data, 
   * or null if there is no data or errors occurred retrieving it.
   */
  public List<TimestampDoublePair> getAquaponicsPhSince(Long timestamp);
  
  /**
   * Returns a (timestamp, integer) pair with the current temperature in Celsius.
   * @return The most recent data on temperature,
   * or null if there is no data or errors occurred retrieving it.
   */
  public TimestampIntegerPair getAquaponicsTemperature();
  
  /**
   * Returns a list of (timestamp, double) pairs indicating circulation data from the passed
   * timestamp up to the present. 
   * @param timestamp The timestamp indicating when data collection should begin.
   * @return A list of (timestamp, double) pairs with circulation data, 
   * or null if there is no data or errors occurred retrieving it.
   */
  public List<TimestampIntegerPair> getAquaponicsTemperatureSince(Long timestamp);
  
  /**
   * Returns a (timestamp, double) pair with the most recent data on turbidity measured 
   * in FTU (Formazin Turbidity Units).
   * @return The most recent turbidity data,
   * or null if there is no data or errors occurred retrieving it.
   */
  public TimestampDoublePair getAquaponicsTurbidity();
  
  /**
   * Returns a list of (timestamp, double) pairs indicating turbidity data from the passed
   * timestamp up to the present. 
   * @param timestamp The timestamp indicating when data collection should begin.
   * @return A list of (timestamp, double) pairs with turbidity data, 
   * or null if there is no data or errors occurred retrieving it.
   */
  public List<TimestampDoublePair> getAquaponicsTurbiditySince(Long timestamp);
  
  /**
   * Returns a (timestamp, integer) pair with the most recent data on water level expressed
   * in gallons. 
   * @return The most recent water level data,
   * or null if there is no data or errors occurred retrieving it.
   */
  public TimestampIntegerPair getAquaponicsWaterLevel();
  
  /**
   * Returns a list of (timestamp, double) pairs indicating circulation data from the passed
   * timestamp up to the present. 
   * @param timestamp The timestamp indicating when data collection should begin.
   * @return A list of (timestamp, double) pairs with circulation data, 
   * or null if there is no data or errors occurred retrieving it.
   */
  public List<TimestampIntegerPair> getAquaponicsWaterLevelSince(Long timestamp);
  
  /**
   * Returns a (timestamp, double) pair with the most recent command to feed the fish and 
   * how much food was requested to be fed.
   * @return The most recent fish feeding data,
   * or null if there is no data or errors occurred retrieving it.
   */
  public TimestampDoublePair getAquaponicsFeedFishCommand();
  
  /**
   * Returns a list of (timestamp, double) pairs indicating fish feeding command data from 
   * the passed timestamp up to the present. 
   * @param timestamp The timestamp indicating when data collection should begin.
   * @return A list of (timestamp, double) pairs with fish feeding command data, 
   * or null if there is no data or errors occurred retrieving it.
   */
  public List<TimestampDoublePair> getAquaponicsFeedFishCommmandSince(Long timestamp);
  
  /**
   * Returns a (timestamp, integer) pair with the most recent command to harvest the fish and 
   * (optionally) how many fish they hope to harvest.
   * @return The most recent fish harvest data,
   * or null if there is no data or errors occurred retrieving it.
   */
  public TimestampIntegerPair getAquaponicsHarvestFishCommand();
  
  /**
   * Returns a list of (timestamp, double) pairs indicating fish harvesting command data from 
   * the passed timestamp up to the present. 
   * @param timestamp The timestamp indicating when data collection should begin.
   * @return A list of (timestamp, double) pairs with fish harvesting command data, 
   * or null if there is no data or errors occurred retrieving it.
   */
  public List<TimestampIntegerPair> getAquaponicsHarvestFishCommmandSince(Long timestamp);
  
  /**
   * Returns a (timestamp, double) pair with the most recent command to add nutrients and 
   * how much nutrients were requested to be released.
   * (Not sure if this is needed given the feed fish command.)
   * @return The most recent nutrient command data,
   * or null if there is no data or errors occurred retrieving it.
   */
  public TimestampDoublePair getAquaponicsNutrientsCommand();
  
  /**
   * Returns a list of (timestamp, double) pairs indicating nutrient command data from 
   * the passed timestamp up to the present. 
   * @param timestamp The timestamp indicating when data collection should begin.
   * @return A list of (timestamp, double) pairs with nutrient command data, 
   * or null if there is no data or errors occurred retrieving it.
   */
  public List<TimestampDoublePair> getAquaponicsNutrientCommmandSince(Long timestamp);
  
  /**
   * Returns a (timestamp, double) pair with the most recent command to adjust pH and 
   * what the desired pH should be as a double.
   * @return The most recent pH command data,
   * or null if there is no data or errors occurred retrieving it.
   */
  public TimestampDoublePair getAquaponicsPhCommand();
  
  /**
   * Returns a list of (timestamp, double) pairs indicating pH command data from 
   * the passed timestamp up to the present. 
   * @param timestamp The timestamp indicating when data collection should begin.
   * @return A list of (timestamp, double) pairs with pH command data, 
   * or null if there is no data or errors occurred retrieving it.
   */
  public List<TimestampDoublePair> getAquaponicsPhCommmandSince(Long timestamp);
  
  /**
   * Returns a (timestamp, integer) pair with the most recent command to adjust the 
   * temperature and what the new temperature should be (in celsius).
   * @return The most recent temperature command,
   * or null if there is no data or errors occurred retrieving it.
   */
  public TimestampIntegerPair getAquaponicsTemperatureCommand();
  
  /**
   * Returns a list of (timestamp, double) pairs indicating temperature command data from 
   * the passed timestamp up to the present. 
   * @param timestamp The timestamp indicating when data collection should begin.
   * @return A list of (timestamp, double) pairs with temperature command data, 
   * or null if there is no data or errors occurred retrieving it.
   */
  public List<TimestampIntegerPair> getAquaponicsTemperatureCommandSince(Long timestamp);
  
  /**
   * Returns a (timestamp, integer) pair with the most recent command to adjust the 
   * water level and what the new water level should be (in gallons).
   * @return The most recent water level command,
   * or null if there is no data or errors occurred retrieving it.
   */
  public TimestampIntegerPair getAquaponicsWaterLevelCommand();
  
  /**
   * Returns a list of (timestamp, integer) pairs indicating water level command data from 
   * the passed timestamp up to the present. 
   * @param timestamp The timestamp indicating when data collection should begin.
   * @return A list of (timestamp, double) pairs with water level command data, 
   * or null if there is no data or errors occurred retrieving it.
   */
  public List<TimestampIntegerPair> getAquaponicsWaterLevelCommmandSince(Long timestamp);

  /**
   * Returns a (timestamp, integer) pair with the most recent data on the inside temperature 
   * of the house expressed in celsius.
   * @return The most recent inside temperature data,
   * or null if there is no data or errors occurred retrieving it.
   */
  public TimestampIntegerPair getHvacTemperature();
  
  /**
   * Returns a list of (timestamp, double) pairs indicating temperature data from the passed
   * timestamp up to the present. 
   * @param timestamp The timestamp indicating when data collection should begin.
   * @return A list of (timestamp, double) pairs with temperature data, 
   * or null if there is no data or errors occurred retrieving it.
   */
  public List<TimestampIntegerPair> getHvacTemperatureSince(Long timestamp);
  
  /**
   * Returns a (timestamp, integer) pair indicating the last requested value for temperature 
   * from the user, expressed in celsius.
   * @return The last requested temperature,
   * or null if there is no data or errors occurred retrieving it.
   */
  public TimestampIntegerPair getHvacTemperatureCommand();
  
  /**
   * Returns a list of (timestamp, integer) pairs indicating requested temperature data from the 
   * passed timestamp up to the present. 
   * @param timestamp The timestamp indicating when data collection should begin.
   * @return A list of (timestamp, integer) pairs with requested temperature data, 
   * or null if there is no data or errors occurred retrieving it.
   */
  public List<TimestampIntegerPair> getHvacTemperatureCommandSince(Long timestamp);

  /**
   * Returns the most recent data on the power being generated by the photovoltaic panel in watts.
   * @return The most recent power generated,
   * or null if there is no data or errors occurred retrieving it.
   */
  public TimestampIntegerPair getPhotovoltaicPower();
  
  /**
   * Returns a list of (timestamp, integer) pairs indicating power data from the passed
   * timestamp up to the present. 
   * @param timestamp The timestamp indicating when data collection should begin.
   * @return A list of (timestamp, integer) pairs with power data, 
   * or null if there is no data or errors occurred retrieving it.
   */
  public List<TimestampIntegerPair> getPhotovoltaicPowerSince(Long timestamp);
  
  /**
   * Returns a (timestamp, integer) pair indicating the mostly collected energy counter reading
   * from the photovoltaic panel.  The "energy counter" is a monotonically increasing integer
   * indicating the total number of watt-hours of energy generated since the counter was 
   * initialized. To obtain the total watt-hours of energy generated during an interval, just
   * subtract the counter values from the start and end of the interval.
   * @return The most recent energy data,
   * or null if there is no data or errors occurred retrieving it.
   */
  public TimestampIntegerPair getPhotovoltaicEnergy();

  /**
   * Returns a list of (timestamp, integer) pairs indicating energy data from the passed
   * timestamp up to the present. 
   * @param timestamp The timestamp indicating when data collection should begin.
   * @return A list of (timestamp, integer) pairs with energy data, 
   * or null if there is no data or errors occurred retrieving it.
   */
  public List<TimestampIntegerPair> getPhotovoltaicEnergySince(Long timestamp);

  /**
   * Returns the most recent data on the power being consumed by the house in watts.
   * @return The most recent power consumed,
   * or null if there is no data or errors occurred retrieving it.
   */
  public TimestampIntegerPair getElectricalPower();

  /**
   * Returns a list of (timestamp, integer) pairs indicating power data from the passed
   * timestamp up to the present. 
   * @param timestamp The timestamp indicating when data collection should begin.
   * @return A list of (timestamp, integer) pairs with power data, 
   * or null if there is no data or errors occurred retrieving it.
   */
  public List<TimestampIntegerPair> getElectricalPowerSince(Long timestamp);
  
  /**
   * Returns a (timestamp, integer) pair indicating the mostly collected energy counter reading
   * from the meter panel.  The "energy counter" is a monotonically increasing integer
   * indicating the total number of watt-hours of energy consumed since the counter was 
   * initialized. To obtain the total watt-hours of energy consumed during an interval, just
   * subtract the counter values from the start and end of the interval.
   * @return The most recent energy data,
   * or null if there is no data or errors occurred retrieving it.
   */
  public TimestampIntegerPair getElectricalEnergy();
  
  /**
   * Returns a list of (timestamp, integer) pairs indicating energy data from the passed
   * timestamp up to the present. 
   * @param timestamp The timestamp indicating when data collection should begin.
   * @return A list of (timestamp, integer) pairs with energy data, 
   * or null if there is no data or errors occurred retrieving it.
   */
  public List<TimestampIntegerPair> getElectricalEnergySince(Long timestamp);

  /**
   * Returns a (timestamp, integer) pair indicating the most recent data on the level of 
   * light in a room. This is an integer from 0 to 100.
   * @param room The room in the house.
   * @return The most recent data on lighting level in a room,
   * or null if there is no data or errors occurred retrieving it.
   */
  public TimestampIntegerPair getLightingLevel(IHaleRoom room);
  
  /**
   * Returns a list of (timestamp, double) pairs indicating lighting data from the passed
   * timestamp up to the present. 
   * @param room The room in the house.
   * @param timestamp The timestamp indicating when data collection should begin.
   * @return A list of (timestamp, double) pairs with circulation data, 
   * or null if there is no data or errors occurred retrieving it.
   */
  public List<TimestampIntegerPair> getLightingLevelSince(IHaleRoom room, Long timestamp);
  
  /**
   * Returns a (timestamp, string) pair indicating the most recent data on the color of light
   * in the room. 
   * @param room The room.
   * @return The most recent data on the lighting color in the room,
   * or null if there is no data or errors occurred retrieving it.
   */
  public TimestampStringPair getLightingColor(IHaleRoom room);
  
  /**
   * Returns a list of (timestamp, string) pairs indicating light color data from the passed
   * timestamp up to the present. 
   * @param room The room.
   * @param timestamp The timestamp indicating when data collection should begin.
   * @return A list of (timestamp, string) pairs with light color data, 
   * or null if there is no data or errors occurred retrieving it.
   */
  public List<TimestampStringPair> getLightingColorSince(IHaleRoom room, Long timestamp);
  
  /**
   * Returns a (timestamp, boolean) pair indicating the most recent data on whether the lights
   * are on or off in a room. 
   * @param room The room. 
   * @return The most recent data on whether the lights are on or off,
   * or null if there is no data or errors occurred retrieving it.
   */
  public TimestampBooleanPair getLightingEnabled(IHaleRoom room);
  
  /**
   * Returns a list of (timestamp, boolean) pairs indicating lighting data from the passed
   * timestamp up to the present. 
   * @param room The room. 
   * @param timestamp The timestamp indicating when data collection should begin.
   * @return A list of (timestamp, boolean) pairs with lighting data, 
   * or null if there is no data or errors occurred retrieving it.
   */
  public List<TimestampBooleanPair> getLightingEnabledSince(IHaleRoom room, Long timestamp);
  
  /**
   * Returns a (timestamp, integer) pair with the most recent command to adjust the 
   * lighting level and what the new level should be (from 0 to 100).
   * @param room The room. 
   * @return The most recent lighting level command,
   * or null if there is no data or errors occurred retrieving it.
   */
  public TimestampIntegerPair getLightingLevelCommand(IHaleRoom room);
  
  /**
   * Returns a list of (timestamp, double) pairs indicating lighting level command data from 
   * the passed timestamp up to the present. 
   * @param room The room. 
   * @param timestamp The timestamp indicating when data collection should begin.
   * @return A list of (timestamp, double) pairs with lighting level command data, 
   * or null if there is no data or errors occurred retrieving it.
   */
  public List<TimestampIntegerPair> getLightingLevelCommmandSince(IHaleRoom room, Long timestamp);
  
  /**
   * Returns a (timestamp, boolean) pair with the most recent command to enable or 
   * disable the lighting.
   * @param room The room. 
   * @return The most recent enable lighting command,
   * or null if there is no data or errors occurred retrieving it.
   */
  public TimestampBooleanPair getLightingEnabledCommand(IHaleRoom room);
  
  /**
   * Returns a list of (timestamp, boolean) pairs indicating lighting enabled command data from 
   * the passed timestamp up to the present. 
   * @param room The room. 
   * @param timestamp The timestamp indicating when data collection should begin.
   * @return A list of (timestamp, boolean) pairs with lighting enabled command data, 
   * or null if there is no data or errors occurred retrieving it.
   */
  public List<TimestampBooleanPair> getLightingEnabledCommmandSince(IHaleRoom room, Long timestamp);
  
  /**
   * Returns a (timestamp, string) pair with the most recent command to set the color of 
   * lights in a room.
   * @param room The room. 
   * @return The most recent enable lighting command,
   * or null if there is no data or errors occurred retrieving it.
   */
  public TimestampStringPair getLightingColorCommand(IHaleRoom room);
  
  /**
   * Returns a list of (timestamp, string) pairs indicating lighting color command data from 
   * the passed timestamp up to the present. 
   * @param room The room. 
   * @param timestamp The timestamp indicating when data collection should begin.
   * @return A list of (timestamp, string) pairs with lighting color command data, 
   * or null if there is no data or errors occurred retrieving it.
   */
  public List<TimestampStringPair> getLightingColorCommmandSince(IHaleRoom room, Long timestamp);
  
  
  /**
   * Adds a listener to this repository whose entryAdded method will be invoked whenever an
   * entry is added to the database for the system name associated with this listener.
   * This method provides a way for the user interface (for example, Wicket) to update itself
   * whenever new data comes in to the repository. 
   * 
   * @param listener The listener whose entryAdded method will be called. 
   */
  public void addSystemStateListener(SystemStateListener listener);
  
  /**
   * Adds a listener to this repository whose messageAdded method will be invoked whenever
   * a SystemStatusMessage is generated. This provides a way for the front-end to update itself
   * whenever a new SystemStatusMessage is retrieved.  
   * @param listener The listener. 
   */
  public void addSystemStatusMessageListener(SystemStatusMessageListener listener);

  /**
   * Returns a list of all SystemStatusMessage instances recorded from the startTime to the 
   * current time, or null if none exist or an error occurred. 
   * @param startTime The start time.
   * @return The list of SystemStatusMessage instances, or null if none or error occurred. 
   */
  public List<SystemStatusMessage> getSystemStatusMessageSince(Long startTime);  
  
  /**
   * Store a SystemStatusMessage instance in the repository.
   * @param message The message to be persisted.
   */
  public void store(SystemStatusMessage message);

}
