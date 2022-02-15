package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

import com.example.demo.models.AppUser;

@Transactional
@Repository
public interface AppUserRepository extends JpaRepository<AppUser,Long>{
  
  Optional<AppUser> findByEmail(String email);

}
