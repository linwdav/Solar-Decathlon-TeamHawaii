package edu.hawaii.ihale.backend.restserver.system;

//import java.util.Map;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

/**
 * A server resource that will handle requests regarding the Aquaponics system. Supported
 * operations: PUT. Supported representations: XML.
 * 
 * @author Michael Cera
 */
public class AquaponicsCommandResource extends ServerResource {
  /**
   * Sends command to system.
   */
  @Put
  public void sendCommand() {

    // Map<String, String> queryMap = getQuery().getValuesMap();
    // String command = (String) this.getRequestAttributes().get("command");
    //
    //
    // if (queryMap.containsKey("arg")) {
    // // String arg = queryMap.get("arg");
    //
    // if (command.equalsIgnoreCase("SET_TEMPERATURE")) {
    // // TODO Code here...
    // }
    // else if (command.equalsIgnoreCase("FEED_FISH")) {
    // // TODO Code here..
    // }
    // else {
    // // TODO Error, invalid command.
    // }
    // }
    // else {
    // // TODO Error, missing parameters.
    // }
  }
}
