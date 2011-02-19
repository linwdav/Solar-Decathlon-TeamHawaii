package edu.hawaii.ihale.backend;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 * The aquaponics resource.
 *
 * @author Leonardo Nguyen, David Lin, Nathan Dorman
 * @version Java 1.6.0_21
 */
public class AquaponicsResource extends ServerResource {
  
  /**
   * Needs to be commented. 
   * @return Needs to be commented. 
   */
  @Get
  public String getYear() {
    return String.valueOf("This is the aquaponics resource!");
  }
}
