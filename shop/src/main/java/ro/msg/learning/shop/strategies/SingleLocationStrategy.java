package ro.msg.learning.shop.strategies;

import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.model.OrderDetail;
import ro.msg.learning.shop.model.ProductAllocation;
import ro.msg.learning.shop.model.Stock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SingleLocationStrategy implements LocationSelectionStrategy {

    @Override
    public List<ProductAllocation> selectLocations(List<OrderDetail> orderDetails, List<Stock> stocks) {
        Map<Location, List<Stock>> locationStocksMap = stocks.stream()
                .collect(Collectors.groupingBy(Stock::getLocation));

        List<ProductAllocation> allocations = new ArrayList<>();

        for (OrderDetail orderDetail : orderDetails) {
            List<Stock> productStocks = locationStocksMap.values().stream()
                    .flatMap(List::stream)
                    .filter(stock -> stock.getProduct().getProductId().equals(orderDetail.getProduct().getProductId()))
                    .collect(Collectors.toList());

            if (productStocks.isEmpty()) {
                throw new RuntimeException("No stock available for product " + orderDetail.getProduct().getProductId());
            }

            int quantityRemaining = orderDetail.getQuantity();

            for (Stock stock : productStocks) {
                if (quantityRemaining <= 0) {
                    break;
                }

                int allocatedQuantity = Math.min(stock.getQuantity(), quantityRemaining);
                if (allocatedQuantity > 0) {
                    quantityRemaining -= allocatedQuantity;

                    allocations.add(new ProductAllocation(
                            stock.getLocation(),
                            orderDetail.getProduct().getProductId(),
                            allocatedQuantity
                    ));
                }
            }

            if (quantityRemaining > 0) {
                throw new RuntimeException("Insufficient stock for product " + orderDetail.getProduct().getProductId());
            }
        }

        return allocations;
    }

}
