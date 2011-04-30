package edu.hawaii.systemh.android.aquaponics;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
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
    TextView deadFishData;
    
    Spinner feeds;
    
    // Store new value to change
    int newTemp = 0;
    int fish = 0;
    double newPh = 0;
    double newWaterLevel = 0;
    double newNutrients = 0;
   
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
        tempData.setText(newTemp + "\u00b0F");
        
        newPh = aquaponics.getPh();
        phData = (TextView) findViewById(R.id.phDataValue);
        phData.setText(String.valueOf(newPh));
        
        ecData = (TextView) findViewById(R.id.ecDataValue);
        ecData.setText(aquaponics.getElectricalConductivity() + " \u00b5s/cm");
        
        oxygenData = (TextView) findViewById(R.id.oxygenDataValue);
        oxygenData.setText(aquaponics.getOxygen() + " mg/l");
        
        newWaterLevel = aquaponics.getWaterLevel();
        levelData = (TextView) findViewById(R.id.levelDataValue);
        levelData.setText(newWaterLevel + " in");
        
        circulationData = (TextView) findViewById(R.id.circulationDataValue);
        circulationData.setText(aquaponics.getCirculation() + " gpm");
        
        turbidityData = (TextView) findViewById(R.id.turbidityDataValue);
        turbidityData.setText(aquaponics.getTurbidity() + " NTUs");
        
        deadFishData = (TextView) findViewById(R.id.deadFishDataValue);
        deadFishData.setText(aquaponics.getDeadFish());
        
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
                
                newTemp = progress;
                tempValue.setText(String.valueOf(newTemp));
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
        ph.setMax(1400);
        ph.setProgress((int) (newPh * 100));
        
        phValue = (TextView) this.findViewById(R.id.phValue);
        phValue.setText(String.valueOf(newPh));
        
        ph.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                    boolean fromUser) {
                
                newPh = progress / 100;
                phValue.setText(String.valueOf(newPh));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                
                aquaponics.setPh(newPh);
            }
        });

        /** Water Level Slider Control**/
        level = (SeekBar) this.findViewById(R.id.levelSeekBar);
        level.setMax(10000);
        level.setProgress((int) (newWaterLevel * 100));
        
        levelValue = (TextView) this.findViewById(R.id.levelValue);
        levelValue.setText(String.valueOf(newWaterLevel));
        
        level.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                    boolean fromUser) {
                
                newWaterLevel = progress / 100;
                levelValue.setText(String.valueOf(newWaterLevel));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                
                aquaponics.setWaterLevel(newWaterLevel);
            }
        });

        /** Water Nutrients Slider Control **/
        nutrients = (SeekBar) this.findViewById(R.id.nutrientsSeekBar);
        nutrients.setMax(10000);
        nutrients.setProgress(0);
        
        nutrientsValue = (TextView) this.findViewById(R.id.nutrientsValue);
        nutrients
        .setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar,
                    int progress, boolean fromUser) {
               
                newNutrients = progress / 100;
                nutrientsValue.setText(String.valueOf(newNutrients));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                
                aquaponics.setNutrients(newNutrients);
            }
        });
        
        /** Feed Fish **/
        feeds = (Spinner) findViewById(R.id.feedSpinner);
        ArrayAdapter<CharSequence> adapter =
            ArrayAdapter.createFromResource(this, R.array.feedAmount, R.layout.spinner);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
        feeds.setAdapter(adapter);
        
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
