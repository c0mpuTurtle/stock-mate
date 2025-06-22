package org.example.stockmate.page.repository;

import org.example.stockmate.page.controller.ProductSalesSummaryDto;
import org.example.stockmate.page.db.OrderDetail;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderDetailRepository extends CrudRepository<OrderDetail, Long> {

    @Query(value = """
        SELECT
            CASE 
                WHEN :unit = 'day' THEN o.date
                WHEN :unit = 'month' THEN LEFT(o.date, 6)
                WHEN :unit = 'year' THEN LEFT(o.date, 4)
            END AS period,
            SUM(p.price) as total
        FROM order_detail od
        JOIN product p ON od.product_id = p.product_id
        JOIN `order` o ON od.order_id = o.order_id
        WHERE o.date BETWEEN :start AND :end
        GROUP BY period
        ORDER BY period
    """, nativeQuery = true)
    List<Object[]> getSalesGroupByUnit(@Param("start") String start,
                                       @Param("end") String end,
                                       @Param("unit") String unit);

    @Query(value = """
        SELECT COALESCE(SUM(p.price), 0)
        FROM order_detail od
        JOIN product p ON od.product_id = p.product_id
        JOIN `order` o ON od.order_id = o.order_id
        WHERE 
            (:type = 'day' AND o.date = :date)
            OR (:type = 'month' AND LEFT(o.date, 6) = :date)
            OR (:type = 'year' AND LEFT(o.date, 4) = :date)
    """, nativeQuery = true)
    Long getTotalSales(@Param("type") String type, @Param("date") String date);

    @Query(value = """
        SELECT 
            p.product_name,
            c.category_name,
            sc.sub_category_name,
            p.price,
            COUNT(*) AS sale_count,
            COUNT(*) * p.price AS total_sales
        FROM order_detail od
        JOIN product p ON od.product_id = p.product_id
        JOIN sub_category sc ON p.sub_category_id = sc.sub_category_id
        JOIN category c ON p.category_id = c.category_id
        JOIN `order` o ON od.order_id = o.order_id
        WHERE o.date BETWEEN :start AND :end
        GROUP BY p.product_id
        ORDER BY total_sales DESC
    """, nativeQuery = true)
    List<Object[]> getProductSalesSummaryDesc(@Param("start") String start, @Param("end") String end);

    @Query(value = """
        SELECT 
            p.product_name,
            c.category_name,
            sc.sub_category_name,
            p.price,
            COUNT(*) AS sale_count,
            COUNT(*) * p.price AS total_sales
        FROM order_detail od
        JOIN product p ON od.product_id = p.product_id
        JOIN sub_category sc ON p.sub_category_id = sc.sub_category_id
        JOIN category c ON p.category_id = c.category_id
        JOIN `order` o ON od.order_id = o.order_id
        WHERE o.date BETWEEN :start AND :end
        GROUP BY p.product_id
        ORDER BY total_sales ASC
    """, nativeQuery = true)
    List<Object[]> getProductSalesSummaryAsc(@Param("start") String start, @Param("end") String end);

    @Query(value = """
    SELECT 
        p.product_name,
        c.category_name,
        sc.sub_category_name,
        p.price,
        COUNT(*) AS sale_count,
        COUNT(*) * p.price AS total_sales
    FROM order_detail od
    JOIN product p ON od.product_id = p.product_id
    JOIN sub_category sc ON p.sub_category_id = sc.sub_category_id
    JOIN category c ON p.category_id = c.category_id
    JOIN `order` o ON od.order_id = o.order_id
    WHERE o.date BETWEEN :start AND :end
    GROUP BY p.product_id
    ORDER BY sale_count ASC
""", nativeQuery = true)
    List<Object[]> getProductSalesSummaryByCountAsc(@Param("start") String start, @Param("end") String end);

    @Query(value = """
    SELECT 
        p.product_name,
        c.category_name,
        sc.sub_category_name,
        p.price,
        COUNT(*) AS sale_count,
        COUNT(*) * p.price AS total_sales
    FROM order_detail od
    JOIN product p ON od.product_id = p.product_id
    JOIN sub_category sc ON p.sub_category_id = sc.sub_category_id
    JOIN category c ON p.category_id = c.category_id
    JOIN `order` o ON od.order_id = o.order_id
    WHERE o.date BETWEEN :start AND :end
    GROUP BY p.product_id
    ORDER BY sale_count DESC
""", nativeQuery = true)
    List<Object[]> getProductSalesSummaryByCountDesc(@Param("start") String start, @Param("end") String end);

}


