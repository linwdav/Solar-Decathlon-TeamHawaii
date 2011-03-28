package edu.hawaii.ihale.backend;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.restlet.resource.ClientResource;
import edu.hawaii.ihale.api.ApiDictionary;
import edu.hawaii.ihale.api.ApiDictionary.IHaleCommandType;
import edu.hawaii.ihale.api.ApiDictionary.IHaleRoom;
import edu.hawaii.ihale.api.ApiDictionary.IHaleState;
import edu.hawaii.ihale.api.ApiDictionary.IHaleSystem;
import edu.hawaii.ihale.api.ApiDictionary.SystemStatusMessageType;
import edu.hawaii.ihale.api.command.IHaleCommand;
import edu.hawaii.ihale.api.repository.SystemStatusMessage;
import edu.hawaii.ihale.api.repository.impl.Repository;
import edu.hawaii.ihale.backend.xml.PutCommand;

/**
 * Provides a sample illustration of IHale backend functionality as it relates to the iHale API
 * implementation.
 * 
 * An IHale Backend has to accomplish two basic things:
 * 
 * (1) Query house systems via HTTP for their state, then store that info in the repository. The
 * storage part is illustrated by the obtainStateFromHouseSystem method.
 * 
 * (2) Implement the IHaleCommandInterface so that the Frontend can send commands to the house
 * systems by way of this Backend. This is illustrated by the doCommand method.
 * 
 * In addition, the doCommand illustrates how to create and store a system status message which the
 * Frontend could display in the interface by attaching a listener.
 * 
 * @author Philip Johnson
 * @author Backend Team
 */
public class IHaleBackend implements IHaleCommand {
  // A logger.
  private Logger log;
  // The repository that can store all the data for the iHale system.
  Repository repository = new Repository();
  // Object that polls data from HSIM
  public static Dispatcher dispatch;
  // to hold the URI data.
  public static Map<String, String> uris;
  public static Map<String, String> commandMap;
  public static Map<String, String> aquaMap;
  public static Map<String, String> lightMap;
  // delay between polling hsim
  public long interval = 5000;
  // ==========================================================
  // ============.properties file location=====================
  // ==========================================================
  private static String currentDirectory = System.getProperty("user.home");
  // Sub-directory containing system device properties file.
  private static String folder = ".ihale";
  // System device properties file name.
  private static String configurationFile = "device-urls.properties";
  // Full path to the system device properties file.
  private static String configFilePath = currentDirectory + "/" + folder + "/" + configurationFile;

  // ============================================================
  // ============================================================

  /** Constructor. Initializes history and reads .properties file. **/
  public IHaleBackend() {

    this.log = Logger.getLogger(this.getClass().toString());
    // instantiate the uris map.
    uris = new HashMap<String, String>();
    // puts URI data into "uris"
    makeURIMap();
    // TODO parse historical xml file
    // TODO store historical xml data.

    // make a dispatcher
    dispatch = new Dispatcher(uris, interval);
    // grab all data before it starts
    commandMap = dispatch.getCommandMap();
    lightMap = dispatch.getLightMap();
    aquaMap = dispatch.getAquaMap();
    // pop a new thread to run forever
    Thread poll = new Thread(dispatch);
    poll.start();
  }

  /**
   * Parses URI properties file. Taken from team Hoike's backend files.
   * 
   * @author Team Hoike
   */
  public static void makeURIMap() {

    try {
      FileInputStream is = new FileInputStream(configFilePath);
      Properties prop = new Properties();
      prop.load(is);
      String key = "";
      String value = "";
      for (Map.Entry<Object, Object> propItem : prop.entrySet()) {
        key = (String) propItem.getKey();
        value = (String) propItem.getValue();
        uris.put(key, value);
      }
      System.out.println(configurationFile);
      is.close();
    }
    catch (IOException e) {
      System.out.println("Failed to read properties file.");
      System.out.println(configFilePath);
    }
  }

