package edu.hawaii.systemh.android.hvac;

import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import edu.hawaii.systemh.android.R;
import edu.hawaii.systemh.android.menu.Menu;

/**
 * The activity that starts the hvac page.
 * 
 * @author Group H
 *
 */
public class Hvac extends Activity implements OnClickListener {

  Button testButton;
  TextView testTextView;
  int numOfClicks = 0;
  
  /** 
   * Called when the activity is first created. 
   * @param savedInstanceState - A mapping from String values to various Parcelable types. 
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // requesting to turn the title OFF
    requestWindowFeature(Window.FEATURE_NO_TITLE);

    // making it full screen
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);

    setContentView(R.layout.hvac);
    
    //Engine.getInstance().getRegisteredClients().add(new HttpClientHelper(null));
    //System.clearProperty("http.proxyHost");

    // Test widgets for Restlet communication with the back-end server.
    testButton = (Button) findViewById(R.id.ButtonHvacTest);
    testButton.setOnClickListener(this);
    testTextView = (TextView) findViewById(R.id.TextViewTestView);
    
  }
  
  @Override
  public void onClick(View view) {
    System.out.println("A button has been clicked!");
    int id = view.getId();
    
    // To test if the button works upon clicking.
    /*
    if (id == R.id.ButtonHvacTest) {
      numOfClicks++;
      testTextView.setText("CHANGE OCCUR: The number of times the TestButton has been clicked is: " + 
         numOfClicks);
      return;
    }
    */
    
    // To test if we can retrieve information after sending a HTTP GET request.
    if (id == R.id.ButtonHvacTest) {

      try {
        
        // Can't use "localhost" with the android emulator, since it is relative to the
        // emulator's perspective and not the local machine running the house simulator.
        String getUrl = "http://192.168.1.100:7102/hvac/state";
        ClientResource getClient = new ClientResource(getUrl);
        
        // Test the PUT request.
        String putUrl = "http://192.168.1.100:7102/hvac/temperature";
        ClientResource putClient = new ClientResource(putUrl);
        DomRepresentation xmlRep = createPutXmlRepresentation("25");
        putClient.put(xmlRep);
        putClient.release();
        
        DomRepresentation domRep = new DomRepresentation(getClient.get());
        
        // Get the XML representation.
        Document domDoc = domRep.getDocument();

        String systemNameAttributeName = "system";
        String deviceNameAttributeName = "device";
        
        // Get the root element, in this case would be state-data element.
        Element stateData = domDoc.getDocumentElement();
        
        // Retrieve the attributes from state-data element, the system and device name.
        String systemName = stateData.getAttribute(systemNameAttributeName);
        String deviceName = stateData.getAttribute(deviceNameAttributeName);
        
        // Retrieve a child node from the Document object. Represents state data.
        NodeList xmlList = domDoc.getElementsByTagName("state");

        // Retrieve an the current home temperature.
        String value = ((Element) xmlList.item(0)).getAttribute("value");

        String displayText = "The system name is: " + systemName + ".\n" + "The device name is: " + 
          deviceName + ".\n" + "The current home temperature is: " + value + "C.\n";
          
        testTextView.setText(displayText);
        getClient.release();
      }
      catch (ResourceException e) {
        e.printStackTrace();
      }
      catch (IOException e) {
        e.printStackTrace();
      }
      catch (ParserConfigurationException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Take the user to the menu.
   * @param view The view.
   */
  public void showMenu(View view) {
    Intent intent = new Intent(Intent.ACTION_VIEW);
    intent.setClassName(this, Menu.class.getName());
    startActivity(intent);
  }

  /**
   * Creates a XML representation of a PUT request to the HVAC system to set the home
   * at a certain temperature.
   *
   * @param value The desired temperature we want the HVAC system to maintain the home at.
   * @return The XML representation for a PUT request to the HVAC system.
   * @throws ParserConfigurationException If there is a problem with the parser.
   * @throws IOException If there is a problem building the document.
   */
  public static DomRepresentation createPutXmlRepresentation(String value) 
    throws ParserConfigurationException, IOException {
    
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = null;
    docBuilder = factory.newDocumentBuilder();
    Document doc = docBuilder.newDocument();

    // Create root tag
    Element rootElement = doc.createElement("command");
    rootElement.setAttribute("name", "SET_TEMPERATURE");
    doc.appendChild(rootElement);

    // Create state tag.
    Element temperatureElement = doc.createElement("arg");
    temperatureElement.setAttribute("value", value);
    rootElement.appendChild(temperatureElement);

    // Convert Document to DomRepresentation.
    DomRepresentation putXmlRepresentation = new DomRepresentation();
    putXmlRepresentation.setDocument(doc);
    
    return putXmlRepresentation;
  }
  
  /**
   * Destroys this activity when onStop is called.
   */
  @Override
  protected void onStop() {
    finish();
    super.onDestroy();
  }
}