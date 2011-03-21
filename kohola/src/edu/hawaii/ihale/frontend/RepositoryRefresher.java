package edu.hawaii.ihale.frontend;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import edu.hawaii.ihale.api.ApiDictionary.IHaleCommandType;
import edu.hawaii.ihale.api.ApiDictionary.IHaleRoom;
import edu.hawaii.ihale.api.ApiDictionary.IHaleState;
import edu.hawaii.ihale.api.ApiDictionary.IHaleSystem;
import edu.hawaii.ihale.api.repository.impl.Repository;
import edu.hawaii.ihale.backend.IHaleBackend;

/**
 * An external timer class that updates all resources periodically.
 * @author Team Maka
 */
public class RepositoryRefresher {
  static Timer time = new Timer();
  /** instance of the backend to use the doCommand(). */
  public IHaleBackend backend;
  /** instance of the repository to use the store(). */
  public Repository repository;
  static boolean enabled = true;

  /**
   * Constructor.
   * @param backend IHaleBackend
   * @param repository Repository.
   */
  public RepositoryRefresher(IHaleBackend backend, Repository repository) {
    this.backend = backend;
    this.repository = repository;
    
  }
  
  /**
   * Starts the refresher.
   * @param mils The delay between refreshing.
   */
  public void start(long mils) {
    long delay = mils; //milliseconds
    long period = mils;
    
    time.scheduleAtFixedRate(new TimerTask() {
      public void run() {
        long timestamp = new Date().getTime();
        String color = "#FFFFFF";
        
        backend.doCommand(IHaleSystem.AQUAPONICS, null, IHaleCommandType.SET_PH,
            getRandomDouble(14, 0));
        backend.doCommand(IHaleSystem.AQUAPONICS, null, IHaleCommandType.FEED_FISH,
            getRandomDouble(14, 0));
        backend.doCommand(IHaleSystem.AQUAPONICS, null, IHaleCommandType.SET_NUTRIENTS,
            getRandomDouble(14, 0));
        backend.doCommand(IHaleSystem.AQUAPONICS, null, IHaleCommandType.SET_WATER_LEVEL,
            getRandomInteger(100, 1));
        backend.doCommand(IHaleSystem.AQUAPONICS, null, IHaleCommandType.HARVEST_FISH,
            getRandomInteger(10, 1));
        backend.doCommand(IHaleSystem.AQUAPONICS, null, IHaleCommandType.SET_TEMPERATURE,
            getRandomInteger(100, 1));
        // will not be able to store directly to the repository like this later
        repository.store(IHaleSystem.AQUAPONICS, IHaleState.CIRCULATION, timestamp,
            getRandomDouble(14, 0));
        repository.store(IHaleSystem.AQUAPONICS, IHaleState.DEAD_FISH, timestamp,
            getRandomInteger(100, 1));
        repository.store(IHaleSystem.AQUAPONICS, IHaleState.ELECTRICAL_CONDUCTIVITY, timestamp,
            getRandomDouble(14, 0));
        repository.store(IHaleSystem.AQUAPONICS, IHaleState.OXYGEN, timestamp,
            getRandomDouble(14, 0));
        repository.store(IHaleSystem.AQUAPONICS, IHaleState.TURBIDITY, timestamp,
            getRandomDouble(14, 0));

        repository.store(IHaleSystem.PHOTOVOLTAIC, IHaleState.ENERGY, timestamp,
            getRandomInteger(100, 1));
        repository.store(IHaleSystem.PHOTOVOLTAIC, IHaleState.POWER, timestamp,
            getRandomInteger(100, 1));

        repository.store(IHaleSystem.ELECTRIC, IHaleState.ENERGY, timestamp,
            getRandomInteger(100, 1));
        repository.store(IHaleSystem.ELECTRIC, IHaleState.POWER, timestamp,
            getRandomInteger(100, 1));

        backend.doCommand(IHaleSystem.HVAC, null, IHaleCommandType.SET_TEMPERATURE,
            getRandomInteger(100, 1));

        // switch lights on and off, alternating each refresh
        if (enabled) {
          backend.doCommand(IHaleSystem.LIGHTING, IHaleRoom.BATHROOM,
              IHaleCommandType.SET_LIGHTING_ENABLED, true);
          backend.doCommand(IHaleSystem.LIGHTING, IHaleRoom.BATHROOM,
              IHaleCommandType.SET_LIGHTING_LEVEL, getRandomInteger(100, 1));
          backend.doCommand(IHaleSystem.LIGHTING, IHaleRoom.BATHROOM,
              IHaleCommandType.SET_LIGHTING_COLOR, color);

          backend.doCommand(IHaleSystem.LIGHTING, IHaleRoom.DINING,
              IHaleCommandType.SET_LIGHTING_ENABLED, true);
          backend.doCommand(IHaleSystem.LIGHTING, IHaleRoom.DINING,
              IHaleCommandType.SET_LIGHTING_LEVEL, getRandomInteger(100, 1));
          backend.doCommand(IHaleSystem.LIGHTING, IHaleRoom.DINING,
              IHaleCommandType.SET_LIGHTING_COLOR, color);

          backend.doCommand(IHaleSystem.LIGHTING, IHaleRoom.LIVING,
              IHaleCommandType.SET_LIGHTING_ENABLED, true);
          backend.doCommand(IHaleSystem.LIGHTING, IHaleRoom.LIVING,
              IHaleCommandType.SET_LIGHTING_LEVEL, getRandomInteger(100, 1));
          backend.doCommand(IHaleSystem.LIGHTING, IHaleRoom.LIVING,
              IHaleCommandType.SET_LIGHTING_COLOR, color);

          backend.doCommand(IHaleSystem.LIGHTING, IHaleRoom.KITCHEN,
              IHaleCommandType.SET_LIGHTING_ENABLED, true);
          backend.doCommand(IHaleSystem.LIGHTING, IHaleRoom.KITCHEN,
              IHaleCommandType.SET_LIGHTING_LEVEL, getRandomInteger(100, 1));
          backend.doCommand(IHaleSystem.LIGHTING, IHaleRoom.KITCHEN,
              IHaleCommandType.SET_LIGHTING_COLOR, color);
          
          enabled = false;
        }
        else {
          backend.doCommand(IHaleSystem.LIGHTING, IHaleRoom.BATHROOM,
              IHaleCommandType.SET_LIGHTING_ENABLED, false);
          backend.doCommand(IHaleSystem.LIGHTING, IHaleRoom.DINING,
              IHaleCommandType.SET_LIGHTING_ENABLED, false);
          backend.doCommand(IHaleSystem.LIGHTING, IHaleRoom.LIVING,
              IHaleCommandType.SET_LIGHTING_ENABLED, false);
          backend.doCommand(IHaleSystem.LIGHTING, IHaleRoom.KITCHEN,
              IHaleCommandType.SET_LIGHTING_ENABLED, false);
          
          enabled = true;
        }
      }
    }, delay, period);
  }
  
  /**
   * @param high Integer high value
   * @param low Integer low value
   * @return a random value bounded by [low,high]
   */
  public double getRandomDouble(int high, int low) {
    final DecimalFormat twoDForm = new DecimalFormat("#.##");
    double randomValue = Double.parseDouble(
        twoDForm.format(Math.random() * (high - low) + low));
    return randomValue;
  }
  
  /**
   * @param high Integer high value
   * @param low Integer low value
   * @return a random value bounded by [low,high]
   */
  public int getRandomInteger(int high, int low) {
    return (int) (Math.random() * (high - low) + low);
  }
}

