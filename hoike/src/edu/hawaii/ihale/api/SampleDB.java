package edu.hawaii.ihale.api;

import java.util.ArrayList;



/**
 * Provides a specification of the operations that should be implemented by every SampleDB.
 * @author Nathan Dorman
 * @author David Lin
 * @author Leonardo Nguyen
 */
public interface SampleDB {
  
  /**
   * Returns the Sample instance associated with uniqueTime and systemID, 
   * or null if not found.
   * 
   * @param uniqueTime The uniqueTime.
   * @param systemID The system associated with the time.
   * @return The sample, or null.
   */
  public Sample getSample(long uniqueTime, String systemID);
  
  /**
   * Return all Samples in a ArrayList.
   * 
   * @return All Samples.
   */
  public ArrayList<Sample> getAllSamples();
  
  /**
   * Return all Samples that share a common system name.
   * 
   * @param systemID The system to retrieve samples for.
   * @return All Samples sharing the same system name.
   */
  public ArrayList<Sample> getSamplesByName(String systemID);
  
  /**
   * Returns all samples in the specified range.
   * 
   * @param start The Start time.
   * @param end The end time.
   * @return All Samples in the specified range.
   */
  public ArrayList<Sample> getSamplesByRange(long start, long end);
  
  /**
   * Returns all samples in the specified range with designated system name.
   * 
   * @param start The start time.
   * @param end The end time.
   * @param systemID The designated system name.
   * @return
   */
  public ArrayList<Sample> getSamplesByRange(long start, long end, String systemID);
  
  /**
   * Store the passed Sample in the database.
   * @param sample The sample to store. 
   */
  public void putSample(Sample sample);
  
  /**
   * Removes the Sample instance with the specified time and system ID.
   * @param uniqueTime The unique time for the instance to be deleted.
   * @param systemID The system ID for the instance to be deleted.
   */
  public void deleteSample(long uniqueTime, String systemID);

}
