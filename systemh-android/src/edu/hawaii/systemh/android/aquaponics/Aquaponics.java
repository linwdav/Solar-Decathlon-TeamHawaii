package edu.hawaii.systemh.android.aquaponics;

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
 * Activity to start the aquaponics page.
 * 
 * @author Group H
 * 
 */
public class Aquaponics extends Activity {

    SeekBar temp;
    SeekBar ph;
    SeekBar level;
    SeekBar nutrients;

    TextView tempValue;
    TextView phValue;
    TextView levelValue;
    TextView nutrientsValue;
    
    TextView tempData;
    TextView phData;
    TextView ecData;
    TextView oxygenData;
    TextView levelData;
    TextView circulationData;
    TextView turbidityData;
    
    // Store new value to change
    int newTemp = 0;

    /**
     * Called when the activity is first created.
     * 
     * @param savedInstanceState
     *            - A mapping from String values to various Parcelable types.
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

        /** Get Data Values for Aquaponic System **/
        final SystemData aquaponics = new SystemData("aquaponics");
        
        newTemp = (int) aquaponics.getTemp();
        tempData = (TextView) findViewById(R.id.tempDataValue);
        tempData.setText(String.valueOf(newTemp) + "\u00b0F");
        
        phData = (TextView) findViewById(R.id.phDataValue);
        phData.setText(String.valueOf(aquaponics.getPh()));
        
        ecData = (TextView) findViewById(R.id.ecDataValue);
        ecData.setText(String.valueOf(aquaponics.getElectricalConductivity()) + " \u00b5s/cm");
        
        oxygenData = (TextView) findViewById(R.id.oxygenDataValue);
        oxygenData.setText(String.valueOf(aquaponics.getOxygen()) + " mg/l");
        
        levelData = (TextView) findViewById(R.id.levelDataValue);
        levelData.setText(String.valueOf(aquaponics.getWaterLevel()) + " in");
        
        circulationData = (TextView) findViewById(R.id.circulationDataValue);
        circulationData.setText(String.valueOf(aquaponics.getCirculation()) + " gpm");
        
        turbidityData = (TextView) findViewById(R.id.turbidityDataValue);
        turbidityData.setText(String.valueOf(aquaponics.getTurbidity()) + " NTUs");
        
        /** Water Temperature Slider Control**/
        temp = (SeekBar) this.findViewById(R.id.tempSeekbar);
        temp.setMax(110);
        temp.setProgress(newTemp);
        
        tempValue = (TextView) this.findViewById(R.id.tempValue);
        tempValue.setText(String.valueOf(newTemp));
        
        temp.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                    boolean fromUser) {
                
                tempValue.setText(String.valueOf(newTemp));
                newTemp = progress;
                
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
               
                aquaponics.setAquaponicsTemp(newTemp);
            }
        });

        /** PH Level Slider Control**/
        ph = (SeekBar) this.findViewById(R.id.phSeekBar);
        phValue = (TextView) this.findViewById(R.id.phValue);
        ph.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                    boolean fromUser) {
                
                phValue.setText(String.valueOf((float)progress / 100));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });

        /** Water Level Slider Control**/
        level = (SeekBar) this.findViewById(R.id.levelSeekBar);
        levelValue = (TextView) this.findViewById(R.id.levelValue);
        level.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                    boolean fromUser) {
                
                levelValue.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });

        /** Water Nutrients Slider Control **/
        nutrients = (SeekBar) this.findViewById(R.id.nutrientsSeekBar);
        nutrientsValue = (TextView) this.findViewById(R.id.nutrientsValue);
        nutrients
        .setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar,
                    int progress, boolean fromUser) {
                // TODO Auto-generated method stub
                nutrientsValue.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
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
     * Destroys this activity when onStop is called.
     */
    @Override
    protected void onStop() {
        finish();
        super.onDestroy();
    }

}
