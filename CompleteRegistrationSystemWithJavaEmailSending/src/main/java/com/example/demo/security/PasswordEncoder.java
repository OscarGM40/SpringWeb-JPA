package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/* fijate que tuvimos que anotarla con configuration,no sé si era obligatorio o fue simplemente por el Bean */
@Configuration
public class PasswordEncoder {

  /* fijate que @Bean es sólo para el interior de las clases,en este caso un método */
  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
    
}
