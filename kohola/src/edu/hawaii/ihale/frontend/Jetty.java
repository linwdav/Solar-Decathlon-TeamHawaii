package edu.hawaii.ihale.frontend;

import org.apache.wicket.protocol.http.WicketServlet;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;

/**
 * Enables this web application to run inside the Jetty container.
 * 
 * @author Philip Johnson
 */
public class Jetty {

  //private static String simulationName;
  //private static String backendName;

  /** The port used by this webapp. */
  private static int port = 7070;
  /** The context root. */
  private static String contextPath = "";
  /** The class corresponding to this web application. */
  private static String applicationClass = "edu.hawaii.ihale.frontend.SolarDecathlonApplication";

  /**
   * Starts up Jetty and points it at Wicket. This method does not return until Jetty is shut down.
   * Jetty will check every five seconds for keyboard input from the console, and if it gets some,
   * it will shutdown.
   * 
   * @param args Ignored.
   * @throws Exception If things go wrong.
   */
  public static void main(String[] args) throws Exception {

    /**
     * To be uncomment after backend implementation to read a param. 
     */
    // if (args.length < 3) {
    // System.out.println("1st parameter: backend name, either naia or hoike\n"
    // + "2nd parameter: simulator name, ether kai or maka");
    // System.exit(1);
    // }
    //
    // if ("naia".equalsIgnoreCase(args[1]) || "hoike".equalsIgnoreCase(args[1])) {
    // backendName = args[1];
    // }
    // else {
    // System.out.println("Backend name must be either naia or hoike");
    // System.exit(1);
    // }
    //
    // if ("kai".equalsIgnoreCase(args[2]) || "maka".equalsIgnoreCase(args[2])) {
    // simulationName = args[2];
    // }
    // else {
    // System.out.println("Simulator name must be either kai or maka");
    // System.exit(1);
    // }
    
    Server server = new Server(port);
    Context context = new Context(server, "/" + contextPath, Context.SESSIONS);

    ServletHolder servletHolder = new ServletHolder(new WicketServlet());
    servletHolder.setInitParameter("applicationClassName", applicationClass);
    servletHolder.setInitOrder(1);
    context.addServlet(servletHolder, "/*");
    try {
      server.start();
      System.out.printf("%nApplication at http://localhost:%s/%s. Press return to exit.%n", port,
          contextPath);
      while (System.in.available() == 0) {
        Thread.sleep(5000);
      }
      server.stop();
      server.join();
    }
    catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  /**
   * accessor method for simulator name.
   * 
   * @return The simulator name
   */
  // public static String getSimulationName() {
  // return simulationName;
  // }

  /**
   * accessor method for backend name.
   * 
   * @return The backend name
   */
  // public static String getBackendName() {
  // return backendName;
  // }
}
