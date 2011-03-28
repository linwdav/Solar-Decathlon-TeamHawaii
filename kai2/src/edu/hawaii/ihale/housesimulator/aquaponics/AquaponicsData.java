package edu.hawaii.ihale.housesimulator.aquaponics;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.Random;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.restlet.ext.xml.DomRepresentation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Provides data on the Aquaponics system, as well as an XML representation.
 * 
 * @author Anthony Kinsey, Michael Cera
 * @author Christopher Ramelb, David Lin, Leonardo Nguyen, Nathan Dorman
 */
public class AquaponicsData {

  /** Random generator. */
  private static final Random randomGenerator = new Random();

  // API items that are required.
  /** The current water circulation. */
  private static double circulation = (randomGenerator.nextDouble() * 10) + 20;
  /** The current number of dead fish. */
  private static int dead_fish = (randomGenerator.nextInt(5));
  /** The current electrical conductivity. */
  private static double electrical_conductivity = (randomGenerator.nextDouble()) + 1;
  /** The current temperature. */
  private static int temperature = randomGenerator.nextInt(11) + 60;
  /** The current turbidity. */
  private static double turbidity = (randomGenerator.nextDouble() * 15) + 15;
  /** The current water level. */
  private static int water_level = (randomGenerator.nextInt(10)) + 200;
  /** The current pH. */
  private static double ph = (randomGenerator.nextDouble() * 3) + 6.5;
  /** The current dissolved oxygen. */
  private static double oxygen = (randomGenerator.nextDouble() * 9) + 1;

  // Desired values for required API items.
  /** The desired water circulation. */
  private static double desiredCirculation = (randomGenerator.nextDouble() * 10) + 20;
  /** The desired electrical conductivity. */
  private static double desiredConductivity = (randomGenerator.nextDouble()) + 1;
  /** The desired temperature. */
  private static int desiredTemperature = randomGenerator.nextInt(11) + 60;
  /** The desired turbidity. */
  private static double desiredTurbidity = (randomGenerator.nextDouble() * 15) + 15;
  /** The desired water level. */
  private static int desiredWaterLevel = ((randomGenerator.nextInt(10)) + 200);
  /** The desired pH. */
  private static double desiredPh = (randomGenerator.nextDouble() * 3) + 6.5;

  /** The max value temperature will increment by. */
  private static final long temperatureIncrement = 1;
  /** The max value pH will increment by. */
  private static double phIncrement = 0.2;

  // Items not in API. These values are useful for modeling the system.
  /** The number of fish in the tank. */
  private static int num_fish = 10;
  /** The amount of fish feed in the tank. */
  private static double fish_feed = randomGenerator.nextDouble() + (0.9 * num_fish);
  /** The amount of nutrients in the tank. Related to turbidity. */
  private static double nutrients = (randomGenerator.nextDouble() * 15) + 15;

  // Desired value for items not in API.
  private static double desiredNutrients = (randomGenerator.nextDouble() * 15) + 15;

  // /** The desired oxygen level. Dependent on the number of fish. */
  // private static double desiredOxygen = num_fish;

  /**
   * Modifies the state of the system.
   */
  public static void modifySystemState() {

    // Increments temperature within range of the desired temperature.
    if (temperature > (desiredTemperature - temperatureIncrement)
        && temperature < (desiredTemperature + temperatureIncrement)) {
      temperature +=
          randomGenerator.nextInt(((int) temperatureIncrement * 2) + 1) - temperatureIncrement;
    }
    else if (temperature < desiredTemperature) {
      temperature += randomGenerator.nextInt((int) temperatureIncrement + 1);
    }
    else {
      temperature -= (randomGenerator.nextInt((int) temperatureIncrement + 1));
    }

    // Increments pH within range of the desired pH.
    if (ph > (desiredPh - phIncrement) && ph < (desiredPh + phIncrement)) {
      ph += (randomGenerator.nextDouble() * (phIncrement * 2)) - phIncrement;
    }
    else if (ph < desiredPh) {
      ph += (randomGenerator.nextDouble() * phIncrement);
    }
    else {
      ph -= (randomGenerator.nextDouble() * phIncrement);
    }

    final String desired = " (Desired: "; // PMD pickiness
    System.out.println("----------------------");
    System.out.println("System: Aquaponics");
    System.out.println("Circulation: " + roundSingleDecimal(circulation) + desired
        + roundSingleDecimal(desiredCirculation) + ")");
    System.out.println("Electrical conductivity: " + roundSingleDecimal(electrical_conductivity)
        + desired + roundSingleDecimal(desiredConductivity) + ")");
    System.out.println("Temperature: " + temperature + desired + desiredTemperature + ")");
    System.out.println("Turbidity: " + roundSingleDecimal(turbidity) + desired
        + roundSingleDecimal(desiredTurbidity) + ")");
    System.out.println("Water level: " + water_level + desired + desiredWaterLevel + ")");
    System.out.println("pH: " + roundSingleDecimal(ph) + desired + roundSingleDecimal(desiredPh)
        + ")");
    System.out.println("Dead fish: " + dead_fish);
    System.out.println("Oxygen level: " + roundSingleDecimal(oxygen));
    System.out.println("Fish feed: " + roundSingleDecimal(fish_feed));
    System.out.println("Nutrients: " + roundSingleDecimal(nutrients) + desired
        + roundSingleDecimal(desiredNutrients) + ")");
  }

