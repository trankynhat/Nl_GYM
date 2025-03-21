package com.example.NL_Gym.filter;

import com.example.NL_Gym.utils.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtAuthenticationFilter(JWTUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println(">>> JwtAuthenticationFilter: Checking token for request: " + request.getRequestURI());
        // Bỏ qua kiểm tra token nếu request là /auth/login
        if (request.getRequestURI().equals("/auth/login")) {
            System.out.println(">>> JwtAuthenticationFilter: Skipping token check for /auth/login");
            filterChain.doFilter(request, response);
            return;
        }
        String token = getJwtFromRequest(request);
        if (token != null && jwtUtil.validateToken(token)) {
            String email = jwtUtil.getUsernameFromToken(token);
            System.out.println(">>> JwtAuthenticationFilter: Token valid for user: " + email);

            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            System.out.println(">>> JwtAuthenticationFilter: User roles: " + userDetails.getAuthorities());
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            System.out.println(">>> JwtAuthenticationFilter: No valid token found.");
        }

        filterChain.doFilter(request, response);
    }
}
