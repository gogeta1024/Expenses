package org.example.expenses.service;

import lombok.RequiredArgsConstructor;
import org.example.expenses.entity.DailyCost;
import org.example.expenses.repository.DailyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DailyServiceImpl implements DailyService {

    private final DailyRepository dailyRepository;
    List<DailyCost> dailyCostList;

    @Override
    public void save(DailyCost dailyCost) {
        dailyRepository.save(dailyCost);
    }

    @Override
    public List<DailyCost> getAll() {
        dailyCostList = dailyRepository.findAll();
        return dailyCostList;
    }

    @Override
    public List<DailyCost> findByDate(LocalDate purchaseDate) {
        dailyCostList = dailyRepository.findByPurchaseDate(purchaseDate);
        return dailyCostList;
    }

    @Override
    public List<DailyCost> findBySupermarket(String supermarket) {
        dailyCostList = dailyRepository.findBySupermarket(supermarket);
        return dailyCostList;
    }

    @Override
    public List<DailyCost> findByType(String type) {
        dailyCostList = dailyRepository.findByType(type);
        return dailyCostList;
    }

    @Override
    public List<DailyCost> findByName(String name) {
        dailyCostList = dailyRepository.findByName(name);
        return dailyCostList;
    }

    @Override
    public List<DailyCost> findByMonthAndYear(int month, int year) {
        dailyCostList = dailyRepository.findByMonthAndYear(month, year);
        return dailyCostList;
    }

    @Override
    public void deleteByID(Long id) {
        dailyRepository.deleteById(id);
    }


    @Override
    public Integer totalAmount(List<DailyCost> dailyCostList) {
        Integer totalAmount = 0;

        for (DailyCost daily : dailyCostList) {
            // Kiểm tra null cho price và quantity
            Integer price = daily.getPrice() != null ? daily.getPrice() : 0;
            Integer quantity = daily.getQuantity() != null ? daily.getQuantity() : 0;

            totalAmount += price * quantity;
        }
        return totalAmount;
    }

    @Override
    public Integer totalType(List<DailyCost> dailyCostList, String type) {
        Integer totalType = 0;
        for (DailyCost daily : dailyCostList) {
            if (type.equals(daily.getType())) {
                totalType += daily.getPrice() * daily.getQuantity();
            }
        }
        return totalType;
    }

/*    @Override
    public List<DailyCost> filterBySupermarket(String supermarket) {
        if (dailyCostList.isEmpty()) {
            return findBySupermarket(supermarket); // Nếu danh sách ban đầu rỗng, tìm kiếm từ cơ sở dữ liệu
        }
        return dailyCostList.stream() //Chuyển đổi danh sách dailyCostList (dạng List<DailyCost>) thành một Stream.
                .filter(dailyCost -> dailyCost.getSupermarket().equalsIgnoreCase(supermarket)) //Biểu thức lambda: dailyCost là một phần tử của danh sách (kiểu DailyCost),Giữ lại phần tử nếu biểu thức lambda trả về true.
                .collect(Collectors.toList()); //Sau khi lọc xong, các phần tử còn lại trong Stream sẽ được thu thập lại thành một danh sách mới (List).
    }*/

    @Override
    public List<DailyCost> filterByDaily(LocalDate purchaseDate, YearMonth yearMonth, String supermarket, String type, String name) {
        // Lấy dữ liệu từ danh sách hiện tại hoặc repository
        List<DailyCost> sourceList = dailyCostList.isEmpty() ? dailyRepository.findAll() : dailyCostList;

        // Áp dụng bộ lọc
        return sourceList.stream()
                .filter(dailyCost ->
                        (purchaseDate == null || dailyCost.getPurchaseDate().equals(purchaseDate)) && // Kiểm tra purchaseDate
                                (yearMonth == null || (dailyCost.getPurchaseDate().getYear() == yearMonth.getYear() && dailyCost.getPurchaseDate().getMonthValue() == yearMonth.getMonthValue())) &&
                                (supermarket == null || supermarket.isEmpty() || dailyCost.getSupermarket().toLowerCase().contains(supermarket.toLowerCase())) &&
                                (type == null || type.isEmpty() || dailyCost.getType().equalsIgnoreCase(type)) &&
                                (name == null || name.isEmpty() || dailyCost.getName().toLowerCase().contains(name.toLowerCase()))
                )
                .collect(Collectors.toList());
    }

    @Override
    public DailyCost findByID(Long id)
    {
        Optional<DailyCost> dailyCostOptional =  dailyRepository.findById(id);
        return dailyCostOptional.orElse(null);

    }

    @Override
    public Page<DailyCost> getPaginatedDailyList(Pageable pageable) {
        return dailyRepository.findAll(pageable);
    }

    @Override
    public Page<DailyCost> filterByDaily(String purchaseDate, String month, String supermarket, String type, String name, Pageable pageable) {
        return dailyRepository.findByCriteria(purchaseDate, month, supermarket, type, name, pageable);
    }




}
