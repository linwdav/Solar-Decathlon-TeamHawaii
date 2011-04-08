package edu.hawaii.systemh.frontend.page.aquaponics;

import java.io.Serializable;

/**
 * The aquaponics stats sessions page.
 * 
 * @author Kylan Hughes
 * @author Chuan Lun Hung
 */
public class AquaponicsStatsSession implements Serializable {

  /** Support serialization. */
  private static final long serialVersionUID = 1L;
  
  private int currentGraph = 0;
  /**
   * empty for now.
   */
  public AquaponicsStatsSession() {
    //empty
  }
  
  /**
   * Returns the current graph associated with the users session.
   * @return The current graph.
   */
  public int getCurrentGraph() {
    return currentGraph;
  }
  
  /**
   * Sets the current graph for the user's session.
   * @param currentGraph - the graph to display.
   */
  public void setCurrentGraph(int currentGraph) {
    this.currentGraph = currentGraph;
  }
  
}
