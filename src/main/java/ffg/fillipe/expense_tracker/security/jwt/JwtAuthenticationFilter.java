package ffg.fillipe.expense_tracker.security.jwt;

import ffg.fillipe.expense_tracker.exceptions.auth.TokenNotFoundException;
import ffg.fillipe.expense_tracker.security.config.SecurityConfig;
import ffg.fillipe.expense_tracker.security.userdetails.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    JwtTokenService jwtTokenService;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (verifyIfEndpointIsNotPublic(request)) {
            String token = recoveryToken(request);

            if (token == null) {
                throw new TokenNotFoundException();
            }

            String subject = jwtTokenService.retrieveSubject(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(subject);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails.getUsername(), null, userDetails.getAuthorities()
            );

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);
    }

    private String recoveryToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null) {
            return authorizationHeader.substring(7).trim();
        }

        return null;
    }

    private boolean verifyIfEndpointIsNotPublic(HttpServletRequest request) {
        return !Arrays.asList(SecurityConfig.PUBLIC_URLS).contains(request.getRequestURI());
    }
}
