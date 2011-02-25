package edu.hawaii.ihale.backend.rest;

import static org.junit.Assert.assertEquals;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.junit.Test;
import edu.hawaii.ihale.api.SystemStateEntry;

/**
 * Unit test of the IHale HTTP server's processing ability.
 *
 * @author Leonardo Nguyen
 * @version Java 1.6.0_21
 */
public class TestIHaleServer {
  
  /**
   * Test XML parsing and creating an instance of IHaleSystemStateEntryRepresentation.
   */
  @Test
  public void testXML() {
        
    try {
      
      // Create the Document instance representing this XML.
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document doc = builder.newDocument();

      // Create and attach the root element <state-data>.
      Element rootElement = doc.createElement("state-data");
      doc.appendChild(rootElement);
      
      Attr attribute = doc.createAttribute("system");
      attribute.setValue("Aquaponics");
      rootElement.setAttributeNode(attribute);
      
      attribute = doc.createAttribute("device");
      attribute.setValue("Arduino-1");
      rootElement.setAttributeNode(attribute);
      
      attribute = doc.createAttribute("timestamp");
      attribute.setValue("1297446335");
      rootElement.setAttributeNode(attribute);
      
      // Attach the first state element (Temp field) to state-data parent
      Element stateElement1 = doc.createElement("state");
      rootElement.appendChild(stateElement1);
      attribute = doc.createAttribute("key");
      attribute.setValue("Temp");
      stateElement1.setAttributeNode(attribute);
      attribute = doc.createAttribute("value");
      attribute.setValue("25");
      stateElement1.setAttributeNode(attribute);
      
      // Attach the second state element (Oxygen field) to state-data parent
      Element stateElement2 = doc.createElement("state");
      rootElement.appendChild(stateElement2);
      attribute = doc.createAttribute("key");
      attribute.setValue("Oxygen");
      stateElement2.setAttributeNode(attribute);
      attribute = doc.createAttribute("value");
      attribute.setValue("100");
      stateElement2.setAttributeNode(attribute);
      
      // Attach the third state element (pH field) to state-data parent.
      Element stateElement3 = doc.createElement("state");
      rootElement.appendChild(stateElement3);
      attribute = doc.createAttribute("key");
      attribute.setValue("pH");
      stateElement3.setAttributeNode(attribute);
      attribute = doc.createAttribute("value");
      attribute.setValue("7");
      stateElement3.setAttributeNode(attribute);
      
      IHaleDAO dao = new IHaleDAO();
      SystemStateEntry xmlEntry = dao.xmlToSystemStateEntry(doc);
      dao.putEntry(xmlEntry);
      
      SystemStateEntry returnedEntry = dao.getEntry("Aquaponics", "Arduino-1", 1297446335);
      
      assertEquals("Checking for storing an entry created from a XML document and retrieving" +
          "it from the database repository.", xmlEntry.toString(), returnedEntry.toString());

    }
    catch (ParserConfigurationException e) {
      e.printStackTrace();
    }
  }
  
//  /**
//   * Test the functionality of polling devices and storing their XML packages into the database.
//   */
//  @Test
//  public void testDeviceCommunication() {
//    // Create a component.
//    Component component = new Component();
//    // Create an application (this class).
//    Application application = null;
//    List<String> urls = new ArrayList<String>();
//    
//    readProperties();
//    for (Map.Entry<String, String> entry : uris.entrySet()) {
//      final String key = entry.getKey();
//      final String contextRoot = "/" + key.split("/")[1] + "/" + key.split("/")[2];
//      final int port = Integer.valueOf(entry.getValue());
//      //System.out.println(contextRoot);
//      //System.out.println(port);
//      urls.add("http://localhost:" + entry.getValue() + contextRoot);
//      application = new Application() {
//        @Override
//        public Restlet createInboundRoot() {
//          // Create a router restlet.
//          Router router = new Router(getContext());
//          // Attach the resources to the router.
//          if ("aquaponics".equals(key.split("/")[1])) {
//            router.attach(contextRoot, AquaponicsResource.class);
//            router.attach("", AquaponicsResource.class);
//          }
//          else if ("hvac".equals(key.split("/")[1])) {
//            router.attach(contextRoot, HvacResource.class);
//            router.attach("", HvacResource.class);
//          }
//          else if ("lighting".equals(key.split("/")[1])) {
//            router.attach(contextRoot, LightingResource.class);
//            router.attach("", LightingResource.class);
//          }
//          else if ("egauge-1.halepilihonua.hawaii.edu".equals(key.split("/")[0])) {
//            router.attach(contextRoot, PhotovoltaicsResource.class);
//            router.attach("", PhotovoltaicsResource.class);
//          }
//          else if ("egauge-2.halepilihonua.hawaii.edu".equals(key.split("/")[0])) {
//            router.attach(contextRoot, ElectricalResource.class);
//            router.attach("", ElectricalResource.class);
//          }
//          else {
//            System.out.println("Error with attaching to router.");
//          }
//          System.out.println(key.split("/")[0]);
//          // Return the root router
//          return router;
//        }
//      };
//      component.getServers().add(Protocol.HTTP, port);
//      // Attach the application to the component with a defined contextRoot.
//      component.getDefaultHost().attach(contextRoot, application);
//    }
//    component.start();
//  }
}
