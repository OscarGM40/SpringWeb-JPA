package io.getarrays.userservice.services;

import java.util.List;


import io.getarrays.userservice.models.Role;
import io.getarrays.userservice.models.User;

public interface UserService {

  User saveUser(User user);
  Role saveRole(Role role);

  void addRoleToUser(String username,String roleName);

  User getUser(String username);
  
  List<User> getUsers();

  
}
