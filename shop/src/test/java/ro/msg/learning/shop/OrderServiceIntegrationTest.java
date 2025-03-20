package ro.msg.learning.shop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.model.PlacedOrder;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.model.Address;
import ro.msg.learning.shop.model.StockId;
import ro.msg.learning.shop.model.OrderDetail;
import ro.msg.learning.shop.model.OrderDetailId;
import ro.msg.learning.shop.repository.*;
import ro.msg.learning.shop.service.OrderService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class OrderServiceIntegrationTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private PlacedOrderRepository placedOrderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private AddressRepository addressRepository;

    private Location testLocation;
    private Product testProduct;
    private Stock testStock;
    private PlacedOrder testOrder;

    @BeforeEach
    void setUp() {

        Address testAddress = Address.builder()
                .country("Test Country")
                .city("Test City")
                .county("Test County")
                .streetAddress("Test Street")
                .build();
        addressRepository.save(testAddress);

        testLocation = Location.builder()
                .name("Test Location")
                .address(testAddress)
                .build();
        locationRepository.save(testLocation);

        testProduct = Product.builder()
                .name("Test Product")
                .description("A product for testing")
                .price(BigDecimal.valueOf(10.0))
                .weight(1.0)
                .imageUrl("image_url")
                .build();
        productRepository.save(testProduct);

        StockId stockId = StockId.builder()
                .locationId(testLocation.getLocationId())
                .productId(testProduct.getProductId())
                .build();

        testStock = Stock.builder()
                .stockId(stockId)
                .product(testProduct)
                .location(testLocation)
                .quantity(100)
                .build();
        stockRepository.save(testStock);

        testOrder = PlacedOrder.builder()
                .user(null)
                .createdAt(LocalDateTime.now())
                .address(testAddress)
                .orderDetails(Collections.emptyList())
                .build();
    }

    @Test
    void shouldReduceStockAndPersistOrder() {
        OrderDetail orderDetail = new OrderDetail();
        OrderDetailId orderDetailId = new OrderDetailId(testOrder.getOrderId(), testProduct.getProductId());
        orderDetail.setOrderDetailId(orderDetailId);
        orderDetail.setProduct(testProduct);
        orderDetail.setQuantity(10);
        orderDetail.setShippedFrom(testLocation);
        testOrder.setOrderDetails(List.of(orderDetail));

        PlacedOrder createdOrder = orderService.createOrder(testOrder);

        Stock updatedStock = stockRepository.findById(testStock.getStockId()).orElseThrow();
        assertThat(updatedStock.getQuantity()).isEqualTo(90);

        PlacedOrder savedOrder = placedOrderRepository.findById(createdOrder.getOrderId()).orElseThrow();
        assertThat(savedOrder).isNotNull();
        assertThat(savedOrder.getOrderDetails()).hasSize(1);

        OrderDetail savedDetail = savedOrder.getOrderDetails().get(0);
        assertThat(savedDetail.getProduct().getProductId()).isEqualTo(testProduct.getProductId());
        assertThat(savedDetail.getQuantity()).isEqualTo(10);
        assertThat(savedDetail.getShippedFrom()).isEqualTo(testLocation);
    }

    @Test
    void shouldFailDueToInsufficientStock() {
        OrderDetail orderDetail = new OrderDetail();
        OrderDetailId orderDetailId = new OrderDetailId(testOrder.getOrderId(), testProduct.getProductId());
        orderDetail.setOrderDetailId(orderDetailId);
        orderDetail.setProduct(testProduct);
        orderDetail.setQuantity(200);
        orderDetail.setShippedFrom(testLocation);
        testOrder.setOrderDetails(List.of(orderDetail));

        Throwable thrown = catchThrowable(() -> orderService.createOrder(testOrder));

        assertThat(thrown).isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Insufficient stock for product");
    }
}
