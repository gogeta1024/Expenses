package org.example.expenses.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.expenses.entity.DailyCost;
import org.example.expenses.entity.MonthlyCost;
import org.example.expenses.service.DailyService;
import org.example.expenses.service.MonthlyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("total")
@RequiredArgsConstructor
public class TotalController {
    private final DailyService dailyService;
    private final MonthlyService monthlyService;

    public void data(List<DailyCost> dailyCostList, List<MonthlyCost> monthlyCostList, Model model)
    {
        Integer total1 = dailyService.totalAmount(dailyCostList);
        model.addAttribute("total1", total1);

        Integer total2 = monthlyService.totalAmount(monthlyCostList);
        model.addAttribute("total2", total2);

        Integer totalFood = dailyService.totalType(dailyCostList,"Thực Phẩm");
        Integer totalItem = dailyService.totalType(dailyCostList,"Đồ Dùng");
        Integer totalMedical = dailyService.totalType(dailyCostList,"Thuốc");
        Integer totalClothing = dailyService.totalType(dailyCostList,"Quần Áo");
        model.addAttribute("totalFood", totalFood);
        model.addAttribute("totalItem", totalItem);
        model.addAttribute("totalMedical", totalMedical);
        model.addAttribute("totalClothing", totalClothing);

        int foodPercentage = (total1 !=null && totalFood != null && total1 > 0 && totalFood > 0) ?  Math.round((float) (totalFood * 100) /total1) : 0;
        int itemPercentage = (total1 !=null && totalItem != null && total1 > 0 && totalItem > 0) ?   Math.round((float) (totalItem * 100) /total1) : 0;
        int medicalPercentage =(total1 !=null && totalMedical != null && total1 > 0 && totalMedical > 0) ?  Math.round((float) (totalMedical * 100) /total1) : 0;
        int clothingPercentage = totalClothing>0 ? (100 - (Math.max(foodPercentage, 0)) - (Math.max(itemPercentage, 0)) - (Math.max(medicalPercentage, 0))) : 0;

        model.addAttribute("foodPercentage",foodPercentage);
        model.addAttribute("itemPercentage", itemPercentage);
        model.addAttribute("medicalPercentage", medicalPercentage);
        model.addAttribute("clothingPercentage", clothingPercentage);

    }

    @GetMapping
    public String total( @RequestParam(value = "page", defaultValue = "0") int page,
                         @RequestParam(value = "size", defaultValue = "10") int size,
                         Model model) {
        List<DailyCost> dailyCostList = dailyService.getAll();
        //model.addAttribute("dailyList", dailyCostList);
        Pageable pageable = PageRequest.of(page,size, Sort.by("purchaseDate").descending());
        Page<DailyCost> dailyPage = dailyService.getPaginatedDailyList(dailyCostList, pageable); // 10 items per page
        model.addAttribute("dailyList", dailyPage.getContent());
        model.addAttribute("totalPages", dailyPage.getTotalPages());
        model.addAttribute("currentPage", dailyPage.getNumber());

        List<MonthlyCost> monthlyCostList = monthlyService.getAll();
        model.addAttribute("monthlyList", monthlyCostList);

        data(dailyCostList,monthlyCostList,model);

        return "total";
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model)
    {
        dailyService.deleteByID(id);
        monthlyService.deleteByID(id);

        return "redirect:/total";
    }

