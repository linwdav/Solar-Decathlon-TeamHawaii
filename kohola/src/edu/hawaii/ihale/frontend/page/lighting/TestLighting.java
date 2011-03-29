package edu.hawaii.ihale.frontend.page.lighting;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;


import edu.hawaii.ihale.frontend.SolarDecathlonApplication;

/**
 * JUnit testing for Lighting page.
 * 
 * @author Anthony Kinsey
 */
public class TestLighting {
  
  /**
   * Performs JUnit tests on the Lighting page.
   */
@Test
  public void testPage() {
    // Start up the WicketTester and check that the page renders.
    WicketTester tester = new WicketTester(new SolarDecathlonApplication());
    tester.startPage(Lighting.class);
    tester.assertRenderedPage(Lighting.class);


    // The following line is useful for seeing what's on the page.
    tester.debugComponentTrees();

  }
}
