package org.example.expenses.service;

import lombok.RequiredArgsConstructor;
import org.example.expenses.dto.DailyDTO;
import org.example.expenses.entity.DailyCost;
import org.example.expenses.repository.DailyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    List<DailyDTO> dailyDTOList;

    private DailyCost convertToEntity(DailyDTO dailyDTO)
    {
        return new DailyCost( dailyDTO.getId(),dailyDTO.getPurchaseDate(),dailyDTO.getSupermarket(),dailyDTO.getType(),
                dailyDTO.getName(),dailyDTO.getWeight(),dailyDTO.getPrice(), dailyDTO.getQuantity(),dailyDTO.getMemo());
    }

    private DailyDTO convertToDTO(DailyCost dailyCost) {
        DailyDTO dailyDTO = new DailyDTO();
        dailyDTO.setId(dailyCost.getId());
        dailyDTO.setPurchaseDate(dailyCost.getPurchaseDate());
        dailyDTO.setSupermarket(dailyCost.getSupermarket());
        dailyDTO.setType(dailyCost.getType());
        dailyDTO.setName(dailyCost.getName());
        dailyDTO.setWeight(dailyCost.getWeight());
        dailyDTO.setPrice(dailyCost.getPrice());
        dailyDTO.setQuantity(dailyCost.getQuantity());
        dailyDTO.setMemo(dailyCost.getMemo());

        // Tính toán formattedPrice (ví dụ định dạng giá trị)
        if (dailyCost.getPrice() != null && dailyCost.getQuantity() != null && dailyCost.getQuantity() != 0) {
            double unitPrice = (double) dailyCost.getPrice() / dailyCost.getQuantity();

            // Kiểm tra phần thập phân, nếu không có phần thập phân thì chỉ hiển thị số nguyên
            if (unitPrice == Math.floor(unitPrice)) {
                // Nếu chia hết (phần thập phân = 0), hiển thị số nguyên
                dailyDTO.setFormattedPrice(String.format("%d", (int) unitPrice));
            } else {
                // Nếu có phần thập phân, hiển thị với 2 chữ số thập phân
                dailyDTO.setFormattedPrice(String.format("%.2f", unitPrice));
            }
        } else {
            dailyDTO.setFormattedPrice("0"); // Giá trị mặc định nếu không đủ thông tin
        }

        return dailyDTO;
    }




    @Override
    public void save(DailyDTO dailyDTO) {
        dailyRepository.save(convertToEntity(dailyDTO));
    }

    @Override
    public List<DailyDTO> getAll() {
        dailyCostList = dailyRepository.findAll(Sort.by(Sort.Direction.DESC, "purchaseDate"));

        return dailyCostList.stream()
                .map(this::convertToDTO) // Sử dụng mapper method
                .collect(Collectors.toList());
    }

    @Override
    public List<DailyDTO> findByDate(LocalDate purchaseDate) {
        dailyCostList = dailyRepository.findByPurchaseDate(purchaseDate);
        return dailyCostList.stream()
                .map(this::convertToDTO) // Sử dụng mapper method
                .collect(Collectors.toList());
    }

    @Override
    public List<DailyDTO> findBySupermarket(String supermarket) {
        dailyCostList = dailyRepository.findBySupermarket(supermarket);
        return dailyCostList.stream()
                .map(this::convertToDTO) // Sử dụng mapper method
                .collect(Collectors.toList());
    }

    @Override
    public List<DailyDTO> findByType(String type) {
        dailyCostList = dailyRepository.findByType(type);
        return dailyCostList.stream()
                .map(this::convertToDTO) // Sử dụng mapper method
                .collect(Collectors.toList());
    }

    @Override
    public List<DailyDTO> findByName(String name) {
        dailyCostList = dailyRepository.findByName(name);
        return dailyCostList.stream()
                .map(this::convertToDTO) // Sử dụng mapper method
                .collect(Collectors.toList());
    }

    @Override
    public List<DailyDTO> findByMonthAndYear(int month, int year) {
        dailyCostList = dailyRepository.findByMonthAndYear(month, year);
        return dailyCostList.stream()
                .map(this::convertToDTO) // Sử dụng mapper method
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByID(Long id) {
        dailyRepository.deleteById(id);
    }


    @Override
    public Integer totalAmount(List<DailyDTO> dailyDTOList) {
        Integer totalAmount = 0;

        for (DailyDTO daily : dailyDTOList) {
            // Kiểm tra null cho price
            Integer price = daily.getPrice() != null ? daily.getPrice() : 0;
            totalAmount += price;
        }
        return totalAmount;
    }


    @Override
    public Integer totalType(List<DailyDTO> dailyDTOList, String type) {
        Integer totalType = 0;
        for (DailyDTO daily : dailyDTOList) {
            if (type.equals(daily.getType())) {
                totalType += daily.getPrice() ;
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
    public List<DailyDTO> filterByDaily(LocalDate purchaseDate, YearMonth yearMonth, String supermarket, String type, String name) {
        // Lấy dữ liệu từ danh sách hiện tại hoặc repository
        List<DailyDTO> sourceList = dailyDTOList==null || dailyDTOList.isEmpty() ? getAll() : dailyDTOList;

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
    public DailyDTO findByID(Long id)
    {
        Optional<DailyCost> dailyCostOptional =  dailyRepository.findById(id);
        return dailyCostOptional.map(this::convertToDTO).orElse(null);
    }

    public Page<DailyDTO> getPaginatedDailyList(List<DailyDTO> dailyDTOList, Pageable pageable) {
        // Xác định vị trí bắt đầu và kết thúc của trang
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        int endItem = Math.min(startItem + pageSize, dailyDTOList.size());

        // Trích xuất dữ liệu trang từ danh sách
        List<DailyDTO> paginatedList;
        if (startItem > dailyDTOList.size()) {
            paginatedList = List.of(); // Trang rỗng nếu không có dữ liệu
        } else {
            paginatedList = dailyDTOList.subList(startItem, endItem);
        }

        // Tạo đối tượng Page với dữ liệu đã phân trang
        return new PageImpl<>(paginatedList, pageable, dailyDTOList.size());
    }

    @Override
    public Page<DailyDTO> filterByDaily(String purchaseDate, String month, String supermarket, String type, String name, Pageable pageable) {
        Page<DailyCost> dailyCosts = dailyRepository.findByCriteria(purchaseDate, month, supermarket, type, name, pageable);
        // Sử dụng map để chuyển đổi từ DailyCost sang DailyDTO
        return dailyCosts.map(this::convertToDTO);
    }




}
