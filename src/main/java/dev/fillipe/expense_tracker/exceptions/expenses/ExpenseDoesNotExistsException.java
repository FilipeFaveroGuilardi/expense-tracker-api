package dev.fillipe.expense_tracker.exceptions.expenses;

import lombok.Getter;

@Getter
public class ExpenseDoesNotExistsException extends RuntimeException {
    private final String message;

    public ExpenseDoesNotExistsException() {
        this.message = "Expense does not exists";
    }

    public ExpenseDoesNotExistsException(String message) {
        super(message);
        this.message = message;
    }
}
