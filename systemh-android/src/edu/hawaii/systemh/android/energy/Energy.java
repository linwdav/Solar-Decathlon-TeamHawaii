package edu.hawaii.systemh.android.energy;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
  
  private static final int GENERATION_IDENTIFIER = 0;
  private static final int CONSUMPTION_IDENTIFIER = 1;

  private SystemData electric;
  private SystemData pv;
  
  private TextView electricText;
  private TextView pvText;
  private TextView netPowerText;

  private int currentElectric;
  private int currentPV;
  
  private boolean running = false;
  
  //handler to handle messages from another running thread
  private Handler handler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
      switch (msg.what) {
      case CONSUMPTION_IDENTIFIER:
        currentElectric = (int)electric.getPower();
        updateElectricText();
        break;
        
      case GENERATION_IDENTIFIER:
        currentPV = (int)pv.getEnergy();
        updatePVText();
        break;
        
      default:
        // do nothing
      }
    }
  };

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

    electricText = (TextView) findViewById(R.id.PowerValue);
    pvText = (TextView) findViewById(R.id.PVValue);
    netPowerText = (TextView) findViewById(R.id.NetPowerValue);

    // Get power consumption from electricity and display it
    electric = new SystemData("electric");
    currentElectric = (int) electric.getPower();
    updateElectricText();

    // Get power generation from PV and display it
    pv = new SystemData("photovoltaics");
    currentPV = (int) pv.getEnergy();
    updatePVText();

    updateNetPowerText();
    
    Runnable runnable = new SystemDataRunnable();
    running = true;
    Thread dataGatherThread = new Thread(runnable);
    dataGatherThread.start();
  }

  /**
   * Updates the electric TextView according to the value of currentElectric.
   */
  public void updateElectricText() {
    electricText.setText(String.valueOf(currentElectric));
    electricText.setTextColor(Color.YELLOW);
  }

  /**
   * Updates the PV TextView according to the value of currentPV.
   */
  public void updatePVText() {
    pvText.setText(String.valueOf(currentPV));
    if (currentPV > currentElectric) {
      pvText.setTextColor(Color.GREEN);
    }
    else {
      pvText.setTextColor(Color.RED);
    }
  }

  /**
   * Updates the difference between power generation and consumption.
   */
  public void updateNetPowerText() {
    // Display the difference between power generation and consumption
    int difference = currentPV - currentElectric;
    if (difference > 0) {
      netPowerText.setText("+" + difference);
      netPowerText.setTextColor(Color.GREEN);
    }
    else if (difference == 0) {
      netPowerText.setText(difference);
      netPowerText.setTextColor(Color.CYAN);
    }
    else {
      netPowerText.setText(difference);
      netPowerText.setTextColor(Color.RED);
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
   * Destroys this activity when onStop is called.
   */
  @Override
  protected void onStop() {
    finish();
    super.onDestroy();
  }
  
  @Override
  protected void onPause() {
    running = false;
    super.onStop();
  }

  
  /**
   * A runnable that gathers System data and updates the views on the current page.
   * 
   * @author Group H
   * 
   */
  public class SystemDataRunnable implements Runnable {

    private Message msg;

    @Override
    public void run() {

      while (running) {
        
        try {
          electric = new SystemData("electric");
          pv = new SystemData("photovoltaics");
          
          msg = new Message();
          msg.what = GENERATION_IDENTIFIER;
          handler.sendMessage(msg);
          
          msg = new Message();
          msg.what = CONSUMPTION_IDENTIFIER;
          handler.sendMessage(msg);
          
          Thread.sleep(5000);
        }
        catch (InterruptedException e) {
          e.printStackTrace();
        }

      }
    }
  }
}
