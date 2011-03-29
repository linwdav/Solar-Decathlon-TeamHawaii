package edu.hawaii.ihale.frontend.page.aquaponics;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;


import edu.hawaii.ihale.frontend.SolarDecathlonApplication;

/**
 * JUnit testing for AquaPonics page.
 * 
 * @author Anthony Kinsey
 */
public class TestAquaPonics {
  
  /**
   * Performs JUnit tests on the AquaPonics page.
   */
@Test
  public void testFormPage() {
    // Start up the WicketTester and check that the page renders.
    WicketTester tester = new WicketTester(new SolarDecathlonApplication());
    tester.startPage(AquaPonics.class);
    tester.assertRenderedPage(AquaPonics.class);


    // The following line is useful for seeing what's on the page.
    tester.debugComponentTrees();

  }
}