  /**
   * Implements a request from the front-end to send off a command to a house system. The backend
   * must store this command request in the repository, indicate that it occurred as a status
   * message, and finally carry out the command by sending the HTTP request to the associated
   * system.
   * 
   * @param system The house system.
   * @param room The room in the house if the system is LIGHTING, or null otherwise.
   * @param command The command requested for the system.
   * @param arg The arguments for the command.
   */
  @Override
  public void doCommand(IHaleSystem system, IHaleRoom room, IHaleCommandType command, Object arg) {

    // All command invocations should be saved in the repository. Here's how you do it.
    Long timestamp = (new Date()).getTime();
    IHaleState state = ApiDictionary.iHaleCommandType2State(command);
    repository.store(system, room, state, timestamp, arg);

    // We probably also want every command invocation to be displayed as a status message.
    String msg = String.format("Sending system %s command %s with arg %s", system, command, arg);
    SystemStatusMessage message =
        new SystemStatusMessage(timestamp, system, SystemStatusMessageType.INFO, msg);
    repository.store(message);

    this.log
        .info(system.toString() + " command: " + command.toString() + " arg: " + arg.toString());

    // Of course, you also have to actually emit the HTTP request to send the command to the
    // relevant system. It might look something like the following.
    // Note the PV and ELECTRIC systems do not current support commands.
    switch (system) {
    case AQUAPONICS:
      handleAquaponicsCommand(command, arg);
      break;
    case HVAC:
      try {
        handleHvacCommand(command, arg);
      }
      catch (Exception e) {
        e.printStackTrace();
      }
      break;
    case LIGHTING:
      handleLightingCommand(room, command, arg);
      break;
    default:
      throw new RuntimeException("Unsupported IHale System Type encountered: " + system);
    }
  }

  /**
   * Emit an HTTP command to the lighting system.
   * 
   * @param room The room to be controlled.
   * @param command The command type: SET_LIGHTING_ENABLED, SET_LIGHTING_LEVEL, SET_LIGHTING_COLOR.
   * @param arg A boolean if the command is enabled, an integer if the command is level, and a
   * string if the command is color.
   */
  private void handleLightingCommand(IHaleRoom room, IHaleCommandType command, Object arg) {

    if (command.equals(ApiDictionary.IHaleCommandType.SET_LIGHTING_ENABLED)
        || command.equals(ApiDictionary.IHaleCommandType.SET_LIGHTING_LEVEL)
        || command.equals(ApiDictionary.IHaleCommandType.SET_LIGHTING_COLOR)) {

      PutCommand doc = generateCommandXml(command, arg);

      // Send the XML representation to the device.
      String url = commandMap.get(room.toString().toLowerCase()) + lightMap.get(command.toString());
      ClientResource client = new ClientResource(url);
      try {
        client.put(doc.getRepresentation());
      }
      catch (IOException e) {
        throw new RuntimeException("Failed to create Dom Representation.", e);
      }
    }
    else {
      throw new RuntimeException("IHaleCommandType is invalid.");
    }
  }

  /**
   * Creates the HTTP command for the HVAC system.
   * 
   * @param command Currently the only supported command is SET_TEMPERATURE.
   * @param arg An integer representing the new number.
   * @return String
   * @throws RuntimeException Thrown creation of XML document fails.
   */
  private void handleHvacCommand(IHaleCommandType command, Object arg) throws RuntimeException {

    if (command.equals(ApiDictionary.IHaleCommandType.SET_TEMPERATURE)) {

      PutCommand doc = generateCommandXml(command, arg);

      // Send the xml representation to the device.
      String url = commandMap.get("hvac") + "hvac/temp";
      ClientResource client = new ClientResource(url);
      try {
        client.put(doc.getRepresentation());
      }
      catch (IOException e) {
        throw new RuntimeException("Failed to create Dom Representation.", e);
      }
    }
    else {
      throw new RuntimeException("IHaleCommandType is invalid.");
    }
  }

