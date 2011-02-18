package edu.hawaii.ihale.ui.page.settings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import edu.hawaii.ihale.ui.page.BasePanel;

/**
 * The User Privileges Page. Used for reference to create the new User privileges page.
 * 
 * @author Michael Cera
 */
@Deprecated
public class DeprecatedUserPrivilegesPage extends BasePanel {

  /** Support serialization. */
  private static final long serialVersionUID = 1L;

  /**
   * Creates the User Privileges Page.
   * 
   * @param id String
   */
  public DeprecatedUserPrivilegesPage(String id) {
    super(id);

    // Initialize our list of group instances.
    List<Group> groups = new ArrayList<Group>();
    groups.add(new Group("Administrator", true, true, true, true, true));
    groups.add(new Group("User", true, false, true, true, false));
    groups.add(new Group("Guest", false, false, true, true, false));

    // Create the dataview to display our list of group instances.
    DataView<Group> dataView =
        new DataView<Group>("GroupList", new ListDataProvider<Group>(groups)) {
          /** For serialization. */
          private static final long serialVersionUID = 1L;

          /**
           * Display each row in the table.
           * 
           * @param item A Group instance to be displayed.
           */
          public void populateItem(Item<Group> item) {
            Group group = item.getModelObject();
            item.add(new Label("GroupName", group.getName()));
            item.add(new Label("Privileges", group.printAquaponics() + group.printElectricity()
                + group.printHvac() + group.printligthing() + group.printWaterHeater()
                + "Add Privilege..."));
          }
        };

    // Add the dataview to the TablePage.
    add(dataView);
  }

  /**
   * Implements a row of data for the table.
   * 
   * @author Michael Cera
   */
  static class Group implements Serializable {
    /** Support serialization. */
    private static final long serialVersionUID = 2L;
    private String name;
    private boolean aquaponics;
    private boolean electricity;
    private boolean hvac;
    private boolean lighting;
    private boolean waterHeater;

    /**
     * Create a new group, given a name and privileges.
     * 
     * @param name The name.
     * @param aquaponics Privilege.
     * @param electricity Privilege.
     * @param hvac Privilege.
     * @param lighting Privilege.
     * @param waterHeater Privilege.
     * 
     */
    public Group(String name, boolean aquaponics, boolean electricity, boolean hvac,
        boolean lighting, boolean waterHeater) {
      this.name = name;
      this.aquaponics = aquaponics;
      this.electricity = electricity;
      this.hvac = hvac;
      this.lighting = lighting;
      this.waterHeater = waterHeater;
    }

    /**
     * Return the name.
     * 
     * @return The name of this group.
     */
    public String getName() {
      return name;
    }

    /**
     * Return privilege.
     * 
     * @return The privilege.
     */
    public String printAquaponics() {
      if (aquaponics) {
        return "Aquaponics, ";
      }
      else {
        return "";
      }
    }

    /**
     * Return privilege.
     * 
     * @return The privilege.
     */
    public String printElectricity() {
      if (electricity) {
        return "Electricity, ";
      }
      else {
        return "";
      }
    }

    /**
     * Return privilege.
     * 
     * @return The privilege.
     */
    public String printHvac() {
      if (hvac) {
        return "HVAC, ";
      }
      else {
        return "";
      }
    }

    /**
     * Return privilege.
     * 
     * @return The privilege.
     */
    public String printligthing() {
      if (lighting) {
        return "Lighting, ";
      }
      else {
        return "";
      }
    }

    /**
     * Return privilege.
     * 
     * @return The privilege.
     */
    public String printWaterHeater() {
      if (waterHeater) {
        return "Water Heater, ";
      }
      else {
        return "";
      }
    }
  }
}
