package dev.fillipe.expense_tracker.exceptions.expenses;

import lombok.Getter;

@Getter
public class ExpenseNotOwnedByUserException extends RuntimeException {
    private final String message;

    public ExpenseNotOwnedByUserException() {
        super("Expense not owned by user");
        message = "Expense, not owned by user";
    }

    public ExpenseNotOwnedByUserException(String message) {
        super(message);
        this.message = message;
    }
}
