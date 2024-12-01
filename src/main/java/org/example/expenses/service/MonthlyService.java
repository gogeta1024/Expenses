package org.example.expenses.service;

import org.example.expenses.entity.DailyCost;
import org.example.expenses.entity.MonthlyCost;

import java.time.YearMonth;
import java.util.List;

public interface MonthlyService {

    void save(MonthlyCost monthlyCost);

    List<MonthlyCost> getAll();
    List<MonthlyCost> findByMonthAndYear(String month);


    void deleteByID(Long id);

    Integer totalAmount(List<MonthlyCost> monthlyCostList);

    Boolean existsByMonth(String month);

    MonthlyCost findByID(Long id);


}
