package org.example.expenses.service;

import jakarta.servlet.http.HttpServletResponse;
import org.example.expenses.dto.DailyDTO;
import org.example.expenses.entity.DailyCost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public interface DailyService {

    void save(DailyDTO dailyDTO);

    List<DailyDTO> getAll();
    List<DailyDTO> findByDate(LocalDate purchaseDate);
    List<DailyDTO> findByMonthAndYear(int month, int year);
    List<DailyDTO> findBySupermarket(String supermarket);
    List<DailyDTO> findByType(String type);
    List<DailyDTO> findByName(String name);

    void deleteByID(Long id);
    DailyDTO findByID(Long id);


    Integer totalAmount(List<DailyDTO> dailyDTOList);

    Integer totalType(List<DailyDTO> dailyDTOList, String type);

    List<DailyDTO> filterByDaily(LocalDate purchaseDate, YearMonth yearMonth, String supermarket, String type, String name);

    Page<DailyDTO> getPaginatedDailyList(List<DailyDTO> dailyDTOList, Pageable pageable);

    Page<DailyDTO> filterByDaily(String purchaseDate, String month, String supermarket, String type, String name, Pageable pageable);

    void exportDailyListToCsv(List<DailyDTO> dailyDTOList, HttpServletResponse response) throws IOException;
}
