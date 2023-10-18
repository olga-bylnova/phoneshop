package com.es.phoneshop.web.controller.pages.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        String originalUrl = (String) request.getSession().getAttribute("originalUrl");
        if (originalUrl != null) {
            response.sendRedirect(originalUrl);
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}