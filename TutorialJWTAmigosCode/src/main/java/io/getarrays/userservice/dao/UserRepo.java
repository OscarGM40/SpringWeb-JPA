package io.getarrays.userservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.getarrays.userservice.models.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
  
  User findByUsername(String username);
  
}
  
