package ro.msg.learning.shop.mapper;

import ro.msg.learning.shop.dto.OrderDetailDto;
import ro.msg.learning.shop.dto.OrderRequestDto;
import ro.msg.learning.shop.dto.OrderResponseDto;
import ro.msg.learning.shop.model.Address;
import ro.msg.learning.shop.model.OrderDetail;
import ro.msg.learning.shop.model.PlacedOrder;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.model.Location;

import java.util.List;
import java.util.stream.Collectors;


public class OrderMapper {

    public static PlacedOrder toPlacedOrder(OrderRequestDto orderRequest) {
        if (orderRequest == null)
            return null;
        return PlacedOrder.builder()
                .createdAt(orderRequest.getOrderTimestamp())
                .address(Address.builder()
                        .addressId(orderRequest.getAddressId())
                        .build())
                .orderDetails(orderRequest.getProducts().stream().map(productOrderDto -> OrderDetail.builder()
                        .product(Product.builder()
                                .productId(productOrderDto.getProductId())
                                .build())
                        .shippedFrom(Location.builder()
                                .locationId(orderRequest.getLocationId())
                                .build())
                        .quantity(productOrderDto.getQuantity())
                        .build()).collect(Collectors.toList()))
                .build();
    }

    public static OrderResponseDto toOrderResponseDto(PlacedOrder createdOrder) {
        if (createdOrder == null)
            return null;
        return OrderResponseDto.builder()
                .orderId(createdOrder.getOrderId())
                .createdAt(createdOrder.getCreatedAt())
                .addressId(createdOrder.getAddress().getAddressId())
                .orderDetails(createdOrder.getOrderDetails().stream().map(orderDetail -> OrderDetailDto.builder()
                        .productId(orderDetail.getProduct().getProductId())
                        .quantity(orderDetail.getQuantity())
                        .shippedFromLocationId(orderDetail.getShippedFrom().getLocationId())
                        .build()).collect(Collectors.toList()))
                .build();
    }
}
