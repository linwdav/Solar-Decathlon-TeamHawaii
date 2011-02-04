package edu.hawaii.ihale.api;

import java.util.ArrayList;
import java.util.List;

public class MasterSystemObject {
  /** List of the SubSystemObjects that comprise the house.*/
  private List<SubSystemObject> subSystems;
  
  public MasterSystemObject() {
    subSystems= new ArrayList<SubSystemObject>();
  }
  
  public void addSubSystemObject(String subSystem, int port) {
	  this.subSystems.add(new SubSystemObject(subSystem, port);
  }

}
