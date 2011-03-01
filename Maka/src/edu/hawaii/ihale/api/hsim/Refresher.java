package edu.hawaii.ihale.api.hsim;

import java.util.Timer;
import java.util.TimerTask;
import edu.hawaii.ihale.api.aquaponics.AquaponicsResource;
import edu.hawaii.ihale.api.hvac.HVACResource;

public class Refresher {
  Timer time;
  AquaponicsResource aquaponics = new AquaponicsResource();
  HVACResource hvac = new HVACResource();
  
  public Refresher() {
  
  }
  
  public void start() {
    long delay = 5000; //milliseconds
    long period = 5000;
    time = new Timer();
    time.scheduleAtFixedRate(new TimerTask() {
      public void run() {
        //lights should never change unless 
        //user sets a new value
        aquaponics.poll();
        hvac.poll();
      }
    }, delay, period);
  }
}
