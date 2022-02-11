package io.getarrays.userservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import io.getarrays.userservice.filters.CustomAuthenticationFilter;
import lombok.RequiredArgsConstructor;

/* para sobreescribir la seguridad por defecto con la mia anoto una clase con los decoradores necesarios,la hago heredar de la clase abstracta core WebSecurityConfigurerAdapter y sobreescribo sus métodos configure */

@Configuration  @EnableWebSecurity @RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  /* beans core que tengo que proporcionar a Spring */
  private final UserDetailsService userDetailsService;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  
  /* autenticacion */  
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
   
    auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder); 
  }

  /* autorizacion */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    /* deshabilito la CRSF para pasar de STATEFUL con cookies a  STATELESS con tokens(usaremos JWT) */
    http.csrf().disable();
    /* pasamos a stateless */ 
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    
    /* permito todo  */
    http.authorizeRequests().anyRequest().permitAll();

    /* aqui le pasaré mis filtros que debo crear */
    http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
    
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
  


  
}
