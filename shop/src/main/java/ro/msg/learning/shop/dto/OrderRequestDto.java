package ro.msg.learning.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto  {
    private LocalDateTime orderTimestamp;
    private Long addressId;
    private Long locationId;
    private List<ProductOrderDto> products;
}