package org.example.expenses.repository;

import org.example.expenses.entity.DailyCost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DailyRepository extends JpaRepository<DailyCost, Long> {

    // Tìm kiếm theo purchaseDate
    List<DailyCost> findByPurchaseDate(LocalDate purchaseDate);

    // Tìm kiếm tất cả bản ghi trong một tháng và năm cụ thể
    @Query("SELECT dc FROM DailyCost dc WHERE EXTRACT(MONTH FROM dc.purchaseDate) = :month AND EXTRACT(YEAR FROM dc.purchaseDate) = :year ORDER BY dc.purchaseDate DESC")
    List<DailyCost> findByMonthAndYear(int month, int year);

    //Tim kiem theo ten sieu thi
    @Query("SELECT dc FROM DailyCost dc WHERE LOWER(dc.supermarket) LIKE LOWER(CONCAT('%', :supermarket, '%'))")
    List<DailyCost> findBySupermarket(String supermarket);

    //Tim kiem theo loai do
    @Query("SELECT dc FROM DailyCost dc WHERE dc.type = :type")
    List<DailyCost> findByType(String type);

    //Tim kiem theo ten do
    @Query("SELECT dc FROM DailyCost dc WHERE LOWER(dc.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<DailyCost> findByName(String name);

    @Query("SELECT d FROM DailyCost d " +
            "WHERE (:purchaseDate IS NULL OR to_char(d.purchaseDate, 'YYYY-MM-DD') = :purchaseDate) " +
            "AND (:month IS NULL OR to_char(d.purchaseDate, 'YYYY-MM') = :month) " +
            "AND (:supermarket IS NULL OR d.supermarket LIKE %:supermarket%) " +
            "AND (:type IS NULL OR d.type LIKE %:type%) " +
            "AND (:name IS NULL OR d.name LIKE %:name%)")
    Page<DailyCost> findByCriteria(
            @Param("purchaseDate") String purchaseDate,
            @Param("month") String month,
            @Param("supermarket") String supermarket,
            @Param("type") String type,
            @Param("name") String name,
            Pageable pageable);




}
