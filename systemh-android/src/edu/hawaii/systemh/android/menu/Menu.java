package edu.hawaii.systemh.android.menu;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import edu.hawaii.systemh.android.R;
import edu.hawaii.systemh.android.aquaponics.Aquaponics;
import edu.hawaii.systemh.android.help.Help;
import edu.hawaii.systemh.android.hvac.Hvac;
import edu.hawaii.systemh.android.lighting.Lighting;

public class Menu extends Activity {

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // requesting to turn the title OFF
    requestWindowFeature(Window.FEATURE_NO_TITLE);

    // making it full screen
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);

    setContentView(R.layout.menu);
  }
  
  /**
   * Take user to the aquaponics page.
   * @param view The view
   */
  public void showAquaponicsPage(View view) {
    Intent intent = new Intent(Intent.ACTION_VIEW);
    intent.setClassName(this, Aquaponics.class.getName());
    startActivity(intent);
  }
  
  /**
   * Take user to the lighting page.
   * @param view The view
   */
  public void showLightingPage(View view) {
    Intent intent = new Intent(Intent.ACTION_VIEW);
    intent.setClassName(this, Lighting.class.getName());
    startActivity(intent);
  }
  
  /**
   * Take user to the Hvac page.
   * @param view The view
   */
  public void showHvacPage(View view) {
    Intent intent = new Intent(Intent.ACTION_VIEW);
    intent.setClassName(this, Hvac.class.getName());
    startActivity(intent);
  }
  
  /**
   * Take user to the Help page.
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
   * Ignore screen orientation change.
   */
  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
  }
}
