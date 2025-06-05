package ffg.fillipe.expense_tracker.exceptions.expenses;

import lombok.Getter;

@Getter
public class ExpenseInvalidDateException extends RuntimeException {
    private final String message;

    public ExpenseInvalidDateException() {
        message = "Expense date is invalid";
    }

    public ExpenseInvalidDateException(String message) {
        super("Expense date is invalid: " + message);
        this.message = "Expense date is invalid: " + message;
    }
}
