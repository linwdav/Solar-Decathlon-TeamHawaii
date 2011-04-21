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
  
} // End Energy Consumption Dictionary
