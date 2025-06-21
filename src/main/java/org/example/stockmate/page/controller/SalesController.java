package org.example.stockmate.page.controller;

import lombok.RequiredArgsConstructor;
import org.example.stockmate.page.service.SalesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class SalesController {

    private final SalesService salesService;

    @GetMapping("/sales/total")
    public String showSalesTotal(@RequestParam(defaultValue = "month") String unit,
                                 @RequestParam(defaultValue = "2025-01") String startDate,
                                 @RequestParam(defaultValue = "2025-06") String endDate,
                                 Model model) {

        if (startDate == null || endDate == null) {
            // 오늘 날짜 기준 기본값 설정
            LocalDate now = LocalDate.now();
            startDate = now.minusMonths(2).withDayOfMonth(1).toString().substring(0, 7); // 2개월 전
            endDate = now.toString().substring(0, 7); // 이번 달
        }

        Map<String, Long> salesMap = salesService.getSalesGroupedByUnit(startDate, endDate, unit);
        model.addAttribute("labels", salesMap.keySet());
        model.addAttribute("salesData", salesMap.values());
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("unit", unit);
        return "sales/total";
    }






}
