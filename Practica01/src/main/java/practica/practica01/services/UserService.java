package practica.practica01.services;

import java.util.List;

import practica.practica01.models.Role;
import practica.practica01.models.User;

public interface UserService {
 User saveUser(User user);
 Role saveRole(Role role);

  void addRoleToUser(String username,String roleName);

  User getUser(String username);
  List<User> getUsers();
}
