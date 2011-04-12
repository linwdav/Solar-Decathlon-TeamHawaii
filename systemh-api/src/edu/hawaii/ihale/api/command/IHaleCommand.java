package edu.hawaii.ihale.api.command;

import edu.hawaii.ihale.api.ApiDictionary.IHaleCommandType;
import edu.hawaii.ihale.api.ApiDictionary.IHaleRoom;
import edu.hawaii.ihale.api.ApiDictionary.IHaleSystem;

/** 
 * Specifies the interface that is used by the front-end to send a command to the house
 * via the back-end. 
 * @author Philip Johnson
 */
public interface IHaleCommand {

  /**
   * Emits a command to be sent to the specified system with the optional arguments. 
   * @param system The system to be commanded.
   * @param room The room (if system is LIGHTING).
   * @param command The command to be sent to the system.
   * @param arg The argument associated with this command.
   */
  public void doCommand(IHaleSystem system, IHaleRoom room, IHaleCommandType command, Object arg);

}
