package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import model.Transaction;
import controller.ExpenseTrackerController;




/**
 * View class in the MVC architecture for the Expense Tracker application.
 *
 * <p>Handles the graphical user interface using Swing. Allows users to input transactions,
 * apply filters, and view the transaction table.
 */
public class ExpenseTrackerView extends JFrame {

  /** Table to display the list of transactions. */
  private JTable transactionsTable;
  /** Button to add a new transaction. */
  private JButton addTransactionBtn;
  /** Button to undo the last added transaction. */
  private JButton undoBtn;
  private JButton removeSelectedBtn;
  /** Input field for entering the transaction amount. */
  private JFormattedTextField amountField;
  /** Input field for entering the transaction category. */
  private JTextField categoryField;
  /** Table model backing the transactions table. */
  private DefaultTableModel model;

  /** Input field for filtering by category. */
  private JTextField categoryFilterField;
  /** Button to apply category filter. */
  private JButton categoryFilterBtn;

  /** Input field for filtering by amount. */
  private JTextField amountFilterField;
  /** Button to apply amount filter. */
  private JButton amountFilterBtn;

  /** Button to clear applied filters. */
  private JButton clearFilterBtn;

  /** The list of currently displayed transactions. */
  private List<Transaction> displayedTransactions = new ArrayList<>();//  Moved here
  private ExpenseTrackerController controller;

  /**
   * Constructs and initializes the Expense Tracker GUI window.
   * Sets up input fields, buttons, layout, and action regions.
   */

