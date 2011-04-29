package edu.hawaii.systemh.housemodel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.time.DateUtils;
import edu.hawaii.systemh.api.repository.TimestampDoublePair;
import edu.hawaii.systemh.housemodel.EnergyConsumptionDictionary.EnergyConsumptionDevice;

/**
 * Abstract class that models a system within the SystemH solar decathalon home. Each system has 
 * a host of associated appliances and devices within the solar decathalon home.
 * 
 * @author Leonardo Nguyen, Kurt Teichman , Bret K. Ikehara
 * @version Java 1.6.0_21
 */
public abstract class HouseSystem {

  /** The system name. **/
  protected String systemName;
  /** A Map of device names to devices. **/
  protected Map<String, Device> deviceMap = new HashMap<String, Device>();

  static final long oneHourInMillis = 1000L * 60L * 60L;

  /**
   * Protected constructor to enforce sub-classing of this class.
   * 
   * @param systemName The system name.
   */
  protected HouseSystem(String systemName) {
    this.systemName = systemName;
    initDeviceValues();
  }

  /**
   * Returns the mapping of device names to devices of the system.
   * 
   * @return Map of device names to devices of the system.
   */
  public Map<String, Device> getDeviceMap() {
    return this.deviceMap;
  }

  /**
   * Initialize the devices of the HouseSystem.
   */
  protected abstract void initDeviceValues();
  
  /**
   * Retrieves the device's current energy usage load.
   * 
   * @param deviceName The device of this system.
   * @return The device's current energy usage load.
   */
  public double getDeviceCurrentLoad(EnergyConsumptionDevice deviceName) {
    
    double loadValue = 0.0;
    // Device name.
    String dn = deviceName.toString();
    if (deviceMap.containsKey(dn)) {
      Device device = deviceMap.get(dn);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(new Date());
      int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
      loadValue = device.getEnergyConsumptionByHourOfDay(currentHour);
    }
    else {
      throw new RuntimeException("The device requested does not belong to the MiscSystem.");
    }
    return loadValue;
  }

  /**
   * Retrieves the system's current energy usage load.
   * 
   * @return The system's current energy usage load.
   */
  public double getSystemCurrentLoad() {

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
    double totalLoadValue = 0.0;
    
    // Map.Entry to retrieve the map's keys, which represents the device names.
    Set<Map.Entry<String, Device>> set = getDeviceMap().entrySet();
    
    // Iterate through the map and retrieve corresponding device's current energy load and total it
    // for the full system load.
    for (Map.Entry<String, Device> mapEntry : set) {
      String keyName = (mapEntry.getKey());
      totalLoadValue += deviceMap.get(keyName).getEnergyConsumptionByHourOfDay(currentHour);
    }
    return totalLoadValue;
  }

  /**
   * Retrieves the congregate of the device's energy usage during a certain interval  associated 
   * with this system.
   * 
   * @param device The device energy usage interested. 
   * @param startTime The start time in ms since "the epoch".
   * @param endTime The end time in ms since "the epoch".
   * 
   * @return List of entries of one hour intervals since the startTime of the device's energy usage
   *         load.
   */
  public List<TimestampDoublePair> getDeviceLoadDuringInterval(EnergyConsumptionDevice device,
      Long startTime, Long endTime) {

    // Device name.
    String dn = device.toString();
    List<TimestampDoublePair> deviceLoadList = new ArrayList<TimestampDoublePair>();

    if (deviceMap.containsKey(dn)) {
      
      // The desired device to retrieve energy usage values from.
      Device d = deviceMap.get(dn);
      
      Calendar startCal = Calendar.getInstance();
      // Floor to the nearest hour.
      startCal.setTime(DateUtils.truncate(new Date(startTime), Calendar.HOUR_OF_DAY));
      
      Calendar endCal = Calendar.getInstance();
      // Floor to the nearest hour.
      endCal.setTime(DateUtils.truncate(new Date(endTime), Calendar.HOUR_OF_DAY));
      
      Long lengthOfInterval = endTime - startTime;
      int intervalInNumOfHours = (int) (lengthOfInterval / oneHourInMillis);
      int currentHour = startCal.get(Calendar.HOUR_OF_DAY);
      int hoursProcessed = 0;
      long currentTime = startTime;
      
      for (int i = currentHour; hoursProcessed < intervalInNumOfHours; i++) {

        // Add the device's energy load at the 'currentTime' to the list.
        deviceLoadList.add(new TimestampDoublePair
            (currentTime, d.getEnergyConsumptionByHourOfDay(i)));
        // Increment the time-stamp by one hour.
        currentTime += oneHourInMillis;
        // The energy consumption array supports only 24 entries, representative of each hour
        // throughout the day. If the interval to retrieve past data exceeds more than a day, 
        // than i (the hour), needs to be reset after exceeding 23 (hour range from 0-23).
        // i reset to -1, will be incremented to 0 from the for-loop-increment process.
        if (i == 23) {
          i = -1;
        }
        hoursProcessed++;
      }
    }
    else {
      throw new RuntimeException("The device requested does not belong to the MiscSystem.");
    }
    return deviceLoadList;
  }
  
  /**
   * Retrieves the congregate system's energy usage during a certain interval.
   * 
   * @param startTime The start time in ms since "the epoch".
   * @param endTime The end time in ms since "the epoch".
   * 
   * @return List of entries one hour intervals since the startTime of the the system's energy 
   *         usage load.
   */
  public List<TimestampDoublePair> getSystemLoadDuringInterval(Long startTime, Long endTime) {

    // Map.Entry to retrieve the map's keys, which represents the device names.
    Set<Map.Entry<String, Device>> set = getDeviceMap().entrySet();
    
    List<List<TimestampDoublePair>> allDeviceLoadList = new ArrayList<List<TimestampDoublePair>>();
    // Iterate through the map and retrieve corresponding device's energy usage for each hour 
    // within the specified time-range.
    for (Map.Entry<String, Device> mapEntry : set) {
      String keyName = (mapEntry.getKey());
      List<TimestampDoublePair> tempList = 
        getDeviceLoadDuringInterval(EnergyConsumptionDevice.valueOf(keyName), startTime, endTime);
      allDeviceLoadList.add(tempList);
    }
    
    double totalLoadValue = 0.0;
    
    Calendar startCal = Calendar.getInstance();
    // Floor to the nearest hour.
    startCal.setTime(DateUtils.truncate(new Date(startTime), Calendar.HOUR_OF_DAY));
    
    Calendar endCal = Calendar.getInstance();
    // Floor to the nearest hour.
    endCal.setTime(DateUtils.truncate(new Date(endTime), Calendar.HOUR_OF_DAY));
    
    Long lengthOfInterval = endTime - startTime;
    int intervalInNumOfHours = (int) (lengthOfInterval / oneHourInMillis);
    long currentTime = startTime;
    List<TimestampDoublePair> systemLoadList = new ArrayList<TimestampDoublePair>();
    
    // For each hour within the specified time, total all the devices associated with this system's 
    // energy load and add the entry to the list being returned.
    for (int i = 0; i < intervalInNumOfHours; i++) {
      for (List<TimestampDoublePair> listItem : allDeviceLoadList) {
        totalLoadValue += listItem.get(i).getValue();
      }
      systemLoadList.add(new TimestampDoublePair(currentTime, totalLoadValue));
      totalLoadValue = 0.0;
      currentTime += oneHourInMillis;
    }
    return systemLoadList;
  }
}
