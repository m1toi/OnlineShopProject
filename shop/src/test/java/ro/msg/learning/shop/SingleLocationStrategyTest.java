package ro.msg.learning.shop;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.model.OrderDetail;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.model.ProductAllocation;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.strategies.SingleLocationStrategy;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SingleLocationStrategyTest {

    @Mock
    private Location location1;

    @Mock
    private Location location2;

    @Mock
    private Product product;

    @Mock
    private OrderDetail orderDetail;

    @Mock
    private Stock stock1;

    @Mock
    private Stock stock2;

    @InjectMocks
    private SingleLocationStrategy strategy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(orderDetail.getProduct()).thenReturn(product);
        when(orderDetail.getQuantity()).thenReturn(10);
        when(product.getProductId()).thenReturn(1L);

        when(stock1.getProduct()).thenReturn(product);
        when(stock1.getQuantity()).thenReturn(15);
        when(stock1.getLocation()).thenReturn(location1);

        when(stock2.getProduct()).thenReturn(product);
        when(stock2.getQuantity()).thenReturn(5);
        when(stock2.getLocation()).thenReturn(location2);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(location1, location2, product, orderDetail, stock1, stock2);
    }

    @Test
    void testSelectLocations_AllocatesStockFromSingleLocation() {
        List<OrderDetail> orderDetails = Collections.singletonList(orderDetail);
        List<Stock> stocks = Arrays.asList(stock1, stock2);

        List<ProductAllocation> result = strategy.selectLocations(orderDetails, stocks);

        assertEquals(1, result.size());
        ProductAllocation allocation = result.get(0);

        assertEquals(location1, allocation.getLocation());
        assertEquals(1L, allocation.getProductId());
        assertEquals(10, allocation.getQuantity());
    }
}
