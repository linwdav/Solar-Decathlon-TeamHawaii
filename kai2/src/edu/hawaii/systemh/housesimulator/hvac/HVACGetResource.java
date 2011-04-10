package edu.hawaii.systemh.housesimulator.hvac;

import java.util.Date;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 * A server resource that will handle requests regarding the HVAC system. 
 * Supported operations: GET.
 * Supported representations: XML.
 * 
 * @author Anthony Kinsey, Michael Cera
 * @author Christopher Ramelb, David Lin, Leonardo Nguyen, Nathan Dorman
 */
public class HVACGetResource extends ServerResource {
  /**
   * Returns the data requested by the URL.
   * 
   * @return The XML representation of the data.
   * @throws Exception If problems occur making the representation.
   */
  @Get
  public Representation getState() throws Exception {
    // Return the representation.
    HVACData.setCurrentTime(new Date().getTime());
    
    return HVACData.toXml();
  }
}
