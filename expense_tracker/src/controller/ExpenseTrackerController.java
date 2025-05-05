package controller;

import view.ExpenseTrackerView;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.ExpenseTrackerModel;
import model.Transaction;
import model.Filter.TransactionFilter;

/**
 * Controller class in the MVC architecture.
 * Coordinates interaction between the ExpenseTrackerModel and ExpenseTrackerView.
 * Applies the Strategy design pattern for transaction filtering.
 */
public class ExpenseTrackerController {

  private ExpenseTrackerModel model;
  private ExpenseTrackerView view;
  /** 
   * The Controller is applying the Strategy design pattern.
   * This is the has-a relationship with the Strategy class 
   * being used in the applyFilter method.
   */
  private TransactionFilter filter;

  /**
   * Constructs the controller with the given model.
   * The view will be injected later using setView().
   *
   * @param model the expense tracker model
   */
  public ExpenseTrackerController(ExpenseTrackerModel model) {
    this.model = model;
  }

  /**
   * Sets the view reference after construction.
   * Used when the view is initialized later and needs to be linked to the controller.
   *
   * @param view the expense tracker view
   */
  public void setView(ExpenseTrackerView view) {
    this.view = view;
  }


  /**
   * Sets the filter strategy to be applied on transactions.
   *
   * @param filter the transaction filter strategy
   */
  public void setFilter(TransactionFilter filter) {
    // Sets the Strategy class being used in the applyFilter method.
    this.filter = filter;
  }

  /**
   * Refreshes the view by fetching the latest transactions from the model.
   */
  public void refresh() {
    List<Transaction> transactions = model.getTransactions();
    view.refreshTable(transactions);
  }

  /**
   * Adds a new transaction to the model if valid, and updates the view.
   *
   * @param amount the transaction amount
   * @param category the transaction category
   * @return true if the transaction was added successfully, false otherwise
   */
  public boolean addTransaction(double amount, String category) {
    if (!InputValidation.isValidAmount(amount)) {
      return false;
    }
    if (!InputValidation.isValidCategory(category)) {
      return false;
    }
    
    Transaction t = new Transaction(amount, category);
    model.addTransaction(t);
    view.getTableModel().addRow(new Object[]{t.getAmount(), t.getCategory(), t.getTimestamp()});
    refresh();
    return true;
  }

  /**
   * Applies the currently set filter strategy to the transaction list
   * and updates the view with the filtered results.
   */
  public void applyFilter() {
    List<Transaction> filteredTransactions;
    // If no filter is specified, show all transactions.
    if (filter == null) {
      filteredTransactions = model.getTransactions();
    }
    // If a filter is specified, show only the transactions accepted by that filter.
    else {
      // Use the Strategy class to perform the desired filtering
      List<Transaction> transactions = model.getTransactions();
      filteredTransactions = filter.filter(transactions);
    }
    // Debug output to console — optional but helpful
    System.out.println("Filtered Transactions:");
    for (Transaction t : filteredTransactions) {
      System.out.println(t);
    }
    view.displayFilteredTransactions(filteredTransactions);
  }
  /**
   * Attempts to undo the last transaction.
   * If successful, updates the view and returns true.
   *
   * @return true if a transaction was undone, false otherwise
   */
  public boolean undoLastTransaction() {
    List<Transaction> transactions = model.getTransactions();
    if (!transactions.isEmpty()) {
      Transaction last = transactions.get(transactions.size() - 1);
      model.removeTransaction(last);
      refresh();
      return true;
    }
    return false;
  }
  /**
   * Checks whether an undo operation is currently allowed.
   *
   * @return true if there are transactions to undo, false otherwise
   */
  public boolean canUndo() {
    return !model.getTransactions().isEmpty();
  }
  /**
   * Removes the transaction at the selected row index.
   * Handles invalid selections gracefully.
   *
   * @param rowIndex the index of the row to remove
   */
  public void removeTransactionAt(int rowIndex) {
    List<Transaction> transactions = model.getTransactions();
    if (rowIndex >= 0 && rowIndex < transactions.size()) {
      Transaction toRemove = transactions.get(rowIndex);
      model.removeTransaction(toRemove);
      refresh();
    } else {
      JOptionPane.showMessageDialog(view, "Please select a valid row to remove.");
    }
  }
}

