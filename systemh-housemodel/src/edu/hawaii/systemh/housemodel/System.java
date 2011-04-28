package edu.hawaii.systemh.housemodel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import edu.hawaii.systemh.api.repository.TimestampDoublePair;
import edu.hawaii.systemh.housemodel.EnergyConsumptionDictionary.EnergyConsumptionDevice;

/**
 * Abstract class that models a system within the solar decathalon home. Each system has a host of
 * associated appliances and devices within the solar decathalon home.
 * 
 * @author Leonardo Nguyen, Kurt Teichman , Bret K. Ikehara
 * @version Java 1.6.0_21
 */
public abstract class System {

  /** The device name. **/
  protected String systemName;
  /** The list of devices associated with this specific system. **/
  // protected ArrayList<Device> deviceList = new ArrayList<Device>();
  /** A Map of device names to devices. **/
  protected Map<String, Device> deviceMap = new HashMap<String, Device>();

  /**
   * Protected constructor to enforce sub-classing of this class.
   * 
   * @param systemName The system name.
   */
  protected System(String systemName) {
    this.systemName = systemName;
  }

  /*
   * public ArrayList<Device> getDeviceList() { return this.deviceList; }
   */

  /**
   * Returns the mapping of device names to devices of the system.
   * 
   * @return Map of device names to devices of the system.
   */
  public Map<String, Device> getDeviceMap() {
    return this.deviceMap;
  }

  /**
   * Gets this model's current load.
   * 
   * @param deviceName EnergyConsumptionDevice
   * @return double
   */
  public abstract double getDeviceCurrentLoad(EnergyConsumptionDevice deviceName);

  /**
   * Gets this system's current load.
   * 
   * @return double
   */
  public abstract double getSystemCurrentLoad();

  /**
   * Gets the congregate of each device's current energy usage during a certain interval associated
   * with this system.
   * 
   * @param device EnergyConsumptionDevice
   * @param startTime Long
   * @param endTime Long
   * 
   * @return List<TimestampDoublePair>
   */
  public abstract List<TimestampDoublePair> getDeviceLoadDuringInterval(
      EnergyConsumptionDevice device, Long startTime, Long endTime);

  /**
   * Gets the congregate system's current energy usage during a certain interval.
   * 
   * @param startTime Long
   * @param endTime Long
   * 
   * @return List<TimestampDoublePair>
   */
  public abstract List<TimestampDoublePair> getSystemLoadDuringInterval(Long startTime,
      Long endTime);
}