  /**
   * Emit an HTTP command to the Aquaponics system.
   * 
   * @param command The command type: FEED_FISH, HARVEST_FISH, SET_PH, SET_WATER_LEVEL,
   * SET_TEMPERATURE, SET_NUTRIENTS.
   * @param arg An integer for feed fish, harvest fish, water level, and temperature, a double
   * otherwise.
   */
  private void handleAquaponicsCommand(IHaleCommandType command, Object arg) {
    
    if (command.equals(ApiDictionary.IHaleCommandType.FEED_FISH)
        || command.equals(ApiDictionary.IHaleCommandType.SET_TEMPERATURE)
        || command.equals(ApiDictionary.IHaleCommandType.HARVEST_FISH)
        || command.equals(ApiDictionary.IHaleCommandType.SET_PH)
        || command.equals(ApiDictionary.IHaleCommandType.SET_WATER_LEVEL)) {

      PutCommand doc = generateCommandXml(command, arg);

      // Send the xml representation to the device.
      String url = commandMap.get("aquaponics") + aquaMap.get(command.toString());
      System.out.println(url);
      ClientResource client = new ClientResource(url);
      try {
        client.put(doc.getRepresentation());
      }
      catch (IOException e) {
        throw new RuntimeException("Failed to create Dom Representation.", e);
      }
    }
    else {
      throw new RuntimeException("IHaleCommandType is invalid.");
    }
  }

  /**
   * Generates the XML for doCommand.
   * 
   * @param command The IHaleCommandType.
   * @param arg String, Integer, Double, or Boolean according to the IHaleCommandType Supplied.
   * @return
   */
  private PutCommand generateCommandXml(IHaleCommandType command, Object arg) {

    PutCommand doc = null;

    if (ApiDictionary.iHaleCommandType2State(command).isType(arg.toString())) {

      // Generates the XML for the command.
      try {
        doc = new PutCommand(command);
      }
      catch (ParserConfigurationException e) {
        throw new RuntimeException("Failed to create command XML.", e);
      }

      // Adds the argument.
      doc.addArgument("arg", arg);

    }
    else {
      throw new RuntimeException("Argument type is invalid.");
    }
    return doc;
  }

  /**
   * This method illustrates a couple examples of what you might do after you got some state
   * information from the house.
   */
  public void exampleStateFromHouseSystems() {
    // Let's say I found out somehow that the Temperature of the house was 22.
    // First I have to represent this information appropriately.
    IHaleSystem system = IHaleSystem.HVAC;
    IHaleState state = IHaleState.TEMPERATURE;
    Integer temperature = 22;
    Long timestamp = (new Date()).getTime();

    // Now I can create a repository instance and store my state info.
    Repository repository = new Repository();
    repository.store(system, state, timestamp, temperature);

    // A little while later, I find out that there are some dead fish in the tank.
    // So let's add that info to the repository.
    system = IHaleSystem.AQUAPONICS;
    state = IHaleState.DEAD_FISH;
    Integer numDeadFish = 2; // R.I.P.
    timestamp = (new Date()).getTime();
    repository.store(system, state, timestamp, numDeadFish);

    // It's bad when fish die, so let's send a high priority status message.
    SystemStatusMessage message =
        new SystemStatusMessage(timestamp, system, SystemStatusMessageType.ALERT,
            "Fish are dying!!! Do something!");
    repository.store(message);
  }

  /**
   * A sample main program.
   * 
   * @param args Ignored.
   */
  public static void main(String[] args) {
    IHaleBackend backend = new IHaleBackend();
    // backend.exampleStateFromHouseSystems();
    System.out.println(commandMap);
    backend.doCommand(IHaleSystem.AQUAPONICS, null, IHaleCommandType.SET_PH, 7);
  }
}