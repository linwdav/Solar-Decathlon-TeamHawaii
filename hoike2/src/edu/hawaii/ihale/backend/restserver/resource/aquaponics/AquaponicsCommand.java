package edu.hawaii.ihale.backend.restserver.resource.aquaponics;

import java.util.Map;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import edu.hawaii.ihale.api.ApiDictionary;
import edu.hawaii.ihale.api.ApiDictionary.IHaleCommandType;
import edu.hawaii.ihale.api.ApiDictionary.IHaleState;
import edu.hawaii.ihale.api.ApiDictionary.IHaleSystem;
import edu.hawaii.ihale.backend.IHaleBackend;

/**
 * A server resource that will handle requests regarding the Aquaponics system. Supported
 * operations: PUT. Supported representations: XML.
 * 
 * @author Michael Cera
 */
public class AquaponicsCommand extends ServerResource {

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
    IHaleSystem system = IHaleSystem.AQUAPONICS;
    Status status = Status.CLIENT_ERROR_NOT_ACCEPTABLE;
    
    String commandParam = this.getRequestAttributes().get("command").toString();
    IHaleCommandType command = IHaleCommandType.valueOf(commandParam);
    IHaleState state = ApiDictionary.iHaleCommandType2State(command);
    
    String arg = queryMap.containsKey("arg") ? queryMap.get("arg") : null;

    Object commandArg = null;
    
    if (arg != null && state.isType(arg)) {

      // decided how to parse argument
      switch (command) {
      case SET_TEMPERATURE:
      case HARVEST_FISH:
        commandArg = Integer.parseInt(arg);
        break;
      case FEED_FISH:
      case SET_NUTRIENTS:
      case SET_PH:
        commandArg = Double.parseDouble(arg);
        break;
      default:
        commandArg = null;
        break;
      }

      if (commandArg != null) {
        backend.doCommand(system, null, command, commandArg);
        status = Status.SUCCESS_OK;
      }
    }

    getResponse().setStatus(status);

    return null;
  }
}
