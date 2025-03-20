package ro.msg.learning.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {
    private Long productId;
    private String productName;
    private String productDescription;
    private BigDecimal price;
    private Double weight;
    private String imageUrl;
    private Long supplierId;
    private Long categoryId;
    private String categoryName;
    private String categoryDescription;
}
