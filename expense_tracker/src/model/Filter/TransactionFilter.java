package model.Filter;

import java.util.List;

import model.Transaction;

/**
 * Strategy interface for filtering a list of transactions.
 *
 * <p>This interface is used as part of the Strategy Design Pattern.
 * Implementations of this interface define different filtering logic
 * based on criteria such as amount or category.
 */
public interface TransactionFilter {

  /**
   * Filters the given list of transactions according to a specific strategy.
   *
   * @param transactions the list of transactions to filter
   * @return a filtered list of transactions
   */
  public List<Transaction> filter(List<Transaction> transactions);

}
