package edu.hawaii.systemh.android.energy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import edu.hawaii.systemh.android.R;
import edu.hawaii.systemh.android.menu.Menu;
import edu.hawaii.systemh.android.systemdata.SystemData;

/**
 * The activity that starts the help page.
 * 
 * @author Group H
 * 
 */
public class Energy extends Activity {
  
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

    setContentView(R.layout.energy);
    
    SystemData pv = new SystemData("photovoltaics");   
    TextView pvText = (TextView) findViewById(R.id.PVValue);
    int currentPV = (int) pv.getEnergy();
    pvText.setText(currentPV + "");
    
    SystemData electric = new SystemData("electric");
    TextView electricText = (TextView) findViewById(R.id.PowerValue);
    int currentElectric = (int) electric.getPower();
    electricText.setText(currentElectric + "");
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

}
