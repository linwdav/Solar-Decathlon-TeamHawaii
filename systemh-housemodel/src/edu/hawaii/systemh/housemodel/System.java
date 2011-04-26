package edu.hawaii.systemh.housemodel;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstract class that models a system within the solar decathalon home. Each system has a host
 * of associated appliances and devices within the solar decathalon home.
 *
 * @author Leonardo Nguyen, Kurt Teichman , Bret K. Ikehara
 * @version Java 1.6.0_21
 */
public class System {

  /** The device name. **/
  protected String systemName;
  /** The list of devices associated with this specific system. **/
  //protected ArrayList<Device> deviceList = new ArrayList<Device>();
  /** A Map of device names to devices. **/
  protected Map<String, Device> deviceMap = new HashMap<String, Device>();
  
  /**
   * Protected constructor to enforce sub-classing of this class. 
   * @param systemName The system name.
   */
  protected System(String systemName) {
    this.systemName = systemName;
  }
  
  /*
  public ArrayList<Device> getDeviceList() {
    return this.deviceList;
  }
  */
  
  /**
   * Returns the mapping of device names to devices of the system.
   * 
   * @return Map of device names to devices of the system.
   */
  public Map<String, Device> getDeviceMap() {
    return this.deviceMap;
  }
}
