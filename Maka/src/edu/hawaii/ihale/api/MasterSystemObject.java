package edu.hawaii.ihale.api;

import java.util.ArrayList;
import java.util.List;
/**
 * The main class for dealing with high level calls.  
 * Provides access to low-level systems and settings.
 * @author Team Maka
 *
 */
public class MasterSystemObject {
  /** List of the SubSystemObjects that comprise the house.*/
  @SuppressWarnings("unused")
  private List<SubSystemObject> subSystems;
  
  /**
   * Constructor.
   */
  MasterSystemObject() {
    subSystems = new ArrayList<SubSystemObject>();
  }
  
}
