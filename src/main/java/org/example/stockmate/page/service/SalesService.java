package org.example.stockmate.page.service;

import lombok.RequiredArgsConstructor;
import org.example.stockmate.page.repository.OrderDetailRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SalesService {

    private final OrderDetailRepository orderDetailRepository;

    public Long calculateSales(String type, String date) {
        if (date == null || date.isBlank()) return 0L;

        String formattedDate = switch (type) {
            case "day", "month" -> date.replace("-", "");
            case "year" -> date;
            default -> throw new IllegalArgumentException("Invalid type");
        };

        Long result = orderDetailRepository.getTotalSales(type, formattedDate);
        return (result != null) ? result : 0L;
    }

    public Map<String, Long> getSalesGroupedByUnit(String startDate, String endDate, String unit) {
        String formattedStart = startDate.replace("-", "");
        String formattedEnd = endDate.replace("-", "");

        List<Object[]> rawResults = orderDetailRepository.getSalesGroupByUnit(formattedStart, formattedEnd, unit);
        Map<String, Long> resultMap = new LinkedHashMap<>();

        for (Object[] row : rawResults) {
            String label = (String) row[0];
            Long value = (row[1] != null) ? ((Number) row[1]).longValue() : 0L;
            resultMap.put(label, value);
        }

        return resultMap;
    }

    public Map<String, Long> getTotalSalesByRange(String startDate, String endDate, String unit) {
        return getSalesGroupedByUnit(startDate, endDate, unit);
    }
}
