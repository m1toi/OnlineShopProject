package ro.msg.learning.shop.controller;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.msg.learning.shop.dto.OrderDetailDto;
import ro.msg.learning.shop.dto.OrderRequestDto;
import ro.msg.learning.shop.dto.OrderResponseDto;
import ro.msg.learning.shop.mapper.OrderMapper;
import ro.msg.learning.shop.model.PlacedOrder;
import ro.msg.learning.shop.service.OrderService;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto orderRequest) {
        PlacedOrder orderEntity = OrderMapper.toPlacedOrder(orderRequest);

        PlacedOrder createdOrder = orderService.createOrder(orderEntity);

        OrderResponseDto responseDto = OrderMapper.toOrderResponseDto(createdOrder);

        return ResponseEntity.ok(responseDto);
    }

}
