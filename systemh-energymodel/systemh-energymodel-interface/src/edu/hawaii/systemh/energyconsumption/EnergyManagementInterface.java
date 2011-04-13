package edu.hawaii.systemh.energyconsumption;

/**
 * Contains abstract methods to allow the Energy Consumption Model
 * for System-H to interface with the chart generation techniques on
 * the frontend.
 * 
 * @author Kylan Hughes, Kevin Leong, Emerson Tabucol
 *
 */
public interface EnergyManagementInterface {

	// Represents the devices that will be tracked/monitored
	// for energy consumption purposes
	public enum EnergyDevice {
		REFRIDGERATOR,
		TELEVISION
	}
}
