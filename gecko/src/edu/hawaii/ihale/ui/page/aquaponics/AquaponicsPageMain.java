package edu.hawaii.ihale.ui.page.aquaponics;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import edu.hawaii.ihale.ui.page.BasePanel;
/**
 * Displays the overall status for the home page.
 * 
 * @author Shoji Bravo
 * @author Bret Ikehara
 */
public class AquaponicsPageMain extends BasePanel {

    /**
     * List of rooms.
     */
    private static final List<String> pHlvl = Arrays.asList(new String[] { "weekly",
        "bi-weekly", "monthly", "bi-monthly"});
    
    private static final List<String> waterlvl = Arrays.asList(new String[] { "25",
            "50", "75", "100","125",
            "150", "175", "200"});
    
  /**
   * Serial ID.
   */
  private static final long serialVersionUID = 3393503073298832456L;

  /**
   * Handles the output for the status screen.
   * 
   * @param name String
   * @param <T> type
   * @param model Model<T>
   */
  public <T> AquaponicsPageMain(String name, IModel<T> model) {
  super(name, model);
  
  Form<String> form = new Form<String>("form");
  
  form.add(new DropDownChoice<String>("pH_drop", pHlvl));
  form.add(new DropDownChoice<String>("water_lvl", waterlvl));
  
  //add form
  add(form);
  }
}
