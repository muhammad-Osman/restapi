package com.self.assessment.utils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class LoggingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // Log incoming request
        String requestData = new String(request.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        log.info("REQUEST: {} {}", request.getMethod(), requestData);

        // Wrap response to capture output
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        filterChain.doFilter(request, responseWrapper);

        // Log outgoing response
        String responseData = new String(responseWrapper.getContentAsByteArray(), StandardCharsets.UTF_8);
        log.info("RESPONSE: {}", responseData);

        responseWrapper.copyBodyToResponse();
    }
}
