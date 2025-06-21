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

        // ✅ 종료 날짜 포함 처리를 위해 단위별로 종료 날짜 조정
        if (unit.equals("month")) {
            int year = Integer.parseInt(formattedEnd.substring(0, 4));
            int month = Integer.parseInt(formattedEnd.substring(4, 6));
            month++;
            if (month > 12) {
                year++;
                month = 1;
            }
            formattedEnd = String.format("%04d%02d", year, month);
        } else if (unit.equals("year")) {
            int year = Integer.parseInt(formattedEnd);
            formattedEnd = String.valueOf(year + 1);
        }
        // day는 yyyyMMdd 형태로 BETWEEN 포함 처리되므로 변경 불필요

        List<Object[]> rawResults = orderDetailRepository.getSalesGroupByUnit(formattedStart, formattedEnd, unit);
        Map<String, Long> resultMap = new LinkedHashMap<>();

        for (Object[] row : rawResults) {
            String label = (String) row[0];
            if (unit.equals("year")) {
                label = label.substring(0, 4); // 연도만 표시
            } else if (unit.equals("month")) {
                label = label.substring(0, 4) + "년 " + label.substring(4, 6) + "월"; // YYYYMM → YYYY년 MM월
            }
            Long value = (row[1] != null) ? ((Number) row[1]).longValue() : 0L;
            resultMap.put(label, value);
        }

        return resultMap;
    }
}
