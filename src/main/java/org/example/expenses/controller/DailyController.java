package org.example.expenses.controller;

import lombok.RequiredArgsConstructor;
import org.example.expenses.dto.DailyDTO;
import org.example.expenses.entity.DailyCost;
import org.example.expenses.service.DailyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("daily")
@RequiredArgsConstructor
public class DailyController {
    private final DailyService dailyService;

    @ModelAttribute("daily")
    public DailyDTO setupDailyDTO() {
        return new DailyDTO(); // Tạo đối tượng mặc định nếu chưa có trong session
    }

    @GetMapping
    public String daily(Model model) {
        // Lấy giá trị ngày hôm nay gán cho purchaseDate
        LocalDate purchaseDate = LocalDate.now();

        // Tính toán month và year từ purchaseDate
        int month = purchaseDate.getMonthValue();
        int year = purchaseDate.getYear();

        List<DailyCost> dailyCostList = dailyService.findByMonthAndYear(month, year);
        model.addAttribute("dailyList", dailyCostList);

        Integer totalAmount = dailyService.totalAmount();
        model.addAttribute("total", totalAmount);
        return "daily";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("daily") DailyDTO dailyDTO, Model model) {
        DailyCost dailyCost = DailyService.convertToEntity(dailyDTO);
        dailyService.save(dailyCost);

        return "redirect:/daily";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model)
    {
        dailyService.deleteByID(id);
        return "redirect:/daily";
    }


}




