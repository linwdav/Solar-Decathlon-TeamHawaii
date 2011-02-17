package edu.hawaii.ihale.backend;

import java.util.Calendar;
import java.util.Locale;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 * Just to test a resource.
 *
 * @author Leonardo Nguyen
 * @version Java 1.6.0_21
 */
public class ResourceTesting extends ServerResource {

  /**
   * Returns the year as of the time this request was received. 
   * @return The year as a text string. 
   */
  @Get
  public String getYear() {
    Calendar currentDate = Calendar.getInstance(Locale.US);
    return String.valueOf(currentDate.get(Calendar.YEAR));
  }
}
