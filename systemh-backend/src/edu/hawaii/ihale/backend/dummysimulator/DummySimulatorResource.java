package edu.hawaii.ihale.backend.dummysimulator;

import org.restlet.representation.EmptyRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

/**
 * Dummy resource file which returns an EmptyRepresenentation for all requests.
 * 
 * @author Michael Cera
 */
public class DummySimulatorResource extends ServerResource {

  /**
   * Dummy resource, returns EmptyRepresentation.
   * 
   * @param representation Sent representation.
   * @return Always returns an EmptyRepresentation
   */
  @Put
  public static Representation putResource(Representation representation) {
    return new EmptyRepresentation();
  }

  /**
   * Dummy resource, returns EmptyRepresentation.
   * 
   * @param representation Sent representation.
   * @return Always returns an EmptyRepresentation
   */
  @Get
  public static Representation getResource(Representation representation) {
    return new EmptyRepresentation();
  }
}
