package edu.hawaii.systemh.energymodel;

/**
 * Dictionary of devices and other terms to be used by the Energy Model.
 * 
 * @author Kylan Hughes, Kevin Leong, Emerson Tabucol
 */
public class EnergyConsumptionDictionary {

  /** 
   * Represents the devices that will be tracked/monitored
   * for energy consumption purposes.
   * 
   * @author Kylan Hughes, Kevin Leong, Emerson Tabucol
   *
   */
  public enum EnergyConsumptionDevice {

    /** Refidgerator. **/
    REFRIDGERATOR, 
    
    /** Freezer. **/
    FREEZER, 
    
    /** Dishwasher. **/
    DISHWASHER, 
    
    /** Washer and Dryer. **/
    WASHER_DRYER,

    /** Stereo system. **/
    STEREO_SYSTEM, 
    
    /** TV. **/
    TELEVISION,

    /** Ventilation Fan. **/
    VENTILATION_FAN, 
    
    /** Solar Thermal Controller. **/
    SOLAR_THERMAL_CONTROLLER,

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
  
} // End Energy Consumption Dictionary
