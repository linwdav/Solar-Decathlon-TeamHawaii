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
   * 
   * @param lighting The lighting instance.
   */
  public void postLighting(Lighting lighting);

  /**
   * Get the lighting state of a room from the subsystem.
   * 
   * @param room The name of a room.
   * @return The lighting instance.
   */
  public Lighting getLighting(String room);

}
