package edu.hawaii.systemh.housesimulator.aquaponics;

import java.util.Random;

/**
 * Provides a fish tank object to be implemented by AquaponicsData for simulation purposes.
 * @author Anthony Kinsey, Michael Cera
 * @author Christopher Ramelb, David Lin, Leonardo Nguyen, Nathan Dorman
 * @author Tony Gaskell
 */
public class FishTank {

  /** Random generator. */
  private static final Random randomGen = new Random();
  
  /** Represents the circulation level of the tank in Gallons Per Minute (GPM). */
  private double circulation;
  /** Represents the electrical conductivity level of the tank in µS/cm. */
  private double electricalConductivity;
  /** Represents the temperature of the tank in Celcius. */
  private int temperature;
  /** Represents the turbidity level of the tank in Nephelometric Turbidity Units (NTU). */
  private double turbidity;
  /** Represents the water level of the tank in inches. */
  private int waterLevel;
  /** Represents the pH level of the tank. */
  private double pH;
  /** Represents the dissolved oxygen level of the tank in mg/l. */
  private double oxygen;
  /** Represents all fish in tank. */
  public Fish[] school;
  
  // Safe condition ranges for fish to live.
  /** Minimum electrical conductivity in us/cm. */
  private static double minEC = 10.0;
  /** Maximum electrical conductivity in us/cm. */
  private static double maxEC = 20.0;
  /** Minimum water temperature in Celsius. */
  private static int minTemp = 20;
  /** Maximum water temperature in Celsius. */
  private static int maxTemp = 45;
  /** Minimum water level in inches. */
  private static int minWater = 36;
  /** Maximum water level in inches. */
  private static int maxWater = 48;
  /** Minimum water PH. */
  private static double minPH = 6.8;
  /** Maximum water PH. */
  private static double maxPH = 8.0;
  /** Minimum oxygen level in mg/l. */
  private static double minOxygen = 4.5;
  /** Maximum oxygen level in mg/l. */
  private static double maxOxygen = 5.5;
  /** Minimum water circulation in gpm. */
  private static double minCirc = 60.0;
  /** Maximum water circulation in gpm. */
  private static double maxCirc = 100.0;

  
  /**
   * Constructor.
   * @param fishCount the initial number of fish in the tank.
   * @param fishHealth the initial health of fish in the tank.
   */
  public FishTank(int fishCount, int fishHealth) {
    this.school = new Fish[fishCount];
    for (int i = 0; i < school.length; ++i) {
      school[i] = new Fish(fishHealth);
    }
    // Set all measured values to be average.
    this.circulation = 80.0;
    this.electricalConductivity = 15.0;
    this.pH = 7.0;
    this.oxygen = 5.0;
    this.temperature = 32;
    this.turbidity = 50.0;
    this.waterLevel = 42;
  }

  /**
   * Feeds the fish in the tank.
   */
  public void feedFish() {
    for (Fish x : school) {
      x.feed();
    }
  }
  
  /**
   * Check the pH of the tank.
   */
  public void checkPH() {
    for (Fish x : school) {
      // 25% chance fish excrete
      if (x.getHealth() > 0 && randomGen.nextInt(2) + randomGen.nextInt(2) == 1 
            && x.excrete()) {
        pH -= (double) 1 / (getLiveFish() + getDeadFish());
        electricalConductivity += (double) 1 / (getLiveFish() + getDeadFish());
      }
    }
  }
  
  /**
   * Returns the number of dead fish in this tank.
   * @return deadFish the number of dead fish in this tank.
   */
  public int getDeadFish() {
    int deadFish = 0;
    for (Fish x : this.school) {
      if (x != null && x.getHealth() <= 0) {
        deadFish++;
      }
    }
    return deadFish;
  }
  
  /**
   * Returns the number of live fish in this tank.
   * @return liveFish the number of live fish in this tank.
   */
  public int getLiveFish() {
    int liveFish = 0;
    for (Fish x : this.school) {
      if (x != null && x.getHealth() > 0) {
        liveFish++;
      }
    }
    return liveFish;
  }
  
  /**
   * Returns the circulation reading of this tank.
   * @return circulation the current circulation rate of this tank.
   */
  public double getCirculation() {
    return this.circulation;
  }
  
