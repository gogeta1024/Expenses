package org.example.expenses.service;

import org.example.expenses.dto.DailyDTO;
import org.example.expenses.entity.DailyCost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public interface DailyService {
    static DailyDTO convertToDTO(DailyCost dailyCost)
    {
        return new DailyDTO(dailyCost.getId(),dailyCost.getPurchaseDate(),dailyCost.getSupermarket(),dailyCost.getType(),
                dailyCost.getName(),dailyCost.getWeight(),dailyCost.getPrice(),dailyCost.getQuantity(),dailyCost.getMemo());
    }

    static DailyCost convertToEntity(DailyDTO dailyDTO)
    {
        return new DailyCost( dailyDTO.getId(),dailyDTO.getPurchaseDate(),dailyDTO.getSupermarket(),dailyDTO.getType(),
                dailyDTO.getName(),dailyDTO.getWeight(),dailyDTO.getPrice(), dailyDTO.getQuantity(),dailyDTO.getMemo());
    }

    void save(DailyCost dailyCost);

    List<DailyCost> getAll();
    List<DailyCost> findByDate(LocalDate purchaseDate);
    List<DailyCost> findByMonthAndYear(int month, int year);
    List<DailyCost> findBySupermarket(String supermarket);
    List<DailyCost> findByType(String type);
    List<DailyCost> findByName(String name);

    void deleteByID(Long id);
    DailyCost findByID(Long id);


    Integer totalAmount(List<DailyCost> dailyCostList);

    Integer totalType(List<DailyCost> dailyCostList,String type);

    List<DailyCost> filterByDaily(LocalDate purchaseDate, YearMonth yearMonth, String supermarket, String type, String name);

    Page<DailyCost> getPaginatedDailyList(List<DailyCost> dailyCostList, Pageable pageable);

    Page<DailyCost> filterByDaily(String purchaseDate, String month, String supermarket, String type, String name, Pageable pageable);
}
