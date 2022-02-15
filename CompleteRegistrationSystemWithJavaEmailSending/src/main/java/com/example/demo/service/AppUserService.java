package com.example.demo.service;

import com.example.demo.repository.AppUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

  @Autowired
  private AppUserRepository appUserRepository;
  private final static String EMAIL_NOT_FOUND="User with this email (%s) not found in our database.Consider registering first";
  
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return appUserRepository
      .findByEmail(email)
      .orElseThrow(() -> new UsernameNotFoundException(
        String.format(EMAIL_NOT_FOUND, email)));
  }
  
}
