package ro.msg.learning.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import ro.msg.learning.shop.model.*;
import ro.msg.learning.shop.repository.OrderDetailRepository;
import ro.msg.learning.shop.repository.PlacedOrderRepository;
import ro.msg.learning.shop.repository.StockRepository;
import ro.msg.learning.shop.strategies.LocationSelectionStrategy;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final StockRepository stockRepository;
    private final PlacedOrderRepository placedOrderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final LocationSelectionStrategy locationSelectionStrategy;

    public PlacedOrder createOrder(PlacedOrder order) {
        List<Stock> eligibleStocks = stockRepository.findStocksForOrder(
                order.getOrderDetails().stream()
                        .map(detail -> detail.getProduct().getProductId())
                        .toList()
        );

        List<ProductAllocation> allocations = locationSelectionStrategy.selectLocations(order.getOrderDetails(), eligibleStocks);

        allocations.forEach(allocation -> {
            Stock stock = eligibleStocks.stream()
                    .filter(s -> s.getProduct().getProductId().equals(allocation.getProductId()) &&
                            s.getLocation().equals(allocation.getLocation()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Stock not found for product " + allocation.getProductId() +
                            " in location " + allocation.getLocation().getLocationId()));

            if (stock.getQuantity() < allocation.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product " + allocation.getProductId() +
                        " in location " + allocation.getLocation().getLocationId());
            }

            stock.setQuantity(stock.getQuantity() - allocation.getQuantity());
            stockRepository.save(stock);
        });

        order.getOrderDetails().forEach(orderDetail -> allocations.stream()
                .filter(allocation -> allocation.getProductId().equals(orderDetail.getProduct().getProductId()))
                .findFirst()
                .ifPresent(allocation -> orderDetail.setShippedFrom(allocation.getLocation())));

        placedOrderRepository.save(order);

        order.getOrderDetails().forEach(detail -> {
            OrderDetailId orderDetailId = new OrderDetailId();
            orderDetailId.setOrderId(order.getOrderId());
            orderDetailId.setProductId(detail.getProduct().getProductId());
            detail.setOrderDetailId(orderDetailId);
        });

        orderDetailRepository.saveAll(order.getOrderDetails());

        return order;
    }

}
