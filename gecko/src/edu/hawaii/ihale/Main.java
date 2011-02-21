package edu.hawaii.ihale;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import edu.hawaii.ihale.api.SystemStateEntry;
import edu.hawaii.ihale.api.SystemStateEntryDB;
import edu.hawaii.ihale.ui.AquaponicsListener;
import edu.hawaii.ihale.ui.BlackMagic;
import edu.hawaii.ihale.ui.HVacListener;
import edu.hawaii.ihale.ui.LightsListener;
import edu.hawaii.ihale.ui.PhotovoltaicListener;
import edu.hawaii.ihale.ui.WaterListener;

/**
 * Provides a simple example of a User Interface component. 
 * @author Philip Johnson
 */
public class Main {
  
  /**
   * Main program simulates a user interface. 
   * @param args Ignored. 
   * @throws Exception If problems occur.
   */
  public static void main(String[] args) throws Exception {
    
    // Create an instance of the database.
    // We could read this string from a properties file on startup, for example.
    String dbClassName = "edu.hawaii.ihale.db.IHaleDB";
    SystemStateEntryDB db = (SystemStateEntryDB)Class.forName(dbClassName).newInstance();
    
    // Add listeners to the system.  Listeners are the way the UI learns that new state has
    // been received from some system in the house.
    db.addSystemStateListener(new AquaponicsListener());
    db.addSystemStateListener(new LightsListener());
    db.addSystemStateListener(new PhotovoltaicListener());
    db.addSystemStateListener(new WaterListener());
    db.addSystemStateListener(new HVacListener());
    
    // Let's simulate some stuff being received in the system so that our listeners can react.
    new BlackMagic(db);
    
    // The other way for the UI to get info is by querying the DB directly. For example,
    // Let's just get all the Aquaponics data from the beginning of time itself. 
    List<SystemStateEntry> entries = 
      db.getEntries("Aquaponics", "Arduino-23", 0, (new Date()).getTime());
    
    System.out.println("Here is all the Aquaponics data.");
    for (SystemStateEntry entry : entries) {
      System.out.println(entry);
    }
    
    // Finally, the front-end can command a system to do something. Here's an example:
    String systemName = "Aquaponics";
    String deviceName = "Arduino-23";
    String command = "setTemperature";
    List<String> commandArgs = new ArrayList<String>();
    commandArgs.add("73");
    db.doCommand(systemName, deviceName, command, commandArgs);
    
  }
}
