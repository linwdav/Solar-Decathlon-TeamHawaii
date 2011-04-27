package edu.hawaii.systemh.housemodel.misc;

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
    double[] x = MS.getDeviceMap().get("REFRIGERATOR").getEnergyArray();
    System.out.println(x[1]);
    System.out.println(x[22]);
    MS.getDeviceMap().get("REFRIGERATOR").addHourEntry(22, 10000);
    x = MS.getDeviceMap().get("REFRIGERATOR").getEnergyArray();
    System.out.println(x[1]);
    System.out.println(x[22]);
  }
}
