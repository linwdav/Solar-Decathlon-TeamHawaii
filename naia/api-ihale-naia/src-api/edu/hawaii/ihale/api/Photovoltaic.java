package edu.hawaii.ihale.api;

import java.text.DateFormat;
import java.util.Date;

/**
 * Describes the photovoltaic system
 * 
 * @author Team Naia
 */
public class Photovoltaic {

  // Time the reading was taken
  private long timestamp;
  // The energy reading in kWh
  private float energy;
  // The energy reading in Ws
  private float energyWS;
  // The average power
  private float power;
  // The frequency in Hertz
  private float frequency;
  // RMS in volts
  private float voltage;
  // RMS in amperes
  private float current;
  // The current value of power component source (eg PV, Grid)
  private float cpower;

  /**
   * Creates the Photovoltaic instance from a given set of inputs
   * 
   * @param timestamp The time for the reading
   * @param energy The average energy reading in kWh 
   * @param energyWS The average energy reading in kWh
   * @param power The average power
   * @param frequency The frequency reading in Hz
   * @param voltage The voltage reading
   * @param current The current in Amp
   * @param cpower The component source
   */
  public Photovoltaic(long timestamp, float energy, float energyWS, float power, float frequency, float voltage, float current, float cpower) {
    this.timestamp = timestamp;
    this.energy = energy;
    this.energyWS = energyWS;
    this.power = power;
    this.frequency = frequency;
    this.voltage = voltage;
    this.current = current;
    this.cpower = cpower;
   
  }

  /**
   * Get the time that this data set was taken. (Unique ID)
   * 
   * @return The time that the reading was taken.
   */
  long getTimestamp() {
    return this.timestamp;
  }
  
  /**
   * Get the energy reading in kWh.
   *
   * @return energy in kHw.
   */
  float getEnergy() {
    return this.energy;
  }
  
  /**
   * Get the energy reading in Ws.
   * 
   * @return energy in Ws.
   */
  float getEnergyWS() {
    return this.power;
  }
  
  /**
   * Get the average power.
   * 
   * @return average power.
   */
  float getPower() {
    return this.power;
  }
  
  /**
   * Get the frequency reading in Hz.
   * 
   * @return frequency in Hz.
   */
  float getFrequency() {
    return this.frequency;
  }
  
  /**
   * Get the voltage reading in volts
   * 
   * @return voltage.
   */
  float getVoltage() {
    return this.voltage;
  }
  
  
  /**
   * Get the current in amperes.
   * 
   * @return current.
   */
  float getCurrent() {
    return this.current;
  }
  
  /**
   * Gets the name for the component source.
   * 
   * @return source name.
   */
  float getCPower() {
    return this.cpower;
  }

  /**
   * Return this Aquaponics object as a formatted string.
   * 
   * @return The data set for the Aquaponics object as a string.
   */
  @Override
  public String toString() {
    
    // Date formatting variables
    Date date = new Date(this.timestamp);
    DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG);
    
    return String.format("[Time: %s Energy (kWh): %d Energy (Ws): %d Power: %d Frquency (Hz): %d Voltage (v): %d Current (Amp): %d Source: %d]", dateFormat.format(date), 
        this.energy, this.energyWS,this.power, this.frequency, this.voltage, this.current, this.cpower );
        
  }
} // End Aquaponics class
