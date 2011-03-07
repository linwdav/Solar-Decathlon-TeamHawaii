package edu.hawaii.solardecathlon.page.services;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.Model;
import edu.hawaii.solardecathlon.components.AttributeModifier;
import edu.hawaii.solardecathlon.page.BasePage;

/**
 * The help page.
 * 
 * @author Noah Woodden
 * @author Kevin Leong
 * @author Anthony Kinsey
 * @author Bret K. Ikehara
 */
public class Help extends BasePage {

  /** Support serialization. */
  private static final long serialVersionUID = 4L;

  protected Class<? extends Panel> helpPage;

  /**
   * Layout of page.
   */
  public Help() {
    linkResource();
  }

  /**
   * Adds all link resources to the page.
   */
  private void linkResource() {

    // Help page side bar
    List<Item<Class<? extends Panel>>> helpList = new ArrayList<Item<Class<? extends Panel>>>();
    helpList.add(new Item<Class<? extends Panel>>("Overview", 0,
        new Model<Class<? extends Panel>>(HelpOverview.class)));

    // set default value.
    if (helpPage == null) {
      helpPage = helpList.get(0).getModelObject();
    }

    add(new ListView<Item<Class<? extends Panel>>>("helpList", helpList) {

      /**
       * Serial ID.
       */
      private static final long serialVersionUID = 3992102347955899398L;

      /**
       * Populates the links.
       */
      @Override
      protected void populateItem(ListItem<Item<Class<? extends Panel>>> item) {
        final Item<Class<? extends Panel>> modelObj = item.getModelObject();
        String className = null;
        boolean selected = helpPage.equals(modelObj.getModelObject());

        AjaxFallbackLink<String> link = new AjaxFallbackLink<String>("link") {

          /**
           * Serial ID.
           */
          private static final long serialVersionUID = 2775237894777162688L;

          /**
           * Update the help contents.
           * 
           * @param target AjaxRequestTarget
           */
          @Override
          public void onClick(AjaxRequestTarget target) {
            helpPage = modelObj.getModelObject();
          }
        };
        link.add(new Label("name", modelObj.getId()));

        // highlight tab
        className = selected ? CLASS_BTN_GREEN : CLASS_BTN_GRAY;
        link.add(new AttributeModifier("class", true, new Model<String>(className), CLASS_PAT_BTN));

        item.add(link);
      }

    });

    // Create the constructor from the class object to populate the help contents.
    try {
      Constructor<? extends Panel> constr = helpPage.getConstructor(new Class[] { String.class });
      add(constr.newInstance("helpContent"));
    }
    catch (IllegalArgumentException e) {
      e.printStackTrace();
    }
    catch (InstantiationException e) {
      e.printStackTrace();
    }
    catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    catch (InvocationTargetException e) {
      e.printStackTrace();
    }
    catch (SecurityException e) {
      e.printStackTrace();
    }
    catch (NoSuchMethodException e) {
      e.printStackTrace();
    }
  }
} // End Help class

