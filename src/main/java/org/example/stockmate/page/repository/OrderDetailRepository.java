package org.example.stockmate.page.repository;

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


}
