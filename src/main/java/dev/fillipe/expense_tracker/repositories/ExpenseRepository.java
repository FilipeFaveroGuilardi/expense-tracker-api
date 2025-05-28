package dev.fillipe.expense_tracker.repositories;

import dev.fillipe.expense_tracker.models.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {


    @Query("SELECT e FROM Expense e WHERE e.user.id = :userId")
    List<Expense> findByUserId(@Param("userId") UUID userId);

    @Query("SELECT e FROM Expense e WHERE e.user.id = :userId AND e.date BETWEEN :startDate AND :endDate")
    List<Expense> findByUserIDAndBetweenDates(@Param("userId") UUID userId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
