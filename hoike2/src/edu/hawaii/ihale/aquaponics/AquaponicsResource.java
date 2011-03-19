package edu.hawaii.ihale.aquaponics;
import org.restlet.resource.ClientResource;
import edu.hawaii.ihale.backend.BackEndResource;
/** A Resource object that handles the retrieval of XML from the House simulator,
 * parses the data, and stores it.  This class allows for "smartness" checking
 * for each system.  This class should be created and called from the Dispatcher
 * class.
 * @author Backend
 *
 */
public class AquaponicsResource extends BackEndResource{
  private ClientResource client;
  
  /**
   * Constructor, initiates the client.
   * @param URI URI Value.
   */
  public AquaponicsResource (String URI) {
    client = new ClientResource(URI);
  }
  
  /** Calls an @get from HSim, parses the XML, 
   * stores the data, and does smartness checks.
   * This method is called by the Dispatcher.
   */
  public void getData() {
    //do a client.get();
    //parse the data.
    //store the data.
    //smartness checks and messages/alerts
  }
}
