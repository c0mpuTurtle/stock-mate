package org.example.stockmate.page.service;

import lombok.RequiredArgsConstructor;
import org.example.stockmate.page.repository.OrderDetailRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.*;

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


        public List<Map<String, Object>> getProductSalesSummary(String start, String end, String sortOption) {
            String formattedStart = start.replace("-", "");
            String formattedEnd = end.replace("-", "");

            List<Object[]> rows;

            // ⬇️ 정렬 옵션에 따라 분기
            if ("total_asc".equals(sortOption)) {
                rows = orderDetailRepository.getProductSalesSummaryAsc(formattedStart, formattedEnd);
            } else {
                rows = orderDetailRepository.getProductSalesSummaryDesc(formattedStart, formattedEnd);
            }

            List<Map<String, Object>> result = new ArrayList<>();
            for (Object[] row : rows) {
                Map<String, Object> map = new HashMap<>();
                map.put("name", row[0]);
                map.put("category", row[1]);
                map.put("subCategory", row[2]);
                map.put("price", row[3]);
                map.put("count", row[4]);
                map.put("total", row[5]);
                result.add(map);
            }
            return result;
        }

    public List<Map<String, Object>> getProductSalesSummaryByCount(String start, String end, String sortOption) {
        String formattedStart = start.replace("-", "");
        String formattedEnd = end.replace("-", "");

        List<Object[]> rows = "count_asc".equals(sortOption)
                ? orderDetailRepository.getProductSalesSummaryByCountAsc(formattedStart, formattedEnd)
                : orderDetailRepository.getProductSalesSummaryByCountDesc(formattedStart, formattedEnd);

        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] row : rows) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", row[0]);
            map.put("category", row[1]);
            map.put("subCategory", row[2]);
            map.put("price", row[3]);
            map.put("count", row[4]);
            map.put("total", row[5]);
            result.add(map);
        }
        return result;
    }


}

