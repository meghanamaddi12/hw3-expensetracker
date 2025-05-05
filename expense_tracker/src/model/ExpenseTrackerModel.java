package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The model component of the Expense Tracker application.
 *
 * <p>It maintains the list of transactions and provides methods
 * to add, remove, and retrieve them. This class ensures data integrity
 * using encapsulation and immutability.
 */
public class ExpenseTrackerModel {

  /**
   * Internal list that holds all transactions.
   */
  //encapsulation - data integrity
  private List<Transaction> transactions;

  /**
   * Constructs an empty ExpenseTrackerModel.
   */
  public ExpenseTrackerModel() {
    transactions = new ArrayList<>(); 
  }

  /**
   * Adds a transaction to the model.
   *
   * @param t the transaction to add
   * @throws IllegalArgumentException if the transaction is null
   */
  public void addTransaction(Transaction t) {
    // Perform input validation to guarantee that all transactions added are non-null.
    if (t == null) {
      throw new IllegalArgumentException("The new transaction must be non-null.");
    }
    transactions.add(t);
  }

  /**
   * Removes the specified transaction from the model.
   *
   * @param t the transaction to remove
   */
  public void removeTransaction(Transaction t) {
    transactions.remove(t);
  }

  /**
   * Returns an unmodifiable copy of the transactions list.
   * Ensures external code cannot modify the internal list.
   *
   * @return an unmodifiable list of transactions
   */

  public List<Transaction> getTransactions() {
    //encapsulation - data integrity
    return Collections.unmodifiableList(new ArrayList<>(transactions));
  }

}
