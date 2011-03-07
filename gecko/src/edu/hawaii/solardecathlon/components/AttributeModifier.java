package edu.hawaii.solardecathlon.components;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.wicket.model.IModel;

/**
 * Implements the attribute modifier class until the bug is fixed in Wicket.
 * 
 * @author Bret K. Ikehara
 */
public class AttributeModifier extends org.apache.wicket.AttributeModifier {

  /**
   * Serial ID.
   */
  private static final long serialVersionUID = 7558986892773309754L;

  private Pattern p;
  
  /**
   * Default Constructor.
   * 
   * @param attribute String
   * @param addAttributeIfNotPresent boolean
   * @param replaceModel IModel<?>
   * @param regex Pattern
   */
  public AttributeModifier(String attribute, boolean addAttributeIfNotPresent,
      IModel<?> replaceModel, Pattern regex) {
    super(attribute, addAttributeIfNotPresent, replaceModel);
    this.p = regex;
  }

  /**
   * Returns the new attribute.
   * 
   * @param currentValue String
   * @param replacementValue String
   * @return String
   */
  @Override
  protected String newValue(String currentValue, String replacementValue) {
    
    String result = null;
    Matcher m = null;
    boolean found = false;
    
    if (currentValue == null) {
      if (getAddAttributeIfNotPresent()) {
        result = replacementValue;
      }
    }
    else {
      m = p.matcher(currentValue);
      found = m.find();
      result = found ? m.replaceAll(replacementValue) : currentValue;
      
      if (!found && getAddAttributeIfNotPresent()) {
        result += " " + replacementValue;
      }
    }
    
    return result;
  }
  
}
