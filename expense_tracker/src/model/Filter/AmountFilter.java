package model.Filter;

import java.util.ArrayList;
import java.util.List;

import model.Transaction;
import controller.InputValidation;

/**
 * A concrete implementation of the TransactionFilter interface that filters
 * transactions based on a specific amount.
 */
public class AmountFilter implements TransactionFilter{
    private double amountFilter;

    /**
     * Constructs an AmountFilter with the specified amount.
     * Validates the amount using InputValidation.
     *
     * @param amountFilter the amount to filter transactions by
     * @throws IllegalArgumentException if the amount is invalid
     */
    public AmountFilter(double amountFilter){
        // Since the AmountFilter constructor is public, 
        // the input validation needs to be performed again.
        if(!InputValidation.isValidAmount(amountFilter)){
            throw new IllegalArgumentException("Invalid amount filter");
        } else {
            this.amountFilter = amountFilter;
        }
    }

    /**
     * Filters the given list of transactions to include only those
     * with an amount equal to the specified filter value.
     *
     * @param transactions the list of transactions to filter
     * @return a list of transactions that match the filter amount
     * @throws IllegalArgumentException if the transactions list is null
     */
    @Override
    public List<Transaction> filter(List<Transaction> transactions){
	// Perform input validation
	if (transactions == null) {
	    throw new IllegalArgumentException("The transactions list must be non-null.");
	}
        List<Transaction> filteredTransactions = new ArrayList<>();
        for(Transaction transaction : transactions){
            // Your solution could use a different comparison here.
            if(transaction.getAmount() == amountFilter){
                filteredTransactions.add(transaction);
            }
        }
        return filteredTransactions;
    }
    
}
