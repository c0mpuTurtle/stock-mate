package org.example.stockmate.page.controller;

import lombok.RequiredArgsConstructor;
import org.example.stockmate.page.repository.OrderDetailRepository;
import org.example.stockmate.page.service.SalesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class SalesController {

    private final SalesService salesService;
    private final OrderDetailRepository orderDetailRepository;

    @GetMapping("/sales/total")
    public String showSalesTotal(@RequestParam(required = false) String startDate,
                                 @RequestParam(required = false) String endDate,
                                 @RequestParam(defaultValue = "month") String unit,
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

    @GetMapping("/sales/summary")
    public String getSummary(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(defaultValue = "total_desc") String sortOption,
            @RequestParam(defaultValue = "month") String unit,
            Model model
    ) {
        if (startDate == null || endDate == null) {
            LocalDate now = LocalDate.now();
            startDate = now.minusMonths(2).withDayOfMonth(1).toString().substring(0, 7); // 2개월 전
            endDate = now.toString().substring(0, 7); // 이번 달
        }

        List<Map<String, Object>> summary = salesService.getProductSalesSummary(startDate, endDate, sortOption);
        model.addAttribute("summary", summary);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("sortOption", sortOption);
        model.addAttribute("unit", unit);
        return "sales/summary";
    }


    @GetMapping("/sales/items")
    public String getItems(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(defaultValue = "count_desc") String sortOption,
            Model model
    ) {
        if (startDate == null || endDate == null) {
            LocalDate now = LocalDate.now();
            startDate = now.minusMonths(2).withDayOfMonth(1).toString().substring(0, 7);
            endDate = now.toString().substring(0, 7);
        }

        List<Map<String, Object>> summary = salesService.getProductSalesSummaryByCount(startDate, endDate, sortOption);

        model.addAttribute("summary", summary);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("sortOption", sortOption);

        return "sales/items";
    }


}
