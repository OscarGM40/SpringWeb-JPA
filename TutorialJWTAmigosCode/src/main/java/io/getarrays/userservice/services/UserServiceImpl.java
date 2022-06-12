package io.getarrays.userservice.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.getarrays.userservice.dao.RoleRepo;
import io.getarrays.userservice.dao.UserRepo;
import io.getarrays.userservice.models.Role;
import io.getarrays.userservice.models.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service @RequiredArgsConstructor
@Transactional @Slf4j
public class UserServiceImpl implements UserService,UserDetailsService{

  private final UserRepo userRepo;
  private final RoleRepo roleRepo;
  private final BCryptPasswordEncoder passwordEncoder;
   
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepo.findByUsername(username);
    if(user == null){
      log.error("User "+user+" not found inthe database");
      throw new UsernameNotFoundException("User "+user+" not found inthe database");
    }else{
      log.info("User found in the database: {}", user);
    }
    /* tengo que devolver un UserDetails fijate */
    /* para diferenciarlo de un User mio y que resalte suelen escribir el full qualifier name (new org.springframework.security.core.userdetails.User ) o me aseguro que el import es correcto */

    /* Tengo que recorrer cada usuario añadiendole los roles que tenga */
    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
   
    user.getRoles().forEach(role -> {
      authorities.add(new SimpleGrantedAuthority(role.getName()));
    });
    /* fijate que el UserDetails necesitará los authorities */
    return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
  }


  @Override
  public User saveUser(User user) {
    log.info("Saving new user: " + user.getUsername());
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepo.save(user);
  }

  @Override
  public Role saveRole(Role role) {
    log.info("Saving new role {}", role.getName());
    return roleRepo.save(role);
  }

  @Override
  public void addRoleToUser(String username, String roleName) {
    log.info("Adding role {} to user {}",roleName,username);
    User user = userRepo.findByUsername(username);
    Role role = roleRepo.findByName(roleName);
    user.getRoles().add(role);
  }

  @Override
  public User getUser(String username) {
    log.info("Fetching user {}",username);
    return userRepo.findByUsername(username);
  }

  @Override
  public List<User> getUsers() {
    log.info("Fetching all users");
    return userRepo.findAll();
  }

  
}
