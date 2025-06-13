package ffg.fillipe.expense_tracker.dto.input;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateExpenseDTO(String description, Double amount, String date) {
    public CreateExpenseDTO(Double amount, String date) {
        this(null, amount, date);
    }
}
