package edu.hawaii.systemh.android.lighting;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import edu.hawaii.systemh.android.R;
import edu.hawaii.systemh.android.menu.Menu;

/**
 * The activity that starts the lighting page.
 * 
 * @author Group H
 * 
 */
public class Lighting extends Activity implements ColorPickerDialog.OnColorChangedListener {

  private View color;
  private SeekBar brightness;

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

    setContentView(R.layout.lighting);

    color = (View) findViewById(R.id.color);

    Spinner spinner = (Spinner) findViewById(R.id.spinner);
    ArrayAdapter<CharSequence> adapter =
        ArrayAdapter.createFromResource(this, R.array.rooms, R.layout.spinner);
    adapter.setDropDownViewResource(R.layout.spinner_dropdown);
    spinner.setAdapter(adapter);
    spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
    
    brightness = (SeekBar) this.findViewById(R.id.brightnessSeekbar);
    
    brightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

      @Override
      public void onProgressChanged(SeekBar seekBar, int progress,
              boolean fromUser) {
          // do nothing
      }

      @Override
      public void onStartTrackingTouch(SeekBar seekBar) {
          // do nothing
      }

      @Override
      public void onStopTrackingTouch(SeekBar seekBar) {
          // send the http command to the lighting system
      }
  });
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
   * Called when the user clicks 'Change color'. The program will show color picker dialog.
   * 
   * @param view The view.
   */
  public void changeColor(View view) {
    ColorPickerDialog colorPicker = new ColorPickerDialog(this, this, "Key", 0, 0);
    colorPicker.show();
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
   * Called after the user clicks 'confirm'.
   * 
   * @param key The key.
   * @param color The color.
   */
  @Override
  public void colorChanged(String key, int color) {

    //int red = Color.red(color);
    //int green = Color.green(color);
    //int blue = Color.blue(color);

    //this.color.setText("#" + Integer.toHexString(red)
    //    + Integer.toHexString(green) + Integer.toHexString(blue));
    //this.color.setTextColor(color);
    this.color.setBackgroundColor(color);

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
      
      // need to read the current color for the selected room.
      // right now just using a default color.    
      color.setBackgroundColor(Color.WHITE);
      
      // set 50 for now...have to read value from the backend later.
      brightness.setProgress(50);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
      // Do nothing.
    }
  }

}
