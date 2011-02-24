package edu.hawaii.ihale.api.hsim;

import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.junit.BeforeClass;
import org.junit.Test;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.resource.ClientResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Tests the operations supported for the Hsim simulator.
 * @author TeamMaka
 */
public class TestHsim {

  /**
   * Start up a test server before testing any of the operations on this resource.
   * @throws Exception If problems occur starting up the server. 
   */
  @BeforeClass
  public static void startServer () throws Exception {
    HsimServer.main(null);
  }
  
  /**
   * Test if you can retrieve the key attatched to the end of the aquaponics URI.
   * @throws Exception If problems occur.
   */
  @Test
  public void testGetPut() throws Exception {
     
    //TEST PUT
    String tUrl = String.format("http://localhost:%s/aquaponics/%s", 8001,
    "temp");
    System.out.println("TEST PUT\n" + tUrl);
    ClientResource c2 = new ClientResource(tUrl);

    DomRepresentation rslt = new DomRepresentation();
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document doc = builder.newDocument();
    //create root element
    Element rootElement = doc.createElement("state-data");
    Element e = doc.createElement("state");
    e.setAttribute("key"," ");
    e.setAttribute("value", "100");
    rootElement.appendChild(e);
    doc.appendChild(rootElement);
    rslt.setDocument(doc);
    c2.put(rslt);
    // */

    //TEST GET
    String testUrl = String.format("http://localhost:%s/aquaponics/%s", 8001,
        "state");
    System.out.println("TEST GET\n" + testUrl);
    ClientResource client = new ClientResource(testUrl);  
    
    DomRepresentation r2 = new DomRepresentation(client.get());
    Transformer transformer = TransformerFactory.newInstance().newTransformer(); 
    transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
    //initialize StreamResult with File object to save to file 
    StreamResult result = new StreamResult(new StringWriter()); 
    DOMSource source = new DOMSource(r2.getDocument()); 
    transformer.transform(source, result); 
    String xmlString = result.getWriter().toString(); 
    System.out.println(xmlString);  
    // Let's now try to retrieve the Contact instance we just put on the server.  
   
    //GET AGAIN
    r2 = new DomRepresentation(client.get());
    result = new StreamResult(new StringWriter()); 
    source = new DOMSource(r2.getDocument()); 
    transformer.transform(source, result); 
    xmlString = result.getWriter().toString(); 
    System.out.println(xmlString);
  }
 
}
