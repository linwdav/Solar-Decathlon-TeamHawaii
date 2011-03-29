package edu.hawaii.ihale.backend;
 
import static org.junit.Assert.assertEquals; 
import org.junit.Test;
import org.restlet.ext.xml.DomRepresentation; 
import org.w3c.dom.Node;
import java.io.IOException;
import java.util.ArrayList; 
import edu.hawaii.ihale.api.ApiDictionary; 
import edu.hawaii.ihale.api.ApiDictionary.IHaleCommandType;
import edu.hawaii.ihale.api.ApiDictionary.IHaleRoom;
import edu.hawaii.ihale.backend.xml.PutCommand;

/**
 * JUnit tests the IHaleBackend. 
 * @author Backend Team
 */


public class TestIHaleBackend {
  IHaleBackend backend = new IHaleBackend();  
  /**
   * Tests the HVAC command handling.
   */
  @Test
  public void handleHvacCommand() { 
    backend.doCommand(ApiDictionary.IHaleSystem.HVAC, null, 
        ApiDictionary.IHaleCommandType.SET_TEMPERATURE, 100);
    assertEquals(parse(backend.doc),100);
  }
  /**
   * Tests the Aquaponics command handling.
   */
  @Test
  public void handleAquaponicsCommand() { 
    ArrayList<IHaleCommandType> enumList = new ArrayList<IHaleCommandType>();
    enumList.add(ApiDictionary.IHaleCommandType.FEED_FISH);
    enumList.add(ApiDictionary.IHaleCommandType.HARVEST_FISH);
    enumList.add(ApiDictionary.IHaleCommandType.SET_NUTRIENTS);
    enumList.add(ApiDictionary.IHaleCommandType.SET_PH);
    enumList.add(ApiDictionary.IHaleCommandType.SET_TEMPERATURE);
    enumList.add(ApiDictionary.IHaleCommandType.SET_WATER_LEVEL);
    
    for (IHaleCommandType command : enumList) {
      backend.doCommand(ApiDictionary.IHaleSystem.AQUAPONICS, null, command, 5);
      assertEquals(parse(backend.doc),"5");
    }
  }
  /**
   * Tests the Lighting command handling.
   */
  @Test
  public void handleLightingCommand() { 
    ArrayList<IHaleCommandType> enumList = new ArrayList<IHaleCommandType>();
    enumList.add(ApiDictionary.IHaleCommandType.SET_LIGHTING_ENABLED);
    enumList.add(ApiDictionary.IHaleCommandType.SET_LIGHTING_LEVEL);
    enumList.add(ApiDictionary.IHaleCommandType.SET_LIGHTING_COLOR);

    ArrayList<IHaleRoom> roomList = new ArrayList<IHaleRoom>();
    roomList.add(ApiDictionary.IHaleRoom.BATHROOM);
    roomList.add(ApiDictionary.IHaleRoom.KITCHEN);
    roomList.add(ApiDictionary.IHaleRoom.DINING);
    roomList.add(ApiDictionary.IHaleRoom.LIVING);
    
    for(IHaleRoom room : roomList) {
      for(IHaleCommandType command : enumList) { 
        if(command == IHaleCommandType.SET_LIGHTING_ENABLED) {
          backend.doCommand(ApiDictionary.IHaleSystem.LIGHTING, room, command, true);
          assertEquals(parse(backend.doc),"true");
        }
        backend.doCommand(ApiDictionary.IHaleSystem.LIGHTING, room, command, 100);
        assertEquals(parse(backend.doc),"5"); 
      }
    }
  }
  
  public String parse(PutCommand put) {
  DomRepresentation rep;
  Node element = null; 
  try {
    rep = put.getRepresentation(); 
    element = rep.getDocument().getChildNodes().item(0).getChildNodes().item(0);
  }
  catch (IOException e) {
    System.out.println("Parse error");
    e.printStackTrace();
  }
  Node item = element.getAttributes().item(0);
  return item.getNodeValue(); 
  }
}
