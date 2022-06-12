package practica.practica01.filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter{
  

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    /* si estamos en el login no quiero que procese nada el middleware */
    if (request.getServletPath().equals("/api/login") || request.getServletPath().equals("/api/token/refresh")) {
      filterChain.doFilter(request, response);
      return;
    } else {
      String authorizationHeader = request.getHeader("Authorization");
      /* compruebo que no sea null y que empiece por Bearer */
      if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
        try {
          String token = authorizationHeader.substring("Bearer ".length()); // podia poner 7 también
          /* la semilla obviamente debe ser la misma al decodificar */
          Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
          /* decodifico el token */
          JWTVerifier verifier = JWT.require(algorithm).build();
          /* verifico el token */
          DecodedJWT decodedJWT = verifier.verify(token);
          /* obtengo el username del token */
          String username = decodedJWT.getSubject();
          /* obtengo los roles del token */
          String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
          Collection<SimpleGrantedAuthority> authorities = Arrays.stream(roles).map(SimpleGrantedAuthority::new)
              .collect(Collectors.toList());
          authorities.stream().forEach(e -> log.info("Authority is: {}", e.getAuthority()));
          /* creo un objeto Authentication con el username y password */
          UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
              null, authorities);
          /* importante,hay que pasarlo a Spring Security */
          SecurityContextHolder.getContext().setAuthentication(authenticationToken);
          filterChain.doFilter(request, response); // similar a next()
        } catch (Exception e) {
          log.error("Error logging in: {}", e.getMessage());
          response.setHeader("error", e.getMessage());
          /* mando una reponse por el body */
          Map<String, String> errorInBody = new HashMap<>();
          errorInBody.put("ok", "false");
          errorInBody.put("error", e.getMessage());
          /* tengo que especificar el tipo de contenido */
          response.setContentType("application/json");
          new ObjectMapper().writeValue(response.getOutputStream(), errorInBody);
        }
      } else {
        filterChain.doFilter(request, response); // similar a next()
      }
    }
  }
}
