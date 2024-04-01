package com.gym.healthIndicator;

import java.sql.Connection;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component("DatabaseConnectionHealthIndicator")
@RequiredArgsConstructor
public class DatabaseConnectionHealthIndicator implements HealthIndicator {
    private final DataSource dataSource;

    @Override
    @SneakyThrows
    public Health health() {
        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(1000)) {
                return Health.up().build();
            } else {
                return Health.down().withDetail("Error", "Invalid Connection").build();
            }
        }
    }
}
