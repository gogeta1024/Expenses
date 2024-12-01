package org.example.expenses.repository;

import org.example.expenses.entity.DailyCost;
import org.example.expenses.entity.MonthlyCost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.YearMonth;
import java.util.List;

@Repository
public interface MonthlyRepository extends JpaRepository<MonthlyCost, Long> {
    boolean existsByMonth(String month);

    @Query("SELECT mc FROM MonthlyCost mc WHERE mc.month = :month")
    List<MonthlyCost> findByMonthAndYear(String month);

}
