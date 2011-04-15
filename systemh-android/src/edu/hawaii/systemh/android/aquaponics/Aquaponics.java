package edu.hawaii.systemh.android.aquaponics;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import edu.hawaii.systemh.android.R;
import edu.hawaii.systemh.android.menu.Menu;

/**
 * Activity to start the aquaponics page.
 * 
 * @author Group H
 *
 */
public class Aquaponics extends Activity {

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

    setContentView(R.layout.aquaponics);
  }

  /**
   * Take the user to the menu.
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
   * Ignore screen orientation change.
   * PMD is giving errors because this method only calls super.
   * @param newConfig - The configuration of the app.
   */
  @Override
  public void onConfigurationChanged(Configuration newConfig) { //NOPMD
    super.onConfigurationChanged(newConfig);
  }

}
