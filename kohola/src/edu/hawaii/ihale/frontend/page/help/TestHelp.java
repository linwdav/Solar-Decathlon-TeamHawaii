package edu.hawaii.ihale.frontend.page.help;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;


import edu.hawaii.ihale.frontend.SolarDecathlonApplication;

/**
 * JUnit testing for Help page.
 * 
 * @author Anthony Kinsey
 */
public class TestHelp {
  
  /**
   * Performs JUnit tests on the Help page.
   */
@Test
  public void testPage() {
    // Start up the WicketTester and check that the page renders.
    WicketTester tester = new WicketTester(new SolarDecathlonApplication());
    tester.startPage(Help.class);
    tester.assertRenderedPage(Help.class);


    // The following line is useful for seeing what's on the page.
    tester.debugComponentTrees();

  }
}
