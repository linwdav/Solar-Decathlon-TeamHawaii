package edu.hawaii.ihale.ui;

import edu.hawaii.ihale.api.SystemStateEntry;
import edu.hawaii.ihale.api.SystemStateListener;

/**
 * A listener that the UI uses to learn when the database has changed state. 
 * @author Philip Johnson
 * @revised Shoji Bravo
 */
public class AquaponicsListener extends SystemStateListener {
    /**
     * The pHLevels.
     */
    private static double pHLevels;
    /**
     * The WaterTemperature.
     */
    private static double waterTemp;
    /**
     * the air temperature.
     */
    private static double airTemp;
    /**
     * the WaterLevels.
     */
    private static double waterLevel;
  /**
   * Provide a default constructor that indicates that this listener is for Aquaponics.
   */
  public AquaponicsListener() {
    super("Aquaponics");
  }

  /**
   * Invoked whenever a new state entry for Aquaponics is received by the system.
   * @param entry A SystemStateEntry for the Aquaponics system.
   */
  @Override
  public void entryAdded(SystemStateEntry entry) {
    System.out.println("Something just happened in Aquaponics: " + entry);
  
  }
  
  /**
   * Get pH levels from entry.
   * @return the pH levels in entry.
   */
  public static double getPH() {
      //for testing purpose
      pHLevels = 7.45;
      return pHLevels;
  }
  /**
   * Get the water temperature.
   * @return the water temperature.
   */
  public static double getWaterTemp() {
      //for testing purpose
      waterTemp = 55;
      return waterTemp;
  }
  /**
   * Get water Levels.
   * @return the water levels in entry.
   */
  public static double getWaterLevels() {
      //for testing purpose
      waterLevel = 43.78;
      return waterLevel;
  }
  /**
   * Get air temperature.
   * @return the air temerature in entry.
   */
  public static double getAirTemp() {
      //for testing purpose
      airTemp = 70;
      return airTemp;
  }
  
  /**
   * Change pH levels from entry.
   * @param ph double
   */
  public static void changePH(double ph) {
      //for testing purpose
      pHLevels = ph;
  }
  /**
   * Change the water temperature.
   * @param wt double
   */
  public static void changeWaterTemp(double wt) {
      //for testing purpose
      waterTemp = wt;
  }
  /**
   * Change air temperature.
   * @param at double
   */
  public static void changeAirTemp(double at) {
      //for testing purpose
      airTemp = at;
  }
  
}
