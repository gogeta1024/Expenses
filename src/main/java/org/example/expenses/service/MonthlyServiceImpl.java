package org.example.expenses.service;

import lombok.RequiredArgsConstructor;
import org.example.expenses.entity.MonthlyCost;
import org.example.expenses.repository.MonthlyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MonthlyServiceImpl implements MonthlyService {

    private final MonthlyRepository monthlyRepository;
    List<MonthlyCost> monthlyCostList;

    @Override
    public void save(MonthlyCost monthlyCost) {
        monthlyRepository.save(monthlyCost);
    }

    @Override
    public List<MonthlyCost> getAll() {
        monthlyCostList = monthlyRepository.findAll();
        return monthlyCostList;
    }

    @Override
    public List<MonthlyCost> findByMonthAndYear(String month)
    {
        monthlyCostList = monthlyRepository.findByMonthAndYear(month);
        return monthlyCostList;
    }


    @Override
    public void deleteByID(Long id) {
        monthlyRepository.deleteById(id);
    }

    @Override
    public Integer totalAmount(List<MonthlyCost> monthlyCostList)
    {
        Integer totalAmount=0;

        for( MonthlyCost monthly: monthlyCostList)
        {
            // Kiểm tra null cho price và quantity
            Integer sport = monthly.getSport() != null ? monthly.getSport() : 0;
            Integer other = monthly.getOther() != null ? monthly.getOther() : 0;
            
            totalAmount += monthly.getRent()+monthly.getElectricity()+monthly.getWater()
                    +monthly.getGas()+sport+other;
        }
        return totalAmount;
    }

    @Override
    public Boolean existsByMonth(String month)
    {
       return monthlyRepository.existsByMonth(month);
    }

    @Override
    public MonthlyCost findByID(Long id)
    {
        Optional<MonthlyCost> monthlyCostOptional =  monthlyRepository.findById(id);
        return monthlyCostOptional.orElse(null);

    }


}
