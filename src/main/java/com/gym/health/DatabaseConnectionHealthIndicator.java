package com.gym.health;

import com.gym.repository.GymUserRepository;
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
    private final GymUserRepository gymUserRepository;

    @Override
    @SneakyThrows
    public Health health() {
        long numberOfUsers = gymUserRepository.count();
        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(1000)) {
                return Health.up().withDetail("numberOfUsers", numberOfUsers).build();
            } else {
                return Health.down().withDetail("Error", "Invalid Connection").build();
            }
        }
    }
}
