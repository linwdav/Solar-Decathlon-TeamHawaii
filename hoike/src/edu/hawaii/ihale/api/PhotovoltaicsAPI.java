package edu.hawaii.ihale.api;

/**
 * Provides a specification of the operations that should be implemented by every PhotovoltaicsAPI.
 * 
 * @author Nathan Dorman
 * @author David Lin
 * @author Leonardo Nguyen
 */
public interface PhotovoltaicsAPI {

  /**
   * GET information from the photovoltaics system sensors.
   * 
   * @return the Photovoltaics
   */
  public Photovoltaics getPhotovoltaics();
  
}
