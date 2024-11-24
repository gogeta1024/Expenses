package org.example.expenses.repository;

import org.example.expenses.entity.DailyCost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DailyRepository extends JpaRepository<DailyCost, Long> {

    // Tìm kiếm theo purchaseDate
    List<DailyCost> findByPurchaseDate(LocalDate purchaseDate);

    // Tìm kiếm tất cả bản ghi trong một tháng và năm cụ thể
    @Query("SELECT dc FROM DailyCost dc WHERE EXTRACT(MONTH FROM dc.purchaseDate) = :month AND EXTRACT(YEAR FROM dc.purchaseDate) = :year")
    List<DailyCost> findByMonthAndYear(int month, int year);
}
