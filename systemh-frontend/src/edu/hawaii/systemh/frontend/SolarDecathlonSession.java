package edu.hawaii.systemh.frontend;

import java.util.HashMap;
import java.util.Map;
import org.apache.wicket.Request;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebSession;

import edu.hawaii.systemh.frontend.page.HeaderSession;
import edu.hawaii.systemh.frontend.page.aquaponics.AquaponicsSession;
import edu.hawaii.systemh.frontend.page.aquaponics.AquaponicsStatsSession;
import edu.hawaii.systemh.frontend.page.dashboard.DashboardSession;
import edu.hawaii.systemh.frontend.page.energy.EnergySession;
import edu.hawaii.systemh.frontend.page.help.HelpSession;
import edu.hawaii.systemh.frontend.page.hvac.HvacSession;
import edu.hawaii.systemh.frontend.page.lighting.LightingSession;

/**
 * Session that holds each user's state.
 * 
 * @author Kylan Hughes
 * @author Chuan Lun Hung
 * 
 */
public class SolarDecathlonSession extends WebSession {

  // Support serialization
  private static final long serialVersionUID = 1L;

  private Map<String, Integer> properties = new HashMap<String, Integer>();
  
  private HeaderSession headerSession = new HeaderSession();
  private AquaponicsSession aquaponicsSession = new AquaponicsSession();
  private AquaponicsStatsSession aquaponicsStatsSession = new AquaponicsStatsSession();
  private DashboardSession dashboardSession = new DashboardSession();
  private EnergySession energySession = new EnergySession();
  private HvacSession hvacSession = new HvacSession();
  private LightingSession lightingSession = new LightingSession();
  private HelpSession helpSession = new HelpSession();

  /**
   * Create default values for the application.
   * 
   * @param application This application.
   * @param request This request.
   */
  public SolarDecathlonSession(WebApplication application, Request request) {
    super(request);
    this.properties.put("EnergyGraph", 0);
    this.properties.put("ActivePage", 0);       
  }

  /**
   * Return the properties instance for this specific user.
   * 
   * @return The graph to display represented by an Integer.
   */
  public Map<String, Integer> getProperties() {
    return this.properties;
  }
  
  /**
   * Used to get the Header session associated with the user's session.
   * @return The HeaderSession
   */
  public HeaderSession getHeaderSession() {
    return this.headerSession;
  }
  
  /**
   * Used to get the Aquaponics session associated with the user's session.
   * @return The AquaponicsSession
   */
  public AquaponicsSession getAquaponicsSession() {
    return this.aquaponicsSession;
  }
  
  /**
   * Used to get the AquaponicsStats session associated with the user's session.
   * @return The AquaponicsStatsSession
   */
  public AquaponicsStatsSession getAquaponicsStatsSession() {
    return this.aquaponicsStatsSession;
  }
  
  /**
   * Used to get the Dashboard session associated with the user's session.
   * @return The DashboardSession
   */
  public DashboardSession getDashboardSession() {
    return this.dashboardSession;
  }
  
  /**
   * Used to get the Energy session associated with the user's session.
   * @return The EnergySession
   */
  public EnergySession getEnergySession() {
    return this.energySession;
  }
  
  /**
   * Used to get the Hvac session associated with the user's session.
   * @return The HvacSession
   */
  public HvacSession getHvacSession() {
    return this.hvacSession;
  }
  
  /**
   * Used to get the Lighting session associated with the user's session.
   * @return The HelpSession
   */
  public LightingSession getLightingSession() {
    return this.lightingSession;
  }
  
  /**
   * Used to get the Help session associated with the user's session.
   * @return The HelpSession.
   */
  public HelpSession getHelpSession() {
    return this.helpSession;
  }
 
}
