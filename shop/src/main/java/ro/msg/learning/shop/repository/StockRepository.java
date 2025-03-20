package ro.msg.learning.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.model.StockId;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, StockId> {
    @Query("SELECT s FROM Stock s WHERE s.product.productId IN :productIds")
    List<Stock> findStocksForOrder(@Param("productIds") List<Long> productIds);
}