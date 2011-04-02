package edu.hawaii.ihale.backend.restserver.resource.lighting;

import java.util.Map;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import edu.hawaii.ihale.api.ApiDictionary.IHaleCommandType;
import edu.hawaii.ihale.api.ApiDictionary.IHaleRoom;
import edu.hawaii.ihale.api.ApiDictionary.IHaleState;
import edu.hawaii.ihale.api.ApiDictionary.IHaleSystem;
import edu.hawaii.ihale.backend.IHaleBackend;

/**
 * A server resource that will handle requests regarding the Lighting system. Supported operations:
 * PUT. Supported representations: XML.
 * 
 * @author Michael Cera
 */
public class LightingCommand extends ServerResource {

  private IHaleBackend backend = IHaleBackend.getInstance();

  /**
   * Sends command to system.
   * 
   * @param representation Representation.
   * @return Null.
   */
  @Put
  public Representation sendCommand(Representation representation) {

    Map<String, String> queryMap = getQuery().getValuesMap();
    String command = (String) this.getRequestAttributes().get("command");
    IHaleSystem system = IHaleSystem.LIGHTING;
    IHaleCommandType commandType;
    IHaleRoom room = null;
    Object commandArg;

    if (queryMap.containsKey("room")) {
      room = IHaleRoom.valueOf(queryMap.get("room"));
    }

    if (queryMap.containsKey("arg")) {

      String arg = queryMap.get("arg");
      if ((command.equals(IHaleCommandType.SET_LIGHTING_LEVEL.toString()) &&
          (IHaleState.SET_LIGHTING_LEVEL_COMMAND.isType(arg)))) {
        commandType = IHaleCommandType.SET_LIGHTING_LEVEL;
        commandArg = Integer.parseInt(arg);
        backend.doCommand(system, room, commandType, commandArg);
      }
      else if ((command.equals(IHaleCommandType.SET_LIGHTING_ENABLED.toString()) &&
          (IHaleState.SET_LIGHTING_ENABLED_COMMAND.isType(arg)))) {
        commandType = IHaleCommandType.SET_LIGHTING_ENABLED;
        commandArg = Boolean.parseBoolean(arg);
        backend.doCommand(system, room, commandType, commandArg);
      }
      else if ((command.equals(IHaleCommandType.SET_LIGHTING_COLOR.toString()) &&
          (IHaleState.SET_LIGHTING_COLOR_COMMAND.isType(arg)))) {
        commandType = IHaleCommandType.SET_LIGHTING_COLOR;
        commandArg = arg;
        backend.doCommand(system, room, commandType, commandArg);
      }
      else {
        getResponse().setStatus(Status.CLIENT_ERROR_NOT_ACCEPTABLE);
      }
    }
    else {
      getResponse().setStatus(Status.CLIENT_ERROR_NOT_ACCEPTABLE);
    }
    return null;
  }
}
