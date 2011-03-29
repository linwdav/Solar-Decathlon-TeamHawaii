package edu.hawaii.ihale.backend.xml;
 
import static org.junit.Assert.assertEquals;  
import org.junit.Test;
import org.restlet.ext.xml.DomRepresentation; 
import org.w3c.dom.Node;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException; 
import edu.hawaii.ihale.api.ApiDictionary;

/**
 * Tests the PutCommand class to ensure that.
 * 
 * @author Backend Team
 */
public class TestPutCommand {

  @Test
  public void test() throws ParserConfigurationException, ValidTypeException, IOException {
    PutCommand put = new PutCommand(ApiDictionary.IHaleCommandType.FEED_FISH);
    put.addArgument("arg", 100);
    DomRepresentation rep = put.getRepresentation();
    Node element = rep.getDocument().getChildNodes().item(0).getChildNodes().item(0);
    Node item = element.getAttributes().item(0);  
    assertEquals(item.getNodeValue(), "100");
    
  }
}