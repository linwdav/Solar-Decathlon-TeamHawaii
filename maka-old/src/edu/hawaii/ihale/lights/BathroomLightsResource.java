package edu.hawaii.ihale.lights;
 
import java.util.Arrays;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import edu.hawaii.ihale.housesimulator.Arduino;
/**
 * Simulates the lighting in a room of the solar decathlon house.
 * Each class is titled after the room it represents.
 * Each room holds a value for the level of its lights (0-100),
 * 0 being off, 100 being fully on.
 * @author Team Maka
 *
 */
public class BathroomLightsResource extends Arduino { 
  String[] localKeys = {"level"};
  String currentLevel;
  
  /**
   * Constructor.
   */
  public BathroomLightsResource() {
    super("lighting","arduino-8");
    BathroomLightsRepository.getInstance();
    keys = localKeys; 
    list = Arrays.asList(keys);
  }

/**
 * Returns the Contact instance requested by the URL. 
 * @return The XML representation of the contact, or CLIENT_ERROR_NOT_ACCEPTABLE if the 
 * unique ID is not present.
 * @throws Exception If problems occur making the representation. Shouldn't occur in 
 * practice but if it does, Restlet will set the Status code. 
 */

@Get
public Representation getResource() throws Exception {
  //refresh values
  // Create an empty XML representation.
  DomRepresentation result = new DomRepresentation();
  DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
  DocumentBuilder builder = factory.newDocumentBuilder();
  Document doc = builder.newDocument();
  //create root element
  Element rootElement = doc.createElement("state-data");
  rootElement.setAttribute("system", "lighting");
  rootElement.setAttribute("device", deviceName);
  rootElement.setAttribute("timestamp", String.valueOf(date.getTime()));

  Element levelElement = doc.createElement("state");
  levelElement.setAttribute("key", "level");
  levelElement.setAttribute("value", BathroomLightsRepository.getLevel());
  rootElement.appendChild(levelElement);

  doc.appendChild(rootElement);
  result.setDocument(doc);
  return result;
}
  @Override
  public void poll() {
    BathroomLightsRepository.setLevel(String.valueOf(mt.nextDouble(0,100)));
  }

  @Override
  public void set(String key, String value) {
    BathroomLightsRepository.setLevel(value);
  }
}
