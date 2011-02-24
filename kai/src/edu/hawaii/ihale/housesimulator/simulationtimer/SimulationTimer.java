package edu.hawaii.ihale.housesimulator.simulationtimer;

import java.util.Timer;
import java.util.TimerTask;
import edu.hawaii.ihale.housesimulator.aquaponics.AquaponicsData;
import edu.hawaii.ihale.housesimulator.electrical.ElectricalData;
import edu.hawaii.ihale.housesimulator.hvac.HVACData;
import edu.hawaii.ihale.housesimulator.lighting.LightingData;
import edu.hawaii.ihale.housesimulator.photovoltaics.PhotovoltaicsData;

/**
 * Allows user to specify how often the house should change it system states.
 * 
 * @author Michael Cera
 * @author Anthony Kinsey
 */
public class SimulationTimer {

  /**
   * This main method starts the timer.
   * 
   * @param args Ignored.
   * @throws Exception If problems occur.
   */
  public static void main(String[] args) throws Exception {
    //startTimer(3);
  }

  /**
   * Starts the timer to modify the system's states.
   * @param stepValue how often the systems should change state
   */
  public static void startTimer(int stepValue) {

    new Timer().scheduleAtFixedRate(new TimerTask() {
      public void run() {
        AquaponicsData.modifySystemState();
        ElectricalData.modifySystemState();
        HVACData.modifySystemState();
        LightingData.modifySystemState();
        PhotovoltaicsData.modifySystemState();
      }
    }, 0, stepValue * 1000);

  }

}
