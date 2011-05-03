package edu.hawaii.systemh.model.behavior;
 
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

/**
 * A Class that provides the high and low temperatures for each 
 * day for the past 3 months (at least 31 days). 
 * @author Greg Burgess
 *
 */
public class DailyTemperatureHistory {

  private static TempPair[][] temps = new TempPair[12][32]; 
  private static final String urlStart = "http://www.wunderground.com/history/airport/KDCA/";
  private static final String urlEnding = 
                        "/1/MonthlyHistory.html?req_city=NA&req_state=NA&req_statename=NA";
   

  /**
   * Makes initializing calls for each of the three months of history.
   * @param year the year of the current date.
   * @param month the month of the current date.
   * @throws Exception if the url cannot be reached, or is invalid.
   */
  public DailyTemperatureHistory(int year, int month) throws Exception {
    int yrs = year;
    int mths = month;
    for (int i = 0; i < 3; i ++) {  
    //handle a rollback in years
      if (mths - (i ) == 0) {
        yrs --;
        mths = 13;
      }
      init(yrs,mths - i);  
      
    } 
  }
  
  /**
   * Initalizes data, and populates the array with data from WeatherUndergound.
   * @param year the year of the current date.
   * @param month the month of the current date.
   * @throws ParserException If the data is invalid, or site cannot be reached.
   */
  private static final void init(int year, int month) throws ParserException {
    String url = urlStart + year + "/" + month + urlEnding;
    System.out.println(url);
    //Create a parser with the URL 
    Parser parse = new Parser(url); 
    
    //grab all the table elements
    TagNameFilter tag = new TagNameFilter("table"); 
    NodeList nodes = parse.parse(tag); 
    Node root = null;

    //search for the calendar table
    for (int i = 0; i < nodes.size(); i ++) {
      if (nodes.elementAt(i).toString().contains("id=\"calTable\"")) {
        root = nodes.elementAt(i);  
    }
  }
   
  //HTML Parse gets messy when you do multiple parses on a document,
  //so just do it manually.
  nodes = root.getChildren(); 
  //cast the nodes back to a string
  String html = nodes.toHtml();
  String[] lines = html.split("\n");
  int day = 0;
  //search for the "Actual" temperature entries.
  for (int i = 0; i < lines.length; i ++) {
    if (lines[i].contains("Actual:")) {
      day ++;
      store(lines,i,day,month);
      //skip a few lines to save time
      i += 3;
    }
  } 
}

  
  /**
   * Stores the data retrieved from WeatherUndergound.
   * @param nodes An array containing lines of code split on newline chars.
   * @param pos position in the array to start from.
   * @param day the day of the month for the day in question.
   * @param month the month of the day in question.
   */
private static void store(String[] nodes, int pos, int day, int month) {
  //parse out the data from the strings
  String temp = nodes[pos + 2]; 
  temp = temp.replaceAll(" ", "");
  temp = temp.replaceAll("\n", "");
  try {
    int high = Integer.parseInt(temp); 
    temp = nodes[pos + 4]; 
    temp = temp.replaceAll(" ", "");
    temp = temp.replaceAll("\n", "");
    
    int low = Integer.parseInt(temp);
    TempPair tempPair = new TempPair(day,low,high);
    temps[month - 1][day - 1] = tempPair;
    //System.out.println("Added " +tempPair + " to [" + (month-1) +"][" + (day-1));
  }
  catch (Exception e) {
    e.printStackTrace();
    System.err.println("Error in reading file");
  }
  }

/**
 * Returns the TempPair for a given day.
 * @param month The month of the day specified.
 * @param day The day of the month of the day specified.
 * @return A TempPair representing the high and low for the day.
 */
public TempPair getTemp(int month, int day) { 
  try {
    return temps [month - 1][day - 1];
  }
  catch (Exception e) {
    return null;
  } 
}
}
