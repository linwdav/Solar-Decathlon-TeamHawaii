package edu.hawaii.systemh.housemodel.misc;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.time.DateUtils;
import edu.hawaii.systemh.housemodel.Device;
import edu.hawaii.systemh.housemodel.EnergyConsumptionDictionary.EnergyConsumptionDevice;

/**
 * . . . 
 *
 * @author Leonardo Nguyen
 * @version Java 1.6.0_21
 */
public class MiscSystemTesting {

  /**
   * . . . 
   *
   * @param args . . .
   */
  public static void main(String args[]) { 
    MiscSystem MS = new MiscSystem();
    //double[] x = MS.getDeviceList().get(0).getEnergyArray();
    String refrigerator = "REFRIGERATOR";
    double[] x = MS.getDeviceMap().get(refrigerator).getEnergyConsumptionArray();
    System.out.println(x[1]);
    System.out.println(x[22]);
    MS.getDeviceMap().get(refrigerator).addHourEntry(22, 10000);
    x = MS.getDeviceMap().get(refrigerator).getEnergyConsumptionArray();
    System.out.println(x[1]);
    System.out.println(x[22]);
    
    System.out.println("MiscSystem has " + MS.getDeviceMap().size() + " devices.");
    for (int i = 0; i < MS.getDeviceMap().size(); i++) {
      //System.out.println(MS.getDeviceMap().get("REFRIGERATOR").getDeviceName());
    }
    
    Set<Map.Entry<String, Device>> set = MS.getDeviceMap().entrySet();
  
    for (Map.Entry<String, Device> mapEntry : set) {
      System.out.println(mapEntry.getKey());
    }
    // 2.0 days
    Long startTime = 1000L * 60L * 60L * 24L * 2L;
    // 5.5 days.
    Long endTime = 1000L * 60L * 60L * 24L * 5L + (1000L * 60L * 60L * 12L);
    Long oneDayInMillis = 1000L * 60L * 60L * 24L;
    Long lengthOfInterval = endTime - startTime;
    System.out.println("The length of interval is: " + lengthOfInterval); 
    int intervalInNumOfDays = (int) (lengthOfInterval / oneDayInMillis);
    System.out.println("The number of days is: " + intervalInNumOfDays);
    Long oneHourInMillis = 1000L * 60L * 60L;
    int intervalInNumOfHours = (int) (lengthOfInterval / oneHourInMillis);
    System.out.println("The number of hours is: " + intervalInNumOfHours);
    System.out.println("The remaining hours is: " + intervalInNumOfHours % 24);
    Calendar calendar = Calendar.getInstance();
    long minutes30Ago = 1000L * 60L * 30L;
    Date dateToSet30MinAgo = new Date();
    dateToSet30MinAgo.setTime(dateToSet30MinAgo.getTime() - minutes30Ago);
    calendar.setTime(dateToSet30MinAgo);
    System.out.println(dateToSet30MinAgo);
    System.out.println("The current hour of day is: " + calendar.get(Calendar.HOUR_OF_DAY));

    calendar.setTime(DateUtils.truncate(new Date(dateToSet30MinAgo.getTime()), 
        Calendar.HOUR_OF_DAY));
    System.out.println("Truncated hour is: " + calendar.getTime());
    
    dateToSet30MinAgo.setTime(dateToSet30MinAgo.getTime() + (minutes30Ago + minutes30Ago));
    calendar.setTime(dateToSet30MinAgo);
    System.out.println(dateToSet30MinAgo);
    System.out.println("The current hour of day is: " + calendar.get(Calendar.HOUR_OF_DAY));

    // 24th hour of the day is 0 not 24, 11:01 PM is 23.
    
    String keyName = "REFRIGERATOR";
    System.out.println(EnergyConsumptionDevice.valueOf(keyName));
    
    
    
    
    
    
    
    
    int currentHour = 23;
    int hoursProcessed = 0;
    intervalInNumOfHours = 100;
    for (int i = currentHour; hoursProcessed < intervalInNumOfHours; i++) {
      System.out.println("The current hour of the day is: " + i);
      if (i == 23) {
        i = -1;
      }
      System.out.println("Hours processed is: " + hoursProcessed);
      hoursProcessed++;
    }
  }
}
