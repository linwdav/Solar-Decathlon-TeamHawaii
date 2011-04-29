package edu.hawaii.systemh.android.settings;

import edu.hawaii.systemh.android.menu.Menu;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import edu.hawaii.systemh.android.R;

/**
 * The activity that starts the settings page.
 * 
 * @author Group H
 * 
 */
public class Settings extends Activity {

  String ipAddress = "127.0.0.1";
  EditText enteredIP;
  SharedPreferences preferences;

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

    setContentView(R.layout.settings);

    // Dislay current ip
    enteredIP = (EditText) findViewById(R.id.edit_ip);
    preferences = getSharedPreferences("USER_SETTINGS", Context.MODE_WORLD_READABLE);

    String ip = preferences.getString("ip_address", null);
    if (ip == null) {
      enteredIP.setText("127.0.0.1");
    }
    else {
      enteredIP.setText(ip);
    }

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
   * Change the ip associated with app.
   * 
   * @param view The view.
   */
  public void changeIP(View view) {
    // Grab input from Text Field.
    enteredIP = (EditText) findViewById(R.id.edit_ip);
    String inputString = enteredIP.getText().toString();

    // Put new username
    SharedPreferences.Editor ed = preferences.edit();
    ed.putString("ip_address", inputString);
    ed.commit();

    // Display confirmation message.
    TextView confirmation_msg = (TextView) findViewById(R.id.confirmation_msg);
    String newIP = preferences.getString("ip_address", "ERROR");
    if ("ERROR".equals(newIP)) {
      confirmation_msg.setText("Error: No IP address was entered.");
      confirmation_msg.setTextColor(Color.RED);
    }
    else {
      confirmation_msg.setText("Changed to " + newIP);
      confirmation_msg.setTextColor(Color.GREEN);
    }
  }
}
