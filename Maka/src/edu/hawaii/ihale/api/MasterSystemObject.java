package edu.hawaii.ihale.api;

import java.util.ArrayList;
import java.util.List;

public class MasterSystemObject {
  /** List of the SubSystemObjects that comprise the house.*/
  private List<SubSystemObject> subSystems;
  
  MasterSystemObject() {
    subSystems= new ArrayList<SubSystemObject>();
  }
  
}
