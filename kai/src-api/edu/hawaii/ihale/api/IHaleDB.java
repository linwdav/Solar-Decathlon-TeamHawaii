package edu.hawaii.ihale.api;

/**
 * Provides a specification of the operations that should be implemented by every iHaleDB.
 * @author Anthony Kinsey
 * @author Michael Cera
 */
public interface IHaleDB {
  
  /**
   * Returns the aquaponics instance associated with the timestamp, or null if not found.
   * @param timestamp The timestamp.
   * @return The aquaponics object, or null
   */
  public Aquaponics getAquaponics(long timestamp);
  
  /**
   * Store the passed Aquaponics Object in the database.
   * @param aquaponics The Aquaponics object to store. 
   */
  public void putAquaponics(Aquaponics aquaponics); 
  
  /**
   * Removes the Aquaponics instance with the specified timestamp.
   * @param timestamp The timestamp for the instance to be deleted.
   */
  public void deleteAquaponics(long timestamp);
  
  /**
   * Returns the HVAC instance associated with the timestamp, or null if not found.
   * @param timestamp The timestamp.
   * @return The HVAC object, or null
   */
  public HVAC getHvac(long timestamp);
  
  /**
   * Store the passed HVAC Object in the database.
   * @param hvac The HVAC object to store. 
   */
  public void putHvac(HVAC hvac); 
  
  /**
   * Removes the HVAC instance with the specified timestamp.
   * @param timestamp The timestamp for the instance to be deleted.
   */
  public void deleteHvac(long timestamp);
  
  /**
   * Returns the Lighting instance associated with the timestamp, or null if not found.
   * @param timestamp The timestamp.
   * @return The Lighting object, or null
   */
  public Lighting getLighting(long timestamp);
  
  /**
   * Store the passed Lighting Object in the database.
   * @param lighting The Lighting object to store. 
   */
  public void putLighting(Lighting lighting); 
  
  /**
   * Removes the Lighting instance with the specified timestamp.
   * @param timestamp The timestamp for the instance to be deleted.
   */
  public void deleteLighting(long timestamp);
  
  /**
   * Returns the Photovoltaics instance associated with the timestamp, or null if not found.
   * @param timestamp The timestamp.
   * @return The Photovoltaics object, or null
   */
  public Photovoltaics getPhotovoltaics(long timestamp);
  
  /**
   * Store the passed Photovoltaics Object in the database.
   * @param photovoltaics The Photovoltaics object to store. 
   */
  public void putPhotovoltaics(Photovoltaics photovoltaics); 
  
  /**
   * Removes the Photovoltaics instance with the specified timestamp.
   * @param timestamp The timestamp for the instance to be deleted.
   */
  public void deletePhotovoltaics(long timestamp);
  
  /**
   * Returns the Security instance associated with the timestamp, or null if not found.
   * @param timestamp The timestamp.
   * @return The Security object, or null
   */
  public Security getSecurity(long timestamp);
  
  /**
   * Store the passed Security Object in the database.
   * @param security The Security object to store. 
   */
  public void putSecurity(Security security); 
  
  /**
   * Removes the Security instance with the specified timestamp.
   * @param timestamp The timestamp for the instance to be deleted.
   */
  public void deleteSecurity(long timestamp);
  
  /**
   * Returns the Water instance associated with the timestamp, or null if not found.
   * @param timestamp The timestamp.
   * @return The Water object, or null
   */
  public Water getWater(long timestamp);
  
  /**
   * Store the passed Water Object in the database.
   * @param water The Water object to store. 
   */
  public void putWater(Water water); 
  
  /**
   * Removes the Water instance with the specified timestamp.
   * @param timestamp The timestamp for the instance to be deleted.
   */
  public void deleteWater(long timestamp);
}
