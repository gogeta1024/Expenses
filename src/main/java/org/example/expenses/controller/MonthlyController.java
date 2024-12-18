package org.example.expenses.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.expenses.dto.MonthlyDTO;
import org.example.expenses.entity.MonthlyCost;
import org.example.expenses.service.MonthlyService;
import org.example.expenses.utils.FormatUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("monthly")
@RequiredArgsConstructor
public class MonthlyController {
private final MonthlyService monthlyService;

    @GetMapping
    public String monthly( @RequestParam(value = "pageMonthly", defaultValue = "0") int pageMonthly,
                           @RequestParam(value = "size", defaultValue = "10") int size,
                           Model model) {
        model.addAttribute("monthly",new MonthlyCost());
        List<MonthlyCost> monthlyCostList = monthlyService.getAll();

        Pageable pageableMonthly = PageRequest.of(pageMonthly,size, Sort.by("month").descending());
        Page<MonthlyCost> monthlyPage = monthlyService.findAll(pageableMonthly);
        model.addAttribute("monthlyList", monthlyPage.getContent());
        model.addAttribute("totalMonthlyPages", monthlyPage.getTotalPages());
        model.addAttribute("currentMonthlyPage", monthlyPage.getNumber());

        Integer totalAmount = monthlyService.totalAmount(monthlyCostList);
        model.addAttribute("total", FormatUtils.formatPrice(totalAmount));
        return "monthly";
    }

    @PostMapping("/save")
    public String save(@Validated  @ModelAttribute("monthly") MonthlyDTO monthlyDTO,
                       BindingResult bindingResult, Model model) {

        if(monthlyService.existsByMonth(monthlyDTO.getMonth()))
        {
            bindingResult.addError(new FieldError("monthly","month","Tháng này đã tồn tại!"));
        }

        if(bindingResult.hasErrors())
        {
            model.addAttribute("org.springframework.validation.BindingResult.monthly", bindingResult);
            return "monthly";
        }

        MonthlyCost monthlyCost = new MonthlyCost();
        BeanUtils.copyProperties(monthlyDTO, monthlyCost);

        monthlyService.save(monthlyCost);

        return "redirect:/monthly";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model)
    {
        monthlyService.deleteByID(id);
        return "redirect:/monthly";
    }

    @GetMapping(value = "edit/{id}")
    public String monthlyEdit(@PathVariable Long id, Model model) {
        MonthlyCost monthlyEdit = monthlyService.findByID(id);
        model.addAttribute("monthlyEdit",monthlyEdit);
        return "monthly-edit";
    }

    @PostMapping("/edit")
    public String monthlyUpdate(
            @Validated @ModelAttribute("monthlyEdit") MonthlyDTO monthlyDTO,
            BindingResult bindingResult, Model model) {


        if(bindingResult.hasErrors())
        {
            model.addAttribute("org.springframework.validation.BindingResult.monthlyEdit", bindingResult); //bên hTML phải thêm trường ẩn mới get dc id
            return "monthly-edit";
        }

        MonthlyCost monthlyEdit = monthlyDTO.getId()!=null ? monthlyService.findByID(monthlyDTO.getId()) : new MonthlyCost();

        BeanUtils.copyProperties(monthlyDTO, monthlyEdit,"id");
        monthlyService.save(monthlyEdit);

        return "redirect:/monthly";
    }


}




