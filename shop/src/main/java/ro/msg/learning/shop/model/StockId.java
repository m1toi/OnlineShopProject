package ro.msg.learning.shop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class StockId implements Serializable {

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "location_id")
    private Long locationId;
}
