package edu.hawaii.ihale.api;

import java.util.List;
import java.util.Map;

/**
 * An Object that represents a whole subsystem, and has access to all applicable sensors. 
 * This object collects and stores all data for sensors within it's scope.  
 * @author Team Maka
 */
  public abstract class SubSystemObject {
  /** A List of Environment in this System's scope.*/
  protected Map<String,Environment> environmentList;
  /** The name of this System (e.g. "Aquaponics").*/
  protected String subSystemName;
  /** The URL Base for use by REST. */
  protected String urlRoot = "http://localhost:";
  /** The port this object will run on. */
  protected int port;
  /** The time interval in milliseconds for data polling.*/
  protected long delay;
  
  /**
   * Constructor.
   * @param subSystemName String representing the name of the SubSystem (e.g. "Aquaponics").
   * @param port The port number the REST interface will run on.
   */
  public SubSystemObject(String subSystemName,int port) {
    this.subSystemName = subSystemName;
    this.port = port;
  }
  
  public Environment getEnvrionment(String key) { 
    
  }
  
  /**
   * Returns the object's URL for use by REST.
   * @return A String representing the URL.
   */
  public String getSSOURL() {
    return urlRoot + port + "/" + subSystemName;
  }
 
  /**
   * Tells the object to poll data from the sensors and put them in the DataBase.
   */
  public abstract void run();
  
}
