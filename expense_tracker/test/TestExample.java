// package test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import controller.ExpenseTrackerController;
import model.ExpenseTrackerModel;
import model.Transaction;
import view.ExpenseTrackerView;

/**
 * Test class for Expense Tracker application.
 * Contains unit tests for adding and removing transactions,
 * and verifying total cost calculations.
 */
public class TestExample {

    private ExpenseTrackerModel model;
    private ExpenseTrackerView view;
    private ExpenseTrackerController controller;
    /**
     * Initializes the model, view, and controller before each test.
     */
    @Before
    public void setup() {
        model = new ExpenseTrackerModel();
        controller = new ExpenseTrackerController(model);
        view = new ExpenseTrackerView(controller);  // pass initialized controller
        controller.setView(view);
    }

    /**
     * Calculates the total cost of all transactions in the model.
     *
     * @return the sum of transaction amounts
     */
    public double getTotalCost() {
        double totalCost = 0.0;
        List<Transaction> allTransactions = model.getTransactions(); // Using the model's getTransactions method
        for (Transaction transaction : allTransactions) {
            totalCost += transaction.getAmount();
        }
        return totalCost;
    }


    /**
     * Tests adding a transaction through the controller and verifies size and total cost.
     */
    @Test
    public void testAddTransaction() {
        // Pre-condition: List of transactions is empty
        assertEquals(0, model.getTransactions().size());

        // Perform the action: Add a transaction
        assertTrue(controller.addTransaction(50.00, "food"));

        // Post-condition: List of transactions contains one transaction
        assertEquals(1, model.getTransactions().size());

        //print:
        System.out.println("Added Transaction: Amount = 50.0, Category = food");
        System.out.println("Total cost after addition: " + getTotalCost());

        // Check the contents of the list
        assertEquals(50.00, getTotalCost(), 0.01);
    }

    /**
     * Tests removing a transaction from the model and verifies list size and cost.
     */
    @Test
    public void testRemoveTransaction() {
        // Pre-condition: List of transactions is empty
        assertEquals(0, model.getTransactions().size());

        // Perform the action: Add and remove a transaction
        Transaction addedTransaction = new Transaction(50.00, "food");
        model.addTransaction(addedTransaction);

        // Pre-condition: List of transactions contains one transaction
        assertEquals(1, model.getTransactions().size());

        // Perform the action: Remove the transaction
        model.removeTransaction(addedTransaction);

        // Post-condition: List of transactions is empty
        List<Transaction> transactions = model.getTransactions();
        assertEquals(0, transactions.size());

        // print
        System.out.println("Transaction removed. Remaining transactions: " + transactions.size());
        System.out.println("Total cost after removal: " + getTotalCost());

        assertEquals(0.00, getTotalCost(), 0.01);


    }
    /**
     * Tests removing a transaction that does NOT exist in the model.
     * Ensures it does not affect the existing list or throw an error.
     */
    @Test
    public void testRemoveTransactionNotInList() {
        // Setup: Add one transaction
        Transaction existingTransaction = new Transaction(100.0, "food");
        model.addTransaction(existingTransaction);
        assertEquals(1, model.getTransactions().size());

        // Create a different transaction that was never added
        Transaction notInList = new Transaction(200.0, "travel");

        // Execute: Try to remove the non-existent transaction
        model.removeTransaction(notInList);

        // Validate: Model size remains unchanged, original transaction remains
        List<Transaction> transactions = model.getTransactions();
        assertEquals(1, transactions.size());
        assertTrue(transactions.contains(existingTransaction));

        // Print info
        System.out.println("Tried removing a transaction NOT in list. No effect. Size: " + transactions.size());
}

}
