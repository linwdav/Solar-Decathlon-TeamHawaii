package edu.hawaii.systemh.android.menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import edu.hawaii.systemh.android.R;
import edu.hawaii.systemh.android.aquaponics.Aquaponics;
import edu.hawaii.systemh.android.energy.Energy;
import edu.hawaii.systemh.android.help.Help;
import edu.hawaii.systemh.android.hvac.Hvac;
import edu.hawaii.systemh.android.lighting.Lighting;
import edu.hawaii.systemh.android.settings.Settings;

/**
 * The main activity of this application, called on startup.
 * 
 * @author Group H
 * 
 */
public class Menu extends Activity {

  private static SharedPreferences preferences;
  
  /**
   * Called when the activity is first created.
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

    setContentView(R.layout.menu);
    
    preferences = getSharedPreferences("USER_SETTINGS", 
        Context.MODE_WORLD_READABLE);
    
  }

  /**
   * Take user to the aquaponics page.
   * 
   * @param view The view
   */
  public void showAquaponicsPage(View view) {
    Intent intent = new Intent(Intent.ACTION_VIEW);
    intent.setClassName(this, Aquaponics.class.getName());
    startActivity(intent);
  }

  /**
   * Take user to the lighting page.
   * 
   * @param view The view
   */
  public void showLightingPage(View view) {
    Intent intent = new Intent(Intent.ACTION_VIEW);
    intent.setClassName(this, Lighting.class.getName());
    startActivity(intent);
  }

  /**
   * Take user to the Hvac page.
   * 
   * @param view The view
   */
  public void showHvacPage(View view) {
    Intent intent = new Intent(Intent.ACTION_VIEW);
    intent.setClassName(this, Hvac.class.getName());
    startActivity(intent);
  }
  
  /**
   * Take user to the Energy page.
   * 
   * @param view The view
   */
  public void showEnergyPage(View view) {
    Intent intent = new Intent(Intent.ACTION_VIEW);
    intent.setClassName(this, Energy.class.getName());
    startActivity(intent);
  }

  /**
   * Take user to the Settings page.
   * 
   * @param view The view
   */
  public void showSettingsPage(View view) {
    Intent intent = new Intent(Intent.ACTION_VIEW);
    intent.setClassName(this, Settings.class.getName());
    startActivity(intent);
  }
  
  /**
   * Take user to the Help page.
   * 
   * @param view The view
   */
  public void showHelpPage(View view) {
    Intent intent = new Intent(Intent.ACTION_VIEW);
    intent.setClassName(this, Help.class.getName());
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
   * Accessor method for preferences.
   * @return The preferences associated with app.
   */
  public static SharedPreferences getPreferences() {
    return preferences;
  }
}
