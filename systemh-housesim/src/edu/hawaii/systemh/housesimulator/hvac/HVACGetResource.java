package edu.hawaii.systemh.housesimulator.hvac;
 
import java.io.StringWriter;
import java.util.Date; 
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.w3c.dom.Document;

/**
 * A server resource that will handle requests regarding the HVAC system. 
 * Supported operations: GET.
 * Supported representations: XML.
 * 
 * @author Anthony Kinsey, Michael Cera
 * @author Christopher Ramelb, David Lin, Leonardo Nguyen, Nathan Dorman
 */
public class HVACGetResource extends ServerResource {
  /**
   * Returns the data requested by the URL.
   * 
   * @return The XML representation of the data.
   * @throws Exception If problems occur making the representation.
   */
  @Get
  public Representation getState() throws Exception {
    // Return the representation.
    
    try {
      //test to see if we have an Internet connection...
      HVACDataWeb data = HVACDataWeb.getInstance();
      data.setCurrentTime(System.currentTimeMillis());
      data.modifySystemState();   
      return data.toXml();
  }
  catch (Exception e) {
    e.printStackTrace(); 
    HVACData.setCurrentTime(new Date().getTime()); 
    return HVACData.toXml();
  }
  }
  
  /**
   * Test main method.
   * @param args null.
   * @throws Exception if not internet Connection available.
   */
  public static void main(String[] args) throws Exception {
    HVACGetResource r = new HVACGetResource(); 
    DomRepresentation dom = (DomRepresentation) r.getState();
    Document doc = dom.getDocument();
    StringWriter stw = new StringWriter();
    Transformer serializer = TransformerFactory.newInstance().newTransformer();
    serializer.transform(new DOMSource(doc), new StreamResult(stw));
    System.out.println(stw.toString()); 
  }
}
