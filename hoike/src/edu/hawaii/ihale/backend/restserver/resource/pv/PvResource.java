package edu.hawaii.ihale.backend.restserver.resource.pv;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 * The PV resource.
 *
 * @author Leonardo Nguyen, David Lin, Nathan Dorman
 * @version Java 1.6.0_21
 */
public class PvResource extends ServerResource {
  /**
   * Needs to be commented. 
   * @return Needs to be commented. 
   */
  @Get
  public String getYear() {
    return String.valueOf("This is the electrical resource!");
  }
}
