package dev.fillipe.expense_tracker.controllers;

import dev.fillipe.expense_tracker.dto.input.CreateExpenseDTO;
import dev.fillipe.expense_tracker.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/expense")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;


    @PostMapping
    private ResponseEntity<Void> createExpense(
            @RequestHeader("Authorization") String token,
            @RequestBody CreateExpenseDTO createExpenseDTO
    ) {
        expenseService.createExpense(createExpenseDTO, token);
        return ResponseEntity.status(201).build();
    }
}
