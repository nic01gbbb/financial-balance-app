package ApplicationBalance.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class JwtTokenFilter extends OncePerRequestFilter {


    @Autowired
    private final JwtService jwtService;

    public JwtTokenFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();


        String[] alloweds = {"/auth/login", "/auth/register"};

        if (Arrays.stream(alloweds).anyMatch(path::startsWith)) {
            System.out.println(path);
            System.out.println("path allowed");
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer")) {
            System.out.println(path);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Token inválido ou ausente\"}");
            return;
        }

        try {
            String token = authorizationHeader.substring(7);
            Claims claims = jwtService.parseClaimsJws(token);
            String username = claims.getSubject();
            String roles = (String) claims.get("role");


            if (roles.equals("ROLE_USER")) {
                System.out.println("Regular user trying accesses!");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"Accesses declined by regular users.\"}");
                return;
            }
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            Arrays.stream(roles.split(",")).forEach(role ->
                    authorities.add(new SimpleGrantedAuthority(role)));

            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (JwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Token inválido\"}");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
