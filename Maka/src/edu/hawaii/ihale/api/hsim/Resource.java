package edu.hawaii.ihale.api.hsim;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.restlet.resource.ServerResource;
import org.w3c.dom.Document;
import java.util.Date;
/**
 * A server resource that will handle requests regarding a specific Contact.
 * Supported operations: GET, PUT, and DELETE.
 * Supported representations: XML.
 * @author Philip Johnson
 */
public class Resource extends ServerResource {
  MT mt = new MT();
  Date date = new Date();

  public Document doc() throws Exception {
    // Create the Document instance representing this XML.
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document doc = builder.newDocument();
    return doc;
  }
  
  public long timeStamp() {
    return date.getTime();
  }
  
}
