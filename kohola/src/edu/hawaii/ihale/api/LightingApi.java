package edu.hawaii.ihale.api;

/**
 * Provides a specification of the operations that should be implemented by iHale for the lighting
 * system.
 * 
 * @author Team Kohola
 */
public interface LightingApi {

  /**
   * Create or modify the lighting in a given room. The room is specified in the Lighting instance.
   * This method should be used when iHale perform a POST to the lighting system assuming iHale can
   * control the lighting in the house.
   * 
   * @param lighting The lighting instance.
   */
  public void postLighting(Lighting lighting);

  /**
   * GET the lighting state of a room from the subsystem. This method should be used when iHale
   * perform a GET to the lighting system to find out the lighting state of a room.
   * 
   * @param room The name of a room.
   * @return The lighting instance.
   */
  public Lighting getLighting(String room);

}
