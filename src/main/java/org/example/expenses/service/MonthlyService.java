package org.example.expenses.service;

import jakarta.servlet.http.HttpServletResponse;
import org.example.expenses.dto.DailyDTO;
import org.example.expenses.entity.MonthlyCost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface MonthlyService {

    void save(MonthlyCost monthlyCost);

    List<MonthlyCost> getAll();
    List<MonthlyCost> findByMonthAndYear(String month);


    void deleteByID(Long id);

    Integer totalAmount(List<MonthlyCost> monthlyCostList);

    Boolean existsByMonth(String month);

    MonthlyCost findByID(Long id);

    Page<MonthlyCost> findAll(Pageable pageable);
    void exportMonthlyListToCsv(List<MonthlyCost> monthlyCostList, HttpServletResponse response) throws IOException;

}
