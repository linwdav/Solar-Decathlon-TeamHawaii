package edu.hawaii.systemh.housemodel.misc;

public class TestMiscSystem {

  public static void main(String args[]) { 
    MiscSystem MS = new MiscSystem("MiscSystem");
    //double[] x = MS.getDeviceList().get(0).getEnergyArray();
    double[] x = MS.getDeviceMap().get("REFRIGERATOR").getEnergyArray();
    System.out.println(x[1]);
    
  }
}
