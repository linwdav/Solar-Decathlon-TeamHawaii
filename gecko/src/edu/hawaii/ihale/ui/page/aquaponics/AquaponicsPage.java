package edu.hawaii.ihale.ui.page.aquaponics;

import java.util.ArrayList;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.model.Model;
import edu.hawaii.ihale.ui.page.BasePage;
import edu.hawaii.ihale.ui.page.home.HomePage;
import edu.hawaii.ihale.ui.page.status.OverAllStatus;
import edu.hawaii.ihale.ui.page.status.OverAllStatusPanel;

/**
 * Aquaponics page.
 * 
 * @author Team 1
 */
public class AquaponicsPage extends BasePage {

    /** Support serialization. */
    private static final long serialVersionUID = 1L;

    /**
     * Creates the Aquaponics page.
     */
    public AquaponicsPage() {
        add(CSSPackageResource.getHeaderContribution(HomePage.class,
                "homePage.css"));

        ArrayList<OverAllStatus> list = new ArrayList<OverAllStatus>();
        
        list.add(new OverAllStatus("pH Levels", "7.5", AquaponicsPage.class));
        list.add(new OverAllStatus("Water Temperature", "65 F",AquaponicsPage.class));
        list.add(new OverAllStatus("Water Levels", "120 gal",
                AquaponicsPage.class));
        list.add(new OverAllStatus("Maintenance Schedule", "20 days",
                AquaponicsPage.class));

        add(new OverAllStatusPanel("AquaponicsPageStatus",
                new Model<String>("AquaponicsPageStatus"), list));
        
        add(new AquaponicsPageMain("AquaponicsPageMain",
                new Model<String>("AquaponicsPageMain")));
    }
}
