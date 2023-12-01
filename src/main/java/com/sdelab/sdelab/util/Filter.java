package com.sdelab.sdelab.util;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class Filter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Generate or extract the correlation ID
        String correlationId = Span.current().getSpanContext().getTraceId();
        response.setHeader("X-Correlation-ID",correlationId);
        // Set the correlation ID in the request context for downstream components
        request.setAttribute("correlationId", correlationId);
        // Continue the request chain
        filterChain.doFilter(request, response);
    }
}
