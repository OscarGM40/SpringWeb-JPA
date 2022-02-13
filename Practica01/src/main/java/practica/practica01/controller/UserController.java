package practica.practica01.controller;

import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import practica.practica01.models.Role;
import practica.practica01.models.User;
import practica.practica01.services.UserServiceImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class UserController {

  @Autowired
  private final UserServiceImpl userServiceImpl;
  
  @GetMapping("/users")
  public ResponseEntity<List<User>> getUsers() {
    return ResponseEntity
        .ok()
        .body(userServiceImpl.getUsers());
  }

  @PostMapping("/user/save")
  public ResponseEntity<User> saveUser(@RequestBody User user) {
    URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());

    return ResponseEntity
        .created(uri)
        .body(userServiceImpl.saveUser(user));
  }

  @PostMapping("/role/save")
  public ResponseEntity<Role> saveRole(@RequestBody Role role) {
    URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
    return ResponseEntity
        .created(uri)
        .body(userServiceImpl.saveRole(role));
  }

  @PostMapping("/role/addtouser")
  public ResponseEntity<?> saveRoleToUser(@RequestBody RoleToUserForm form) {

    userServiceImpl.addRoleToUser(form.getUsername(), form.getRoleName());

    return ResponseEntity
        .ok()
        .build(); 
  }

  @GetMapping("/token/refresh")
  public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

    String authorizationHeader = request.getHeader("Authorization");
    log.info("authorizationHeader: {}", authorizationHeader);
    
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      try {
        String refresh_token = authorizationHeader.substring("Bearer ".length());

        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        /* decodifico el token */
        JWTVerifier verifier = JWT.require(algorithm).build();
        /* verifico el token */
        DecodedJWT decodedJWT = verifier.verify(refresh_token);
        /* obtengo el username del token,los roles me da igual */
        String username = decodedJWT.getSubject();
        /* esta vez hay que buscar si existe */
        User user = userServiceImpl.getUser(username);

        /* obviamente habria que crear una utility class */
        String access_token = JWT.create()
            .withSubject(user.getName())
            .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000)) // 10 minutos
            .withIssuer(request.getRequestURL().toString())
            /* claims son una serie de reglas */
            .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
            .sign(algorithm);

        Map<String, String> tokensInBody = new HashMap<>();
        tokensInBody.put("ok", "true");
        tokensInBody.put("access_token", access_token);
        tokensInBody.put("refresh_token", refresh_token);
        /* tengo que especificar el tipo de contenido */
        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getOutputStream(), tokensInBody);

      } catch (Exception e) {
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
      throw new RuntimeException("Refresh token is missing");
    }
  }

}
/* ojo que la anoto con @Data para usar su AllConstructor */
@Data
class RoleToUserForm {
  private String username;
  private String roleName;
}