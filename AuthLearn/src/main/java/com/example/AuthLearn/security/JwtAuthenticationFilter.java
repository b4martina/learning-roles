package com.example.AuthLearn.security;

import ch.qos.logback.core.util.StringUtil;
import com.example.AuthLearn.service.CustomUserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTGenerator jwtGenerator;
    @Autowired
    private CustomUserDetailService customUserDetailService;

    public JwtAuthenticationFilter(JWTGenerator jwtGenerator, CustomUserDetailService customUserDetailService) {
        this.jwtGenerator = jwtGenerator;
        this.customUserDetailService = customUserDetailService;
    }

    private String getJWTFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getJWTFromRequest(request);

        if (StringUtils.hasText(token)) {//
            String username = jwtGenerator.extractUsername(token);


            if (StringUtils.hasText(username) && jwtGenerator.isTokenValid(token, username)) {
                UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,
                        userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            filterChain.doFilter(request, response);
        }
    }


}