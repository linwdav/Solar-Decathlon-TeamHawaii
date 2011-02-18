package edu.hawaii.ihale.ui.page.service;

import java.util.Properties;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import edu.hawaii.ihale.SolarDecathlonSession;
import edu.hawaii.ihale.ui.page.BasePage;
import edu.hawaii.ihale.ui.page.home.HomePage;

/**
 * Constructor for the Log In page.
 * 
 * @author Bret Ikehara
 */
public class LogInPage extends BasePage {

  /**
   * Serial ID.
   */
  private static final long serialVersionUID = 1L;

  private static final String logInUser = "LogInUser";
  private static final String logInPassword = "LogInPasswd";

  Properties prop = new Properties();

  /**
   * Constructor for the Log In page.
   */
  public LogInPage() {
    Form<String> form = new Form<String>("LogInForm") {

      /**
       * 
       */
      private static final long serialVersionUID = 1785418102289448901L;

      /**
       * Handles the error.
       */
      @Override
      protected void onError() {
        super.onError();
        setResponsePage(new LogInPage());
      }

      /**
       * Handles submission.
       */
      @Override
      protected void onSubmit() {
        super.onSubmit();
        SolarDecathlonSession session = (SolarDecathlonSession) getSession();

        if (session.authenticate(prop.getProperty(LogInPage.logInUser),
            prop.getProperty(LogInPage.logInPassword))) {
          setResponsePage(HomePage.class);
        }
        else {
          System.out.println("User Name or Pasword was incorrect. Please try again.");
        }
      }
    };

    form.add(new TextField<String>("LogInUser",
        new PropertyModel<String>(prop, LogInPage.logInUser), String.class).setRequired(true));

    form.add(new PasswordTextField("LogInPasswd", new PropertyModel<String>(prop,
        LogInPage.logInPassword)).setType(String.class).setRequired(true));

    add(CSSPackageResource.getHeaderContribution(LogInPage.class, "style.css"));
    add(new FeedbackPanel("feedback"));
    add(form);
  }
}
