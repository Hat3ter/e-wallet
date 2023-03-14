package com.kuehne.nagel.ewalletapi.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kuehne.nagel.ewalletapi.models.response.ErrorResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenService tokenService;

    private final ObjectMapper mapper;

    /**
     * Auth header
     */
    private static final String TOKEN_HEADER = "Authorization";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        String token = request.getHeader(TOKEN_HEADER);
        try {
            Authentication authentication = tokenService.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (AuthenticationException e) {

            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            ErrorResponse<String> errorResponse = new ErrorResponse<>(e.getMessage(), e.getMessage());
            String errorResponseAsString = mapper.writeValueAsString(errorResponse);
            response.getOutputStream().println(errorResponseAsString);
        }
        filterChain.doFilter(request, response);
    }
}
