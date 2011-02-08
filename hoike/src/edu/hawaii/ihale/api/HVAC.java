package edu.hawaii.ihale.api;

/**
 * ADT to store information about the HVAC system.
 * 
 * @author Nathan Dorman
 * @author David Lin
 * @author Leonardo Nguyen
 */
public class HVAC {

  /** Fields for the system. */
  private float livingRoomHumidity;
  private float livingRoomTemperature;
  private float kitchenHumidity;
  private float kitchenTemperature;
  private float bathroomHumidity;
  private float bathroomTemperature;
  private float naturalSpaceHumidity;
  private float naturalSpaceTemperature;
  private float extraRoomHumidity;
  private float extraRoomTemperature;
  private long lastUpdated;
  
  /**
   * Default constructor.
   * 
   * @param livingRoomHumidity The living room's relative humidity.
   * @param livingRoomTemperature The living room's temperature in Fahrenheit.
   * @param kitchenHumidity The kitchen's relative humidity.
   * @param kitchenTemperature The kitchen's room temperature in Fahrenheit.
   * @param bathroomHumidity The bathroom's relative humidity.
   * @param bathroomTemperature The bathroom's room temperature in Fahrenheit.
   * @param naturalSpaceHumidity The natural space section of the home's relative humidity.
   * @param naturalSpaceTemperature The natural space section of the home's room temperature in 
   *                                Fahrenheit.
   * @param extraRoomHumidity The extra room within the home's relative humidity.
   * @param extraRoomTemperature The extra room within the home's room temperature in Fahrenheit.
   * @param lastUpdated The timestamp for the last update.
   */
  public HVAC(float livingRoomHumidity, float livingRoomTemperature, float kitchenHumidity, 
      float kitchenTemperature, float bathroomHumidity, float bathroomTemperature, 
      float naturalSpaceHumidity, float naturalSpaceTemperature, float extraRoomHumidity, 
      float extraRoomTemperature, long lastUpdated) {
    
    this.livingRoomHumidity = livingRoomHumidity;
    this.livingRoomTemperature = livingRoomTemperature;
    this.kitchenHumidity = kitchenHumidity;
    this.kitchenTemperature = kitchenTemperature;
    this.bathroomHumidity = bathroomHumidity;
    this.bathroomTemperature = bathroomTemperature;
    this.kitchenHumidity = kitchenHumidity;
    this.kitchenTemperature = kitchenTemperature;
    this.naturalSpaceHumidity = naturalSpaceHumidity;
    this.naturalSpaceTemperature = naturalSpaceTemperature;
    this.extraRoomHumidity = extraRoomHumidity;
    this.extraRoomTemperature = extraRoomTemperature;
    this.lastUpdated = lastUpdated;
  }

  /**
   * Returns the living room's relative humidity.
   * 
   * @return The living room's relative humidity.
   */
  public float getlivingRoomHumidity() {
    return livingRoomHumidity;
  }

  /**
   * Returns the living room's relative temperature.
   * 
   * @return The living room's relative humidity.
   */
  public float getlivingRoomTemperature() {
    return livingRoomTemperature;
  }

  /**
   * Returns the kitchen's relative humidity.
   * 
   * @return The kitchen's relative humidity.
   */
  public float getkitchenHumidity() {
    return kitchenHumidity;
  }

  /**
   * Returns The kitchen's room temperature.
   * 
   * @return The kitchen's room temperature.
   */
  public float getkitchenTemperature() {
    return kitchenTemperature;
  }
  
  /**
   * Returns the bathroom's relative humidity.
   *
   * @return The bathroom's relative humidity.
   */
  public float getBathroomHumidity() {
    return bathroomHumidity;
  }
  
  /**
   * Returns the bathroom's room temperature.
   * 
   * @return The bathroom's room temperature.
   */
  public float getBathroomTemperature() {
    return bathroomTemperature;
  }
  
  /**
   * Returns the natural space section of the home's relative humidity.
   * 
   * @return The natural space section of the home's relative humidity.
   */
  public float getNaturalSpaceHumidity() {
    return naturalSpaceHumidity;
  }
  
  /**
   * Returns the natural space section of the home's room temperature.
   * 
   * @return The natural space section of the home's room temperature.
   */
  public float getNaturalSpaceTemperature() {
    return naturalSpaceTemperature;
  }
  
  /**
   * Returns the extra room within the home's relative humidity.
   *
   * @return The extra room within the home's relative humidity.
   */
  public float getExtraRoomHumidty() {
    return extraRoomHumidity;
  }
  
  /**
   * Returns the extra room within the home's room temperature.
   * 
   * @return The extra room within the home's room temperature.
   */
  public float getExtraRoomTemperature() {
    return extraRoomTemperature;
  }
  
  /**
   * Return the timestamp for the last update.
   * 
   * @return the lastUpdated
   */
  public long getLastUpdated() {
    return lastUpdated;
  }
}
