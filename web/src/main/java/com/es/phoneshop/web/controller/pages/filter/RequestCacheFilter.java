package com.es.phoneshop.web.controller.pages.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RequestCacheFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (request.getMethod().equalsIgnoreCase("GET") && !request.getRequestURI().endsWith("/login")) {
            if (!request.getRequestURI().endsWith(".js") && !request.getRequestURI().endsWith(".css")) {
                String originalUrl = request.getRequestURI();
                request.getSession().setAttribute("originalUrl", originalUrl);
            }
        }
        filterChain.doFilter(request, response);
    }
}