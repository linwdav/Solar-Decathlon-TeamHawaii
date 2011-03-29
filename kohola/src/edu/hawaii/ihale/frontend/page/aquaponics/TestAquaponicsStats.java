package edu.hawaii.ihale.frontend.page.aquaponics;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;


import edu.hawaii.ihale.frontend.SolarDecathlonApplication;

/**
 * JUnit testing for AquaponicsStats page.
 * 
 * @author Anthony Kinsey
 */
public class TestAquaponicsStats {
  
  /**
   * Performs JUnit tests on the AquaponicsStats page.
   */
@Test
  public void testFormPage() {
    // Start up the WicketTester and check that the page renders.
    WicketTester tester = new WicketTester(new SolarDecathlonApplication());
    tester.startPage(AquaponicsStats.class);
    tester.assertRenderedPage(AquaponicsStats.class);


    // The following line is useful for seeing what's on the page.
    tester.debugComponentTrees();

  }
}
