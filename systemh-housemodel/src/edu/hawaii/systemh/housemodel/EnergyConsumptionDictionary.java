package edu.hawaii.systemh.housemodel;

/**
 * Dictionary of devices and other terms to be used by the Energy Model.
 * 
 * @author Kylan Hughes, Kevin Leong, Emerson Tabucol
 */
public class EnergyConsumptionDictionary {

  /**
   * Represents the systems that will be tracked/monitored for energy consumption purposes.
   *    *
   * @author Leonardo Nguyen
   * @version Java 1.6.0_21
   */
  public enum EnergyConsumptionSystem {
    
    /** Aquaponics. **/ 
    AQUAPONICS,
    
    /** HVAC. **/
    HVAC,
    
    /** Lighting. **/
    LIGHTING,
    
    /** Contains all the devices not defined to an actual system within the SystemH home. **/
    MISC
  }
  
  /** 
   * Represents the devices that will be tracked/monitored for energy consumption purposes.
   * 
   * @author Kylan Hughes, Kevin Leong, Emerson Tabucol
   *
   */
  public enum EnergyConsumptionDevice {

    /** Refrigerator. **/
    REFRIGERATOR, 
    
    /** Freezer. **/
    FREEZER, 
    
    /** Home Electronic System. **/
    HOME_ELECTRONIC,
    
    /** Dishwasher. **/
    DISHWASHER, 
    
    /** Washer. **/
    CLOTHES_WASHER,
    
    /** Dryer. **/
    CLOTHES_DRYER,

    /** The heating and cooling system. **/
    HEATING_COOLING,
    
    /** Cooking appliance. **/
    COOKING,
    
    /** Humidifier. **/
    HUMIDIFIER,
    
    /** The lighting in the home. **/
    LIGHTING,
    
    /** Hot water usage. **/
    HOT_WATER,

    /** Handles miscellaneous other devices that
     *  are not individually tracked.
     */
    OTHER
  } // End Enumerated Type
  
  /** 
   * Represents the different types of chart displays.
   * E.g., Current load, Consumption, etc.
   * 
   * @author Kylan Hughes, Kevin Leong, Emerson Tabucol
   *
   */
  public enum ChartDisplayType {
    
    /** The power generated vs consumed over a week. **/
    CONSUMPTION_WEEK,
    
    /** The power generated vs consumed over a day. **/
    CONSUMPTION_DAY,
    
    /** The power generated vs consumed over a month. **/
    CONSUMPTION_MONTH,
    
    /** The breakout of the current electrical load (by Device). **/
    DEVICES_CURRENT_LOAD,
    
    /** The breakout of the current electrical load (by System). **/
    SYSTEM_CURRENT_LOAD,
    
    /** The breakout of the electrical load over a week (by Device). **/
    DEVICES_LOAD_WEEK,
    
    /** The breakout of the electrical load over a day (by Device). **/
    DEVICES_LOAD_DAY,
    
    /** The breakout of the electrical load over a month (by Device). **/
    DEVICES_LOAD_MONTH,
    
    /** The breakout of the electrical load over a week (by System). **/
    SYSTEM_LOAD_WEEK,
    
    /** The breakout of the electrical load over a day (by System). **/
    SYSTEM_LOAD_DAY,
    
    /** The breakout of the electrical load over a month (by System). **/
    SYSTEM_LOAD_MONTH
    
  } // End CharDisplayType 
}