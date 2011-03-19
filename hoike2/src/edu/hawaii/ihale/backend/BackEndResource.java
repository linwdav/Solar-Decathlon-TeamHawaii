package edu.hawaii.ihale.backend; 

/** An abstract class that represents a system.  
 * Preforms @gets on the house simulator.
 * @author Backend
 *
 */

//not sure if this should extend restlet... 
//might have to in order to use the Client object.
public abstract class BackEndResource {

  public void getData() {
    //override this
  }
  
  //maybe include a dynamic method for parsing XML?
}
