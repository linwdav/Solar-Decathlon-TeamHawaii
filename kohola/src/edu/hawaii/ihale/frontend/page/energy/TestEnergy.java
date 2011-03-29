package edu.hawaii.ihale.frontend.page.energy;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;


import edu.hawaii.ihale.frontend.SolarDecathlonApplication;

/**
 * JUnit testing for Energy page.
 * 
 * @author Anthony Kinsey
 */
public class TestEnergy {
  
  /**
   * Performs JUnit tests on the Energy page.
   */
@Test
  public void testPage() {
    // Start up the WicketTester and check that the page renders.
    WicketTester tester = new WicketTester(new SolarDecathlonApplication());
    tester.startPage(Energy.class);
    tester.assertRenderedPage(Energy.class);


    // The following line is useful for seeing what's on the page.
    tester.debugComponentTrees();

  }
}
