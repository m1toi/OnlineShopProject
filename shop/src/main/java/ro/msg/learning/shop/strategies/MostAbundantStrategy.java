package ro.msg.learning.shop.strategies;

import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.model.OrderDetail;
import ro.msg.learning.shop.model.ProductAllocation;
import ro.msg.learning.shop.model.Stock;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MostAbundantStrategy implements LocationSelectionStrategy {

    @Override
    public List<ProductAllocation> selectLocations(List<OrderDetail> orderDetails, List<Stock> stocks) {
        List<ProductAllocation> allocations = new ArrayList<>();

        for (OrderDetail orderDetail : orderDetails) {
            Stock chosenStock = stocks.stream()
                    .filter(stock -> stock.getProduct().getProductId().equals(orderDetail.getProduct().getProductId()) &&
                            stock.getQuantity() >= orderDetail.getQuantity())
                    .max(Comparator.comparingInt(Stock::getQuantity))
                    .orElseThrow(() -> new RuntimeException("No stock available for product " + orderDetail.getProduct().getProductId()));

            allocations.add(new ProductAllocation(
                    chosenStock.getLocation(),
                    orderDetail.getProduct().getProductId(),
                    orderDetail.getQuantity()
            ));
        }

        return allocations;
    }

}
