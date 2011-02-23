package edu.hawaii.ihale.backend.restserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import edu.hawaii.ihale.api.SystemStateEntry;
import edu.hawaii.ihale.api.SystemStateEntryDB;
import edu.hawaii.ihale.api.SystemStateEntryDBException;
import edu.hawaii.ihale.api.SystemStateListener;
import edu.hawaii.ihale.backend.db.IHaleDB;
import edu.hawaii.ihale.backend.db.IHaleSystemStateEntry;

/**
 * A class that resolves the persistency issues of the Java API by mirroring
 * IHaleDBRedirector and IHaleDB, converting SystemStateEntry objects to 
 * IHaleSystemEntry objects (has persistency traits). Then passing the 
 * IHaleSystemEntry objects to IHaleDB methods to store into the
 * database repository.
 *
 * @author Leonardo Nguyen
 * @version Java 1.6.0_21
 */
public class IHaleDAO implements SystemStateEntryDB {
  
  private static final Map<String, ArrayList<String>> systemToFieldMap = 
    new HashMap<String, ArrayList<String>>();
  
  // PMD complains about use of String literals.
  private String longString = "Long";
  private String stringString = "String";
  private String doubleString = "Double";
  
  /**
   * Default constructor.
   */
  public IHaleDAO() {
    // Default constructor.
  }
  
  static {
    createSystemToKeyMap();
  }

  /**
   * Returns the SystemStateEntry instance associated with the system, device, and timestamp, 
   * or null if not found.
   * @param systemName The system name.
   * @param deviceName The device name.
   * @param timestamp The timestamp.
   * @return The associated SystemStateEntry, or null if not found.
   */
  public SystemStateEntry getEntry(String systemName, String deviceName, long timestamp) {
    
    IHaleSystemStateEntry entry = IHaleDB.getEntry(systemName, deviceName, timestamp);
    
    SystemStateEntry returnEntry = 
      new SystemStateEntry(entry.getSystemName(), entry.getDeviceName(), entry.getTimestamp());
    
    // Retrieve the Long data types from the Entity class and populate the corresponding 
    // longData Map of SystemStateEntry object.
    Map<String, Long> longData = entry.getLongData();
    Iterator<Entry<String, Long>> longIt = longData.entrySet().iterator();
    while (longIt.hasNext()) {
      Map.Entry<String, Long> mapEntry = longIt.next();
      String key = mapEntry.getKey().toString();
      returnEntry.putLongValue(key, longData.get(key));
    }
    
    // The same process as above but for SystemStateEntry's stringData Map.
    Map<String, String> stringData = entry.getStringData();
    Iterator<Entry<String, String>> stringIt = stringData.entrySet().iterator();
    while (stringIt.hasNext()) {
      Map.Entry<String, String> mapEntry = stringIt.next();
      String key = mapEntry.getKey().toString();
      returnEntry.putStringValue(key, stringData.get(key));
    }
    
    // The same process as above but for SystemStateEntry's doubleData Map.
    Map<String, Double> doubleData = entry.getDoubleData();
    Iterator<Entry<String, Double>> doubleIt = doubleData.entrySet().iterator();
    while (doubleIt.hasNext()) {
      Map.Entry<String, Double> mapEntry = doubleIt.next();
      String key = mapEntry.getKey().toString();
      returnEntry.putDoubleValue(key, doubleData.get(key));
    }
    return returnEntry;
  }
  
