package org.example.springmicroserviceshandson.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.example.springmicroserviceshandson.services.AuthenticationService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final AuthenticationService authenticationService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String jwt = extractToken(request);

        if (jwt != null && isSecurityContextEmpty()) {
            processTokenAuthentication(jwt, request);
        }

        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader(AUTHORIZATION_HEADER);
        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            return null;
        }
        return authHeader.substring(BEARER_PREFIX.length());
    }

    private void processTokenAuthentication(String jwt, HttpServletRequest request) {
        try {
            UserDetails userDetails = authenticationService.validateToken(jwt);

            if (userDetails != null) {
                UsernamePasswordAuthenticationToken authToken = createAuthToken(userDetails, request);
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

            if (userDetails instanceof BlogUserDetails)
                request.setAttribute("userId",((BlogUserDetails)userDetails).getId());

        } catch (Exception e) {
            logger.error("Could not set user authentication", e);
        }
    }

    private UsernamePasswordAuthenticationToken createAuthToken(UserDetails userDetails, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return authToken;
    }

    private boolean isSecurityContextEmpty() {
        return SecurityContextHolder.getContext().getAuthentication() == null;
    }

}