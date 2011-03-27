package edu.hawaii.ihale.backend;

import java.util.Date;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.restlet.ext.xml.DomRepresentation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import edu.hawaii.ihale.api.ApiDictionary;
import edu.hawaii.ihale.api.ApiDictionary.IHaleCommandType;
import edu.hawaii.ihale.api.ApiDictionary.IHaleRoom;
import edu.hawaii.ihale.api.ApiDictionary.IHaleState;
import edu.hawaii.ihale.api.ApiDictionary.IHaleSystem;
import edu.hawaii.ihale.api.ApiDictionary.SystemStatusMessageType;
import edu.hawaii.ihale.api.command.IHaleCommand;
import edu.hawaii.ihale.api.repository.SystemStatusMessage;
import edu.hawaii.ihale.api.repository.impl.Repository;

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
 * @author Michael Cera
 */
public class IHaleBackend implements IHaleCommand {

  /** The repository that can store all the data for the iHale system. */
  Repository repository = new Repository();

  /** Constructor. Initializes history and reads .properties file. **/
  public IHaleBackend() {
    // parse property / URI file
    // parse historical xml file
    // store historical xml data.
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
        // TODO Auto-generated catch block
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

    if (command.toString().equals("SET_LIGHTING_ENABLED")) {
      if (ApiDictionary.iHaleCommandType2State(command).isType(arg.toString())) {
        // TODO Genereate XML and send
      }
      else {
        // TODO Error message
      }
    }
    else if (command.toString().equals("SET_LIGHTING_LEVEL")) {
      if (ApiDictionary.iHaleCommandType2State(command).isType(arg.toString())) {
        // TODO Genereate XML and send
      }
      else {
        // TODO Error message
      }

    }
    else if (command.toString().equals("SET_LIGHTING_COLOR")) {
      if (ApiDictionary.iHaleCommandType2State(command).isType(arg.toString())) {
        // TODO Genereate XML and send
      }
      else {
        // TODO Error message
      }

    }

  }

  /**
   * Emit an HTTP command to the HVAC system.
   * 
   * @param command Currently the only supported command is SET_TEMPERATURE.
   * @param arg An integer representing the new number.
   * @throws Exception
   */
  private void handleHvacCommand(IHaleCommandType command, Object arg) throws Exception {

    if (command.toString().equals("SET_TEMPERATURE")) {
      if (ApiDictionary.iHaleCommandType2State(command).isType(arg.toString())) {

        // Create document.
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = null;
        docBuilder = factory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();

        // Create root tag
        Element rootElement = doc.createElement("command");
        rootElement.setAttribute("name", command.toString());
        doc.appendChild(rootElement);

        // Create state tag.
        Element temperatureElement = doc.createElement("state");
        temperatureElement.setAttribute("key", arg.toString());
        rootElement.appendChild(temperatureElement);

        // Convert Document to DomRepresentation.
        DomRepresentation representation = new DomRepresentation();
        representation.setDocument(doc);

        // TODO Send representation to device.
      }
      else {
        // TODO Error message
      }
    }
    else {
      // TODO Error message
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
    // Left as an exercise for the reader.
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
    backend.exampleStateFromHouseSystems();
  }

}
