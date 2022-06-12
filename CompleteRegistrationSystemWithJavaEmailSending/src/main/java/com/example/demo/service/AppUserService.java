package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.demo.models.AppUser;
import com.example.demo.registration.token.ConfirmationToken;
import com.example.demo.registration.token.ConfirmationTokenService;
import com.example.demo.repository.AppUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

  @Autowired
  private AppUserRepository appUserRepository;
  private final static String EMAIL_NOT_FOUND = "User with this email (%s) not found in our database.Consider registering first";
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final ConfirmationTokenService confirmationTokenService;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return appUserRepository
        .findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException(
            String.format(EMAIL_NOT_FOUND, email)));
  }

  public String signUpUser(AppUser appUser) {
    boolean userExists = appUserRepository.findByEmail(appUser.getEmail()).isPresent();

    if (userExists) {
      // todo if email not confirmed send confirmation email again
      // todo check too if the user is the same
      throw new IllegalStateException("User with this email (" + appUser.getEmail() + ") already exists");
    }
    String encodedPass = bCryptPasswordEncoder.encode(appUser.getPassword());
    appUser.setPassword(encodedPass);
    /* recuerda guardarlo a la DB mediante el repository */
    appUserRepository.save(appUser);

    String token = UUID.randomUUID().toString();

    ConfirmationToken confirmationToken = new ConfirmationToken(
        token,
        LocalDateTime.now(),
        LocalDateTime.now().plusMinutes(15),
        null,
        appUser);

    confirmationTokenService.saveConfirmationToken(confirmationToken);
    // TODO send email
    return token;
  }

  public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
    }

}