  /**
   * Sets the desired temperature.
   * 
   * @param newDesiredTemperature the desired temperature
   */
  public static void setDesiredTemperature(int newDesiredTemperature) {
    desiredTemperature = newDesiredTemperature;
  }

  /**
   * Adds fish feed to the tank.
   * 
   * @param feedAmount the amount of fish feed to add.
   */
  public static void addFishFeed(double feedAmount) {
    fish_feed += feedAmount;
  }

  /**
   * Harvests fish from tank.
   * 
   * @param numFish the amount of fish to harvest.
   */
  public static void harvestFish(int numFish) {
    num_fish -= numFish;
  }

  /**
   * Sets the desired nutrient amount.
   * 
   * @param newDesiredNutrients the nutrient level.
   */
  public static void setNutrients(double newDesiredNutrients) {
    desiredNutrients = newDesiredNutrients;
  }

  /**
   * Sets the desired pH.
   * 
   * @param newDesiredPh the ph.
   */
  public static void setDesiredPh(double newDesiredPh) {
    desiredPh = newDesiredPh;
  }

  /**
   * Sets the desired water level.
   * 
   * @param newDesiredWaterLevel the water level.
   */
  public static void setDesiredWaterLevel(int newDesiredWaterLevel) {
    desiredWaterLevel = newDesiredWaterLevel;
  }

  /**
   * Rounds double value to a single decimal place.
   * 
   * @param doubleValue A double value
   * @return Rounded value
   */
  static double roundSingleDecimal(double doubleValue) {
    DecimalFormat singleDecimal = new DecimalFormat("#.#");
    return Double.valueOf(singleDecimal.format(doubleValue));
  }

  /**
   * Returns the data as an XML Document instance.
   * 
   * @return The data as XML.
   * @throws Exception If problems occur creating the XML.
   */
  public static DomRepresentation toXml() throws Exception {
    final String state = "state"; // PMD pickiness
    final String key = "key"; // PMD pickiness
    final String value = "value"; // PMD pickiness

    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = null;
    docBuilder = factory.newDocumentBuilder();
    Document doc = docBuilder.newDocument();

    // Create root tag
    Element rootElement = doc.createElement("state-data");
    rootElement.setAttribute("system", "AQUAPONICS");
    rootElement.setAttribute("device", "arduino-1");
    rootElement.setAttribute("timestamp", String.valueOf(new Date().getTime()));
    doc.appendChild(rootElement);

    // Create state tag.
    Element circulationElement = doc.createElement(state);
    circulationElement.setAttribute(key, "CIRCULATION");
    circulationElement.setAttribute(value, String.valueOf(circulation));
    rootElement.appendChild(circulationElement);

    // Create state tag.
    Element deadFishElement = doc.createElement(state);
    deadFishElement.setAttribute(key, "DEAD_FISH");
    deadFishElement.setAttribute(value, String.valueOf(dead_fish));
    rootElement.appendChild(deadFishElement);

    // Create state tag.
    Element electricalConductivityElement = doc.createElement(state);
    electricalConductivityElement.setAttribute(key, "ELECTRICAL_CONDUCTIVITY");
    electricalConductivityElement.setAttribute(value, String.valueOf(electrical_conductivity));
    rootElement.appendChild(electricalConductivityElement);

    // Create state tag.
    Element temperatureElement = doc.createElement(state);
    temperatureElement.setAttribute(key, "TEMPERATURE");
    temperatureElement.setAttribute(value, String.valueOf(temperature));
    rootElement.appendChild(temperatureElement);

    // Create state tag.
    Element turbidityElement = doc.createElement(state);
    turbidityElement.setAttribute(key, "TURBIDITY");
    turbidityElement.setAttribute(value, String.valueOf(turbidity));
    rootElement.appendChild(turbidityElement);

    // Create state tag.
    Element waterLevelElement = doc.createElement(state);
    waterLevelElement.setAttribute(key, "WATER_LEVEL");
    waterLevelElement.setAttribute(value, String.valueOf(water_level));
    rootElement.appendChild(waterLevelElement);

    // Create state tag.
    Element phElement = doc.createElement(state);
    phElement.setAttribute(key, "PH");
    phElement.setAttribute(value, String.valueOf(roundSingleDecimal(ph)));
    rootElement.appendChild(phElement);

    // Create state tag.
    Element oxygenElement = doc.createElement(state);
    oxygenElement.setAttribute(key, "OXYGEN");
    oxygenElement.setAttribute(value, String.valueOf(oxygen));
    rootElement.appendChild(oxygenElement);

    // Convert Document to DomRepresentation.
    DomRepresentation result = new DomRepresentation();
    result.setDocument(doc);

    // Return the XML in DomRepresentation form.
    return result;
  }

}
