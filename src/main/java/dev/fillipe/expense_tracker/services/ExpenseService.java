package dev.fillipe.expense_tracker.services;

import dev.fillipe.expense_tracker.dto.input.CreateExpenseDTO;
import dev.fillipe.expense_tracker.dto.output.ExpenseDTO;
import dev.fillipe.expense_tracker.dto.output.ListExpensesDTO;
import dev.fillipe.expense_tracker.enums.ExpenseFilterEnum;
import dev.fillipe.expense_tracker.models.Expense;
import dev.fillipe.expense_tracker.models.User;
import dev.fillipe.expense_tracker.repositories.ExpenseRepository;
import dev.fillipe.expense_tracker.repositories.UserRepository;
import dev.fillipe.expense_tracker.security.jwt.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    public ListExpensesDTO retrieveExpenses(String token, ExpenseFilterEnum dateFilter, LocalDate startDateCustom, LocalDate endDateCustom) {
        User user = getUserFromJwtToken(token);

        LocalDate startDate = null;
        LocalDate endDate = null;

        switch (dateFilter) {
            case ExpenseFilterEnum.WEEK:
                startDate = LocalDate.now().minusDays(7);
                endDate = LocalDate.now();
                break;
            case ExpenseFilterEnum.MONTH:
                startDate = LocalDate.now().minusMonths(1);
                endDate = LocalDate.now();
                break;
            case ExpenseFilterEnum.QUARTER:
                startDate = LocalDate.now().minusMonths(3);
                endDate = LocalDate.now();
                break;
            case ExpenseFilterEnum.CUSTOM:
                startDate = startDateCustom;
                endDate = endDateCustom;
                break;
            default:
                throw new IllegalArgumentException("Invalid date filter");
        }

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }

        List<Expense> expensesRetrieved = expenseRepository.findByUserIDAndBetweenDates(user.getId(), startDate, endDate);

        return new ListExpensesDTO(user.getId().toString(), user.getEmail(),
                expensesRetrieved.stream().map(expense -> conversionService.convert(expense, ExpenseDTO.class)).toList()
        );
    }
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
    public void deleteExpense(String token, Long expenseId) {
        User user = getUserFromJwtToken(token);
        Expense expense = expenseRepository.findById(expenseId).orElseThrow(RuntimeException::new);

        if (!expense.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Expense does not belong to the user");
        }

        expenseRepository.delete(expense);
    }
    public void updateExpense(String token, Long expenseId, LocalDate date, Double amount, String description) {
        User user = getUserFromJwtToken(token);
        Expense expense = expenseRepository.findById(expenseId).orElseThrow(RuntimeException::new);

        if (!expense.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Expense does not belong to the user");
        }


        if (!date.isEqual(expense.getDate()) || date.isBefore(expense.getDate())) {
            expense.setDate(date);
        }
        if (!description.equals(expense.getDescription()) || !description.isBlank()) {
            expense.setDescription(description);
        }
        if (amount > 0 || amount != expense.getAmount().doubleValue()) {
            expense.setAmount(BigDecimal.valueOf(amount));
        }


        expenseRepository.save(expense);
    }
    public void deleteallusers() {
        userRepository.deleteAll();
    }

    private User getUserFromJwtToken(String token) {
        String subject = jwtTokenService.retrieveSubject(token.substring(7).trim());

        return userRepository.findByEmail(subject).orElseThrow(RuntimeException::new);
    }

}
