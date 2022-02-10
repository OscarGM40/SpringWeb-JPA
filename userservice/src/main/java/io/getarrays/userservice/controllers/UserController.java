package io.getarrays.userservice.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.getarrays.userservice.models.Role;
import io.getarrays.userservice.models.User;
import io.getarrays.userservice.services.UserServiceImpl;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
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
    /*
     * el autor prefiere crear el URI,con la ruta del controlador actual,pero no es
     * necesario
     */
    URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());

    return ResponseEntity
        // .status(HttpStatus.CREATED) <- al usar created() ya no uso status()
        .created(uri)
        .body(userServiceImpl.saveUser(user));
  }

  @PostMapping("/role/save")
  public ResponseEntity<Role> saveRole(@RequestBody Role role) {
    URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
    return ResponseEntity
        // .status(HttpStatus.CREATED)
        .created(uri)
        .body(userServiceImpl.saveRole(role));
  }

  @PostMapping("/role/addtouser")
  public ResponseEntity<?> saveRoleToUser(@RequestBody RoleToUserForm form) {

    userServiceImpl.addRoleToUser(form.getUsername(), form.getRoleName());

    return ResponseEntity
        .ok()
        .build(); // si la response va vacia(como es este caso) hay que llamar a build() para que
                  // construya la response(fijate que llamando a body() como arriba se construye
                  // sola)
  }

  

}

/*
 * fijate como se crea clases para tipar la @RequestBody.Muy ingenioso
 */
@Data
class RoleToUserForm {
  private String username;
  private String roleName;
}
