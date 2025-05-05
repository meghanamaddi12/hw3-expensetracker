package model.Filter;

import java.util.ArrayList;
import java.util.List;

import model.Transaction;
import controller.InputValidation;

/**
 * A concrete implementation of the TransactionFilter interface that filters
 * transactions based on a specified category.
 */
public class CategoryFilter implements TransactionFilter {
    private String categoryFilter;

    /**
     * Constructs a CategoryFilter with the specified category.
     * Validates the category using InputValidation.
     *
     * @param categoryFilter the category to filter transactions by
     * @throws IllegalArgumentException if the category is invalid
     */

    public CategoryFilter(String categoryFilter) {
        // Since the CategoryFilter constructor is public, 
        // the input validation needs to be performed again.
        if(!InputValidation.isValidCategory(categoryFilter)){
            throw new IllegalArgumentException("Invalid category filter");
        }else{
            this.categoryFilter = categoryFilter;
        }
    }

    /**
     * Filters the given list of transactions to include only those
     * with a category that matches the filter value (case-insensitive).
     *
     * @param transactions the list of transactions to filter
     * @return a list of transactions that match the filter category
     * @throws IllegalArgumentException if the transactions list is null
     */
    @Override
    public List<Transaction> filter(List<Transaction> transactions) {
	// Perform input validation
        if (transactions == null) {
            throw new IllegalArgumentException("The transactions list must be non-null.");
	}
	
        List<Transaction> filteredTransactions = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getCategory().equalsIgnoreCase(categoryFilter)) {
                filteredTransactions.add(transaction);
            }
        }

        return filteredTransactions;
    }
}
