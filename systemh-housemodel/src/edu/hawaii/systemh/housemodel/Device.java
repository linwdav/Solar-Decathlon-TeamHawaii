package edu.hawaii.systemh.housemodel;

/**
 * . . .
 *
 * @author Leonardo Nguyen, Kurt Teichman , Bret K. Ikehara
 * @version Java 1.6.0_21
 */
public class Device {

  /** The device name. **/
  private String deviceName;
  /** The device's consumption of energy throughout the hours of the day. **/
  private static final double[] energyConsumptionByHourOfDay = new double[24];
  
  /**
   * The main constructor.
   * @param deviceName The device name.
   */
  public Device(String deviceName) {
    this.deviceName = deviceName;
  }
  
  public String getDeviceName() {
    return this.deviceName;
  }
  
  public void addHourEntry(int hour, double value) {
    Device.energyConsumptionByHourOfDay[hour] = value;
  }
  
  public double[] getEnergyArray() {
    return Device.energyConsumptionByHourOfDay;
  }
}
