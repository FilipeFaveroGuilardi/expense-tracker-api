package dev.fillipe.expense_tracker.controllers;

import dev.fillipe.expense_tracker.dto.input.CreateExpenseDTO;
import dev.fillipe.expense_tracker.dto.output.ListExpensesDTO;
import dev.fillipe.expense_tracker.enums.ExpenseFilterEnum;
import dev.fillipe.expense_tracker.models.Expense;
import dev.fillipe.expense_tracker.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/expense")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;


    @PostMapping
    public ResponseEntity<Void> createExpense(
            @RequestHeader("Authorization") String token,
            @RequestBody CreateExpenseDTO createExpenseDTO
    ) {
        expenseService.createExpense(createExpenseDTO, token);
        return ResponseEntity.status(201).build();
    }

    @GetMapping
    public ResponseEntity<ListExpensesDTO> retrieveExpenses(
            @RequestHeader("Authorization") String token,
            @RequestParam(name = "dateFilter") String dateFilter,
            @RequestParam(name = "startDate", required = false) String startDate,
            @RequestParam(name = "endDate", required = false) String endDate
            ) {
        String filterToUpperCase = dateFilter.toUpperCase();
        ExpenseFilterEnum filter = ExpenseFilterEnum.valueOf(filterToUpperCase);

        LocalDate parsedStartDate = null;
        LocalDate parsedEndDate = null;
        if (startDate != null || endDate != null) {
            parsedStartDate = LocalDate.parse(startDate, Expense.DATE_FORMATTER);
            parsedEndDate = LocalDate.parse(endDate, Expense.DATE_FORMATTER);
        }

        return ResponseEntity.ok(expenseService.retrieveExpenses(token, filter, parsedStartDate, parsedEndDate));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateExpense(
            @RequestHeader("Authorization") String token,
            @PathVariable(name = "id") Long expenseId,
            @RequestParam(name = "date", required = false) String date,
            @RequestParam(name = "amount", required = false) Double amount,
            @RequestParam(name = "description", required = false) String description
    ) {
        LocalDate parsedDate = null;
        if (date != null) {
            parsedDate = LocalDate.parse(date, Expense.DATE_FORMATTER);
        }

        expenseService.updateExpense(token, expenseId, parsedDate, amount, description);
        return ResponseEntity.status(204).build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(
            @RequestHeader("Authorization") String token,
            @PathVariable(name = "id") Long expenseId
    ) {
        expenseService.deleteExpense(token, expenseId);
        return ResponseEntity.status(204).build();
    }

}
