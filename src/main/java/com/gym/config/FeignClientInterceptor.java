package com.gym.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class FeignClientInterceptor implements RequestInterceptor {
    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String header = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
            .getHeader(AUTHORIZATION_HEADER);
        requestTemplate.header(AUTHORIZATION_HEADER, header);
    }
}
