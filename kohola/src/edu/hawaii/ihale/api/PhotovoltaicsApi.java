package edu.hawaii.ihale.api;

/**
 * Provides a specification of the operations that should be implemented by iHale for the
 * photovoltaics system.
 * 
 * @author Team Kohola
 */
public interface PhotovoltaicsApi {

  /**
   * Store the passed Photovoltaics in the database. This method should be used when the
   * photovoltaics system performs a PUT to iHale.
   * 
   * @param photovoltaics The Photovoltaics to store.
   */
  public void putPhotovoltaics(Photovoltaics photovoltaics);

  /**
   * GET the energy meter reading from the photovoltaic system. 
   * 
   * @return The energy meter readings.
   */
  public Photovoltaics getPhotovoltaics();
}
