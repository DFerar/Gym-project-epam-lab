package com.gym.service.external;

import com.gym.config.FeignClientInterceptor;
import com.gym.dto.request.instructor.InstructorWorkloadRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "GymMicroserviceApplication", configuration = FeignClientInterceptor.class)
@CircuitBreaker(name = "cb")
public interface GymMicroserviceClient {
    @PostMapping("workload/accept")
    void acceptWorkload(
        @RequestBody InstructorWorkloadRequest instructorWorkloadRequest
    );
}
