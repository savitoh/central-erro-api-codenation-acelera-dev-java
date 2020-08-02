package com.github.savitoh.centralerroapi.seguranca.filter;

import com.github.savitoh.centralerroapi.seguranca.jwt.TokenManager;
import com.github.savitoh.centralerroapi.seguranca.service.UserPrincipalDetailsService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FiltroJwtAutenticacao extends OncePerRequestFilter {

    private final TokenManager tokenManager;

    private final UserPrincipalDetailsService userPrincipalDetailsService;

    public FiltroJwtAutenticacao(TokenManager tokenManager, UserPrincipalDetailsService userPrincipalDetailsService) {
        this.tokenManager = tokenManager;
        this.userPrincipalDetailsService = userPrincipalDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String jwt = getToken(request);

        if (tokenManager.ehValido(jwt)) {

            Integer userId = tokenManager.getUserId(jwt);
            UserDetails userDetails = userPrincipalDetailsService.loadUserById(userId);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }


    private String getToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer "))
            return bearerToken.substring(7);
        return null;
    }

}
