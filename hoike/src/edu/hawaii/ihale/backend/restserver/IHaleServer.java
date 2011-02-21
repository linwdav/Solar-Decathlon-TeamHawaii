package edu.hawaii.ihale.backend.restserver;

import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;
import edu.hawaii.ihale.backend.restserver.resource.aquaponics.AquaponicsResource;
import edu.hawaii.ihale.backend.restserver.resource.hvac.HvacResource;
import edu.hawaii.ihale.backend.restserver.resource.lighting.LightingResource;

/**
 * A HTTP server that provides access to iHale's home system database via a REST interface. This
 * class does two things: (1) it sets up and runs a web application (via the main() method), and (2)
 * it defines how URLs sent to this web application get dispatched to ServerResources that handle
 * them.
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
   * Starts a server running on the specified port. We create a separate runServer method, rather
   * than putting this code into the main() method, so that we can run tests on a separate port.
   * 
   * @param port The port on which this server should run.
   * @throws Exception if problems occur starting up this server.
   */
  public static void runServer(int port) throws Exception {
    // Create a new Restlet component and add a HTTP server connector to it.
    Component component = new Component();
    String ipAddress = "127.0.0.1";
    component.getServers().add(Protocol.HTTP, ipAddress, port);
    // Create an application (this class).
    Application application = new IHaleServer();
    String contextRoot = "";
    // Attach the application to the component.
    component.getDefaultHost().attach(contextRoot, application);

    /*
     * VirtualHost host = new VirtualHost(component.getContext()); host.setHostDomain("localhost|" +
     * ipAddress); host.setHostPort(Integer.toString(port)); host.attach(contextRoot, application);
     * component.getHosts().add(host);
     * 
     * host = new VirtualHost(component.getContext());
     * host.setHostDomain("myName|yourName|hisName|herName");
     * host.setHostPort(Integer.toString(port)); String target = "localhost"; Redirector redirector
     * = new Redirector(component.getContext(), target, Redirector.MODE_CLIENT_PERMANENT);
     * host.attach(redirector); host.attach(contextRoot, application);
     * component.getHosts().add(host);
     */

    component.start();
  }

  /**
   * This main method starts up a web application that will listen on port number provided from a
   * properties file.
   * 
   * @param args Ignored.
   * @throws Exception If problems occur.
   */
  public static void main(String[] args) throws Exception {

    /**
     * TO-DO: Retrieve the port number from the properties file
     * 
     */

    // The port number Restlet HTTP server is listening on for incoming requests.
    int port = 8000;
    runServer(port);
  }

  /**
   * Specify the dispatching restlet that maps URIs to their associated resources for processing.
   * 
   * @return A Router restlet that implements dispatching.
   */
  @Override
  public Restlet createInboundRoot() {

    /**
     * TO-DO: Retrieve the system URIs from the properties file and store in the below strings.
     */

    // --- start ---
    
    try {
      InputStream is = IHaleServer.class.getResourceAsStream(configFilePath);
      Properties prop = new Properties();
      prop.load(is);

      Map<String, String> uris = new HashMap<String, String>();
      Collection<Object> values = prop.values();
      // for (Object object : values) {
      // String key = object.toString();
      //
      // }
      int switcher = 0;
      String key = "";
      String value = "";
      for (@SuppressWarnings("rawtypes")
      Iterator iterate = values.iterator(); iterate.hasNext();) {
        if (switcher == 0) {
          key = (String) iterate.next();
          switcher = 1;
        }
        if (switcher == 1) {
          value = (String) iterate.next();
          uris.put(key, value);
          switcher = 0;
        }
      }

      is.close();
    }
    catch (Exception e) {
      System.out.println("failed to read properties file");
    }
    
    // --- end ---
    
    
    // Define the systems that support resource handling by the Restlet HTTP server.
    String aquaponicsSystem = "aquaponics";
    String hvacSystem = "hvac";
    String lightingSystem = "lighting";
    @SuppressWarnings("unused")
    String pvSystem = "";
    @SuppressWarnings("unused")
    String electricalSystem = "";

    // Create a router restlet.
    Router router = new Router(getContext());
    // Attach the resources to the router.
    router.attach("/" + aquaponicsSystem + "/{request}", AquaponicsResource.class);
    router.attach("/" + hvacSystem + "/{request}", HvacResource.class);
    router.attach("/" + lightingSystem + "/{request}", LightingResource.class);
    /**
     * TO-DO: Need to figure out a solution for PV and Electrical since they share the same ending
     * URI patterns. i.e., PV: http://egauge-1.halepilihonua.hawaii.edu/cgi-bin/egauge?tot
     * Electrical: http://egauge-2.halepilihonua.hawaii.edu/cgi-bin/egauge?tot
     */

    // Return the root router
    return router;
  }
}