    @RequestMapping(value = "/search", method = {RequestMethod.GET, RequestMethod.POST})
    public String search(
            @RequestParam(value = "purchaseDate", required = false) LocalDate purchaseDate,
            @RequestParam(value = "month", required = false) YearMonth yearMonth,
            @RequestParam(value = "supermarket", required = false) String supermarket,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            Model model,
            HttpServletRequest request) {

        if (purchaseDate != null && yearMonth != null) {
            // Trả về một danh sách dailyCostList rỗng
            model.addAttribute("dailyList", null);
            // Trả về danh sách monthlyCostList (hoặc có thể để rỗng nếu cần)
            model.addAttribute("monthlyList", null);
            model.addAttribute("foodPercentage",0);
            model.addAttribute("itemPercentage",0);
            model.addAttribute("medicalPercentage",0);
            model.addAttribute("clothingPercentage",0);

            // Trả về trang tổng quát hoặc trang báo lỗi nếu cần
            model.addAttribute("error", "Chỉ có thể chọn một trong hai: Ngày hoặc Tháng.");
            return "total";
        }

        model.addAttribute("purchaseDate", purchaseDate);
        model.addAttribute("month", yearMonth);
        model.addAttribute("supermarket", supermarket);
        model.addAttribute("type", type);
        model.addAttribute("name", name);
        model.addAttribute("searching", true);  // Đánh dấu tìm kiếm đang diễn ra

        HttpSession session = request.getSession();
        List<DailyCost> dailyCostList;

        if (purchaseDate != null || yearMonth != null || supermarket != null || type != null || name != null) {
            // Nếu có tham số tìm kiếm, thực hiện tìm kiếm
            dailyCostList = dailyService.filterByDaily(purchaseDate, yearMonth, supermarket, type, name);
            session.setAttribute("dailyCostList", dailyCostList); // Lưu vào session
        } else {
            // Nếu không có tham số tìm kiếm, lấy từ session
            dailyCostList = (List<DailyCost>) session.getAttribute("dailyCostList");
            if (dailyCostList == null) {
                dailyCostList = dailyService.getAll(); // Dữ liệu mặc định
            }
        }

        List<MonthlyCost> monthlyCostList = monthlyService.findByMonthAndYear(yearMonth != null ? yearMonth.toString() : null);

        // Nếu không có kết quả, trả về thông báo lỗi
        if (dailyCostList.isEmpty()){
            model.addAttribute("error1", "Không tìm thấy kết quả phù hợp.");
        }

        Pageable pageable = PageRequest.of(page,size, Sort.by("purchaseDate").descending());
        Page<DailyCost> dailyPage = dailyService.getPaginatedDailyList(dailyCostList, pageable); // 10 items per page

        model.addAttribute("dailyList", dailyPage.getContent());
        model.addAttribute("totalPages", dailyPage.getTotalPages());
        model.addAttribute("currentPage", dailyPage.getNumber());
        model.addAttribute("monthlyList", monthlyCostList);

        data(dailyCostList,monthlyCostList,model);


        return "total";
    }

    /*@RequestMapping(value = "/search", method = {RequestMethod.GET, RequestMethod.POST})
    public String search(
            @RequestParam(value = "purchaseDate", required = false) LocalDate purchaseDate,
            @RequestParam(value = "month", required = false) YearMonth yearMonth, // Dùng YearMonth
            @RequestParam(value = "supermarket", required = false) String supermarket,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "page", defaultValue = "0") int page,  // Tham số trang mặc định là 0
            @RequestParam(value = "size", defaultValue = "10") int size, // Tham số kích thước trang mặc định là 10
            Model model,
            HttpServletRequest request) {

        if (purchaseDate != null && yearMonth != null) {
            // Trả về thông báo nếu chọn cả purchaseDate và yearMonth
            model.addAttribute("error", "Chỉ có thể chọn một trong hai: Ngày hoặc Tháng.");
            return "total";
        }

        // Thêm thông tin vào Model cho việc tìm kiếm
        model.addAttribute("purchaseDate", purchaseDate);
        model.addAttribute("month", yearMonth);
        model.addAttribute("supermarket", supermarket);
        model.addAttribute("type", type);
        model.addAttribute("name", name);
        model.addAttribute("searching", true);  // Đánh dấu tìm kiếm đang diễn ra

        // Chuyển YearMonth thành chuỗi "YYYY-MM"
        String monthStr = (yearMonth != null) ? yearMonth.toString() : null;

        // Chuyển purchaseDate thành chuỗi rõ ràng
        String purchaseDateStr = (purchaseDate != null) ? purchaseDate.toString() : null;

        // Tạo Pageable với thông số page và size
        Pageable pageable = PageRequest.of(page, size);

        // Tạo danh sách kết quả từ dịch vụ (filterByDaily)
        Page<DailyCost> dailyCostList = dailyService.filterByDaily(purchaseDateStr, monthStr, supermarket, type, name, pageable);

        // Lấy danh sách MonthlyCost (nếu có)
        List<MonthlyCost> monthlyCostList = monthlyService.findByMonthAndYear(monthStr);

        // Nếu không có kết quả, trả về thông báo lỗi
        if (dailyCostList.isEmpty()){
            model.addAttribute("error1", "Không tìm thấy kết quả phù hợp.");
        }

        // Thêm thông tin kết quả vào Model
        model.addAttribute("dailyList", dailyCostList.getContent());
        model.addAttribute("monthlyList", monthlyCostList);
        model.addAttribute("currentPage", dailyCostList.getNumber());
        model.addAttribute("totalPages", dailyCostList.getTotalPages());

        // Chạy một hàm để xử lý dữ liệu (nếu có)
        data(dailyCostList.getContent(), monthlyCostList, model);

        return "total";
    }*/




}




