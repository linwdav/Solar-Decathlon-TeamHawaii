package edu.hawaii.systemh.api.command;

import edu.hawaii.systemh.api.ApiDictionary.SystemHCommandType;
import edu.hawaii.systemh.api.ApiDictionary.SystemHRoom;
import edu.hawaii.systemh.api.ApiDictionary.SystemHSystem;

/** 
 * Specifies the interface that is used by the front-end to send a command to the house
 * via the back-end. 
 * @author Philip Johnson
 */
public interface SystemHCommand {

  /**
   * Emits a command to be sent to the specified system with the optional arguments. 
   * @param system The system to be commanded.
   * @param room The room (if system is LIGHTING).
   * @param command The command to be sent to the system.
   * @param arg The argument associated with this command.
   */
  public void doCommand(SystemHSystem system, SystemHRoom room, 
      SystemHCommandType command, Object arg);

}
