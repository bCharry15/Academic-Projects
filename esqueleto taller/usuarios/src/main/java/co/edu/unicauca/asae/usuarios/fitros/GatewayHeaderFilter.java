package co.edu.unicauca.asae.usuarios.fitros;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class GatewayHeaderFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("X-Gateway-Passed");

        if (header == null || !header.equals("true")) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN,
                    "Acceso denegado desde el filtro. Debe pasar por el API Gateway.");
            return;
        }

        filterChain.doFilter(request, response);
    }
}