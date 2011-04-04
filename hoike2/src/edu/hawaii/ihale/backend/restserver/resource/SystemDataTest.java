package edu.hawaii.ihale.backend.restserver.resource;

import org.junit.BeforeClass;
import edu.hawaii.ihale.api.repository.impl.Repository;
import edu.hawaii.ihale.backend.IHaleBackend;
import edu.hawaii.ihale.backend.restserver.RestServer;

/**
 * References the IHaleBackend objects.
 * 
 * @author Bret K. Ikehara
 */
public class SystemDataTest {

  protected static RestServer server;
  protected static IHaleBackend backend;
  protected static Repository repository;

  /**
   * Stores reference before tests start. This is important so that the REST server runs.
   */
  @BeforeClass
  public static void beforeClass() {
    
    server = IHaleBackend.getServer();
    
    repository = IHaleBackend.getRepository();
    
    backend = IHaleBackend.getInstance();
  }
}
