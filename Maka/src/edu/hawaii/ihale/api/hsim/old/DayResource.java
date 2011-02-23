package edu.hawaii.ihale.api.hsim;

import java.util.Calendar;
import java.util.Locale;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 * A server resource that will handle requests for the current day.
 * A new instance of this resource is created for each request. 
 * @author Philip Johnson
 */
public class DayResource extends ServerResource {
  
  /**
   * Returns the day as of the time this request was received. 
   * @return The day as a text string. 
   */
  @Get
  public String getDay() {
    Calendar currentDate = Calendar.getInstance(Locale.US);
    return String.valueOf(currentDate.get(Calendar.DAY_OF_MONTH));
  }

}
