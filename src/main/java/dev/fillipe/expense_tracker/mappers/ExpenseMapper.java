package dev.fillipe.expense_tracker.mappers;

import dev.fillipe.expense_tracker.dto.output.ExpenseDTO;
import dev.fillipe.expense_tracker.models.Expense;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface ExpenseMapper extends Converter<Expense, ExpenseDTO> {
    ExpenseDTO convert (Expense expense);

}
