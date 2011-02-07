package edu.hawaii.ihale.api;

/**
 * Provides a specification of the operations that should be implemented by every hvacAPI.
 * 
 * @author Nathan Dorman
 * @author David Lin
 * @author Leonardo Nguyen
 */
public interface HvacAPI {

  /**
   * GET information from the HVAC system sensors.
   * 
   * @return the HVAC
   */
  public HVAC getHVAC();
  
}
