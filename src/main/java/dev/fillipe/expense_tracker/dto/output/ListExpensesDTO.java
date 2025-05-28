package dev.fillipe.expense_tracker.dto.output;
import java.util.List;

public record ListExpensesDTO(String userId, String userEmail, List<ExpenseDTO> expenses) {
}
