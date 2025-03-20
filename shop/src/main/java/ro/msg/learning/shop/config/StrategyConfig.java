package ro.msg.learning.shop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.msg.learning.shop.strategies.LocationSelectionStrategy;
import ro.msg.learning.shop.strategies.MostAbundantStrategy;
import ro.msg.learning.shop.strategies.SingleLocationStrategy;

@Configuration
public class StrategyConfig {

    @Value("${shop.strategy.location-selection}")
    private String strategyName;

    @Bean
    public LocationSelectionStrategy locationSelectionStrategy() {
        return switch (strategyName.toLowerCase()) {
            case "most-abundant" -> new MostAbundantStrategy();
            case "single-location" -> new SingleLocationStrategy();
            default -> throw new IllegalArgumentException("Invalid strategy name: " + strategyName);
        };
    }
}
