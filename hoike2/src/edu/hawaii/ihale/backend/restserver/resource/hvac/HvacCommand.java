package edu.hawaii.ihale.backend.restserver.resource.hvac;

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
 * A server resource that will handle requests regarding the HVAC system. Supported operations: PUT.
 * Supported representations: XML.
 * 
 * @author Michael Cera
 */
public class HvacCommand extends ServerResource {

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
    IHaleSystem system = IHaleSystem.HVAC;
    Status status = Status.CLIENT_ERROR_NOT_ACCEPTABLE;

    String commandParam = this.getRequestAttributes().get("command").toString();
    IHaleCommandType command = IHaleCommandType.valueOf(commandParam);
    IHaleState state = ApiDictionary.iHaleCommandType2State(command);

    String arg = queryMap.containsKey("arg") ? queryMap.get("arg") : null;

    Object commandArg = null;

    if (arg != null && state.isType(arg)) {

      if (command.equals(IHaleCommandType.SET_TEMPERATURE)) {
        commandArg = Integer.parseInt(arg);
      }

      if (commandArg != null) {
        status = Status.SUCCESS_OK;
        backend.doCommand(system, null, command, commandArg);
      }      
    }

    getResponse().setStatus(status);

    return null;
  }
}
