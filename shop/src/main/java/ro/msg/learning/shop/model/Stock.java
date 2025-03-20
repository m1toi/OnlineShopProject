package ro.msg.learning.shop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "stock")
public class Stock {

    @EmbeddedId
    private StockId stockId;

    @ManyToOne
    @MapsId("product_id")
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product product;

    @ManyToOne
    @MapsId("location_id")
    @JoinColumn(name = "location_id", referencedColumnName = "location_id")
    private Location location;

    private Integer quantity;

}
