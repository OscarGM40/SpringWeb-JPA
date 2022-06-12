package practica.practica01.filters;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomAuthenticationFilter  extends UsernamePasswordAuthenticationFilter{
  
  private final AuthenticationManager authenticationManager;

  public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

    
  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
      throws AuthenticationException {
        /* accedo al name y password y los imprimo*/
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        log.info("Name is: {}", name);
        log.info("Password is: {}", password);
        /* creo un objeto Authentication con el username y password */
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(name, password);
        /* llamo al authenticationManager para que me autentique */
        return authenticationManager.authenticate(token);
  }


  /* cuando sea una authentication exitosa este método se ejecutará y es aqui donde debo mandarle el jwt */  
  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
      Authentication authentication) throws IOException, ServletException {
        /* necesito acceder al user que se acaba de logear exitosamente.User es de tipo UserDetails,ojo */
        User user = (User)authentication.getPrincipal();  
        
        /* obviamente no debo pasar la semilla en duro */
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());      
        
        /* creo el access_jwt de 10m */
        String access_token = JWT.create()
        /* los subjects son Strings que crearan el payload,con el name nos vale para este ejemplo tan sencillo */
            .withSubject(user.getUsername())
            .withExpiresAt(new Date(System.currentTimeMillis() + 10*60*1000)) // 10 minutos
            /* issuer es el emisor de este token,le pasaremos el dominio donde viene la request */
            .withIssuer(request.getRequestURL().toString())
            /* claims son una serie de reglas */
            .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
            .sign(algorithm);
        
            /* creo el refresh_jwt de 30m */
        String refresh_token = JWT.create()
        /* los subjects son Strings que crearan el payload,con el name nos vale para este ejemplo tan sencillo */
            .withSubject(user.getUsername())
            .withExpiresAt(new Date(System.currentTimeMillis() + 30*60*1000)) // 10 minutos
            /* issuer es el emisor de este token,le pasaremos el dominio donde viene la request */
            .withIssuer(request.getRequestURL().toString())
            /* claims son una serie de reglas,el refresh no va a llevar */
            .sign(algorithm);

        /* mando el jwt al response */
        response.setHeader("acces_token", access_token);
        response.setHeader("refresh_token", refresh_token);
        
        /* mando una reponse por el body */
        Map<String, String> tokensInBody = new HashMap<>();
        tokensInBody.put("ok", "true");
        tokensInBody.put("access_token", access_token);
        tokensInBody.put("refresh_token", refresh_token);
        /* tengo que especificar el tipo de contenido */
        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getOutputStream(), tokensInBody);
  }

}
