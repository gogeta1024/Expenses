package org.example.expenses.service;

import org.example.expenses.dto.DailyDTO;
import org.example.expenses.entity.DailyCost;

import java.time.LocalDate;
import java.util.List;

public interface DailyService {
    static DailyDTO convertToDTO(DailyCost dailyCost)
    {
        return new DailyDTO(dailyCost.getId(),dailyCost.getPurchaseDate(),dailyCost.getSupermarket(),dailyCost.getType(),
                dailyCost.getName(),dailyCost.getWeight(),dailyCost.getPrice(),dailyCost.getQuantity());
    }

    static DailyCost convertToEntity(DailyDTO dailyDTO)
    {
        return new DailyCost( dailyDTO.getId(),dailyDTO.getPurchaseDate(),dailyDTO.getSupermarket(),dailyDTO.getType(),
                dailyDTO.getName(),dailyDTO.getWeight(),dailyDTO.getPrice(), dailyDTO.getQuantity());
    }

    void save(DailyCost dailyCost);

    List<DailyCost> getAll();
    List<DailyCost> getByDate(LocalDate purchaseDate);
    List<DailyCost> findByMonthAndYear(int month, int year);
    void deleteByID(Long id);


    Integer totalAmount();

}
