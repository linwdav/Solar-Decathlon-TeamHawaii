package edu.hawaii.ihale.api;

/**
 * Provides a specification of the operations that should be implemented by every HvacAPI.
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
  
  /**
   * POST information from the HVAC system sensors into the database.
   * 
   * @param hvac The hvac system information.
   */
  public void putHVAC(HVAC hvac);
  
  /**
   * DELETE a HVAC record with timestamp value.
   *
   * @param timestamp The timestamp of the HVAC record to be removed.
   */
  public void deleteHVAC(long timestamp);
  
}
