package dev.fillipe.expense_tracker.services;

import dev.fillipe.expense_tracker.dto.input.CreateExpenseDTO;
import dev.fillipe.expense_tracker.models.Expense;
import dev.fillipe.expense_tracker.models.User;
import dev.fillipe.expense_tracker.repositories.ExpenseRepository;
import dev.fillipe.expense_tracker.repositories.UserRepository;
import dev.fillipe.expense_tracker.security.jwt.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ExpenseService {



    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    public void createExpense(CreateExpenseDTO expense, String token) {
        User user = getUserFromJwtToken(token);
        LocalDate date = LocalDate.parse(expense.date(), Expense.DATE_FORMATTER);

        if (expense.amount().doubleValue() < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        if (date.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Date cannot be in the future");
        }

        Expense newExpense = Expense.builder()
                .amount(expense.amount())
                .date(date)
                .description(expense.description())
                .user(user)
                .build();

        expenseRepository.save(newExpense);
    }

    private User getUserFromJwtToken(String token) {
        String subject = jwtTokenService.retrieveSubject(token.substring(7).trim());

        return userRepository.findByEmail(subject).orElseThrow(RuntimeException::new);
    }

}
