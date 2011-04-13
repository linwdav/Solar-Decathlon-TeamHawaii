package edu.hawaii.systemh.energymodel.chartinterface;

import java.util.List;

import edu.hawaii.ihale.api.ApiDictionary.IHaleSystem;
import edu.hawaii.ihale.api.repository.TimestampDoublePair;

/**
 * Contains abstract methods to allow the Energy Consumption Model
 * for System-H to interface with the chart generation thru Wicket on
 * the frontend.
 * 
 * @author Kylan Hughes, Kevin Leong, Emerson Tabucol
 *
 */
public interface EnergyManagementChartInterface {
	
	// Represents the devices that will be tracked/monitored
	// for energy consumption purposes
	// NOTE: THIS IS NOT A COMPREHENSIVE LIST
	public enum EnergyConsumptionDevice {
		
		// Appliances
		REFRIDGERATOR,
		FREEZER,
		DISH_WASHER,
		WASHER_DRYER,
		
		// Entertainment
		STEREO_SYSTEM,
		TELEVISION,
		
		// House devices/systems
		VENTILATION_FAN,
		SOLAR_THERMAL_CONTROLLER,
		
		// For all other minor/small devices
		MISC
	} // End Enumerated Type
	
	/**
	 * Returns the current load for the device specified.
	 * 
	 * @param deviceName The device we want to query for.
	 * @return The current load of the device.
	 */
	public double getDeviceCurrentLoad(EnergyConsumptionDevice deviceName);
	
	/**
	 * Returns the current load for the system specified.
	 * Example systems include: HVAC, Lighting, Aquaponics, 
	 * 							Electric, Photovoltaics, Misc
	 * 
	 * @param system The system to query for.
	 * @return The current load of the system.
	 */
	public double getSystemCurrentLoad(IHaleSystem system);
	
	/**
	 * Returns a list of data about energy consumption about a particular
	 * device from a specified time up until now.
	 * 
	 * @param device The device to request data for.
	 * @param startTime The time to start getting data.
	 * @return A list of all data requested.
	 */
	public List<TimestampDoublePair> getDeviceLoadSince 
						(EnergyConsumptionDevice device, Long startTime);
	
	/**
	 * Returns a list of data about energy consumption about a particular
	 * device for the specified time interval.
	 * 
	 * @param device The device to request data for.
	 * @param startTime The time to start getting data.
	 * @param endTime The time to end getting data.
	 * @return A list of all data requested.
	 */
	public List<TimestampDoublePair> getDeviceLoadDuringInterval
			(EnergyConsumptionDevice device, Long startTime, Long endTime);
	
	/**
	 * Returns a list of data about energy consumption about a particular
	 * system from a specified time up until now.
	 * 
	 * @param system The system to request data for.
	 * @param startTime The time to start getting data.
	 * @return A list of all data requested.
	 */
	public List<TimestampDoublePair> getSystemLoadSince
									(IHaleSystem system, Long startTime);
	
	/**
	 * Returns a list of data about energy consumption about a particular
	 * system for the specified time interval.
	 * 
	 * @param system The system to request data for.
	 * @param startTime The time to start getting data.
	 * @param endTime The time to end getting data.
	 * @return A list of all data requested.
	 */
	public List<TimestampDoublePair> getSystemLoadDuringInterval
						(IHaleSystem system, Long startTime, Long endTime);
	
	
} // End Energy Management Interface interface
