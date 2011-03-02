package edu.hawaii.ihale.api.hsim;

import java.util.Timer;
import java.util.TimerTask;
import edu.hawaii.ihale.api.aquaponics.AquaponicsResource;
import edu.hawaii.ihale.api.electrical.SolarResource;
import edu.hawaii.ihale.api.hvac.HVACResource;
import edu.hawaii.ihale.api.photovoltaics.GridResource;
/**
 * An external timer class that updates all resources periodically.
 * @author Team Maka
 */
public class Refresher {
  static Timer time;
  static AquaponicsResource aquaponics = new AquaponicsResource();
  static HVACResource hvac = new HVACResource();
  static SolarResource solar = new SolarResource();
  static GridResource electrical = new GridResource();
  /**
   * Constructor.
   */
  public Refresher() {
    //Empty.
  }
  
  /**
   * Starts the refresher.
   * @param mils The delay between refreshing.
   */
  @edu.umd.cs.findbugs.annotations.SuppressWarnings(value =
    "ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD",
    justification = "It's ok, since the affected fields are static " +
    "and this is the only way, except by calling a set(), that the values " + 
    "will ever change.")
  public void start(long mils) {
    long delay = mils; //milliseconds
    long period = mils;
    time = new Timer();
    time.scheduleAtFixedRate(new TimerTask() {
      public void run() {
        //lights should never change unless 
        //user sets a new value
        aquaponics.poll();
        hvac.poll();
        electrical.poll();
        solar.poll();
      }
    }, delay, period);
  }
}