  public ExpenseTrackerView(ExpenseTrackerController controller) {
    this.controller = controller;
    setTitle("Expense Tracker");
    setSize(600, 400);

    String[] columnNames = {"serial", "Amount", "Category", "Date"};
    this.model = new DefaultTableModel(columnNames, 0);

    transactionsTable = new JTable(model);
    addTransactionBtn = new JButton("Add Transaction");

    // Initialize undo button and disable it by default (undo not allowed initially)
    undoBtn = new JButton("Undo Last Transaction");
    removeSelectedBtn = new JButton("Remove Selected Transaction");
    undoBtn.setEnabled(false); // initially disabled
    undoBtn.addActionListener(e -> {
      // You may need to pass controller reference to the view first!
      boolean success = controller.undoLastTransaction();
      if (!success) {
        JOptionPane.showMessageDialog(this, "Nothing to undo.");
      }
      updateUndoButtonState();
    });


    JLabel amountLabel = new JLabel("Amount:");
    NumberFormat format = NumberFormat.getNumberInstance();
    amountField = new JFormattedTextField(format);
    amountField.setColumns(10);

    JLabel categoryLabel = new JLabel("Category:");
    categoryField = new JTextField(10);

    JLabel categoryFilterLabel = new JLabel("Filter by Category:");
    categoryFilterField = new JTextField(10);
    categoryFilterBtn = new JButton("Filter by Category");

    JLabel amountFilterLabel = new JLabel("Filter by Amount:");
    amountFilterField = new JTextField(10);
    amountFilterBtn = new JButton("Filter by Amount");

    clearFilterBtn = new JButton("Clear Filter");

    JPanel inputPanel = new JPanel();
    inputPanel.add(amountLabel);
    inputPanel.add(amountField);
    inputPanel.add(categoryLabel);
    inputPanel.add(categoryField);
    inputPanel.add(addTransactionBtn);

    JPanel buttonPanel = new JPanel();
    buttonPanel.add(amountFilterBtn);
    buttonPanel.add(categoryFilterBtn);
    buttonPanel.add(clearFilterBtn);
    buttonPanel.add(undoBtn);
    buttonPanel.add(removeSelectedBtn);



    add(inputPanel, BorderLayout.NORTH);
    add(new JScrollPane(transactionsTable), BorderLayout.CENTER);
    add(buttonPanel, BorderLayout.SOUTH);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  /**
   * Returns the table model used to store transaction data.
   *
   * @return the DefaultTableModel of the table
   */
  public DefaultTableModel getTableModel() {
    return model;
  }

  /**
   * Returns the table component used to display transactions.
   *
   * @return the JTable used in the view
   */
  public JTable getTransactionsTable() {
    return transactionsTable;
  }

  /**
   * Returns the numeric input value from the amount field.
   *
   * @return the amount entered, or 0 if empty
   */
  public double getAmountField() {
    if (amountField.getText().isEmpty()) {
      return 0;
    } else {
      return Double.parseDouble(amountField.getText());
    }
  }

  /**
   * Sets the amount input field.
   *
   * @param amountField the formatted amount input field
   */
  public void setAmountField(JFormattedTextField amountField) {
    this.amountField = amountField;
  }

  /**
   * Returns the category entered by the user.
   *
   * @return the category text
   */
  public String getCategoryField() {
    return categoryField.getText();
  }

  /**
   * Sets the category input field.
   *
   * @param categoryField the text field for category input
   */
  public void setCategoryField(JTextField categoryField) {
    this.categoryField = categoryField;
  }

  /**
   * Registers a listener for the category filter button.
   *
   * @param listener the ActionListener to attach
   */
  public void addApplyCategoryFilterListener(ActionListener listener) {
    categoryFilterBtn.addActionListener(listener);
  }

  /**
   * Prompts the user to input a category filter.
   *
   * @return the entered category filter string
   */
  public String getCategoryFilterInput() {
    return JOptionPane.showInputDialog(this, "Enter Category Filter:");
  }

  /**
   * Registers a listener for the amount filter button.
   *
   * @param listener the ActionListener to attach
   */
  public void addApplyAmountFilterListener(ActionListener listener) {
    amountFilterBtn.addActionListener(listener);
  }

  /**
   * Prompts the user to input an amount filter.
   *
   * @return the entered amount as a double, or 0.0 if invalid
   */
  public double getAmountFilterInput() {
    String input = JOptionPane.showInputDialog(this, "Enter Amount Filter:");
    try {
      return Double.parseDouble(input);
    } catch (NumberFormatException e) {
      return 0.0;
    }
  }

  /**
   * Registers a listener for the clear filter button.
   *
   * @param listener the ActionListener to attach
   */
  public void addClearFilterListener(ActionListener listener) {
    clearFilterBtn.addActionListener(listener);
  }
  /**
   * Registers a listener for the Undo button.
   *
   * @param listener the ActionListener to be triggered on click
   */
  public void addUndoListener(ActionListener listener) {
    undoBtn.addActionListener(listener);
  }
  /**
   * Registers a listener for the "Remove Selected Transaction" button.
   *
   * @param listener the ActionListener to be triggered on click
   */
  public void addRemoveSelectedListener(ActionListener listener) {
    removeSelectedBtn.addActionListener(listener);
  }



  /**
   * Refreshes the transaction table with a new list of transactions.
   *
   * @param transactions the list of transactions to display
   */
  public void refreshTable(List<Transaction> transactions) {
    model.setRowCount(0);
    this.displayedTransactions = transactions; // ✅ Track displayed transactions

    int rowNum = model.getRowCount();
    double totalCost = 0;

    for (Transaction t : transactions) {
      totalCost += t.getAmount();
    }

    for (Transaction t : transactions) {
      model.addRow(new Object[]{++rowNum, t.getAmount(), t.getCategory(), t.getTimestamp()});
    }

    model.addRow(new Object[]{"Total", null, null, totalCost});
    transactionsTable.updateUI();
  }

  /**
   * Returns the "Add Transaction" button.
   *
   * @return the JButton instance
   */
  public JButton getAddTransactionBtn() {
    return addTransactionBtn;
  }

  /**
   * Displays a filtered list of transactions in the table.
   *
   * @param filteredTransactions the filtered list to display
   */
  public void displayFilteredTransactions(List<Transaction> filteredTransactions) {
    refreshTable(filteredTransactions);
  }

  /**
   * Returns the list of currently displayed transactions.
   *
   * @return the displayed transactions
   */
  public List<Transaction> getDisplayedTransactions() {
    return displayedTransactions;
  }

  // Optional: remove if no longer needed
  // public void highlightRows(List<Integer> rowIndexes) { ... }

  // public void highlightRows(List<Integer> rowIndexes) {
  //     // The row indices are being used as hashcodes for the transactions.
  //     // The row index directly maps to the the transaction index in the list.
  //     transactionsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
  //         @Override
  //         public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
  //                                                       boolean hasFocus, int row, int column) {
  //             Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
  //             if (rowIndexes.contains(row)) {
  //                 c.setBackground(new Color(173, 255, 168)); // Light green
  //             } else {
  //                 c.setBackground(table.getBackground());
  //             }
  //             return c;
  //         }
  //     });

  //     transactionsTable.repaint();
  // }
  /**
   * Sets the enabled state of the Undo button based on whether undo is allowed.
   *
   * @param canUndo true to enable the Undo button, false to disable it
   */
  public void setUndoEnabled(boolean canUndo) {
    undoBtn.setEnabled(canUndo);
  }
  /**
   * Updates the Undo button state based on whether undo is possible.
   */
  public void updateUndoButtonState() {
    setUndoEnabled(controller.canUndo());
  }



}
