package ffg.fillipe.expense_tracker.mappers;

import ffg.fillipe.expense_tracker.dto.output.ExpenseDTO;
import ffg.fillipe.expense_tracker.models.Expense;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {
    ExpenseDTO convert (Expense expense);

}
