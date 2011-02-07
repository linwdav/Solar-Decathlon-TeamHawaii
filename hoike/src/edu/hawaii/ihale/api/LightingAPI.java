package edu.hawaii.ihale.api;

/**
 * Provides a specification of the operations that should be implemented by every LightingAPI.
 * 
 * @author Nathan Dorman
 * @author David Lin
 * @author Leonardo Nguyen
 */
public interface LightingAPI {

  /**
   * GET information from the lighting system sensors.
   * 
   * @return the Lighting
   */
  public Lighting getLighting();
  
}
