package com.gym.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class FeignClientInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String header = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
            .getHeader("Authorization");
        requestTemplate.header("Authorization", header);
    }
}
