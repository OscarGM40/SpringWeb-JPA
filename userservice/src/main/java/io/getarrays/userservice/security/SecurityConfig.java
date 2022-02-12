package io.getarrays.userservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.getarrays.userservice.filters.CustomAuthenticationFilter;
import io.getarrays.userservice.filters.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;

/* para sobreescribir la seguridad por defecto con la mia anoto una clase con los decoradores necesarios,la hago heredar de la clase abstracta core WebSecurityConfigurerAdapter y sobreescribo sus métodos configure */

@Configuration  @EnableWebSecurity @RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  /* beans core que tengo que proporcionar a Spring*/
  private final UserDetailsService userDetailsService;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
  
  /* autenticacion */  
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    
    auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder); 
  }

  /* autorizacion */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
    customAuthenticationFilter.setFilterProcessesUrl("/api/login");

    /* deshabilito la CRSF para pasar de STATEFUL con cookies a  STATELESS con tokens(usaremos JWT) */
    http.csrf().disable();
    /* pasamos a stateless */ 
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    
    /* permito todo temporalmente */
    // http.authorizeRequests().anyRequest().permitAll();

    /* todos pueden acceder al login,solo los ROLE_USER pueden ver /api/user y solo los ADMIN pueden crear por POST.Además,cada request debe estar autenticado */
    http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/login").permitAll();
    http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/user/**").hasAnyAuthority("ROLE_USER");
    http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/user/save/**").hasAnyAuthority("ROLE_ADMIN");
    http.authorizeRequests().anyRequest().authenticated();

    /* aqui le pasaré mis filtros que debo crear */
    http.addFilter(customAuthenticationFilter);
    http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    
  }

  


  
}
