package edu.hawaii.ihale.api;

/**
 * Provides a specification of the operations that should be implemented by every WaterAPI.
 * 
 * @author Nathan Dorman
 * @author David Lin
 * @author Leonardo Nguyen
 */
public interface WaterAPI {

  /**
   * GET information from the Water system sensors.
   * 
   * @return the Water
   */
  public Water getWater();
  
}
