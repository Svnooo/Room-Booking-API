package com.mayora.meetingroom.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {

    @Value("${app.api-key}")
    private String expectedApiKey;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        // Bypass check for CORS preflight requests
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        String path = request.getRequestURI();

        // Enforce API Key validation for all public endpoints under /api/public/
        // if (path.startsWith("/api/public")) {
        //     String apiKeyHeader = request.getHeader("X-API-KEY");

        //     if (apiKeyHeader == null || !apiKeyHeader.equals(expectedApiKey)) {
        //         response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        //         response.setContentType("application/json");
        //         response.setCharacterEncoding("UTF-8");
        //         response.getWriter().write("{\"message\": \"Invalid or missing API Key\", \"status\": 401}");
        //         return;
        //     }
        // }

        filterChain.doFilter(request, response);
    }
}
