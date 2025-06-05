package ffg.fillipe.expense_tracker.dto.input;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateExpenseDTO(String description, BigDecimal amount, String date) {
    public CreateExpenseDTO(BigDecimal price, String date) {
        this(null, price, date);
    }
}
