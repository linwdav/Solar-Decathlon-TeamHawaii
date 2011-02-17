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

  // Path to the properties file
  private static String currentDirectory = System.getProperty("user.dir");
  private static String configurationFile = "configuration.properties";
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
    // Attach the application to the component with a defined contextRoot.
    //String contextRoot = "";
    component.getDefaultHost().attach(application);
    //component.getDefaultHost().attach(contextRoot, application);
    component.start();
  }  
  
  /**
   * This main method starts up a web application that will listen on port number provided from
   * a properties file.
   * @param args Ignored.
   * @throws Exception If problems occur.
   */
  public static void main(String[] args) throws Exception {
    
    
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
      // Create a router restlet.
      //Router router = new Router(getContext());
      
      Router router = new Router();
      // Attach the resources to the router.
      router.attach("/aquaponics/{request}", ResourceTesting.class);
      router.attach("/hvac/{request}", ResourceTesting.class);
      // Return the root router
      return router;
  }
}