  /**
   * Returns the electrical conductivity reading of this tank.
   * @return electricalConductivity the current electrical conductivity level of this tank.
   */
  public double getElectricalConductivity() {
    return this.electricalConductivity;
  }
  
  /**
   * Returns the pH reading of this tank.
   * @return pH the current pH level of this tank.
   */
  public double getPH() {
    return this.pH;
  }
  
  /**
   * Returns the dissolved oxygen reading of this tank.
   * @return disolvedOxygen current dissolved oxygen level of this tank.
   */
  public double getOxygen() {
    return this.oxygen;
  }
  
  /**
   * Returns the temperature of this tank.
   * @return temperature the current temperature of this tank in Celcius.
   */
  public int getTemperature() {
    return this.temperature;
  }
  
  /**
   * Returns the turbidity reading of this tank.
   * @return turbidity the current turbidity level of this tank.
   */
  public double getTurbidity() {
    return this.turbidity;
  }
  
  /**
   * Returns the water level of this tank.
   * @return water current water level of this tank.
   */
  public int getWaterLevel() {
    return this.waterLevel;
  }
  
  /**
   * Change water circulation.
   */
  public void changeCirculation() {
    
    if (circulation < minCirc) {
      // Fish need more circulation and oxygen!!! Increment circulation by 0.0 to 1.0 units.
      circulation += randomGen.nextDouble();
    }
    else if (circulation > maxCirc) {
      // Too much oxygen!!! Decrement circulation by 0.0 to 1.0 units.
      circulation -= randomGen.nextDouble();
    }
    else {
      // Increment or decrement circulation by 0.0 to 1.0 units to reflect randomness.
      circulation += randomGen.nextDouble() - randomGen.nextDouble();
    }
  }
  
  /**
   * Change number of dead fish according to current conditions.
   */
  public void changeDeadFish() {
    if (((electricalConductivity < minEC) || (electricalConductivity > maxEC) 
        || (temperature < minTemp) || (temperature > maxTemp) 
        || (waterLevel < minWater) || (waterLevel > maxWater) || (pH < minPH) || (pH > maxPH)
        || (oxygen < minOxygen) || (oxygen > maxOxygen))
        && (getLiveFish() > 0)) {
      for (Fish x : school) {
        // On average, 1 fish will be inflicted with damage
        if (randomGen.nextInt(getLiveFish()) == 1) {
          x.decrementHealth();
        }
        if (x.getHealth() == 1 && randomGen.nextInt(2) == 1) {
          // 50% chance of 1 fish dying.
          x.decrementHealth();
          // Each dead fish increases electrical conductivity and pH by 0.3
          electricalConductivity += 0.3;
        }
      }
    }
  }
  
  /**
   * Change water oxygen.  It correlates with min and max circulation that were set as safe ranges.
   * For example, if circulation is 60 then oxygen is 5.5.  If circulation is 100, then oxygen
   *  is 6.5.  If circulation is 80, then oxygen is 6.0.
   */
  public void changeOxygen() {
    oxygen =
        (maxOxygen - minOxygen) * ((circulation - minCirc) / (maxCirc - minCirc)) + minOxygen;
  }
  
  /**
   * Change water temperature.
   * @param desiredTemperature the target temperature level.
   */
  public void changeTemperature(int desiredTemperature) {
    if (temperature < desiredTemperature) {
      // 25% chance of incrementing by 1 degree.
      temperature += randomGen.nextInt(2) * randomGen.nextInt(2);
    }
    else if (temperature > desiredTemperature) {
      // 25% chance of decrementing by 1 degree.
      temperature -= randomGen.nextInt(2) * randomGen.nextInt(2);
    }
    else if (temperature == desiredTemperature) {
      // 12.5% chance of incrementing or decrementing 1 degree to reflect randomness.
      temperature +=
          (randomGen.nextInt(2) * randomGen.nextInt(2) * randomGen.nextInt(2))
              - (randomGen.nextInt(2) * randomGen.nextInt(2) * randomGen.nextInt(2));
    }
  }
  
