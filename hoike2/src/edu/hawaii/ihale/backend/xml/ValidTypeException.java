package edu.hawaii.ihale.backend.xml;

/**
 * Creates a custom exception to check for valid object types.
 * 
 * @author Bret K. Ikehara
 */
public class ValidTypeException extends Exception {

  /**
   * Serial ID.
   */
  private static final long serialVersionUID = -1328245431900875578L;

  /**
   * Default Constructor.
   */
  public ValidTypeException() {
    super("Invalid argument for this IHaleState.");
  }
  
  /**
   * Throws an valid type error with a custom message.
   * 
   * @param error String
   */
  public ValidTypeException(String error) {
    super(error);
  }
}
