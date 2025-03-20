package ro.msg.learning.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class OrderResponseDto {
    private Long orderId;
    private LocalDateTime createdAt;
    private Long addressId;
    private List<OrderDetailDto> orderDetails;
}