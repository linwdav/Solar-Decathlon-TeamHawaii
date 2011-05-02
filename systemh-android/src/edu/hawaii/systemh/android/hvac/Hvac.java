package edu.hawaii.systemh.android.hvac;


import java.util.Date;
import org.restlet.data.Status;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;
import edu.hawaii.systemh.android.R;
import edu.hawaii.systemh.android.menu.Menu;
import edu.hawaii.systemh.android.systemdata.SystemData;

/**
 * The activity that starts the hvac page. Note: temperature values are in Celsius as that
 * are the units being returned by the house simulator XMLs.
 * 
 * @author Group H
 *
 */
public class Hvac extends Activity {
  
  /** Displays the desired home temperature SeekBar value. **/
  private TextView desiredHomeTempLabel;  
  /** Controls the desired home temperature. **/
  private SeekBar desiredHomeTempSeekBar;
  /** Displays feedback to when a user has set a desired temperature. **/
  private static TextView desiredHomeTempLog;
  
  //private int currentTemp;
  //private int desiredHomeTemp;
  private static String feedbackMsg = "";
  private SystemData hvac;
  
  /** 
   * Called when the activity is first created. 
   * @param savedInstanceState - A mapping from String values to various Parcelable types. 
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Displays the current home temperature
    TextView currTempTextView;
    int currentTemp;
    
    // Requesting to turn the title OFF.
    requestWindowFeature(Window.FEATURE_NO_TITLE);

    // Making it full screen.
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);

    setContentView(R.layout.hvac);
    
    hvac = new SystemData("hvac");
    
    currTempTextView = (TextView) findViewById(R.id.TextViewCurrTempValue);
    currentTemp = (int) hvac.getTemp();
    currTempTextView.setText(currentTemp + "\u00b0C");

    desiredHomeTempLog = (TextView) findViewById(R.id.TextViewDesiredHomeTempLog);
    if (!"".equals(feedbackMsg)) {
      desiredHomeTempLog.setText(feedbackMsg);
    }
    
    desiredHomeTempLabel = (TextView) findViewById(R.id.TextViewDesiredHomeTempValue);
    
    desiredHomeTempSeekBar = (SeekBar) findViewById(R.id.SeekBarDesiredHomeTemp);
    desiredHomeTempSeekBar.setMax(32);
    desiredHomeTempSeekBar.setProgress(23);
    
    desiredHomeTempSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

      // When a user moves the SeekBar slider controlling the desired home temperature,
      // the value is displayed on the left of the SeekBar within a TextView.
      // Provides feedback on the values selected by the user.
      @Override
      public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int desiredHomeTemp = progress;
        desiredHomeTempLabel.setText(String.valueOf(desiredHomeTemp));
      }

      @Override
      public void onStartTrackingTouch(SeekBar seekBar) {
        // Unimplemented.
      }

      // Sends a PUT request to the back-end to have the HVAC system maintain the home
      // at the specified temperature and report to the UI on success.
      @Override
      public void onStopTrackingTouch(SeekBar seekBar) {
        int tempSet = desiredHomeTempSeekBar.getProgress();
        Status status = hvac.setHvacTemp(tempSet);
        if (status != null && status.equals(Status.SUCCESS_OK)) {
          feedbackMsg += new Date() + " :\t" + tempSet + "\u00b0 has been set.\n";
          desiredHomeTempLog.setText(feedbackMsg);
        }
        else {
          feedbackMsg += new Date() + " :\tThe request has failed.\n";
        }
      }
    });
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
}