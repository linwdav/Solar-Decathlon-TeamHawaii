package edu.hawaii.systemh.housesimulator.aquaponics;

import java.util.Random;

/**
 * Provides fish objects to be implemented by FishTank for simulation purposes.
 * @author Tony Gaskell
 */
public class Fish {

  /** Random generator. */
  private static final Random randomGen = new Random();
  
  // Attributes
  /** Determines how active the fish is. */
  private boolean active;
  /** When health reaches 0, the fish is dead. */
  private int health;
  /** Stomach contents. */
  private boolean[] stomach = new boolean[3];

  /**
   * Constructs an active fish with passed health parameter.
   * @param x The max health of the fish.
   */
  public Fish(int x) {
    this.active = true;
    this.health = x;
    this.stomach[0] = true;
  }

  // Functions
  /**
   * Switches fish to a higher level of activity.
   */
  public void swim() {
    this.active = true;
  }

  /**
   * Switches fish to a lower level of activity.
   */
  public void sleep() {
    this.active = false;
  }

  /**
   * Feeds the fish.
   */
  public boolean feed() {
    for (int i = 0; i < stomach.length; ++i) {
      if (stomach[i] != true && health > 0) {
        stomach[i] = true;
        this.health += 1;
        return true;
      }
    }
    // 25% chance fish suffers from being overfed.
    this.health -= this.health -= (randomGen.nextInt(2) * randomGen.nextInt(2));
    return false;
  }

  /**
   * Relieves the fish.
   */
  public boolean excrete() {
    // Time-based?
    for (int i = stomach.length - 1; i >= 0; --i) {
      if (stomach[i] != false) {
        stomach[i] = false;
        return true;
      }
    }
    // 3.1% chance that the fish suffers from hunger.
    this.health -= (randomGen.nextInt(2) * randomGen.nextInt(2) * randomGen.nextInt(2)
        * randomGen.nextInt(2) * randomGen.nextInt(2));
    return false;
  }

  // Getters
  /**
   * Returns the activity state of the fish.
   * @return swim True if active, false if inactive.
   */
  public boolean isActive() {
    return this.active;
  }

  /**
   * Returns the health of the fish.
   * @return health The current health of the fish.
   */
  public int getHealth() {
    return this.health;
  }

  // Modifiers
  /**
   * Lowers the health of the fish.
   */
  public void decrementHealth() {
    this.health -= 1;
  }
  
  /**
   * Increases the health of the fish.
   */
  public void incrementHealth() {
    this.health += 1;
  }

}
