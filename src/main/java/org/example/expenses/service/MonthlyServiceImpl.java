package org.example.expenses.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.expenses.dto.DailyDTO;
import org.example.expenses.entity.DailyCost;
import org.example.expenses.entity.MonthlyCost;
import org.example.expenses.repository.MonthlyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@Service
public class MonthlyServiceImpl implements MonthlyService {

    private final MonthlyRepository monthlyRepository;
    List<MonthlyCost> monthlyCostList;

    @Autowired
    public MonthlyServiceImpl(MonthlyRepository monthlyRepository)
    {
        this.monthlyRepository = monthlyRepository;
    }

    @Override
    public void save(MonthlyCost monthlyCost) {
        monthlyRepository.save(monthlyCost);
    }

    @Override
    public List<MonthlyCost> getAll() {
        monthlyCostList = monthlyRepository.findAll(Sort.by(Sort.Direction.DESC, "month"));
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

    @Override
    public Page<MonthlyCost> findAll(Pageable pageable)
    {
      return monthlyRepository.findAll(pageable);
    }
    @Override
    public void exportMonthlyListToCsv(List<MonthlyCost> monthlyCostList, HttpServletResponse response) throws IOException
    {
        // Đặt kiểu nội dung và header cho file CSV
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=monthly_list.csv");

        // Sử dụng try-with-resources để đảm bảo tài nguyên được giải phóng
        try (PrintWriter writer = response.getWriter()) {
            // Ghi header CSV
            writer.println("ID,Month,Rent,Electricity,Water,Gas,Sport,Other");

            // Ghi dữ liệu từng dòng
            if (monthlyCostList != null && !monthlyCostList.isEmpty()) {
                for (MonthlyCost monthly : monthlyCostList) {
                    writer.println(String.join(",",
                            monthly.getId() != null ? String.valueOf(monthly.getId()) : "",
                            monthly.getMonth() != null ? monthly.getMonth() : "",
                            monthly.getRent() != null ? monthly.getRent().toString() : "",
                            monthly.getElectricity() != null ? monthly.getElectricity().toString() : "",
                            monthly.getWater() != null ? monthly.getWater().toString() : "",
                            monthly.getGas() != null ? monthly.getGas().toString() : "",
                            monthly.getSport() != null ? monthly.getSport().toString() : "",
                            monthly.getOther() != null ? monthly.getOther().toString() : ""
                    ));
                }
            } else {
                // Nếu danh sách rỗng, ghi dòng thông báo
                writer.println("No data available");
            }
            writer.flush();
        } catch (IOException e) {
            throw new IOException("Error occurred while exporting CSV", e);
        }
    }


}
