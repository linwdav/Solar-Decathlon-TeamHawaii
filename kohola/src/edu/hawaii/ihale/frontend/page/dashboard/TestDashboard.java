package edu.hawaii.ihale.frontend.page.dashboard;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;


import edu.hawaii.ihale.frontend.SolarDecathlonApplication;

/**
 * JUnit testing for Dashboard page.
 * 
 * @author Anthony Kinsey
 */
public class TestDashboard {
  
  /**
   * Performs JUnit tests on the Dashboard page.
   */
@Test
  public void testPage() {
    // Start up the WicketTester and check that the page renders.
    WicketTester tester = new WicketTester(new SolarDecathlonApplication());
    tester.startPage(Dashboard.class);
    tester.assertRenderedPage(Dashboard.class);


    // The following line is useful for seeing what's on the page.
    tester.debugComponentTrees();

  }
}
