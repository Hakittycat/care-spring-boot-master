package in.joshbetz.careproject.security;

import in.joshbetz.careproject.jwt.JsonWebTokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class TokenAuthenticationFilter extends BasicAuthenticationFilter {

    private static final List<String> DO_NOT_FILTER = Arrays.asList("/login", "/register");
    private final UserDetailsServiceImpl careUserDetailsService;
    private final JsonWebTokenService tokenService;

    public TokenAuthenticationFilter(AuthenticationManager authenticationManager, UserDetailsServiceImpl careUserDetailsService, JsonWebTokenService tokenService) {
        super(authenticationManager);
        this.careUserDetailsService = careUserDetailsService;
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);
            Authentication authentication = createAuthentication(token);
            if(authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return DO_NOT_FILTER.contains(request.getServletPath());
    }

    private Authentication createAuthentication(String token) {
        String username = tokenService.getUsername(token);
        try {
            UserDetails userDetails = careUserDetailsService.loadUserByUsername(username);
            return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        } catch (UsernameNotFoundException e) {
            return null;
        }
    }
}
