package com.example.demo.registration;

import com.example.demo.models.AppUser;
import com.example.demo.models.AppUserRole;
import com.example.demo.service.AppUserService;
import com.example.demo.service.EmailValidator;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RegistrationService {

  private final AppUserService appUserService;
  private EmailValidator emailValidator;

  public String register(RegistrationRequest request) {
    boolean isValidEmail = emailValidator.test(request.getEmail());
    if (!isValidEmail) {
      throw new IllegalStateException("Invalid email address");
    }
    return appUserService.signUpUser(
      new AppUser(
        request.getFirstName(),
        request.getLastName(),
        request.getEmail(),
        request.getPassword(), 
        AppUserRole.USER
        )
    );
  }
}
