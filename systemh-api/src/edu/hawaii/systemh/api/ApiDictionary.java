package edu.hawaii.systemh.api;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Defines the legal names to be used in the SystemH API.
 * @author Philip Johnson
 */
public class ApiDictionary {

  /** The systems in the SystemH house. */
  public enum SystemHSystem {
    /** The Aquaponics system. */
    AQUAPONICS, 
    /** The HVAC system. */
    HVAC, 
    /** The PV system. */
    PHOTOVOLTAIC, 
    /** The electrical consumption system. */
    ELECTRIC, 
    /** The lighting system. Note that each room is represented individually. */
    LIGHTING 
    } 

  /** There is one lighting system for each of the following rooms. */
  public enum SystemHRoom { 
    /** The living room. */
    LIVING, 
    /** The dining room. */
    DINING, 
    /** The kitchen. */
    KITCHEN,
    /** The bathroom. */
    BATHROOM 

    }

  /** All state variables names. */
  public enum SystemHState {
    /** Water circulation: Double. (for aquaponics). */
    CIRCULATION (Double.class),
    /** Number of dead fish: Integer. (for aquaponics). */
    DEAD_FISH (Integer.class),
    /** Conductivity: Double. (for aquaponics). */
    ELECTRICAL_CONDUCTIVITY (Double.class), 
    /** Nutrients: Double. (for aquaponics.). */
    NUTRIENTS (Double.class), 
    /** Oxygen: Double (for aquaponics). */
    OXYGEN (Double.class), 
    /** PH: Double. (for aquaponics). */
    PH (Double.class), 
    /** Temperature: Integer (for aquaponics, hvac). */
    TEMPERATURE (Integer.class), 
    
    /** Whether hvac is on in a room: Boolean (for hvac). */
    HVAC_ENABLED (Boolean.class) {
      /** {@inheritDoc} */
      @Override
      public boolean isType(String stringValue) {
        return isBooleanType(stringValue);
      }
    },

    /** Requests for power setting: Boolean (on or off) (for hvac). */
    SET_HVAC_ENABLED_COMMAND (Boolean.class) {
      /** {@inheritDoc} */
      @Override
      public boolean isType(String stringValue) {
        return isBooleanType(stringValue);
      }
    }, 
    /** Water turbidity: Double (for aquaponics). */
    TURBIDITY (Double.class), 
    /** Water level: Integer. (for aquaponics). */
    WATER_LEVEL (Integer.class), 
    /** Requested fish feeding: Double (for aquaponics). */
    FEED_FISH_COMMAND (Double.class),
    /** Requested fish harvest: Integer. (for aquaponics). */
    HARVEST_FISH_COMMAND (Integer.class),
    /** Requests to change nutrients: Double. (for aquaponics). */
    SET_NUTRIENTS_COMMAND (Double.class),
    /** Requests to change pH: Double (for aquaponics). */
    SET_PH_COMMAND (Double.class),
    /** Requests for temperature change: Integer (for aquaponics, HVAC). */ 
    SET_TEMPERATURE_COMMAND (Integer.class),
    /** Requests to change water level: Integer (for aquaponics). */
    SET_WATER_LEVEL_COMMAND (Integer.class),
    /** Power level: Integer (for PV, Electricity). */
    POWER (Integer.class), 
    /** Energy counter: Integer (for PV, Electricity). */
    ENERGY (Integer.class), 
    /** Whether lighting is on in a room: Boolean (for lighting). */
    LIGHTING_ENABLED (Boolean.class) {
      /** {@inheritDoc} */
      @Override
      public boolean isType(String stringValue) {
        return isBooleanType(stringValue);
      }
    },
    /** Current intensity of lights in a room: Integer (for lighting). */
    LIGHTING_LEVEL (Integer.class),
    /** Current color of lights in a room: String (for lighting). */
    LIGHTING_COLOR (String.class) {
      /** {@inheritDoc}  */
      @Override
      public boolean isType(String stringValue) {
        return isColorType(stringValue);
      }
    },
    /** Requests for power setting: Boolean (on or off) (for lighting). */
    SET_LIGHTING_ENABLED_COMMAND (Boolean.class) {
      /** {@inheritDoc} */
      @Override
      public boolean isType(String stringValue) {
        return isBooleanType(stringValue);
      }
    }, 
    /** Requests for lighting level: Integer (for Lighting). */
    SET_LIGHTING_LEVEL_COMMAND (Integer.class), 
    /** Requests for lighting color: String (for lighting). */
    SET_LIGHTING_COLOR_COMMAND (String.class) {
      /** {@inheritDoc}  */
      @Override
      public boolean isType(String stringValue) {
        return isColorType(stringValue);
      }
    };
    
    /** Stores the type of this state variable. */
    private final Class<?> type;
    
    /**
     * Create a new instance of this enumerated type.
     * @param type The type of this enumerated type.
     */
    private SystemHState(Class<?> type) {
      this.type = type;
    }
    
