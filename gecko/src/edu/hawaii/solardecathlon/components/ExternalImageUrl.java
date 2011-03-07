package edu.hawaii.solardecathlon.components;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.model.Model;

/**
 * A component for displaying external images.
 * From: https://cwiki.apache.org/WICKET/how-to-load-an-external-image.html
 * See that page for details and other approaches.
 * @author Philip Johnson
 */
public class ExternalImageUrl extends WebComponent {
  /** Allow serialization. */
  private static final long serialVersionUID = 1L;

  /**
   * Create the img component.
   * @param id The wicket id.
   * @param imageUrl The url to be displayed.
   * @param height The height of the image to be displayed.
   * @param width The width of the image to be displayed.
   */
  public ExternalImageUrl(String id, String imageUrl, String height, String width) {
    super(id);
    add(new AttributeModifier("src", true, new Model<String>(imageUrl)));
    add(new AttributeModifier("width", true, new Model<String>(height)));
    add(new AttributeModifier("height", true, new Model<String>(width)));
    setVisible(!(imageUrl == null || imageUrl.equals("")));
  }

  /**
   * Support component tag by calling superclass.
   * @param tag The tag.
   */
  protected void onComponentTag(ComponentTag tag) {
    super.onComponentTag(tag);
    checkComponentTag(tag, "img");
  }
}
