package edu.hawaii.ihale.frontend.page.aquaponics;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;


import edu.hawaii.ihale.frontend.SolarDecathlonApplication;

/**
 * Example test showing a simple way to test a form.
 * 
 * @author Philip Johnson
 */
public class TestAquaPonics {
  
  /**
   * Tests the form page.
   */
@Test
  public void testFormPage() {
    // Start up the WicketTester and check that the page renders.
    WicketTester tester = new WicketTester(new SolarDecathlonApplication());
    tester.startPage(AquaPonics.class);
    tester.assertRenderedPage(AquaPonics.class);


    // The following line is useful for seeing what's on the page.
    tester.debugComponentTrees();

    //assertEquals("Testing that submitted form's value showed up", 
    //    "Retrieved Contact: ajk, anthony, kinsey, student, " + timestamp, resource);

  }
}
