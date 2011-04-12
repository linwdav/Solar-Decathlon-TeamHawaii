package edu.hawaii.ihale.backend.restserver.resource;

import org.junit.BeforeClass;
import org.junit.AfterClass;
import edu.hawaii.ihale.api.repository.impl.Repository;
import edu.hawaii.ihale.backend.restserver.RestServer;

/**
 * References the IHaleBackend objects.
 * 
 * @author Bret K. Ikehara
 */
public class SystemDataTest {

  protected static Repository repository;

  /**
   * Stores reference before tests start. This is important so that the REST server runs.
   * @throws Exception 
   */
  @BeforeClass
  public static void beforeClass() throws Exception {
    
    RestServer.runServer(8111);
    repository = new Repository();
  } 
  
  /**
   * Closes the RestServer.
   * @throws Exception 
   */
  @AfterClass
  public static void afterClass() throws Exception {
    RestServer.stopServer();
  }
}
