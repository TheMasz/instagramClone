package com.clone.instagram.backend.config.token;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.clone.instagram.backend.service.TokenService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TokenFilter extends GenericFilter {
    private final TokenService  tokenService;

    public TokenFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String authorization = request.getHeader("Authorization");

        if (ObjectUtils.isEmpty(authorization)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (!authorization.startsWith("Bearer")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String token = authorization.substring(7);
        DecodedJWT decoded = tokenService.verify(token);

        if (decoded==null) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String principal = decoded.getClaim("principal").asString();
        String role = decoded.getClaim("role").asString();

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principal, "(protected)", authorities);

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
