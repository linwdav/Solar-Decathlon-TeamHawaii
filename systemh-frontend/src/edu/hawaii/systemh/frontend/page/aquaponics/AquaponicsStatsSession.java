package edu.hawaii.systemh.frontend.page.aquaponics;

import java.io.Serializable;
import edu.hawaii.systemh.frontend.googlecharts.ChartDataHelper.ChartDataType;

/**
 * The aquaponics stats sessions page.
 * 
 * @author Kylan Hughes
 * @author Chuan Lun Hung
 */
public class AquaponicsStatsSession implements Serializable {

  /** Support serialization. */
  private static final long serialVersionUID = 1L;
  
  private ChartDataType currentGraph = ChartDataType.AQUAPONICS_PH;
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
  public ChartDataType getCurrentGraph() {
    return currentGraph;
  }
  
  /**
   * Sets the current graph for the user's session.
   * @param chartDataType - the graph to display.
   */
  public void setCurrentGraph(ChartDataType chartDataType) {
    this.currentGraph = chartDataType;
  }
  
}
