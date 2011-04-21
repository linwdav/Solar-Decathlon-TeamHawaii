package edu.hawaii.systemh.energymodel.chartinterface;

import java.util.List;
import edu.hawaii.systemh.api.ApiDictionary.SystemHSystem;
import edu.hawaii.systemh.api.repository.TimestampDoublePair;
import edu.hawaii.systemh.energymodel.EnergyConsumptionDictionary.EnergyConsumptionDevice;

/**
 * Contains abstract methods to allow the Energy Consumption Model for System-H to interface with
 * the chart generation thru Wicket on the frontend.
 * 
 * @author Kylan Hughes, Kevin Leong, Emerson Tabucol
 * 
 */
public interface EnergyManagementChartInterface {

  /**
   * Returns the current load for the device specified.
   * 
   * @param deviceName The device we want to query for.
   * @return The current load of the device.
   */
  public double getDeviceCurrentLoad(EnergyConsumptionDevice deviceName);

  /**
   * Returns the current load for the system specified. Example systems include: HVAC, Lighting,
   * Aquaponics, Electric, Photovoltaics, Misc
   * 
   * @param system The system to query for.
   * @return The current load of the system.
   */
  public double getSystemCurrentLoad(SystemHSystem system);

  /**
   * Returns a list of data about energy consumption about a particular device for the specified
   * time interval.
   * 
   * @param device The device to request data for.
   * @param startTime The time to start getting data.
   * @param endTime The time to end getting data.
   * @return A list of all data requested.
   */
  public List<TimestampDoublePair> getDeviceLoadDuringInterval(EnergyConsumptionDevice device,
      Long startTime, Long endTime);

  /**
   * Returns a list of data about energy consumption about a particular system for the specified
   * time interval.
   * 
   * @param system The system to request data for.
   * @param startTime The time to start getting data.
   * @param endTime The time to end getting data.
   * @return A list of all data requested.
   */
  public List<TimestampDoublePair> getSystemLoadDuringInterval(SystemHSystem system, Long startTime,
      Long endTime);

  /**
   * Returns the amount of power consumed during a specified interval.
   * 
   * @param startTime The time to start getting data.
   * @param endTime The time to end getting data.
   * @return The power consumed.
   */
  public double powerConsumed(Long startTime, Long endTime);

  /**
   * Returns the amount of power generated during a specified interval.
   * 
   * @param startTime The time to start getting data.
   * @param endTime The time to end getting data.
   * @return The power generated.
   */
  public double powerGenerated(Long startTime, Long endTime);

} // End Energy Management Interface interface
