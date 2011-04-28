package edu.hawaii.systemh.android.systemdata;

import java.text.DecimalFormat;

import org.restlet.ext.xml.DomRepresentation;
import org.restlet.resource.ClientResource;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Retrieves and Change data from the database.
 * 
 * @author Group H
 * 
 */
public class SystemData {

    // Initialize
    String ipAddress = "192.168.1.113";

    String system = "";
    String device = "";
    long timestamp = 0;
    String key = "";
    String value = "";

    long temperature = 0;
    double circulation = 0;
    double ec = 0;
    double turbidity = 0;
    int waterLevel = 0;
    double ph = 0;
    double oxygen = 0;
    int deadFish = 0;

    long power = 0;
    long energy = 0;

    int lightingLevel = 0;
    boolean enabled = false;
    String color = "";

    /**
     * Getter of values
     * 
     * @param systemName The name of the system you want to get data from
     */
    public SystemData(String systemName) {

        String getUrl = null;

        try {

            if ("Aquaponics".equalsIgnoreCase(systemName)) {
                getUrl = "http://" + ipAddress + ":8111/AQUAPONICS/state";

            } else if ("hvac".equalsIgnoreCase(systemName)) {
                getUrl = "http://" + ipAddress + ":8111/HVAC/state";

            } else if ("electric".equalsIgnoreCase(systemName)) {
                getUrl = "http://" + ipAddress + ":8111/ELECTRIC/state";

            } else if ("photovoltaics".equalsIgnoreCase(systemName)) {
                getUrl = "http://" + ipAddress + ":8111/PHOTOVOLTAIC/state";

            } else if ("lighting-bathroom".equalsIgnoreCase(systemName)) {
                getUrl = "http://" + ipAddress
                        + ":8111/LIGHTING/state?room=BATHROOM";

            } else {
                System.out.println("Unknown System Name!");
            }
            
            ClientResource getClient = new ClientResource(getUrl);

            DomRepresentation domRep = new DomRepresentation(getClient.get());
            
            // Get the XML representation.
            Document doc = domRep.getDocument();
            doc.getDocumentElement().normalize();

            // get root element of the xml
            Node root = doc.getDocumentElement();

            // Get attributes of root element
            NamedNodeMap rootAttributes = root.getAttributes();
            for (int i = 0; i < rootAttributes.getLength(); i++) {
                Node rootAttribute = rootAttributes.item(i);
                
                String label = rootAttribute.getNodeName();
                String data = rootAttribute.getNodeValue();
                
                // Checks the label and store 
                if ("system".equals(label)) {
                  system = data;
                  //System.out.println("System: " + system);
                }
                
                if ("device".equals(label)) {
                  device = data;
                  //System.out.println("Device: " + device);
                }
                
                if ("timestamp".equals(label)) {
                  timestamp = Long.parseLong(data.trim());
                  //System.out.println("Timestamp: " + timestamp);
                }
            }

            // Get attributes of child element
            NodeList children = root.getChildNodes();

            for (int i = 0; i < children.getLength(); i++) {

                Node child = children.item(i);

                if (child.getNodeType() == Node.ELEMENT_NODE) {

                    // Get attributes of root element
                    NamedNodeMap childAttributes = child.getAttributes();
                    for (int x = 0; x < childAttributes.getLength(); x++) {
                        Node childAttribute = childAttributes.item(x);
                        
                        String name = childAttribute.getNodeName();
                        String val = childAttribute.getNodeValue();
                        
                        // Store the key and value (e.g. Temperature 75) 
                        if ("key".equals(name)) {
                            key = val;
                        } 
                        else {
                            value = val;
                        }
                    }   
                        
                        if ("Temperature".equalsIgnoreCase(key)) {
                            temperature = Long.parseLong(value);
                        } else if ("Electrical_Conductivity".equalsIgnoreCase(key)) {
                            ec = Double.valueOf(value);
                        }else if ("Ph".equalsIgnoreCase(key)) {
                            ph = Double.valueOf(value);
                        }else if ("Circulation".equalsIgnoreCase(key)) {
                            circulation = Double.valueOf(value);
                        }else if ("Turbidity".equalsIgnoreCase(key)) {
                            turbidity = Double.valueOf(value);
                        }else if ("Oxygen".equalsIgnoreCase(key)) {
                            oxygen = Double.valueOf(value);
                        }else if ("Water_Level".equalsIgnoreCase(key)) {
                            waterLevel = Integer.parseInt(value);
                        }else if ("Dead_Fish".equalsIgnoreCase(key)) {
                            deadFish = Integer.parseInt(value);
                        }else if ("Energy".equalsIgnoreCase(key)) {
                            energy = Long.parseLong(value);
                        }else if ("Power".equalsIgnoreCase(key)) {
                            power = Long.parseLong(value);
                        }else if ("Lighting_Level".equalsIgnoreCase(key)) {
                            lightingLevel = Integer.parseInt(value);
                        }else if ("Lighting_Enabled".equalsIgnoreCase(key)) {
                            enabled = Boolean.parseBoolean(value);
                        }else if ("Lighting_Color".equalsIgnoreCase(key)) {
                            color = value;
                        }
                        else {
                            System.out.println("Unknown Key Name!");
                        }   
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    /**
     * 
     * @return
     */
    public long getTemp () {
        return temperature;
    }
    
    /**
     * 
     * @param value
     */
    public void setTemp(String value){
        
        String putUrl = "http://" + ipAddress + ":8111/AQUAPONICS/command/SET_TEMPERATURE?arg=" + value; 
        ClientResource putClient = new ClientResource(putUrl);
        putClient.put(putUrl);
        putClient.release();
    }
    
    public double getCirculation () {
        return roundTwoDecimals(circulation);
    }
    
    public double getTurbidity () {
        return roundTwoDecimals(turbidity);
    }
    
    public double getPh () {
        return roundTwoDecimals(ph);
    }
    
    public int getWaterLevel () {
        return waterLevel;
    }
    
    public double getElectricalConductivity () {
     
        return roundTwoDecimals(ec);
    }
    
    public double getOxygen () {
        return roundTwoDecimals(oxygen);
    }
    
    public int getDeadFish () {
        return deadFish;
    }
    
    public long getPower () {
        return power;
    }
    
    public long getEnergy () {
        return energy;
    }
    
    public int getLevel () {
        return lightingLevel;
    }
    
    public boolean getEnabled () {
        return enabled;
    }
    
    public String getColor () {
        return color;
    }
    
    /**
     * 
     * @param d The number to convert.
     * @return double in two deciimal places.
     */
    public double roundTwoDecimals(double d) {
      DecimalFormat twoDForm = new DecimalFormat("#.##");
      return Double.valueOf(twoDForm.format(d));
    }

}
