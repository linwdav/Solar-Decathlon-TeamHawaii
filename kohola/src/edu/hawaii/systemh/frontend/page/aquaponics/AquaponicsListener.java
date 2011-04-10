package edu.hawaii.systemh.frontend.page.aquaponics;

import edu.hawaii.ihale.api.ApiDictionary.IHaleRoom;
import edu.hawaii.ihale.api.ApiDictionary.IHaleState;
import edu.hawaii.ihale.api.ApiDictionary.IHaleSystem;
import edu.hawaii.ihale.api.repository.SystemStateListener;

/**
 * A listener for aquaponics that the UI uses to learn when the database has changed state.
 * 
 * @author Philip Johnson
 * @author Kylan Hughes
 * @author Chuan Lun Hung
 */
public class AquaponicsListener extends SystemStateListener {

  // private static final String SYSTEM_NAME = "aquaponics";
  // private static final String TEMPERATURE_KEY = "temp";;
  // private static final String PH_KEY = "ph";
  // private static final String OXYGEN_KEY = "oxygen";

  private Integer temp = -1;
  private Double pH = -1.0;
  private Double oxygen = -1.0;
  private Double circulation = -1.0;
  private Integer deadFish = -1;
  private Double conductivity = -1.0;
  private Double nutrients = -1.0;
  private Double turbidity = -1.0;
  private Integer waterLevel = -1;
  private Double feedFishAmount = -1.0;
  private Integer fishToHarvest = -1;

  /**
   * Provide a default constructor that indicates that this listener is for Aquaponics.
   */
  public AquaponicsListener() {
    super(IHaleSystem.AQUAPONICS);
  }

  /**
   * Runs when the Aquaponics state changes. 
   * @param state One of the aquaponics state values. 
   * @param room Always null for the Aquaponics system.
   * @param timestamp The time when this state change occurred.
   * @param value The value associated with this state change. 
   */
  @Override
  public void entryAdded(IHaleState state, IHaleRoom room, Long timestamp, Object value) {
    
    switch (state) {
    case CIRCULATION : 
      circulation = (Double)value;
      break;
    case DEAD_FISH : 
      deadFish = (Integer)value;
      break;
      
    case FEED_FISH_COMMAND :
      feedFishAmount = (Double)value;
      break;
      
    case ELECTRICAL_CONDUCTIVITY: 
      conductivity = (Double)value;
      break;
    
    case HARVEST_FISH_COMMAND:
      fishToHarvest = (Integer)value;
      break;
      
    case NUTRIENTS: 
      nutrients = (Double)value;
      break;
    
    case SET_NUTRIENTS_COMMAND: 
      nutrients = (Double)value;
      break;
      
    case OXYGEN: 
      oxygen = (Double)value;
      break;
    
    case PH: 
      pH = (Double)value;
      break;
    
    case SET_PH_COMMAND: 
      pH = (Double)value;
      break;
      
    case TEMPERATURE: 
      temp = (Integer)value;
      break;
    
    case SET_TEMPERATURE_COMMAND:
      temp = (Integer)value;
      break;
      
    case TURBIDITY: 
      turbidity = (Double)value;
      break;
      
    case WATER_LEVEL: 
      waterLevel = (Integer)value;
      break;
    
    case SET_WATER_LEVEL_COMMAND:
      waterLevel = (Integer)value;
      break;
    
    default: 
      System.out.println("Unhandled aquaponics state: " + state);
    
    }

  }

  /**
   * Return the temperature.
   * 
   * @return The temperature Fahrenheit.
   */
  public Integer getTemp() {
    return temp;
  }

  /**
   * Return the ph value.
   * 
   * @return The ph value.
   */
  public Double getPH() {
    return pH;
  }

  /**
   * Return the oxygen.
   * 
   * @return The oxygen
   */
  public Double getOxygen() {
    return oxygen;
  }
  
  /**
   * Return the circulation.
   * 
   * @return The circulation
   */
  public Double getCirculation() {
    return circulation;
  }
  
  /**
   * Return the number of dead fish.
   * 
   * @return The number of dead fish
   */
  public Integer getDeadFish() {
    return deadFish;
  }
  
  /**
   * Return the conductivity.
   * 
   * @return The conductivity
   */
  public Double getConductivity() {
    return conductivity;
  }
  
  /**
   * Return the nutrients.
   * 
   * @return The nutrients
   */
  public Double getNutrients() {
    return nutrients;
  }
  
  /**
   * Return the turbidity.
   * 
   * @return The turbidity
   */
  public Double getTurbidity() {
    return turbidity;
  }  
  
  /**
   * Return the water level.
   * 
   * @return The water level
   */
  public Integer getWaterLevel() {
    return waterLevel;
  }
  
  /**
   * Return the amount fish are fed.
   * @return The amount in grams that fish are fed.
   */
  public Double getFeedFishAmount() {
    return feedFishAmount;
  }
  
  /**
   * Return the amount of fish to harvest.
   * @return The amount of fish to harvest.
   */
  public Integer getFishToHarvest() {
    return fishToHarvest;
  }
  
}
