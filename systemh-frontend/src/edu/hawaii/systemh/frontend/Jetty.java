package edu.hawaii.systemh.frontend;

//import java.io.FileInputStream;
//import java.util.logging.Handler;
//import java.util.logging.LogManager;
//import java.util.logging.Logger;
//import java.util.logging.FileHandler;
//import java.io.IOException;
//import java.util.logging.Level;
import org.apache.wicket.protocol.http.WicketServlet;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;

/**
 * Enables this web application to run inside the Jetty container.
 * 
 * @author Philip Johnson
 * @author Kylan Hughes
 * @author Chuan Lun Hung
 */
public class Jetty {

  //private static String simulationName;
  //private static String backendName;

  /** The port used by this webapp. */
  private static int port = 7070;
  /** The context root. */
  private static String contextPath = "";
  /** The class corresponding to this web application. */
  private static String applicationClass = "edu.hawaii.systemh.frontend.SolarDecathlonApplication";

  /**
   * Starts up Jetty and points it at Wicket. This method does not return until Jetty is shut down.
   * Jetty will check every five seconds for keyboard input from the console, and if it gets some,
   * it will shutdown.
   * 
   * @param args Ignored.
   * @throws Exception If things go wrong.
   */
  public static void main(String[] args) throws Exception {
    
//    // Log actions into a text file
//    String currentDirectory = System.getProperty("user.dir");
//    String configurationFilename = "logging.properties";
//    String configFilePath = currentDirectory + "/" + configurationFilename;
//    try {
//      FileInputStream config = new FileInputStream(configFilePath);
//      LogManager.getLogManager().readConfiguration(config);
//      String logFilename = System.getProperty("user.home") + "/.ihale/log.txt";
//      // Allow appending to the logging file.
//      Handler fh = new FileHandler(logFilename, true);
//      Logger.getLogger("").setLevel(Level.OFF);
//      Logger.getLogger("").addHandler(fh);
//    }
//    catch (IOException ioe) {
//      // CheckStyle was complaining about use of tabs when there wasn't so this long string is
//      // placed into a String variable to comply with the warning.
//      System.out.println("Error, logging properties file not found at " + configFilePath);
//      System.out.println("Log messages will be appended to the console");
//    }
    
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
