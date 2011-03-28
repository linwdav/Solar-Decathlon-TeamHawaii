package edu.hawaii.ihale.frontend.page;

//import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;

/**
 * Defines a class that extends ModalWindow.
 * @author kurtteichman
 */
public abstract class SelectModalWindow extends ModalWindow {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * SelectModalWindow constructor.
     * @param id defines the id to be associated with the window.
     * @param systemChecker object allows for checking erroneous
     * systems.
     */
    public SelectModalWindow(String id, SystemChecker systemChecker) {
        super(id);

        // Set sizes of this ModalWindow. You can also do this
        // from the HomePage but its not a bad idea to set some
        // good default values.
        setInitialWidth(450);
        setInitialHeight(300);

        // Set the content panel, implementing the abstract methods
        setContent(new SelectContentPanel(this.getContentId(), systemChecker) {
            /** */
          private static final long serialVersionUID = 1L;
            
            /*
            void onCancel(AjaxRequestTarget target) {
                SelectModalWindow.this.onCancel(target);
            }

            void onSelect(AjaxRequestTarget target, String selection) {
                SelectModalWindow.this.onSelect(target, selection);
            }
            */
        });        
    }
    
    
    /**
     * Method to cancel modal window.
     * @param target allows the user to cancel the modal window.
     */
    //abstract void onCancel(AjaxRequestTarget target);

    /**
     * Method 
     * @param target
     * @param selection defines the selection to be used
     */
    //abstract void onSelect(AjaxRequestTarget target, String selection);

}
