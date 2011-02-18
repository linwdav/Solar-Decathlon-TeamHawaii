package edu.hawaii.ihale.ui.page.style;

import org.apache.wicket.RequestCycle;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;

/**
 * Supports the addition of IE CSS hacks to the header as needed by Blueprint CSS. 
 * @author Neil Curzon
 * From: http://apache-wicket.1842946.n4.nabble.com/If-IE-comments-td1892057.html
 */
public class IEStylesheetHeaderContributor extends AbstractBehavior implements IHeaderContributor {
  /** Support serialization. */
  private static final long serialVersionUID = 1L;
  /** The reference to the resource. */
  private final ResourceReference ref;

  /**
   * Creates the new IE comment header contributor.
   * @param ref the reference to the css sheet containing the IE hacks.
   */
  public IEStylesheetHeaderContributor(ResourceReference ref) {
    this.ref = ref;
  }


  /**
   * Adds the IE comment to the header. 
   * @param response The response object.
   */
  @Override
  public void renderHead(IHeaderResponse response) {
    response.renderString("<!--[if IE]> <link type=\"text/css\" rel=\"stylesheet\" href=\""
        + RequestCycle.get().urlFor(ref) + "\"/> <![endif]-->");
  }

}