    /**
     * Returns the type associated with this state variable.
     * @return The type.
     */
    public Class<?> getType() {
      return this.type;
    }

    /**
     * Default implementation of isType calls the valueOf method of the class associated
     * with this enumerated type value. If it doesn't throw an exception, then we're good.
     * @param stringValue The value whose type we are trying to assess.
     * @return True if the string could be of the enumerated type's value.
     */
    public boolean isType(String stringValue) {
      try {
        Method method = this.type.getMethod("valueOf", String.class);
        method.invoke(null, stringValue);
        return true;
      } 
      catch (RuntimeException e) {
        return false;
      }
      catch (Exception e) {
        return false;
      } 
    }
    
    /**
     * Returns true if stringValue is 'true' or 'false', case-insensitive.
     * @param stringValue The string value.
     * @return True if true, false if false.
     */
    private static boolean isBooleanType(String stringValue) {
      String lowerCased = stringValue.toLowerCase(Locale.US);
      return (("true".equals(lowerCased) || "false".equals(lowerCased)));
    }
    
    /**
     * Returns true if stringValue is 7 characters and begins with '#".
     * @param stringValue The string value.
     * @return True if a lighting color.
     */
    private static boolean isColorType(String stringValue) {
      return ((stringValue.length() == 7) && stringValue.startsWith("#"));
    }
  }

  
  /** All command names. */
  public enum SystemHCommandType { 
    /** For aquaponics. */
    FEED_FISH(SystemHSystem.AQUAPONICS,"/aquaponics/fish/feed"),
    /** For aquaponics. */
    HARVEST_FISH(SystemHSystem.AQUAPONICS,"/aquaponics/fish/harvest"),
    /** For aquaponics. */
    SET_PH(SystemHSystem.AQUAPONICS,"/aquaponics/ph"),
    /** For aquaponics. */
    SET_WATER_LEVEL(SystemHSystem.AQUAPONICS,"/aquaponics/water/level"),
    /** For aquaponics, hvac. */
    SET_TEMPERATURE(SystemHSystem.HVAC,"hvac/temperature",
                    SystemHSystem.AQUAPONICS,"aquaponics/temperature"),
    /** For Hvac. */
    SET_HVAC_ENABLED(SystemHSystem.HVAC,"/hvac/enabled"),
    /** For aquaponics. */
    SET_NUTRIENTS(SystemHSystem.AQUAPONICS,"/aquaponics/nutrients"),
    /** For lighting. */
    SET_LIGHTING_LEVEL(SystemHSystem.LIGHTING,"/lighting/level"),
    /** For lighting. */
    SET_LIGHTING_COLOR(SystemHSystem.LIGHTING,"/lighting/color"),
    /** For lighting. */ 
    SET_LIGHTING_ENABLED(SystemHSystem.LIGHTING,"/lighting/enabled");
    
    //map of SystemHSystems to uri ending for each variable.
    //This is mostly for the SET_TEMPERATURE enum which is
    //in the scope of both HVAC and AQUAPONICS.
    private Map<SystemHSystem,String> uriMap;
    
    /**
     * Creates a new SystemHCommandType given the SystemHSystem 
     * to which the command belongs, and a String representing the URI ending.
     * Multiple entries can be added at a time, but the SystemHSystem must
     * immediately preceed the URI String.
     * @param var A variable list of Objects, following the pattern
     *         {SystemHSystem, String} zero or more times.
     */
    SystemHCommandType(Object ...var) {
      uriMap = new HashMap<SystemHSystem,String>();
       for (int i = 0; i < var.length; i += 2) {  
         uriMap.put((SystemHSystem)var[i],(String) var[i + 1]); 
         }
       } 
    
    /**
     * Returns the URI ending for a SystemHCommandType given the SystemHSystem.
     * For example, SystemHCommandType.SET_TEMPERATURE.getUri(SystemHSystem.HVAC))
     * will return hvac/temperature.
     * @param system The SystemHSystem to which the CommandType is associated.
     * @return A String representation of the URI Ending for that particular 
     * SystemHSystem-SystemHCommand pairing.
     */
    public String getUri(SystemHSystem system) {
      return uriMap.get(system);
    } 
  }
  
  /** Indicates the possible types of SystemStatusMessages.  */
  public enum SystemStatusMessageType { 
    /** Debugging status messages. Not displayed in production versions of system. */
    DEBUG,
    /** Informational messages. */
    INFO,
    /** Warning messages, indicating things might go wrong soon. */
    WARNING,
    /** Alert message, indicating things have gone wrong, action should be taken. */
    ALERT
  }
  
  /**
   * Accepts an SystemHCommand type, and returns the equivalent SystemHState type.
   * Useful for the implementation of the SystemHCommand class. 
   * @param command The SystemHCommand type.
   * @return The corresponding SystemHState type. 
   */
  public static SystemHState systemHCommandType2State(SystemHCommandType command) {
    return SystemHState.valueOf(command.toString() + "_COMMAND");
  }
}
