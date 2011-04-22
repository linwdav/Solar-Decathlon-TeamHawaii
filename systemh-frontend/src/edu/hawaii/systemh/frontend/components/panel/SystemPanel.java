package edu.hawaii.systemh.frontend.components.panel;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.JavascriptPackageResource;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/**
 * Defines the Systems' Status panel on the bottom-right side of the webpage.
 * 
 * @author Bret K. Ikehara
 */
public abstract class SystemPanel extends Panel implements IHeaderContributor {

  private WebMarkupContainer panel;

  /**
   * Serial ID.
   */
  private static final long serialVersionUID = -4395753710453042233L;

  /**
   * Default Constructor defining status model.
   * 
   * @param id String
   * @param model IModel<String>
   */
  public SystemPanel(String id, IModel<String> model) {
    super(id);
    super.add(CSSPackageResource
        .getHeaderContribution(edu.hawaii.systemh.frontend.components.panel.SystemPanel.class,
            "SystemPanel.css", "screen"));
    super.add(JavascriptPackageResource.getHeaderContribution(
        edu.hawaii.systemh.frontend.components.panel.SystemPanel.class, "SystemPanel.js"));

    this.panel = new WebMarkupContainer("system-panel");
    this.panel.setOutputMarkupId(true);
    this.panel.setMarkupId(id);
    super.add(this.panel);

    Label status = new Label("system-button", model);
    status.setEscapeModelStrings(false);
    this.panel.add(status);
  }

  /**
   * Adds the component to the panel.
   * 
   * @param comp Component
   */
  public Component addToPanel(Component comp) {
    return this.panel.add(comp);
  }

  /**
   * Renders the javascript on the page.
   */
  @Override
  public void renderHead(IHeaderResponse response) {
    response.renderOnDomReadyJavascript("$('#" + this.panel.getMarkupId() + "').panel();");
  }
}