package controller;

import java.util.Arrays;

/**
 * Utility class for validating input values such as amount and category.
 */
public class InputValidation {

  /**
   * Validates whether the given amount is within the valid range (0 &lt; amount &le; 1000).
   *
   * @param amount the amount to validate
   * @return true if valid, false otherwise
   */
  public static boolean isValidAmount(double amount) {
    
    // Check range
    if(amount >1000) {
      return false;
    }
    if (amount < 0){
      return false;
    }
    if (amount == 0){
      return false;
    }
    return true;
  }

  /**
   * Validates whether the given category is non-null, non-empty, alphabetic,
   * and one of the predefined valid categories.
   *
   * @param category the category string to validate
   * @return true if valid, false otherwise
   */
  public static boolean isValidCategory(String category) {

    if(category == null) {
      return false; 
    }
  
    if(category.trim().isEmpty()) {
      return false;
    }

    if(!category.matches("[a-zA-Z]+")) {
      return false;
    }

    String[] validWords = {"food", "travel", "bills", "entertainment", "other"};

    if(!Arrays.asList(validWords).contains(category.toLowerCase())) {
      // invalid word  
      return false;
    }
  
    return true;
  
  }
  /**
   * Private constructor to prevent instantiation of this utility class.
   */
  private InputValidation() {
    // Prevent instantiation
  }
}