package edu.hawaii.systemh.frontend.components.image;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.model.Model;

/**
 * Creates the Dynamic Image object. This class is an extension of the image component in order to
 * load dynamic images. The model must describe how to create the ResourceReference object.
 * 
 * @author Bret K. Ikehara
 */
public class DynamicImage extends Image {

  /**
   * Serial ID.
   */
  private static final long serialVersionUID = 8779092407022498865L;

  /**
   * Creates the image with the model to change the image. The model describes how the image should
   * be updated.
   * 
   * @param id String
   * @param model Model<ResourceReference>
   */
  public DynamicImage(String id, Model<ResourceReference> model) {
    super(id);
    setDefaultModel(model);
    setOutputMarkupId(true);
  }

  /**
   * Gets the Image Resource Reference.
   * 
   * @return ResourceReference
   */
  @Override
  protected ResourceReference getImageResourceReference() {
    return (ResourceReference) getDefaultModelObject();
  }
}