  /**
   * Store the passed SystemStateEntry in the database.
   * @param entry The entry instance to store. 
   */
  public void putEntry(SystemStateEntry entry) {
        
    // A Map such that the key is the device field key (i.e., Oxygen, Temp) and the
    // value mapped to the key is the value type (i.e., Double, Long).
    Map<String, String> keyTypePairMap = getTypeList(entry.getSystemName());

    // The three Maps longData, stringData, doubleData are representative of the Map fields
    // of SystemStateEntry objects. There is no accessor method to them so we must create
    // our own and mirror exactly those Maps and provide them to the Entity class to be stored
    // into the database repository. In this case the Entity class is IHaleSystemStateEntry.
    Map<String, Long> longData = new HashMap<String, Long>();
    Map<String, String> stringData = new HashMap<String, String>();
    Map<String, Double> doubleData = new HashMap<String, Double>();
    
    // Retrieve the keys and iterate through them checking for the corresponding value type
    // Double, String, or Long. Then retrieve from the SystemStateEntry object and put both
    // the key and value associated with the key into the appropriate Map.
    
    Iterator<Entry<String, String>> iterator = keyTypePairMap.entrySet().iterator();
    while (iterator.hasNext()) {
      Map.Entry<String, String> mapEntry = iterator.next();
      String key = mapEntry.getKey().toString();
      String value = keyTypePairMap.get(key);
      if (value.equals(longString)) {
        longData.put(key, entry.getLongValue(key));
      }
      else if (value.equals(stringString)) {
        stringData.put(key, entry.getStringValue(key));
      }
      else if (value.equals(doubleString)) {
        doubleData.put(key, entry.getDoubleValue(key));
      }
    }

    IHaleSystemStateEntry entryToStore = 
      new IHaleSystemStateEntry(entry.getSystemName(), entry.getDeviceName(), entry.getTimestamp(),
          longData, stringData, doubleData);
    IHaleDB.putEntry(entryToStore);
  }
  
  /**
   * Removes the entry with the specified system name, device name, and timestamp.
   * @param systemName The system name.
   * @param deviceName The device name.
   * @param timestamp The timestamp.
   */
  public void deleteEntry(String systemName, String deviceName, long timestamp) {
    IHaleDB.deleteEntry(systemName, deviceName, timestamp);
  }
  
  /**
   * Returns a list of SystemStateEntry instances consisting of all entries between the 
   * two timestamps. 
   * @param systemName The system name.
   * @param deviceName The device name.
   * @param startTime The start time.
   * @param endTime The end time. 
   * @return A (possibly empty) list of SystemStateEntries.
   * @throws SystemStateEntryDBException If startTime is greater than endTime. 
   */
  public List<SystemStateEntry> getEntries(String systemName, String deviceName, long startTime, 
      long endTime) throws SystemStateEntryDBException {
    
    List<IHaleSystemStateEntry> iHaleEntries = 
      IHaleDB.getEntries(systemName, deviceName, startTime, endTime);
    List<SystemStateEntry> returnEntryList = new ArrayList<SystemStateEntry>();
    
    // For each entry retrieved from the data repository, we must transform from 
    // IHaleSystemStateEntry to SystemStateEntry and store it in a List to be returned.
    for (int i = 0; i < iHaleEntries.size(); i++) {
      
      IHaleSystemStateEntry iHaleEntry = iHaleEntries.get(i);
      SystemStateEntry returnEntry = 
        new SystemStateEntry(iHaleEntry.getSystemName(), iHaleEntry.getDeviceName(), 
            iHaleEntry.getTimestamp());
      
      // Retrieve the Long data types from the Entity class and populate the corresponding 
      // longData Map of SystemStateEntry object.
      Map<String, Long> longData = iHaleEntry.getLongData();
      Iterator<Entry<String, Long>> longIt = longData.entrySet().iterator();
      while (longIt.hasNext()) {
        Map.Entry<String, Long> mapEntry = longIt.next();
        String key = mapEntry.getKey().toString();
        returnEntry.putLongValue(key, longData.get(key));
      }
      
      // The same process as above but for SystemStateEntry's stringData Map.
      Map<String, String> stringData = iHaleEntry.getStringData();
      Iterator<Entry<String, String>> stringIt = stringData.entrySet().iterator();
      while (stringIt.hasNext()) {
        Map.Entry<String, String> mapEntry = stringIt.next();
        String key = mapEntry.getKey().toString();
        returnEntry.putStringValue(key, stringData.get(key));
      }
      
      // The same process as above but for SystemStateEntry's doubleData Map.
      Map<String, Double> doubleData = iHaleEntry.getDoubleData();
      Iterator<Entry<String, Double>> doubleIt = doubleData.entrySet().iterator();
      while (doubleIt.hasNext()) {
        Map.Entry<String, Double> mapEntry = doubleIt.next();
        String key = mapEntry.getKey().toString();
        returnEntry.putDoubleValue(key, doubleData.get(key));
      }
      returnEntryList.add(returnEntry);
    }
    return returnEntryList;
  }
  
