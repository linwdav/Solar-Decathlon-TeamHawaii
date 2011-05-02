package edu.hawaii.systemh.android.help;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import edu.hawaii.systemh.android.R;
import edu.hawaii.systemh.android.menu.Menu;

/**
 * The activity that starts the help page.
 * 
 * @author Group H
 * 
 */
public class Help extends Activity {

  /**
   * Called when the activity is first created.
   * 
   * @param savedInstanceState - A mapping from String values to various Parcelable types.
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // requesting to turn the title OFF
    requestWindowFeature(Window.FEATURE_NO_TITLE);

    // making it full screen
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);

    setContentView(R.layout.help);

    Spinner spinner = (Spinner) findViewById(R.id.spinner);
    ArrayAdapter<CharSequence> adapter =
        ArrayAdapter.createFromResource(this, R.array.systems, R.layout.spinner);
    adapter.setDropDownViewResource(R.layout.spinner_dropdown);
    spinner.setAdapter(adapter);
    spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());

  }

  /**
   * Take the user to the menu.
   * 
   * @param view The view.
   */
  public void showMenu(View view) {
    Intent intent = new Intent(Intent.ACTION_VIEW);
    intent.setClassName(this, Menu.class.getName());
    startActivity(intent);
  }

  /**
   * Destroys this activity when onStop is called.
   */
  @Override
  protected void onStop() {
    finish();
    super.onDestroy();
  }

  /**
   * The listener for the room choices.
   * 
   * @author Group H
   * 
   */
  public class MyOnItemSelectedListener implements OnItemSelectedListener {

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

      TextView helpContent = (TextView) findViewById(R.id.helpContent);

      String room = parent.getSelectedItem().toString();

      if ("Aquaponics".equals(room)) {
        SpannableString content =
            new SpannableString(
                "This page provides necessary information and controls for the aquaponics system." 
                    + "\n\n"
                    // the controls
                    + "Controls\n\n"
                    + "This section provides ways to interact with the aquaponics system. "
                    + "The users can set the desired values for different parameters.\n\n"
                    + "Water Temp: Allow the user to change the desired water "
                    + "temperature (°F) for the system.\n"
                    + "pH Level: Allow the user to change the pH level for the system\n"
                    + "Water Level: Allow the user to change the water "
                    + "level (inches) for the system\n"
                    + "Water nutrients: Allow the user to change the "
                    + "water nutrients for the system\n\n"
                    // parameters start from here
                    // ph
                    + "pH\n"
                    + "In chemistry, pH is a measure of the acidity or basicity of a solution."
                    + "Pure water is said to be neutral, with a pH close to 7.0 at 25°C (77°F)"
                    + ". Solutions with a pH less than 7 are said to be acidic and solutions with a"
                    + " pH greater than 7 are said to be basic or alkaline.\n\n"
                    // temperature
                    + "Temperature\n"
                    + "Temperature indicates the degree of hotness or coldness of the water."
                    + "The amount of oxygen which can be dissolved in water depends on "
                    + "temperature. The colder the water the more oxygen it can hold. It's "
                    + "important to keep the temperature within the appropriate range."
                    + "\nUnit used: Fahrenheit (°F)\n\n"
                    // oxygen
                    + "Dissolved Oxygen\n"
                    + "Dissolved oxygen is the amount of oxygen that is dissolved in the water"
                    + ". It's an indication of how well the water can support aquatic"
                    + " plant and animal life. In most cases, higher dissolved oxygen level "
                    + "means better water quality. \nUnit used:"
                    + " mg/l\n\n"// 1377
                    // electrical conductivity
                    + "Electrical conductivity\n"
                    + "Electrical conductivity estimates the amount of total dissolved"
                    + " salts or ions in the water. \nUnit used: µs/cm\n\n"// 1512
                    // circulation
                    + "Circulation\n"
                    + "Circulation indicates the total amount of time the pump has been"
                    + "on in one day divided by the total water volume. \nUnit used: "
                    + "hours/gallons.\n\n"// 1665
                    // turbidity
                    + "Turbidity\n"
                    + "Turbidity is the cloudiness of a fluid caused by individual particles."
                    + "The measurement of turbidity indicates the water quality. Unit used: "
                    + "Formazin Turbidity Unit (FTU).\n\n"/* 1846 */
                    + "Water Level\n"
                    + "The measurement of water level indicates the amount of water in the"
                    + " aquaponics system. \nUnit used: gallons\n\n"// 1966
                    // nutrient
                    + "Nutrient\n"
                    + "The measurement of nutrients indicated the amount of nutrients in the " 
                    + "water.");
        content.setSpan(new UnderlineSpan(), 530, 532, 0);
        content.setSpan(new UnderlineSpan(), 805, 816, 0);
        content.setSpan(new UnderlineSpan(), 1110, 1127, 0);
        content.setSpan(new UnderlineSpan(), 1375, 1398, 0);
        content.setSpan(new UnderlineSpan(), 1510, 1521, 0);
        content.setSpan(new UnderlineSpan(), 1663, 1672, 0);
        content.setSpan(new UnderlineSpan(), 1844, 1855, 0);
        content.setSpan(new UnderlineSpan(), 1964, 1972, 0);
        helpContent.setText(content);
      }
      else if ("Lighting".equals(room)) {
        helpContent
            .setText("This page allows the user to control the lighting (e.g. light switch"
                + ", light brightness, light color) in each room.\n\n"
                + "The page provides a drop-down menu in which you can choose the room.\n\n"
                + "After choosing the desired room there is a button for turning on and off the "
                + "lights, a color picker, and a slider to control the brightness of "
                + "the lights in the room.");
      }
      else if ("Hvac".equals(room)) {
        helpContent.setText("This page displays the current home temperature and allows the user " 
            + "to control the HVAC system's ability to maintain the home at a user-specified " 
            + "temperature.");
      }
      else if ("Energy".equals(room)) {
        helpContent
            .setText("This page gives feedback on the energy consumption and generation of the "
            + "house.");
      }
      else {
        helpContent.setText("wtf");
      }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
      // Do nothing.
    }
  }

}
