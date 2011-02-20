package edu.hawaii.ihale.backend;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;


/**
 * A HTTP server that provides access to iHale's home system database via a REST interface.
 * This class does two things: (1) it sets up and runs a web application (via the main() method), 
 * and (2) it defines how URLs sent to this web application get dispatched to ServerResources that
 * handle them.
 * 
 * @author Leonardo Nguyen, David Lin, Nathan Dorman
 * @version Java 1.6.0_21
 */
public class IHaleServer extends Application {

  // Path to where the Restlet server properties file.
  private static String currentDirectory = System.getProperty("user.dir");
  // Restlet server properties file name.
  private static String configurationFile = "configuration.properties";
  // Full path to the Restlet server properties file.
  @SuppressWarnings("unused")
  private static String configFilePath = currentDirectory + "\\" + configurationFile;
  
  /**
   * Starts a server running on the specified port.
   * We create a separate runServer method, rather than putting this code into the main() method,
   * so that we can run tests on a separate port.
   * @param port The port on which this server should run.
   * @throws Exception if problems occur starting up this server. 
   */
  public static void runServer(int port) throws Exception {
    // Create a component.  
    Component component = new Component();
    component.getServers().add(Protocol.HTTP, port);
    // Create an application (this class).
    Application application = new IHaleServer();
    // Attach the application to the component.
    component.getDefaultHost().attach(application);
    component.start();
  }  
  
  /**
   * This main method starts up a web application that will listen on port number provided from
   * a properties file.
   * @param args Ignored.
   * @throws Exception If problems occur.
   */
  public static void main(String[] args) throws Exception {
    
    /** TO-DO: Retrieve the port number from the properties file
     * 
     */
    
    // The port number Restlet HTTP server is listening on for incoming requests.
    int port = 8000;
    runServer(port);
  }   
  
  /**
   * Specify the dispatching restlet that maps URIs to their associated resources for processing.
   * @return A Router restlet that implements dispatching.
   */
  @Override
  public Restlet createInboundRoot() {

      /** TO-DO: Retrieve the system URIs from the properties file and store in the below
       *         strings. 
       */
    
      // Define the systems that support resource handling by the Restlet HTTP server.
      String aquaponicsSystem = "aquaponics";
      String hvacSystem = "hvac";
      String lightingSystem = "lighting";
      // Commented out to avoid ant complaints.
      //String pvSystem = "";
      //String electricalSystem = "";
      
      // Create a router restlet.
      Router router = new Router();
      // Attach the resources to the router.
      router.attach("/" + aquaponicsSystem + "/{request}", AquaponicsResource.class);
      router.attach("/" + hvacSystem + "/{request}", HvacResource.class);
      router.attach("/" + lightingSystem + "/{request}", LightingResource.class);

      /** TO-DO: Need to figure out a solution for PV and Electrical since they share the same
       *         ending URI patterns. 
       *         i.e., PV:
       *               http://egauge-1.halepilihonua.hawaii.edu/cgi-bin/egauge?tot
       *               Electrical:
       *               http://egauge-2.halepilihonua.hawaii.edu/cgi-bin/egauge?tot
       */
      
      // Return the root router
      return router;
  }
}
