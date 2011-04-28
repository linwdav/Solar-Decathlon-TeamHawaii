package edu.hawaii.systemh.housemodel.misc;

import java.util.Map;
import java.util.Set;
import edu.hawaii.systemh.housemodel.Device;

/**
 * . . . 
 *
 * @author Leonardo Nguyen
 * @version Java 1.6.0_21
 */
public class MiscSystemTesting {

  /**
   * . . . 
   *
   * @param args . . .
   */
  public static void main(String args[]) { 
    MiscSystem MS = new MiscSystem();
    //double[] x = MS.getDeviceList().get(0).getEnergyArray();
    double[] x = MS.getDeviceMap().get("REFRIGERATOR").getEnergyConsumptionArray();
    System.out.println(x[1]);
    System.out.println(x[22]);
    MS.getDeviceMap().get("REFRIGERATOR").addHourEntry(22, 10000);
    x = MS.getDeviceMap().get("REFRIGERATOR").getEnergyConsumptionArray();
    System.out.println(x[1]);
    System.out.println(x[22]);
    
    System.out.println("MiscSystem has " + MS.getDeviceMap().size() + " devices.");
    for (int i = 0; i < MS.getDeviceMap().size(); i++) {
      //System.out.println(MS.getDeviceMap().get("REFRIGERATOR").getDeviceName());
    }
    
    Set<Map.Entry<String, Device>> set = MS.getDeviceMap().entrySet();
  
    for (Map.Entry<String, Device> mapEntry : set) {
      System.out.println(mapEntry.getKey());
    }
  }
}
