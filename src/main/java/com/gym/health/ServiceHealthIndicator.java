package com.gym.health;

import java.util.List;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component("ServiceHealthIndicator")
public class ServiceHealthIndicator implements HealthIndicator {
    private final RestTemplate restTemplate = new RestTemplate();
    private static final List<String> SERVICE_URLS = List.of(
        "http://localhost:8080/customer/Test.Customer?loginUserName=admin.admin&loginPassword=admin"
        //can add more urls to check
    );

    @Override
    public Health health() {
        for (String serviceUrl : SERVICE_URLS) {
            try {
                restTemplate.getForObject(serviceUrl, String.class);
            } catch (Exception e) {
                return Health.down().withDetail("Error", "Cannot reach " + serviceUrl).build();
            }
        }
        return Health.up().build();
    }
}
