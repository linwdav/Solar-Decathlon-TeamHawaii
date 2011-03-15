package edu.hawaii.ihale.frontend.page;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.Model;

public class HeaderSession {
  
  private WebMarkupContainer dashboardItem;
  private WebMarkupContainer energyItem;
  private WebMarkupContainer aquaponicsItem;
  private WebMarkupContainer lightingItem;
  private WebMarkupContainer hvacItem;
  
  public HeaderSession() {
    //empty
  }

  /**
   * Get method for dashboard tab.
   * @return The dashboard container.
   */
  public WebMarkupContainer getDashboardItem() {
    return this.dashboardItem;
  }
  
  /**
   * Get method for energy tab.
   * @return The energy container.
   */
  public WebMarkupContainer getEnergyItem() {
    return this.energyItem;
  }
  
  /**
   * Get method for aquaponics tab.
   * @return The aquaponics container.
   */
  public WebMarkupContainer getAquaponicsItem() {
    return this.aquaponicsItem;
  }
  
  /**
   * Get method for lighting tab.
   * @return The lighting container.
   */
  public WebMarkupContainer getLightingItem() {
    return this.lightingItem;
  }
  
  /**
   * Get method for hvac tab.
   * @return The hvac container.
   */
  public WebMarkupContainer getHvacItem() {
    return this.hvacItem;
  }
  
  /**
   * Mutator method for dashboard tab.
   * @param wmc the value to set dashboard too.
   */
  public void setDashboardItem(WebMarkupContainer wmc) {
    this.dashboardItem = wmc;
  }
  
  /**
   * Mutator method for energy tab.
   * @param wmc the value to set energy too.
   */
  public void setEnergyItem(WebMarkupContainer wmc) {
    this.energyItem = wmc;
  }
  
  /**
   * Mutator method for aquaponics tab.
   * @param wmc the value to set aquaponics too.
   */
  public void setAquaponicsItem(WebMarkupContainer wmc) {
    this.aquaponicsItem = wmc;
  }
  
  /**
   * Mutator method for lighting tab.
   * @param wmc the value to set lighting too.
   */
  public void setLightingItem(WebMarkupContainer wmc) {
    this.lightingItem = wmc;
  }
  
  /**
   * Mutator method for hvac tab.
   * @param wmc the value to set hvac too.
   */
  public void setHvacItem(WebMarkupContainer wmc) {
    this.hvacItem = wmc;
  }
  
  public void setActiveTab(int i) {
    String classContainer = "class";
    String activeContainer = "active";

    System.out.println("tab to be active: " + i);
    switch (i) {

    case 1:
      energyItem
          .add(new AttributeModifier(classContainer, true, new Model<String>(activeContainer)));
      break;
    case 2:
      aquaponicsItem.add(new AttributeModifier(classContainer, true, new Model<String>(
          activeContainer)));
      break;
    case 3:
      lightingItem.add(new AttributeModifier(classContainer, true, new Model<String>(
          activeContainer)));
      break;
    case 4:
      hvacItem.add(new AttributeModifier(classContainer, true, new Model<String>(
          activeContainer)));
      break;
    // case 5:
    // securityItem.add(new AttributeModifier(classContainer, true, new Model<String>(
    // activeContainer)));
    // break;
    // case 6:
    // reportsItem.add(new AttributeModifier(classContainer, true,
    // new Model<String>(activeContainer)));
    // break;
    // case 7:
    // settingsItem.add(new AttributeModifier(classContainer, true, new Model<String>(
    // activeContainer)));
    // break;
    // case 8:
    // administratorItem.add(new AttributeModifier(classContainer, true, new Model<String>(
    // activeContainer)));
    // break;
    // case 9:
    // helpItem.add(new AttributeModifier(classContainer, true,
    // new Model<String>(activeContainer)));
    // break;
    case 0: // pass-through
    default:
      dashboardItem.add(new AttributeModifier(classContainer, true, new Model<String>(
          activeContainer)));
      break;
    }
  }
}
