package edu.hawaii.ihale.backend;

/**
 * 
 * @author Team Naia
 */
public class DataGatheringThread implements Runnable {
  
  // Delay between data fetching in milliseconds
  long delay;
  
  // Boolean value to stop thread
  boolean done;
  
  /**
   * Constructor.  Parameter tells the thread how long to wait
   * in between requests for data.
   * 
   * @param delay The time between data requests.
   */
  public DataGatheringThread(long delay) {
    this.delay = delay;
    this.done = false;
  }
  
  /**
   * Method to stop the thread from outside
   * the object.
   */
  public void done() {
    this.done = true;
  } // End done
  
  /**
   * Infinite loop to gather data from devices.
   */
  @Override
  public void run() {
    while (!done) {
      SimulatorInterface.readFromDevices();
      
      try {
        Thread.sleep(delay);
      }
      catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      
    } // End infinite while loop
  } // End run method

} // End data gathering thread
