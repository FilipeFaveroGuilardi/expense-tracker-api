package ffg.fillipe.expense_tracker.dto.output;

import java.time.LocalDate;
import java.util.UUID;

public record ExpenseDTO(Long id, String description, Double amount, LocalDate date) {
    public ExpenseDTO(Long id, Double amount, LocalDate date) {
        this(id, null, amount, date);
    }
}
