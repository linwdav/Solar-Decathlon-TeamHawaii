package edu.hawaii.ihale.frontend.page.hvac;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;


import edu.hawaii.ihale.frontend.SolarDecathlonApplication;

/**
 * JUnit testing for Hvac page.
 * 
 * @author Anthony Kinsey
 */
public class TestHvac {
  
  /**
   * Performs JUnit tests on the Hvac page.
   */
@Test
  public void testPage() {
    // Start up the WicketTester and check that the page renders.
    WicketTester tester = new WicketTester(new SolarDecathlonApplication());
    tester.startPage(Hvac.class);
    tester.assertRenderedPage(Hvac.class);


    // The following line is useful for seeing what's on the page.
    tester.debugComponentTrees();

  }
}
