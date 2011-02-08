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
  
  /**
   * PUT information from the photovoltaics system sensors into a database.
   * 
   * @param photovoltaics The photovoltaics information to be inserted.
   */
  public void putPhotovoltaics(Photovoltaics photovoltaics);
  
  /**
   * DELETE a Photovoltaic record with timestamp value.
   *
   * @param timestamp The timestamp of the Photovoltaic record to be removed.
   */
  public void deletePhotovoltaics(long timestamp);
}