  /**
   * Harvests fish from tank.
   * @param numFish the amount of fish to harvest.
   */
  public void harvestFish(int numFish) {
    int fishCount = numFish;
    for (Fish x : school) {
      if (fishCount > 0) {
        if (x != null) {
          x = null;
          --fishCount;
        }
      }
      else {
        break;
      }
    }
  }
  
  /**
   * Change water level based on desired water level.
   * The change in water level causes a direct change to both oxygen and circulation levels.
   * @param desiredWaterLevel the target water level.
   */
  public void changeWaterLevel(double desiredWaterLevel) {
    double previousWaterLevel = waterLevel;
    if (waterLevel < desiredWaterLevel) {
      // 50% chance to increment water_level by 0 to 2 units.
      waterLevel += randomGen.nextInt(2) + randomGen.nextInt(2);
    }
    else if (waterLevel > desiredWaterLevel) {
      // 50% chance to decrement water_level by 0 to 2 units.
      waterLevel -= randomGen.nextInt(2) + randomGen.nextInt(2);
    }
    else {
      // 50% chance of Increment or decrement water by 0 to 1 unit to reflect randomness.
      waterLevel += randomGen.nextInt(2) * (randomGen.nextInt(2) - randomGen.nextInt(2));
    }
    circulation = (waterLevel / previousWaterLevel) * circulation;
    oxygen = (waterLevel / previousWaterLevel) * oxygen;
  }
  
  /**
   * Change water turbidity.  This equation will probably need tweaking later on.
   */
  public void changeTurbidity() {
    turbidity = (electricalConductivity * 2) + getLiveFish() + (getDeadFish() * 5)
                - (circulation * 0.1);
  }
  
  /**
   * Change electrical conductivity.
   * @param desiredElectricalConductivity the target electrical conductivity level.
   */
  public void changeElectricalConductivity(double desiredElectricalConductivity) {
    if (electricalConductivity < desiredElectricalConductivity) {
      // 25% chance of incrementing by 0.05
      electricalConductivity += (randomGen.nextInt(2) * randomGen.nextInt(2)) * 0.05;
    }
    else if (electricalConductivity > desiredElectricalConductivity) {
      // 50% chance of decrementing by 0.05. EC declines as nutrients and food gets used up.
      electricalConductivity -= (randomGen.nextInt(2)) * 0.05;
    }
    else {
      // 12.5% chance of incrementing or decrementing 0.05 to reflect randomness.
      electricalConductivity +=
          ((randomGen.nextInt(2) * randomGen.nextInt(2) * randomGen.nextInt(2)) - (randomGen
              .nextInt(2) * randomGen.nextInt(2) * randomGen.nextInt(2))) * 0.05;
    }
  }
  
  /**
   * Change water PH.
   * @param desiredPH the target pH level.
   */
  public void changePH(double desiredPH) {
    if (pH < desiredPH) {
      // 25% chance of incrementing by 0.1
      pH += (randomGen.nextInt(2)) * (randomGen.nextInt(2)) * 0.1;
    }
    else if (pH > desiredPH) {
      // 25% chance of decrementing by 0.1
      pH -= (randomGen.nextInt(2)) * (randomGen.nextInt(2)) * 0.1;
    }
    else {
      // 12.5% chance of incrementing or decrementing 0.1 from desired PH.
      pH +=
          ((randomGen.nextInt(2) * randomGen.nextInt(2) * randomGen.nextInt(2)) - (randomGen
              .nextInt(2) * randomGen.nextInt(2) * randomGen.nextInt(2))) * 0.1;
    }
  }

  /**
   * Resets the desired system state values randomly to within acceptable safe ranges automatically
   * according to the conditions set above. Try not to touch these formulas!
   */
  public void resetDesiredStates() {
    AquaponicsData.setNutrients((randomGen.nextDouble() * (maxEC - minEC)) + minEC);
    AquaponicsData.setDesiredTemperature(randomGen.nextInt(maxTemp - minTemp + 1) + minTemp);
    AquaponicsData.setDesiredWaterLevel((randomGen.nextInt(maxWater - minWater + 1)) + minWater);
    AquaponicsData.setDesiredPH((randomGen.nextDouble() * (maxPH - minPH)) + minPH);
  }
}
