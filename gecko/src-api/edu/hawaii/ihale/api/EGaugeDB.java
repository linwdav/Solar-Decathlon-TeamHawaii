package edu.hawaii.ihale.api;

/**
 * Interface that describes the database interface for each iHale system.
 * 
 * @author Team Hawaii
 */
public interface EGaugeDB {
  
  /**
   * Get the instance of this EGauge by Primary Key ID.
   *  
   * @param id String
   * @return EGauge
   */
  public EGauge getById(String id);

  /**
   * Get the instance of this EGauge by Secondary Key TimeStamp.
   *  
   * @param timestamp long
   * @return EGauge
   */
  public EGauge getByTimeStamp(long timestamp);
}
