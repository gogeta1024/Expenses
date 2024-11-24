package org.example.expenses.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.expenses.entity.DailyCost;
import org.example.expenses.repository.DailyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyServiceImpl implements DailyService{

 private final DailyRepository dailyRepository;
 List<DailyCost> dailyCostList;

    @Override
    public void save(DailyCost dailyCost)
    {
    dailyRepository.save(dailyCost);
    }

    @Override
    public List<DailyCost> getAll()
    {
        dailyCostList = dailyRepository.findAll();
        return dailyCostList;
    }

    @Override
    public List<DailyCost> getByDate(LocalDate purchaseDate)
    {
        dailyCostList = dailyRepository.findByPurchaseDate(purchaseDate);
        return dailyCostList;
    }

    @Override
    public List<DailyCost> findByMonthAndYear(int month, int year) {
        dailyCostList =  dailyRepository.findByMonthAndYear(month, year);
        return dailyCostList;
    }

    @Override
   public void deleteByID(Long id)
    {
       dailyRepository.deleteById(id);
    }


    @Override
    public Integer totalAmount()
    {
        Integer totalAmount=0;

        for( DailyCost daily:dailyCostList)
        {
            // Kiểm tra null cho price và quantity
            Integer price = daily.getPrice() != null ? daily.getPrice() : 0;
            Integer quantity = daily.getQuantity() != null ? daily.getQuantity() : 0;

            totalAmount += price * quantity;
        }
        return totalAmount;
    }


}
