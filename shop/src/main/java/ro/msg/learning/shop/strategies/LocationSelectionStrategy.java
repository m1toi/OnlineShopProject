package ro.msg.learning.shop.strategies;

import ro.msg.learning.shop.model.OrderDetail;
import ro.msg.learning.shop.model.ProductAllocation;
import ro.msg.learning.shop.model.Stock;

import java.util.List;

public interface LocationSelectionStrategy {
    List<ProductAllocation> selectLocations(List<OrderDetail> orderDetails, List<Stock> availableStocks);
}
