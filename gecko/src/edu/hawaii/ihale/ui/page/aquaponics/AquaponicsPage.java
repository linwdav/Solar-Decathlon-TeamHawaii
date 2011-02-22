package edu.hawaii.ihale.ui.page.aquaponics;

import java.util.ArrayList;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.model.Model;

import edu.hawaii.ihale.ui.AquaponicsListener;
import edu.hawaii.ihale.ui.page.BasePage;
import edu.hawaii.ihale.ui.page.home.HomePage;
import edu.hawaii.ihale.ui.page.status.OverAllStatus;
import edu.hawaii.ihale.ui.page.status.OverAllStatusPanel;

/**
 * Aquaponics page.
 * 
 * @author Team 1
 * @revised Shoji Bravo
 */
public class AquaponicsPage extends BasePage {

    /**
     * The pH levels from entry.
     */
    private static String pH = Double.toString(AquaponicsListener.getPH());
    /**
     * the water level from entry.
     */
    private static String waterLevel = Double.toString(AquaponicsListener.getWaterLevels());
    /**
     * the water temperature from entry.
     */
    private static String waterTemp = Double.toString(AquaponicsListener.getWaterTemp());
    /**
     * the air temperature from entry.
     */
    private static String airTemp = Double.toString(AquaponicsListener.getAirTemp());
    /**
     * the 
     */
    /** Support serialization. */
    private static final long serialVersionUID = 1L;

    /**
     * Creates the Aquaponics page.
     */
    public AquaponicsPage() {
        add(CSSPackageResource.getHeaderContribution(HomePage.class,
                "homePage.css"));

        ArrayList<OverAllStatus> list = new ArrayList<OverAllStatus>();
        
        
        
        list.add(new OverAllStatus("pH Levels", pH , AquaponicsPage.class));
        list.add(new OverAllStatus("Water Temperature", waterTemp ,AquaponicsPage.class));
        list.add(new OverAllStatus("Water Levels", waterLevel,
                AquaponicsPage.class));
        list.add(new OverAllStatus("Air Temperature", airTemp, AquaponicsPage.class));
        list.add(new OverAllStatus("Maintenance Schedule", "20 days",
                AquaponicsPage.class));
        

        add(new OverAllStatusPanel("AquaponicsPageStatus",
                new Model<String>("AquaponicsPageStatus"), list));
        
        add(new AquaponicsPageMain("AquaponicsPageMain",
                new Model<String>("AquaponicsPageMain")));
    }
}
