import javax.swing.JOptionPane;
import controller.ExpenseTrackerController;
import model.ExpenseTrackerModel;
import view.ExpenseTrackerView;
import model.Filter.AmountFilter;
import model.Filter.CategoryFilter;

/**
 * Main entry point for the Expense Tracker application.
 *
 * <p>Initializes the MVC components and sets up action listeners
 * for user interaction with the GUI.
 */
public class ExpenseTrackerApp {

    /**
     * Launches the Expense Tracker application.
     *
     * @param args command-line arguments (not used)
     */
  public static void main(String[] args) {


      ExpenseTrackerModel model = new ExpenseTrackerModel();
      ExpenseTrackerController controller = new ExpenseTrackerController(model);
      ExpenseTrackerView view = new ExpenseTrackerView(controller);
      controller.setView(view);
      view.setVisible(true);

      // Add listener for "Remove Selected Transaction" button
      view.addRemoveSelectedListener(e -> {
          int selectedRow = view.getTransactionsTable().getSelectedRow();
          controller.removeTransactionAt(selectedRow);
      });




      // Handle add transaction button clicks
    view.getAddTransactionBtn().addActionListener(e -> {
      // Get transaction data from view
      double amount = view.getAmountField();
      String category = view.getCategoryField();
      
      // Call controller to add transaction
      boolean added = controller.addTransaction(amount, category);
      
      if (!added) {
        JOptionPane.showMessageDialog(view, "Invalid amount or category entered");
        view.toFront();
      }
    });

      // Add action listener to the "Apply Category Filter" button
    view.addApplyCategoryFilterListener(e -> {
      try{
      String categoryFilterInput = view.getCategoryFilterInput();
      CategoryFilter categoryFilter = new CategoryFilter(categoryFilterInput);
      if (categoryFilterInput != null) {
          // controller.applyCategoryFilter(categoryFilterInput);
          controller.setFilter(categoryFilter);
          controller.applyFilter();
      }
     }catch(IllegalArgumentException exception) {
    JOptionPane.showMessageDialog(view, exception.getMessage());
    view.toFront();
   }});


    // Add action listener to the "Apply Amount Filter" button
    view.addApplyAmountFilterListener(e -> {
      try{
      double amountFilterInput = view.getAmountFilterInput();
      AmountFilter amountFilter = new AmountFilter(amountFilterInput);
      if (amountFilterInput != 0.0) {
          controller.setFilter(amountFilter);
          controller.applyFilter();
      }
    }catch(IllegalArgumentException exception) {
    JOptionPane.showMessageDialog(view,exception.getMessage());
    view.toFront();
   }});


   // Add action listener to the "Clear Filter" button
   view.addClearFilterListener(e -> {
     controller.setFilter(null);
     controller.applyFilter();
   });
      // Add action listener to the "Undo Last Transaction" button
      view.addUndoListener(e -> {
          controller.undoLastTransaction();
      });

  }
    /**
     * Default constructor. Not used — all logic is in the static main method.
     */
    public ExpenseTrackerApp() {
        // No initialization required
    }

}
