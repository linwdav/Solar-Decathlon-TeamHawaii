package edu.hawaii.ihale.backend;

import org.junit.Test;

/**
 * Tests the data gathering Thread methods.
 * 
 * @author Team Naia
 *
 */
public class TestDataGatheringThread {

  /**
   * Test run of the Data Thread.
   */
  @Test
  public void testRun() {
    
    // Boolean to turn off main thread
    boolean runMain = true;
    
    // Create Thread Object.  Parameter is how long to wait between
    // requests for data.
    DataGatheringThread dataGathering = new DataGatheringThread(10000);
    
    // Create Thread
    Thread dataGatheringThread = new Thread(dataGathering);
    
    // Start Thread
    dataGatheringThread.start();
    
    long timeStart = System.currentTimeMillis();
    
    while (runMain) {
      System.out.println("Main Process Running");
      
      // Wait for 5 seconds
      long t0, t1;
      t0 = System.currentTimeMillis();
      do {
        t1 = System.currentTimeMillis();
      } while (t1 - t0 < 5000);
      
      if (t1 - timeStart > 50000) {
        dataGathering.done();
        runMain = false;
      }
    } // End while
  } // End testRun method
} // End Test Class