  /**
   * Returns a list of all currently defined system names. 
   * @return The list of system names.
   */
  public List<String> getSystemNames() {
    return IHaleDB.getSystemNames();
  }
  
  /**
   * Returns a list of all the device names associated with the passed SystemName.
   * @param systemName A string indicating a system name. 
   * @return A list of device names for this system name.
   * @throws SystemStateEntryDBException if the system name is not known.
   */
  public List<String> getDeviceNames(String systemName) throws SystemStateEntryDBException {
    return IHaleDB.getDeviceNames(systemName);
  }
  
  /**
   * Adds a listener to this repository whose entryAdded method will be invoked whenever an
   * entry is added to the database for the system name associated with this listener.
   * This method provides a way for the user interface (for example, Wicket) to update itself
   * whenever new data comes in to the repository. 
   * 
   * @param listener The listener whose entryAdded method will be called. 
   */
  public void addSystemStateListener(SystemStateListener listener) {
    // TO-DO: . . .
  }
                                                                 
  
  /**
   * Emits a command to be sent to the specified system with the optional arguments. 
   * @param systemName The system name.
   * @param deviceName The device name. 
   * @param command The command.
   * @param args Any additional arguments required by the command. 
   */
  public void doCommand(String systemName, String deviceName, String command, List<String> args) {
    // TO-DO: . . .
  }
  
  /**
   * Create a Map from a system to its associated field values that would be returned by a system
   * device. The key to a field and its value type is concatenated into a single string separated 
   * by ||.
   */
  public static void createSystemToKeyMap() {
    
    ArrayList<String> keyTypePairList = new ArrayList<String>();
    
    /** Aquaponics System **/
    keyTypePairList.add("pH||Double");
    keyTypePairList.add("Oxygen||Double");
    keyTypePairList.add("Temp||Long");
    IHaleDAO.systemToFieldMap.put("Aquaponics", keyTypePairList);
    
    /** HVAC System **/
    keyTypePairList = new ArrayList<String>();
    keyTypePairList.add("Temp||Long");
    IHaleDAO.systemToFieldMap.put("HVAC", keyTypePairList);
    
    /** Lighting System **/
    keyTypePairList = new ArrayList<String>();
    keyTypePairList.add("Level||Long");
    IHaleDAO.systemToFieldMap.put("Lighting", keyTypePairList);
    
    /** Photovoltaics System **/
    keyTypePairList = new ArrayList<String>();
    keyTypePairList.add("power||Long");
    keyTypePairList.add("energy||Long");
    IHaleDAO.systemToFieldMap.put("Photovoltaics", keyTypePairList);
    
    /** Electrical System **/
    keyTypePairList = new ArrayList<String>();
    keyTypePairList.add("power||Long");
    keyTypePairList.add("energy||Long");
    IHaleDAO.systemToFieldMap.put("Electrical", keyTypePairList);
  }
  
  /**
   * Returns a Map such that the key value is the device field key (i.e., Oxygen, Temp) and the
   * value mapped to the key is the value type (i.e., Double, Long).
   * 
   * @param systemName The system name.
   * @return The key type pair map.
   */
 public Map<String, String> getTypeList(String systemName) {

   ArrayList<String> keyTypePairList = IHaleDAO.systemToFieldMap.get(systemName);

   Map<String, String> keyTypePairMap = new HashMap<String, String>();
   //String[] temp = new String[2];
   for (int i = 0; i < keyTypePairList.size(); i++) {
     // Character | are special, need to be escaped with \\
     String[] temp = keyTypePairList.get(i).split("\\|\\|");
     keyTypePairMap.put(temp[0], temp[1]);
   }
   return keyTypePairMap;
 }  
}
