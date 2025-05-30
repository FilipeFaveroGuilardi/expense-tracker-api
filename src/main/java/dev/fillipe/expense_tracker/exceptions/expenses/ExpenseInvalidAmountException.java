package dev.fillipe.expense_tracker.exceptions.expenses;

import lombok.Getter;

@Getter
public class ExpenseInvalidAmountException extends RuntimeException {
    private final String message;

    public ExpenseInvalidAmountException() {
        message = "Expense amount is invalid";
    }

    public ExpenseInvalidAmountException(String message) {
        super(message);
        this.message = message;
    }
}
