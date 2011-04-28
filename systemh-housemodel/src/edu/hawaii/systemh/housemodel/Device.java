package edu.hawaii.systemh.housemodel;

/**
 * A device or appliance within the SystemH home. Contains information regarding the device's
 * energy usage by the hour throughout the day. All energy measurements are in Watts/h.
 *
 * @author Leonardo Nguyen, Kurt Teichman , Bret K. Ikehara
 * @version Java 1.6.0_21
 */
public class Device {

  /** The device name. **/
  private String deviceName;
  /** The device's consumption of energy throughout the hours of the day. There are 24 entries,
   *  to represent each hour throughout the day. **/
  private static final double[] energyConsumptionByHourOfDay = new double[24];
  
  /**
   * The main constructor.
   * @param deviceName The device name.
   */
  public Device(String deviceName) {
    this.deviceName = deviceName;
  }
  
  /**
   * Returns the device name.
   *
   * @return The device name.
   */
  public String getDeviceName() {
    return this.deviceName;
  }
  
  /**
   * Adds an entry to the energyConsumptionByHourOfDay array with hour parameter as the array index 
   * and the amount of energy consumed for this device at that hour as its value.
   *
   * @param hour The hour.
   * @param value The amount of energy consumed by the device at that hour.
   */
  public void addHourEntry(int hour, double value) {
    Device.energyConsumptionByHourOfDay[hour] = value;
  }
  
  /**
   * Returns the energy consumption of the device at a hour specified.
   *
   * @param hour The hour.
   * @return The energy consumption of the device at a specific hour.
   */
  public double getEnergyConsumptionByHourOfDay(int hour) {
    return Device.energyConsumptionByHourOfDay[hour];
  }
  
  /**
   * Returns the daily energy consumption of the device.
   *
   * @return The daily energy consumption of the device.
   */
  public double getEnergyConsumptionDaily() {
    double totalLoad = 0.0;
    for (int i = 0; i < 24; i++) {
      totalLoad += Device.energyConsumptionByHourOfDay[i];
    }
    return 0.0;
  }
  
  /**
   * Returns an array containing a daily report of the energy consumption of this device.
   *
   * @return An array containing a daily recording of the energy consumption of this device.
   */
  public double[] getEnergyConsumptionArray() {
    return Device.energyConsumptionByHourOfDay.clone();
  }
}
